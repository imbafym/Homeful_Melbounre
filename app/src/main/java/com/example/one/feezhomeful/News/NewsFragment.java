package com.example.one.feezhomeful.News;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.example.one.feezhomeful.R;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment implements OnItemClickListener {
    private static String result = "http://beta.newsapi.org/v2/everything?sources=abc-news-au,google-news-au,news-com-au,the-guardian-au,australian-financial-review&q=homeless&sortBy=relevancy&apiKey=1e6e2249597748c181d39b641bc4a747";
    private Context mContext;
    private TextView textView;
    RestClient newsUtils;
    List<NewsEntity> listNewsBean;
    ListView listview;
    View vMain;
    //添加缓存，
    LruCache<String, Bitmap> cache;// 缓存，本质相当于一个map
    int ImgStart,ImgEnd;



    private NewsAdapter newsAdapter;
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            listNewsBean = (List<NewsEntity>) msg.obj;
            NewsAdapter newsAdapter = new NewsAdapter(getContext(), getFragmentManager(),listNewsBean);
            listview.setAdapter(newsAdapter);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        if (vMain == null)
        {
            vMain = inflater.inflate(R.layout.fragment_news, container, false);
            listview = (ListView) vMain.findViewById(R.id.list_news);
            //textView = (TextView) vMain.findViewById(R.id.textTest);
            mContext = getContext();
            newsUtils = new RestClient();
            NewsDBUtils newsDatabase = new NewsDBUtils(mContext);

            // 1.先去数据库中获取缓存的新闻数据展示到listview
            ArrayList<NewsEntity> allnews_database = RestClient.getDBNews(mContext);

            ArrayList<NewsEntity> resultList = new ArrayList<>();
            for(NewsEntity s : allnews_database){
                if(!s.getUrlToImage().equals("null")){
                    resultList.add(s);
                }
            }

            if (allnews_database != null && allnews_database.size() > 0) {
                // 创建一个adapter设置给listview

                newsAdapter = new NewsAdapter(mContext,getFragmentManager(),resultList);
                listview.setAdapter(newsAdapter);
                newsAdapter.notifyDataSetChanged();
            }

            newsAdapter = new NewsAdapter(mContext,getFragmentManager(),allnews_database);
            listview.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    switch (scrollState){
                        case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:{
                            //set stop scroll
                            newsAdapter.setScrollState(false);
                            //current numbers of item on screen
                            int count = view.getChildCount();

                            for (int i = 0; i < count; i++) {
                                //获取到item的name
                                TextView tv_name = (TextView) view.getChildAt(i).findViewById(R.id.item_tv_title);
                                TextView tv_des = (TextView) view.getChildAt(i).findViewById(R.id.item_tv_des);
                                TextView tv_publish = (TextView) view.getChildAt(i).findViewById(R.id.item_tv_publish);
                                MyImageView tv_img_icon = (MyImageView) view.getChildAt(i).findViewById(R.id.item_img_icon);
                                if (tv_name.getTag() != null ) { //非null说明需要加载数据
                                    tv_name.setText(tv_name.getTag().toString());//直接从Tag中取出我们存储的数据name并且赋值
                                    tv_name.setTag(null);//设置为已加载过数据
                                }
                                if (tv_des.getTag() != null) { //非null说明需要加载数据
                                    tv_des.setText(tv_des.getTag().toString());//直接从Tag中取出我们存储的数据name并且赋值
                                    tv_des.setTag(null);//设置为已加载过数据
                                }
                                if (tv_publish.getTag() != null) { //非null说明需要加载数据
                                    tv_publish.setText(tv_publish.getTag().toString());//直接从Tag中取出我们存储的数据name并且赋值
                                    tv_publish.setTag(null);//设置为已加载过数据
                                }
                                if (tv_img_icon.getTag() != null ) { //非null说明需要加载数据
                                    tv_img_icon.setImageUrl(tv_img_icon.getTag().toString());//直接从Tag中取出我们存储的数据name并且赋值
                                    tv_img_icon.setTag(null);//设置为已加载过数据
                                }
                            }
                            break;
                        }
                        case AbsListView.OnScrollListener.SCROLL_STATE_FLING://fast scroll
                        {
                            //set statue to scroll
                            newsAdapter.setScrollState(true);
                            break;
                        }

                        case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// scrolling
                        {
                            //set statue to scroll
                            newsAdapter.setScrollState(true);
                            break;
                        }
                    }
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                }
            });
            listview.setDivider(this.getResources().getDrawable(R.drawable.transparent_color));
            listview.setDividerHeight(20);

            new Thread(new Runnable() {

                @Override
                public void run() {
                    listNewsBean = newsUtils.getNetNews(mContext, result);
                    Message message = Message.obtain();
                    message.obj = listNewsBean;
                    mHandler.sendMessage(message);
                }
            }).start();

            listview.setOnItemClickListener(this);
        }
        return vMain;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        NewsEntity news = (NewsEntity) parent.getItemAtPosition(position);
        String url = news.getUrl();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);

    }


}

