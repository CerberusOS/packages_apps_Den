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

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cerberusos.den.BaseSettingsFragment;
import com.cerberusos.den.Constants;
import com.cerberusos.den.PreferenceMultiClickHandler;
import com.cerberusos.den.R;
import com.cerberusos.gear.preference.LongClickablePreference;
import com.cerberusos.den.utils.Util;

import android.os.SystemProperties;

import java.util.Random;

public class Dashboard extends BaseSettingsFragment {

    //private static final String PREF_CERBERUSOS_LOGO = "cerberusos_logo";
    private static final String PREF_CERBERUSOS_OTA = "cerberusos_ota";
    //private static final String PREF_LOG_IT = "log_it";

    private static final String PREF_CERBERUSOS_PARTS = "device_part_cat";
    private static final String PREF_CERBERUSOS_PARTS_PACKAGE_NAME = "org.omnirom.device";


    private static final Intent INTENT_OTA = new Intent().setComponent(new ComponentName(
            Constants.CERBERUSOS_OTA_PACKAGE, Constants.CERBERUSOS_OTA_ACTIVITY));

    //private LongClickablePreference mCerberusOSLogo;
    private Preference mCerberusOSOTA;
    private Preference mCerberusOSParts;

    private Random mRandom = new Random();
    private int mLogoClickCount = 0;

    @Override
    protected int getPreferenceResource() {
        return R.xml.dashboard;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PackageManager pm = getActivity().getPackageManager();

        //mCerberusOSLogo = (LongClickablePreference) findPreference(PREF_CERBERUSOS_LOGO);

        mCerberusOSOTA = findPreference(PREF_CERBERUSOS_OTA);
        if (!Util.isPackageEnabled(Constants.CERBERUSOS_OTA_PACKAGE, pm)) {
            mCerberusOSOTA.getParent().removePreference(mCerberusOSOTA);
        }

        // DeviceParts
        mCerberusOSParts = findPreference(PREF_CERBERUSOS_PARTS);
        if (!Util.isPackageEnabled(PREF_CERBERUSOS_PARTS_PACKAGE_NAME, pm) && mCerberusOSParts != null) {
            getPreferenceScreen().removePreference(mCerberusOSParts);
        }

        //Preference logIt = findPreference(PREF_LOG_IT);
        //Util.requireRoot(getActivity(), logIt);

    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        if (preference == mCerberusOSOTA){ // || preference == mCerberusOSLogo) {
            startActivity(INTENT_OTA);
            return true;
        } else {
            return super.onPreferenceTreeClick(preference);
        }
    }
}
