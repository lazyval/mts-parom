package me.levelapp.parom.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.google.common.eventbus.Subscribe;
import me.levelapp.parom.R;
import me.levelapp.parom.model.Parom;
import me.levelapp.parom.model.events.ShowActionsEvent;

/**
 * User: anatoly
 * Date: 09.08.12
 * Time: 14:43
 */
public class ActionBarFragment extends Fragment {
    private FrameLayout mActions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View ret = inflater.inflate(R.layout.fragment_action_bar, container, false);
        mActions = (FrameLayout) ret.findViewById(R.id.wrapper_action);
        return ret;
    }


    @Override
    public void onResume() {
        super.onResume();
        Parom.bus().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        Parom.bus().unregister(this);
    }

    @Subscribe
    public void showActionView(ShowActionsEvent e) {

            mActions.removeAllViews();
            mActions.setVisibility(View.GONE);
        if (e.getActionView()!= null ) {
            mActions.setVisibility(View.VISIBLE);
            mActions.addView(e.getActionView());
        }
    }
}
