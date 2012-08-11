package me.levelapp.parom.ui;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.TranslateAnimation;
import me.levelapp.parom.R;
import me.levelapp.parom.ui.imageview.ShipImageView;

/**
 * Created with IntelliJ IDEA.
 * User: Irina
 * Date: 11.08.12
 * Time: 13:02
 * To change this template use File | Settings | File Templates.
 */
public class ShipView {
    private int shipY, width, pastMinutes, estimatedMinutes;
    private ShipImageView shipView;

    public ShipView(Activity activity, int pastMinutes, int estimatedMinutes, int shipY)   {
        determineScreenWidth(activity);

        this.pastMinutes = pastMinutes;
        this.estimatedMinutes = estimatedMinutes;
        this.shipY = shipY;

        LayoutInflater inflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        shipView = (ShipImageView)inflater.inflate(R.layout.view_ship, null);
        shipView.setParentView(this);
    }

    public void CreateShip(int imageWidth) {
        int xEnd=(int) (width - (double) imageWidth / 2), xStart=(int) ((double) imageWidth / 2);
        double proportionOfTraversedWay = ((double)pastMinutes) / estimatedMinutes;

        int currX = (int) (xStart + Math.round((xEnd - xStart) * proportionOfTraversedWay) - (double)imageWidth / 2),
                currY = shipY;

        TranslateAnimation animation = new TranslateAnimation(currX, currX, currY, currY);
        animation.setDuration(0);
        animation.setFillAfter(true);

        shipView.startAnimation(animation);
    }

    private void determineScreenWidth(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        width = metrics.widthPixels;
    }

    public View getShipView() {
        return shipView;
    }

}
