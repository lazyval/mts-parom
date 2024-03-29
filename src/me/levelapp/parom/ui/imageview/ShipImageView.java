package me.levelapp.parom.ui.imageview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created with IntelliJ IDEA.
 * User: Irina
 * Date: 11.08.12
 * Time: 13:13
 * To change this template use File | Settings | File Templates.
 */
public class ShipImageView extends ImageView {
    private int pathLength, pastMinutes, estimatedMinutes=1, currentX;
    private ImageView waterImage;
    private LinearLayout.LayoutParams waterImageLayoutParams;

    public ShipImageView(Context context) {
        super(context);
        waterImageLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public ShipImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        waterImageLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public ShipImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        waterImageLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if((getWidth()>0) && (pathLength>getWidth())) {
            this.setNewShipPosition(getWidth());

            if(pathLength - currentX > 0) {
                waterImageLayoutParams.width = currentX;
                waterImage.setLayoutParams(waterImageLayoutParams);
            } else {
                waterImageLayoutParams.width = 1;
                waterImage.setLayoutParams(waterImageLayoutParams);
            }
        }
    }

    public void setTime(int pastMinutes, int estimatedMinutes) {
        if((pastMinutes<=estimatedMinutes) && (pastMinutes>=0) && (estimatedMinutes!=0)) {
            this.pastMinutes=pastMinutes;
            this.estimatedMinutes=estimatedMinutes;
        }
    }

    public void setParams(int pathLength, ImageView waterImage) {
        this.pathLength=pathLength;
        this.waterImage = waterImage;
    }

    private void setNewShipPosition(int shipWidth) {
        /*int xEnd=(int) (pathLength - (double) shipWidth / 2), xStart=(int) ((double) shipWidth / 2);
        double proportionOfTraversedWay = ((double)pastMinutes) / estimatedMinutes;

        int currX = (int) (xStart + Math.round((xEnd - xStart) * proportionOfTraversedWay) - (double)shipWidth / 2);  */
        int xEnd=pathLength-shipWidth, xStart=0;
        double proportionOfTraversedWay = ((double)pastMinutes) / estimatedMinutes;
        currentX = (int)(xStart + Math.round((xEnd - xStart) * proportionOfTraversedWay));

        TranslateAnimation animation = new TranslateAnimation(currentX, currentX, Animation.RELATIVE_TO_SELF,
                Animation.RELATIVE_TO_SELF);
        animation.setDuration(0);
        animation.setFillAfter(true);

        this.startAnimation(animation);

    }
}
