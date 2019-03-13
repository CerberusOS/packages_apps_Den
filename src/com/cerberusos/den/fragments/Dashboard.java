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

import com.plattysoft.leonids.ParticleSystem;

import java.util.Random;

public class Dashboard extends BaseSettingsFragment {

    //private static final String PREF_CERBERUSOS_LOGO = "cerberusos_logo";
    private static final String PREF_CERBERUSOS_OTA = "cerberusos_ota";
    //private static final String PREF_LOG_IT = "log_it";

    private static final String PREF_CERBERUSOS_PARTS = "device_part";
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

        boolean perfProf  = SystemProperties.get("cerberus.eng.perf", "0").equals("1") || 
                            SystemProperties.get("spectrum.support", "0").equals("1");
        boolean thermProf  = SystemProperties.get("cerberus.eng.therm", "0").equals("1");

        Preference profilesCategory = findPreference("app_setings_cat");

        if( !perfProf && !thermProf ) {
            if( profilesCategory != null ) {
                getPreferenceScreen().removePreference(profilesCategory);
            }
        }

        //mCerberusOSLogo = (LongClickablePreference) findPreference(PREF_CERBERUSOS_LOGO);

        mCerberusOSOTA = findPreference(PREF_CERBERUSOS_OTA);
        if (!Util.isPackageEnabled(Constants.CERBERUSOS_OTA_PACKAGE, pm)) {
            mCerberusOSOTA.getParent().removePreference(mCerberusOSOTA);
        }

        // DeviceParts
        mCerberusOSParts = findPreference(PREF_CERBERUSOS_PARTS);
        if (!Util.isPackageEnabled(PREF_CERBERUSOS_PARTS_PACKAGE_NAME, pm)) {
            mCerberusOSParts.getParent().removePreference(mCerberusOSParts);
        }

        //Preference logIt = findPreference(PREF_LOG_IT);
        //Util.requireRoot(getActivity(), logIt);

        /*mCerberusOSLogo.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                int firstRandom = mRandom.nextInt(91 - 0);
                int secondRandom = mRandom.nextInt(181 - 90) + 90;
                int thirdRandom = mRandom.nextInt(181 - 0);

                // Let's color the star randomly
                Drawable star = getResources().getDrawable(R.drawable.star_white_border, null);
                int randomColor;
                randomColor = Color.rgb(
                        Color.red(mRandom.nextInt(0xFFFFFF)),
                        Color.green(mRandom.nextInt(0xFFFFFF)),
                        Color.blue(mRandom.nextInt(0xFFFFFF)));
                star.setTint(randomColor);

                ParticleSystem ps = new ParticleSystem(getActivity(), 100, star, 3000);
                ps.setScaleRange(0.7f, 1.3f);
                ps.setSpeedRange(0.1f, 0.25f);
                ps.setAcceleration(0.0001f, thirdRandom);
                ps.setRotationSpeedRange(firstRandom, secondRandom);
                ps.setFadeOut(200, new AccelerateInterpolator());
                ps.oneShot(getView(), 100);

                mCerberusOSLogo.setLongClickBurst(2000/((++mLogoClickCount)%5+1));
                return true;
            }
        });*/
        /*mCerberusOSLogo.setOnLongClickListener(R.id.logo_view, 1000,
                new Preference.OnPreferenceClickListener() {
                        @Override
                        public boolean onPreferenceClick(Preference preference) {
                            int firstRandom = mRandom.nextInt(91 - 0);
                            int secondRandom = mRandom.nextInt(181 - 90) + 90;
                            int thirdRandom = mRandom.nextInt(181 - 0);

                            Drawable star =
                                    getResources().getDrawable(R.drawable.star_alternative, null);

                            ParticleSystem ps = new ParticleSystem(getActivity(), 100, star, 3000);
                            ps.setScaleRange(0.7f, 1.3f);
                            ps.setSpeedRange(0.1f, 0.25f);
                            ps.setAcceleration(0.0001f, thirdRandom);
                            ps.setRotationSpeedRange(firstRandom, secondRandom);
                            ps.setFadeOut(1000, new AccelerateInterpolator());
                            ps.oneShot(getView(), 100);
                            return true;
                        }
                });*/
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
