package me.levelapp.parom.ui.scrollpanel;

import android.os.Bundle;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ScrollPanelMainActivity extends Activity {
	private static final int DURATION_TIME = 500;
	private final static String[] TEST = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", 
		"14", "15", "16", "17"};
	
	public static double range, extent, offset, height;
	public static int verticalScrollbarWidth;
	
	private MyListView list;
	private static TextView timeline;
	private int yPos, yPosOld;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.activity_main);
        
        determineDisplayMetrics();
        
        timeline = (TextView)findViewById(R.id.text);
        
        list = (MyListView)findViewById(R.id.list);
        list.setAdapter(new ArrayAdapter<String>(this, R.layout.textview, TEST));        
        list.setOnScrollListener(new OnScrollListener(){

			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				
				if(range != 0) {
					double pixInUnits = height / range, offsetInPixs = offset * pixInUnits, 
							halfExtentInPxs = extent * pixInUnits / 2;
					yPos = (int) Math.round(offsetInPixs + halfExtentInPxs);
					
					TranslateAnimation ta = new TranslateAnimation(0, 0, yPosOld, yPos);					
					ta.setDuration(DURATION_TIME);
					ta.setFillAfter(true);
					
					timeline.startAnimation(ta);
										
					yPosOld = yPos;
					
					if((timeline.getVisibility()==View.GONE) && (extent != 0)) {
						timeline.setVisibility(View.VISIBLE);
					}
				}
			}				

			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
			}
        	
        });
        
        timeline.setPadding(0, 0, /*verticalScrollbarWidth*/7, 0);
    }

    private void determineDisplayMetrics() {
		// TODO Auto-generated method stub
    	DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        height = metrics.heightPixels;
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
