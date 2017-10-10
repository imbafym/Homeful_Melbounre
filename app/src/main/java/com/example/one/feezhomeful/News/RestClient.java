package monash.news_demo;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by LHZ on 16/9/17.
 */

public class RestClient {
    // 从网络中获取Json数据，解析Json数据
    public static ArrayList<NewsEntity> getNetNews(Context context, String urlString)
    {
        String textResult = "";
        HttpURLConnection conn = null;

        ArrayList<NewsEntity> arrayListNews = new ArrayList<NewsEntity>();
        try
        {
            URL url = new URL(urlString);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setConnectTimeout(20 * 1000);

            {
                // 获取请求到的流信息
                Scanner inStream = new Scanner(conn.getInputStream());
                //read the input steream and store it as string
                while (inStream.hasNextLine()) {
                    textResult += inStream.nextLine();
                }
                JSONObject root_json = new JSONObject(textResult);
                JSONArray jsonArray  = root_json.getJSONArray("articles");
                for (int i = 0; i < jsonArray .length(); i ++ ){
                    JSONObject news_json = jsonArray.getJSONObject(i);
                    NewsEntity newsEntity = new NewsEntity();
                    newsEntity.setAuthor(news_json.getString("author"));
                    newsEntity.setDescription(news_json.getString("description"));
                    newsEntity.setTitle(news_json.getString("title"));
                    newsEntity.setUrl(news_json.getString("url"));
                    newsEntity.setUrlToImage(news_json.getString("urlToImage"));
                    Log.i("NewsUtils", newsEntity.getUrlToImage());
                    newsEntity.setPublishedAt(news_json.getString("publishedAt"));
                    arrayListNews.add(newsEntity);

                }

                // 如果获取到网络上的数据，就删除之前获取的新闻数据，保存新的新闻数据
                new NewsDBUtils(context).deleteNews();
                new NewsDBUtils(context).saveNews(arrayListNews);

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
     finally
        {
            conn.disconnect();
        }
        return arrayListNews;
    }
    // 返回数据库缓存到的数据
    public static ArrayList<NewsEntity> getDBNews(Context context){

        return new NewsDBUtils(context).getNews();
    }
}
