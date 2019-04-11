/*
 * Copyright (C) 2019 CERBERUSOS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.cerberusos.den.fragments;

import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.content.res.Resources;
import android.content.FontInfo;
import android.content.IFontService;
import android.content.pm.PackageManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import com.cerberusos.den.BaseSettingsFragment;
import com.cerberusos.den.preference.FontDialogPreference;
import com.cerberusos.den.R;
import com.cerberusos.den.utils.Util;
import android.util.Log;

import android.content.Context;

public class SystemExtensions extends BaseSettingsFragment{

    private static final String PREF_SYSTEM_APP_REMOVER = "system_app_remover";

    private static final String KEY_FONT_PICKER_FRAGMENT_PREF = "custom_font";
    private static final String SUBS_PACKAGE = "projekt.substratum";

    private FontDialogPreference mFontPreference;
    private IFontService mFontService;

    private Context mContext;


    @Override
    protected int getPreferenceResource() {
        return R.xml.system_extensions;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = (Context) getActivity();

        PackageManager pm = getActivity().getPackageManager();


        final Resources res = getActivity().getResources();

        boolean mHallSensor = res.getBoolean(
                com.android.internal.R.bool.config_deviceHasHallSensor);

        boolean mLidSensor = res.getBoolean(
                com.android.internal.R.bool.config_deviceHasLidSensor);

        if( !mHallSensor ) {
            Preference mHallSensorPref = findPreference("cerberus_hall_sensor_enabled");
            mHallSensorPref.setVisible(false);
        }

        if( !mLidSensor ) {
            Preference mLidSensorPref = findPreference("cerberus_lid_sensor_enabled");
            Preference mLidReversePref = findPreference("cerberus_lid_sensor_reverse");
            Preference mLidIgnorePref = findPreference("cerberus_lid_ignore_wake");
            mLidSensorPref.setVisible(false);
            mLidReversePref.setVisible(false);
            mLidIgnorePref.setVisible(false);
        }

        Preference systemAppRemover = findPreference(PREF_SYSTEM_APP_REMOVER);
        Util.requireRoot(getActivity(), systemAppRemover);

        mFontPreference =  (FontDialogPreference) findPreference(KEY_FONT_PICKER_FRAGMENT_PREF);
        mFontService = IFontService.Stub.asInterface(
                 ServiceManager.getService("fontservice"));
        if (!Util.isPackageInstalled(SUBS_PACKAGE,pm)) {
            mFontPreference.setSummary(getCurrentFontInfo().fontName.replace("_", " "));
        } else {
            mFontPreference.setSummary(getActivity().getString(R.string.disable_fonts_installed_title));
        }
    }

    private FontInfo getCurrentFontInfo() {
        try {
            return mFontService.getFontInfo();
        } catch (RemoteException e) {
            return FontInfo.getDefaultFontInfo();
        }
    }
}
