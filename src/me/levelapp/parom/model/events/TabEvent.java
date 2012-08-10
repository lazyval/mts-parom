package me.levelapp.parom.model.events;

/**
 * User: anatoly
 * Date: 10.08.12
 * Time: 13:18
 */
public class TabEvent  implements BaseEvent {
    private int mTabId;

    public int getTabId() {
        return mTabId;
    }

    public TabEvent(int mTabId) {

        this.mTabId = mTabId;
    }
}
