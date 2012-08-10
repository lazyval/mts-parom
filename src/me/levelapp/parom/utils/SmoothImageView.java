package me.levelapp.parom.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * User: anatoly
 * Date: 23.07.12
 * Time: 3:56
 */
public class SmoothImageView extends ImageView implements ImageLoader.Displayable{
    public SmoothImageView(Context context) {
        super(context);
    }

    public SmoothImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SmoothImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void requestLayout() {
        //i do nothing
    }

    @Override
    public void display(Bitmap b) {
        setImageBitmap(b);
    }
}
