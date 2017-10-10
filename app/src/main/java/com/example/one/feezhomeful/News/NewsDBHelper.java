package monash.news_demo;

/**
 * Created by LHZ on 16/9/17.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * 数据库帮助类，创建数据库
 * @author ASUS-H61M
 *
 */
public class NewsDBHelper extends SQLiteOpenHelper {

    public NewsDBHelper(Context context) {
        super(context, "NetNews", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table news (author varchar(200), description varchar(300), title varchar(200), "
                + "url varchar(100), urlToImage varchar(200), publish varchar(200))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

}
