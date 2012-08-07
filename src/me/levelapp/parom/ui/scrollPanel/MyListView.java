package me.levelapp.parom.ui.scrollpanel;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ListView;

public class MyListView extends ListView {

	public MyListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public MyListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean awakenScrollBars(int startDelay, boolean invalidate) {
		// TODO Auto-generated method stub
		ScrollPanelMainActivity.extent =  computeVerticalScrollExtent();
		ScrollPanelMainActivity.range = computeVerticalScrollRange();
		ScrollPanelMainActivity.offset = computeVerticalScrollOffset();
		ScrollPanelMainActivity.verticalScrollbarWidth = getVerticalScrollbarWidth();
		return super.awakenScrollBars(startDelay, invalidate);
	}
}
