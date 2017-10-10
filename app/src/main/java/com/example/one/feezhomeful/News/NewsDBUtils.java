package monash.news_demo;

/**
 * Created by LHZ on 16/9/17.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * 数据库工具类，封装对数据库进行增删改查的方法
 * @author ASUS-H61M
 *
 */
public class NewsDBUtils {
    private NewsDBHelper dbHelper;

    public NewsDBUtils (Context context) {
        dbHelper = new NewsDBHelper(context);
    }

    // 保存新闻到数据库中
    public void saveNews(ArrayList<NewsEntity> arrayList) {
        SQLiteDatabase sqLite = dbHelper.getWritableDatabase();
        for(NewsEntity newsEntity : arrayList) {
            ContentValues value = new ContentValues();
            value.put("author", newsEntity.getAuthor());
            value.put("description", newsEntity.getDescription());
            value.put("title", newsEntity.getTitle());
            value.put("url", newsEntity.getUrl());
            value.put("urlToImage", newsEntity.getUrlToImage());
            value.put("publish", newsEntity.getPublishedAt());
            sqLite.insert("news", null, value);
        }
        sqLite.close();
    }


    // 删除数据库数据
    public void deleteNews (){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.delete("news", null, null);
        db.close();
    }

    // 从数据库中获取存储的行为
    public ArrayList<NewsEntity> getNews() {
        ArrayList<NewsEntity> arrayList = new ArrayList<NewsEntity>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("news", null, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                NewsEntity newsEntity = new NewsEntity();
                newsEntity.setAuthor(cursor.getString(0));
                newsEntity.setDescription(cursor.getString(1));
                newsEntity.setTitle(cursor.getString(2));
                newsEntity.setUrl(cursor.getString(3));
                newsEntity.setUrlToImage(cursor.getString(4));
                newsEntity.setPublishedAt(cursor.getString(5));
                arrayList.add(newsEntity);
            }
        }

        return arrayList;
    }
}

