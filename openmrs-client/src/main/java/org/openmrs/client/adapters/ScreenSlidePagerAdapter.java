package org.openmrs.client.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import org.openmrs.client.fragments.ModulesFragment;
import org.openmrs.client.models.ModuleInfo;

import java.util.List;

public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

    private static List<ModuleInfo> mModules;
    public static final int ITEMS_PER_PAGE = 4;
    public static final int ITEMS_IN_ROW = 2;

    public ScreenSlidePagerAdapter(FragmentManager fm) {
        super(fm);
        mModules = ModuleInfo.getActiveModules();
    }

    public static List<ModuleInfo>  getPage(int position) {
        int from = position * ITEMS_PER_PAGE;
        int to = Math.min((position + 1) * ITEMS_PER_PAGE, mModules.size());
        Log.d( ScreenSlidePagerAdapter.class.getCanonicalName() ,"getting page from: " + Integer.toString(from) + "to: " + Integer.toString(to) + "page number: " + Integer.toString(position));
        if (!(from < to)) {
            return null;
        }
        return mModules.subList(from, to);
    }

    public static boolean isEmpty() {
        return mModules.isEmpty();
    }

    @Override
    public Fragment getItem(int position) {
        ModulesFragment fragment = ModulesFragment.newInstance(position);
        return fragment;
    }

    @Override
    public int getCount() {
        return Math.max((int) Math.ceil((double) mModules.size() / (double) ITEMS_PER_PAGE), 1);
    }
}
