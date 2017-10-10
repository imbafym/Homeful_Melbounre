package monash.news_demo;

/**
 * Created by LHZ on 16/9/17.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;
/*
 * 將Stream流解碼爲圖片的自定義視圖
 */
public class MyImageView extends android.support.v7.widget.AppCompatImageView {

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            Bitmap bitmap = (Bitmap) msg.obj;
            MyImageView.this.setImageBitmap(bitmap);
        };
    };

    public MyImageView(Context context) {
        super(context);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setImageUrl(final String urlString) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5 * 1000);
                    int code = conn.getResponseCode();
                    if (code == 200) {
                        InputStream is = conn.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        //ethan
//                        float w = bitmap.getWidth();//get width
//                        float h = bitmap.getHeight();//get height
//                        int W = (int)(w/2);
//                        int H = (int) ( (h*W)/w);
//                        //
//                        Bitmap b = Bitmap.createScaledBitmap(bitmap, W, H, false);//scale the bitmap
                        Message message = Message.obtain();
                        message.obj = bitmap;
                        mHandler.sendMessage(message);
                    }


                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();;

    }

}
