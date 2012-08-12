package me.levelapp.parom.ui.imageview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

/**
 * Created with IntelliJ IDEA.
 * User: Irina
 * Date: 11.08.12
 * Time: 13:13
 * To change this template use File | Settings | File Templates.
 */
public class ShipImageView extends ImageView {
    private int pathLength, pastMinutes, estimatedMinutes=1;

    public ShipImageView(Context context) {
        super(context);
    }

    public ShipImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShipImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if((getWidth()>0) && (pathLength>getWidth())) {
            this.setNewShipPosition(getWidth());
        }
    }

    public void setTime(int pastMinutes, int estimatedMinutes) {
        if((pastMinutes<=estimatedMinutes) && (pastMinutes>=0) && (estimatedMinutes!=0)) {
            this.pastMinutes=pastMinutes;
            this.estimatedMinutes=estimatedMinutes;
        }
    }

    public void setParams(int pathLength) {
        this.pathLength=pathLength;
    }

    private void setNewShipPosition(int shipWidth) {
        /*int xEnd=(int) (pathLength - (double) shipWidth / 2), xStart=(int) ((double) shipWidth / 2);
        double proportionOfTraversedWay = ((double)pastMinutes) / estimatedMinutes;

        int currX = (int) (xStart + Math.round((xEnd - xStart) * proportionOfTraversedWay) - (double)shipWidth / 2);  */
        int xEnd=pathLength-shipWidth, xStart=0;
        double proportionOfTraversedWay = ((double)pastMinutes) / estimatedMinutes;
        int currX = (int)(xStart + Math.round((xEnd - xStart) * proportionOfTraversedWay));

        TranslateAnimation animation = new TranslateAnimation(currX, currX, Animation.RELATIVE_TO_SELF,
                Animation.RELATIVE_TO_SELF);
        animation.setDuration(0);
        animation.setFillAfter(true);

        this.startAnimation(animation);
    }
}
