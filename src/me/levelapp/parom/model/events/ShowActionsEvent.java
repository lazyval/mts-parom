package me.levelapp.parom.model.events;

import android.view.View;

/**
 * User: anatoly
 * Date: 09.08.12
 * Time: 14:22
 */
public class ShowActionsEvent implements BaseEvent {
    View mActionView;
    public ShowActionsEvent(View actionsView) {
        mActionView = actionsView;
    }

    public View getActionView() {
        return mActionView;
    }

    @Override
    public String toString() {
        return ShowActionsEvent.class.getName();
    }
}
