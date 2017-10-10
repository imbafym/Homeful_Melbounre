package com.example.one.feezhomeful;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GuideActivity extends Activity {

    /**
     * 是否显示引导界面
     */
    boolean isShow = false;
    /**
     * ViewPager对象
     */
    private ViewPager mViewPager;
    /**
     * ViewPager的每个页面集合
     */
    private List<View> views;
    /**
     * ViewPager下面的小圆圈
     */
    private ImageView[] mImageViews;
    /**
     * 装载小圆圈的LinearLayout
     */
    private LinearLayout indicatorLayout;
    /**
     * welcome page
     */
    private ImageView show;
    /**
     * view for pager
     */
    private View pagerView;

    private MyPagerAdapter myPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_guide);
        // 得到Preference存储的isShow数据
        isShow = PreferenceUtil.getBoolean(this, PreferenceUtil.SHOW_GUIDE);
        //load the welcome page
        show = (ImageView)findViewById(R.id.gradually);
        //fade out
        AlphaAnimation alpha = new AlphaAnimation(1.0f, 0.0f);
        alpha.setDuration(5000);
        alpha.setFillAfter(true);
        show.startAnimation(alpha);
        addFoodOnlineAsy();
        addMaleShelterAsy();
        addFemaleShelterAsy();
        addFoodAsy();
        addMaleToiletAsy();
        addFemaleToiletAsy();
        addWaterAsy();

        alpha.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (isShow) {
                    initLog();
                } else {
                    initView();
                }
            }
        });


    }

    /**
     * 进入登录界面
     */
    private void initLog() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    /**
     * 进入引导界面
     */
    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        pagerView = inflater.inflate(R.layout.activity_guide, null);
        setContentView(pagerView);
        mViewPager = (ViewPager) findViewById(R.id.guide_viewPager);
        indicatorLayout = (LinearLayout) findViewById(R.id.linearlayout);
        views = new ArrayList<View>();
        views.add(inflater.inflate(R.layout.pager_layout1, null));
        views.add(inflater.inflate(R.layout.pager_layout2, null));
        views.add(inflater.inflate(R.layout.pager_layout3, null));
        myPagerAdapter = new MyPagerAdapter(this, views);
        mImageViews = new ImageView[views.size()];
        drawCircl();
        mViewPager.setAdapter(myPagerAdapter);
        mViewPager.setOnPageChangeListener(new GuidePageChangeListener());
    }

    /**
     * circle
     */
    private void drawCircl() {
        int num = views.size();
        for (int i = 0; i < num; i++) {
            //
            mImageViews[i] = new ImageView(this);
            if (i == 0) {
                // 默认选中第一张照片，所以将第一个小圆圈变为icon_carousel_02
                mImageViews[i].setImageResource(R.drawable.icon_carousel_02);
            } else {
                mImageViews[i].setImageResource(R.drawable.icon_carousel_01);
            }
            // 给每个小圆圈都设置间隔
            mImageViews[i].setPadding(7, 7, 7, 7);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER_VERTICAL;
            // 让每一个小圆圈都在LinearLayout的CENTER_VERTICAL（中间垂直）
            indicatorLayout.addView(mImageViews[i], params);
        }

    }

    /**
     *
     * @author Harry 页面改变监听事件
     */
    private class GuidePageChangeListener implements OnPageChangeListener {
        public void onPageScrollStateChanged(int arg0) {
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        /**
         * 页面有所改变，如果是当前页面，将小圆圈改为icon_carousel_02，其他页面则改为icon_carousel_01
         */
        public void onPageSelected(int arg0) {
            for (int i = 0; i < mImageViews.length; i++) {
                if (arg0 != i) {
                    mImageViews[i]
                            .setImageResource(R.drawable.icon_carousel_01);
                } else {
                    mImageViews[arg0]
                            .setImageResource(R.drawable.icon_carousel_02);
                }
            }
        }
    }

    class MyPagerAdapter extends PagerAdapter {
        private List<View> mViews;
        private Activity mContext;

        public MyPagerAdapter(Activity context, List<View> views) {
            this.mViews = views;
            this.mContext = context;
        }

        @Override
        public int getCount() {
            return mViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(mViews.get(arg1));
        }

        /**
         * 实例化页卡，如果变为最后一页，则获取它的button并且添加点击事件
         */
        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(mViews.get(arg1), 0);
            if (arg1 == mViews.size() - 1) {
                TextView enterBtn = (TextView) arg0
                        .findViewById(R.id.guide_enter);
                enterBtn.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 将isShow保存为true，并进入登录界面
                        PreferenceUtil.setBoolean(mContext,
                                PreferenceUtil.SHOW_GUIDE, true);
                        initLog();
                    }
                });
            }
            return mViews.get(arg1);
        }
    }

    public void addFemaleShelterAsy() {
        new AsyncTask<Void, Void, List<Shelter>>() {

            @Override
            protected List<Shelter> doInBackground(Void... params) {
                List<Shelter> s = RestClient.getFemaleShelter();
                return s;
            }

            @Override
            protected void onPostExecute(List<Shelter> input) {
                List<Shelter> allShelters = input;
                LocationRepo shelterRepo = new LocationRepo(getApplicationContext());
                try{
//                    dbManager.initialTable(dbManager.getWritableDatabase());
                    shelterRepo.open();
                    shelterRepo.initialFemaleShelterTable();
                    for(Shelter s : allShelters){
                        shelterRepo.insertFemaleShelter(new ShelterDB(), s);
                    }}catch(Exception e){
                }
                shelterRepo.close();
            }
        }.execute();
    }


    public void addMaleShelterAsy() {
        new AsyncTask<Void, Integer, List<Shelter>>() {

            @Override
            protected List<Shelter> doInBackground(Void... params) {
                List<Shelter> s = RestClient.getMaleShelter();
                for(int i = 0; i < s.size(); i++){
                    publishProgress(i);
                }

                return s;
            }


//            @Override
//            protected void onProgressUpdate(Integer... values) {
//                super.onProgressUpdate();
//                //通过publishProgress方法传过来的值进行进度条的更新.
//                mainProgressBar.setProgress(values[0]);
//            }


            @Override
            protected void onPostExecute(List<Shelter> input) {

                List<Shelter> allShelters = input;
                LocationRepo shelterRepo = new LocationRepo(getApplicationContext());
                try{
//                    dbManager.initialTable(dbManager.getWritableDatabase());
                    shelterRepo.open();
                    shelterRepo.initialMaleShelterTable();
                    for(Shelter s : allShelters){
                        shelterRepo.insertMaleShelter(new ShelterDB(), s);
                    }}catch(Exception e){
                }
                shelterRepo.close();
            }
        }.execute();
    }




    public void addFemaleToiletAsy() {
        new AsyncTask<Void, Void, List<Toilet>>() {

            @Override
            protected List<Toilet> doInBackground(Void... params) {
                List<Toilet> s = RestClient.getFemaleToilet();
                return s;
            }

            @Override
            protected void onPostExecute(List<Toilet> input) {
                List<Toilet> allToilet = input;
                LocationRepo shelterRepo = new LocationRepo(getApplicationContext());
                try{
                    shelterRepo.open();
                    shelterRepo.initialFemaleToiletTable();
                    for(Toilet s : allToilet){
                        shelterRepo.insertFemaleToilet( s);
                    }}catch(Exception e){
                }
                shelterRepo.close();
            }
        }.execute();
    }

    public void addMaleToiletAsy() {
        new AsyncTask<Void, Void, List<Toilet>>() {

            @Override
            protected List<Toilet> doInBackground(Void... params) {
                List<Toilet> s = RestClient.getMaleToilet();
                return s;
            }

            @Override
            protected void onPostExecute(List<Toilet> input) {
                List<Toilet> allToilet = input;
                LocationRepo shelterRepo = new LocationRepo(getApplicationContext());
                try{
                    shelterRepo.open();
                    shelterRepo.initialMaleToiletTable();
                    for(Toilet s : allToilet){
                        shelterRepo.insertMaleToilet(s);
                    }}catch(Exception e){
                }
                shelterRepo.close();
            }
        }.execute();
    }

    public void addWaterAsy() {
        new AsyncTask<Void, Void, List<Water>>() {

            @Override
            protected List<Water> doInBackground(Void... params) {
                List<Water> s = RestClient.getWater();


                return s;
            }

            @Override
            protected void onPostExecute(List<Water> input) {
                List<Water> allWater = input;
                LocationRepo shelterRepo = new LocationRepo(getApplicationContext());
                try{
                    shelterRepo.open();
                    shelterRepo.initialWaterTable();
                    for(Water s : allWater){
                        shelterRepo.insertWater( s);
                    }}catch(Exception e){

                }
                shelterRepo.close();
            }
        }.execute();
    }

    public void addFoodAsy() {
        new AsyncTask<Void, Void, List<Food>>() {
            @Override
            protected List<Food> doInBackground(Void... params) {
                List<Food> s = RestClient.getFood();
                return s;
            }

            @Override
            protected void onPostExecute(List<Food> input) {
                List<Food> allFoods = input;
                LocationRepo foodRepo = new LocationRepo(getApplicationContext());
                try{
                    foodRepo.open();
                    foodRepo.initialFoodTable();
                    for(Food f : allFoods){
                        foodRepo.insertFood(new FoodDB(), f);
                    }}catch(Exception e){

                }
                foodRepo.close();
            }
        }.execute();
    }

    public void addFoodOnlineAsy() {
        new AsyncTask<Void, Void, List<Food>>() {
            @Override
            protected List<Food> doInBackground(Void... params) {
                List<Food> s = RestClient.getFoodFromDatabase();
                return s;
            }

            @Override
            protected void onPostExecute(List<Food> input) {
                List<Food>allFoodsOnlineDB = input;
                LocationRepo foodRepo = new LocationRepo(getApplicationContext());
                try{
                    foodRepo.open();
                    foodRepo.initialFoodOnlineDBTable();
                    for(Food f : allFoodsOnlineDB){
                        foodRepo.insertFoodToOnlineDB(new FoodDB(), f);
                    }}catch(Exception e){

                }
                foodRepo.close();
            }
        }.execute();
    }
}