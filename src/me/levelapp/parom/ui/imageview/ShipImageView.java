package me.levelapp.parom.ui.imageview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import me.levelapp.parom.ui.ShipView;

/**
 * Created with IntelliJ IDEA.
 * User: Irina
 * Date: 11.08.12
 * Time: 13:13
 * To change this template use File | Settings | File Templates.
 */
public class ShipImageView extends ImageView {
    private ShipView parent;

    public ShipImageView(Context context) {
        super(context);
    }

    public ShipImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShipImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setParentView(ShipView parent) {
        this.parent=parent;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(getWidth()>0) {
            parent.CreateShip(getWidth());
        }
    }
}
