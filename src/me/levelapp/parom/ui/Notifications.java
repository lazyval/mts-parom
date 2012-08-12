package me.levelapp.parom.ui;

/**
 * Created with IntelliJ IDEA.
 * User: Irina
 * Date: 10.08.12
 * Time: 13:34
 * To change this template use File | Settings | File Templates.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;
import me.levelapp.parom.R;
import me.levelapp.parom.model.Parom;
import me.levelapp.parom.ui.MainActivity;

import java.util.Date;
import java.util.List;

/**
 * User: anatoly
 * Date: 05.07.12
 * Time: 0:07
 */
public class Notifications {

    private static  int NOTIFICATION_ID = 0;
    private final Parom mAppContext;
    NotificationManager mManager;
    private Boolean singleNotification;
//    private VKMessage currentMessage;

    public Notifications(Parom vk) {
        mAppContext = vk;
        mManager = (NotificationManager) mAppContext.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void notifyNewMessage(String imageUri, String title, String desc) {

//            currentMessage = unread.get(0);
            String ticket = null;// currentMessage.getProfile().getDisplayName() + " : " + currentMessage.getBody();

        Uri u = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(mAppContext, u);
        if(r != null) {
            r.play();
        }
            Notification notification = new Notification(R.drawable.icon_notification, ticket, System.currentTimeMillis());
            RemoteViews root = new RemoteViews(mAppContext.getPackageName(), R.layout.notification_single_message);
            String fileName = imageUri;//VK.downloader().getDownloadedFileName(currentMessage.getProfile().getPhoto());


        // сжать
        final BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap b = BitmapFactory.decodeFile(fileName, options);

        if(b!=null) {
            options.inJustDecodeBounds = true;

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, MainActivity.getScreenDensity() * 60);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            b = BitmapFactory.decodeFile(fileName, options);

        } else {
            b = BitmapFactory.decodeResource(mAppContext.getResources(), R.drawable.ic_debug_party, options);
            options.inJustDecodeBounds = true;

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, MainActivity.getScreenDensity() * 60);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;

            b = BitmapFactory.decodeResource(mAppContext.getResources(), R.drawable.ic_debug_party, options);
        }

        root.setImageViewBitmap(R.id.img_avatar, b);

            root.setTextViewText(R.id.label_name, title);
            String  cuttedBody = desc;//new StringBuilder();

            root.setTextViewText(R.id.label_msg, cuttedBody.toString());
            root.setTextViewText(R.id.label_date, DateFormat.format("dd MMM h:mmaa", new Date()));
            notification.contentView = root;
            notification.flags |= Notification.FLAG_AUTO_CANCEL;


            Intent notificationIntent = new Intent(mAppContext, MainActivity.class);

            notification.contentIntent = PendingIntent.getActivity(mAppContext, 0,
                    notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            mManager.notify(R.string.debug_name, notification);
    }

    private int calculateInSampleSize(BitmapFactory.Options options, float reqWidth) {
        // Raw height and width of image
        final int width = options.outWidth;
        int inSampleSize = 1;

        if(width > reqWidth) {
            inSampleSize = Math.round((float)width / reqWidth);
        }

        return inSampleSize;
    }
}