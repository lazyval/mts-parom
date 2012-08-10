package me.levelapp.parom.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioGroup;
import me.levelapp.parom.model.Parom;
import me.levelapp.parom.model.events.ShowActionsEvent;

import java.util.HashMap;

/**
 * User: anatoly
 * Date: 10.06.12
 * Time: 1:47
 */
public class TabBarManager implements RadioGroup.OnCheckedChangeListener {
    private final RadioGroup mTabbar;
    private final HashMap<Integer, TabInfo> mContent;
    private final FragmentActivity mActivity;
    private final int mContainerId;
    private TabInfo lastTab;

    public TabBarManager(FragmentActivity activity, RadioGroup group, int contentConteinerId) {
        mContent = new HashMap<Integer, TabInfo>();
        mTabbar = group;
        mTabbar.setOnCheckedChangeListener(this);
        mActivity = activity;
        mContainerId = contentConteinerId;
    }

    private View.OnTouchListener actionDownChecker = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                mTabbar.check(view.getId());
            }
            return true;
        }
    };

    public void add(int radioButtonResourceId, Class<? extends Fragment> contentClass) {
        mContent.put(radioButtonResourceId, new TabInfo(radioButtonResourceId, contentClass));
        //hack to check radio button on ACTION_DOWN, not UP!
        View v = mTabbar.findViewById(radioButtonResourceId);
        v.setOnTouchListener(actionDownChecker);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int id) {
        TabInfo newTab = mContent.get(id);

        if (newTab != lastTab) {
            FragmentTransaction transaction = mActivity.getSupportFragmentManager().beginTransaction();
            if (lastTab != null && lastTab.fragment != null) {
//                transaction.detach(lastTab.fragment);
                transaction.hide(lastTab.fragment);
            }
            if (newTab.fragment == null) {
                newTab.fragment = Fragment.instantiate(mActivity, newTab.getTag());
                transaction.add(mContainerId, newTab.fragment);
            } else {
//                transaction.setAttachments(newTab.fragment);
                transaction.show(newTab.fragment);
            }
            lastTab = newTab;
//            transaction.setCustomAnimations(R.anim.tab_transaction, R.anim.tab_transaction);
            //transaction.addToBackStack(null);


            transaction.commit();

            notifyTabChanged(lastTab);
        }
    }

    private void notifyTabChanged(TabInfo lastTab) {
        ShowActionsEvent e;
        if (lastTab.fragment instanceof Actionable) {
            Actionable a = (Actionable) lastTab.fragment;
            e = new ShowActionsEvent(a.getActionView(mActivity));
        } else {
            e = new ShowActionsEvent(null);
        }
        Parom.bus().post(e);

    }

    public void detachAll() {
        FragmentTransaction transaction = mActivity.getSupportFragmentManager().beginTransaction();
        for (TabInfo tab : mContent.values()) {
            if (tab.fragment != null)
                transaction.detach(tab.fragment);
        }
    }

    private class TabInfo {
        int id;
        Class<? extends Fragment> clazz;
        Fragment fragment = null;

        public String getTag() {
            return clazz.getName();
        }

        private TabInfo(int id, Class<? extends Fragment> clazz) {
            this.id = id;
            this.clazz = clazz;
        }
    }

    public static interface Actionable {
        View getActionView(FragmentActivity mActivity);
    }
}