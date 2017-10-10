package com.example.one.feezhomeful;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

/**
 * Created by one on 4/09/2017.
 */



public class SettingBasePopUpWindow extends PopupWindow {

    private Context mContext;
    private float mShowAlpha = 0.88f;
    private Drawable mBackgroundDrawable;

    public SettingBasePopUpWindow(Context context) {
        this.mContext = context;
        initBasePopupWindow();
    }

    @Override
    public void setOutsideTouchable(boolean touchable) {
        super.setOutsideTouchable(touchable);
        if(touchable) {
            if(mBackgroundDrawable == null) {
                mBackgroundDrawable = new ColorDrawable(0x00000000);
            }
            super.setBackgroundDrawable(mBackgroundDrawable);
        } else {
            super.setBackgroundDrawable(null);
        }
    }

    @Override
    public void setBackgroundDrawable(Drawable background) {
        mBackgroundDrawable = background;
        setOutsideTouchable(isOutsideTouchable());
    }

    /**
     * 初始化BasePopupWindow的一些信息
     * */
    private void initBasePopupWindow() {
        setAnimationStyle(android.R.style.Animation_Dialog);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setOutsideTouchable(true);  //默认设置outside点击无响应
        setFocusable(true);
    }

    @Override
    public void setContentView(View contentView) {
        if(contentView != null) {
            contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            super.setContentView(contentView);
            RadioGroup rdg =  (RadioGroup)contentView.findViewById(R.id.radiogroup_gender);
            final RadioButton maleRadioButton = (RadioButton)contentView.findViewById(R.id.radioButton_male);
            final RadioButton femaleRadioButton = (RadioButton)contentView.findViewById(R.id.radioButton_female);
            final Switch babySwitch = (Switch)contentView.findViewById(R.id.switch_baby);
            final Button closeButton = (Button)contentView.findViewById(R.id.close_button_pop_setting) ;
            SharedPreferences preferences = mContext.getSharedPreferences(
                    "currentGender", Context.MODE_PRIVATE);
            Boolean flag = preferences.getBoolean("Gender",true);
            if(flag){
                rdg.check(maleRadioButton.getId());
            }else {
                rdg.check(femaleRadioButton.getId());
            }
            rdg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    if(checkedId == maleRadioButton.getId()){
                        SharedPreferences prefs = mContext.getSharedPreferences(
                                "currentGender", Context.MODE_PRIVATE);
                        prefs.edit().putBoolean("Gender",true).apply();
                        Toast.makeText(mContext,"Your are Male",Toast.LENGTH_SHORT).show();
                    }else if(checkedId == femaleRadioButton.getId()){
                        SharedPreferences prefs = mContext.getSharedPreferences(
                                "currentGender", Context.MODE_PRIVATE);
                        prefs.edit().putBoolean("Gender",false).apply();
                        Toast.makeText(mContext,"Your are Female",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            SharedPreferences babyPre = mContext.getSharedPreferences(
                    "baby", Context.MODE_PRIVATE);
            Boolean isBaby = babyPre.getBoolean("baby",false);
            if(isBaby) {
                babySwitch.setChecked(true);
            }else {
                babySwitch.setChecked(false);
            }
            babySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    SharedPreferences prefs = mContext.getSharedPreferences(
                            "baby", Context.MODE_PRIVATE);
                    if(isChecked) {
                        prefs.edit().putBoolean("baby", true).apply();
                    }else{
                        prefs.edit().putBoolean("baby", false).apply();
                    }
                }
            });
            closeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

            addKeyListener(contentView);
        }


    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        showAnimator().start();
    }

    @Override
    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);
        showAnimator().start();
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        super.showAsDropDown(anchor, xoff, yoff);
        showAnimator().start();
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        super.showAsDropDown(anchor, xoff, yoff, gravity);
        showAnimator().start();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        dismissAnimator().start();
    }

    /**
     * 窗口显示，窗口背景透明度渐变动画
     * */
    private ValueAnimator showAnimator() {
        ValueAnimator animator = ValueAnimator.ofFloat(1.0f, mShowAlpha);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                setWindowBackgroundAlpha(alpha);
            }
        });
        animator.setDuration(360);
        return animator;
    }

    /**
     * 窗口隐藏，窗口背景透明度渐变动画
     * */
    private ValueAnimator dismissAnimator() {
        ValueAnimator animator = ValueAnimator.ofFloat(mShowAlpha, 1.0f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();
                setWindowBackgroundAlpha(alpha);
            }
        });
        animator.setDuration(320);
        return animator;
    }

    /**
     * 为窗体添加outside点击事件
     * */
    private void addKeyListener(View contentView) {
        if(contentView != null) {
            contentView.setFocusable(true);
            contentView.setFocusableInTouchMode(true);
            contentView.setOnKeyListener(new View.OnKeyListener() {

                @Override
                public boolean onKey(View view, int keyCode, KeyEvent event) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_BACK:
                            dismiss();
                            return true;
                        default:
                            break;
                    }
                    return false;
                }
            });
        }
    }

    /**
     * 控制窗口背景的不透明度
     * */
    private void setWindowBackgroundAlpha(float alpha) {
        Window window = ((Activity)getContext()).getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.alpha = alpha;
        window.setAttributes(layoutParams);
    }
}

