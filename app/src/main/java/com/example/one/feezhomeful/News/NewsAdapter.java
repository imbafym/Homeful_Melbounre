package monash.news_demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import monash.news_demo.MyImageView;
import monash.news_demo.NewsEntity;
import monash.news_demo.R;

/**
 * Created by LHZ on 16/9/17.
 */

public class NewsAdapter extends BaseAdapter{
    private LayoutInflater mLayoutInflater;
    private List<NewsEntity> mDatas;

    public NewsAdapter(Context context, List<NewsEntity> entityList)
    {
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mDatas = entityList;
    }

    @Override
    public int getCount()
    {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;
        if(convertView == null)
        {
            convertView = mLayoutInflater.inflate(R.layout.layout_news_item, null);
            viewHolder = new ViewHolder();
            viewHolder.item_img_icon = (MyImageView) convertView.findViewById(R.id.item_img_icon);
            //viewHolder.item_tv_author = (TextView) convertView.findViewById(R.id.item_tv_author);
            viewHolder.item_tv_title = (TextView) convertView.findViewById(R.id.item_tv_title);
            viewHolder.item_tv_des = (TextView) convertView.findViewById(R.id.item_tv_des);
            viewHolder.item_tv_publish = (TextView) convertView.findViewById(R.id.item_tv_publish);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        NewsEntity newsEntity = mDatas.get(position);
        //viewHolder.item_tv_author.setText(newsEntity.getAuthor());
        viewHolder.item_tv_title.setText(newsEntity.getTitle());
        viewHolder.item_tv_des.setText(newsEntity.getDescription());
        viewHolder.item_tv_publish.setText(newsEntity.getPublishedAt());
        viewHolder.item_img_icon.setImageUrl(newsEntity.getUrlToImage());
        return convertView;
    }

}

class ViewHolder
{
    MyImageView item_img_icon;
    TextView item_tv_des;
    TextView item_tv_title;
    TextView item_tv_author;
    TextView item_tv_publish;
}
