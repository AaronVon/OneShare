package com.pioneer.aaron.oneshare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pioneer.aaron.oneshare.Constants.Constant;
import com.pioneer.aaron.oneshare.wxapi.WXEntryActivity;
import com.yalantis.guillotine.animation.GuillotineAnimation;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final long RIPPLE_DURATION = 250;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.root)
    FrameLayout root;
    @Bind(R.id.content_hamburger)
    View contentHamburger;

    //guillotine items
    TextView tx_wechat;
    TextView tx_weibo;
    TextView tx_qzone;
    ImageView ic_wechat;
    ImageView ic_weibo;
    ImageView ic_qzone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initGuillotine();
        initPreferences();

    }

    private void bindGuillotine() {
        ic_wechat = (ImageView) findViewById(R.id.ic_wechat);
        tx_wechat = (TextView) findViewById(R.id.tx_wechat);
        ic_weibo = (ImageView) findViewById(R.id.ic_weibo);
        tx_weibo = (TextView) findViewById(R.id.tx_weibo);
        ic_qzone = (ImageView) findViewById(R.id.ic_qzone);
        tx_qzone = (TextView) findViewById(R.id.tx_qzone);
    }

    private void initGuillotine() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }

        View guillotineMenu = LayoutInflater.from(this).inflate(R.layout.guillotine, null);
        root.addView(guillotineMenu);

        new GuillotineAnimation.GuillotineBuilder(guillotineMenu, guillotineMenu.findViewById(R.id.guillotine_hamburger), contentHamburger)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .build();
    }

    private void initPreferences() {
        bindGuillotine();
        SharedPreferences preferences = getSharedPreferences(Constant.SHAREDPREFS, MODE_PRIVATE);
        if (preferences.getBoolean(Constant.INITIALLAUNCH, true)) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(Constant.WECHAT_SELECTED, false);
            editor.putBoolean(Constant.WEIBO_SELECTED, false);
            editor.putBoolean(Constant.QZONE_SELECTED, false);
            editor.putBoolean(Constant.INITIALLAUNCH, false);
            editor.commit();
        } else {
            if (preferences.getBoolean(Constant.WECHAT_SELECTED, false)) {
                ic_wechat.setImageResource(R.drawable.wechat_selected);
                tx_wechat.setTextColor(getResources().getColor(R.color.selected_item_color));
            }
            if (preferences.getBoolean(Constant.WEIBO_SELECTED, false)) {
                ic_weibo.setImageResource(R.drawable.weibo_selected);
                tx_weibo.setTextColor(getResources().getColor(R.color.selected_item_color));
            }
            if (preferences.getBoolean(Constant.QZONE_SELECTED, false)) {
                ic_qzone.setImageResource(R.drawable.qzone_selected);
                tx_qzone.setTextColor(getResources().getColor(R.color.selected_item_color));
            }
        }
    }

    @Nullable
    @OnClick({R.id.wechat_group, R.id.qzone_group, R.id.weibo_group})
    public void click_group(View view) {
        bindGuillotine();
        SharedPreferences preferences = getSharedPreferences(Constant.SHAREDPREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        switch (view.getId()) {
            case R.id.wechat_group:
                if (preferences.getBoolean(Constant.WECHAT_SELECTED, false)) {
                    ic_wechat.setImageResource(R.drawable.wechat);
                    tx_wechat.setTextColor(Color.WHITE);
                    editor.putBoolean(Constant.WECHAT_SELECTED, false);
                } else {
                    ic_wechat.setImageResource(R.drawable.wechat_selected);
                    tx_wechat.setTextColor(getResources().getColor(R.color.selected_item_color));
                    editor.putBoolean(Constant.WECHAT_SELECTED, true);
                }
                break;
            case R.id.weibo_group:
                if (preferences.getBoolean(Constant.WEIBO_SELECTED, false)) {
                    ic_weibo.setImageResource(R.drawable.weibo);
                    tx_weibo.setTextColor(Color.WHITE);
                    editor.putBoolean(Constant.WEIBO_SELECTED, false);
                } else {
                    ic_weibo.setImageResource(R.drawable.weibo_selected);
                    tx_weibo.setTextColor(getResources().getColor(R.color.selected_item_color));
                    editor.putBoolean(Constant.WEIBO_SELECTED, true);
                }
                break;
            case R.id.qzone_group:
                if (preferences.getBoolean(Constant.QZONE_SELECTED, false)) {
                    ic_qzone.setImageResource(R.drawable.qzone);
                    tx_qzone.setTextColor(Color.WHITE);
                    editor.putBoolean(Constant.QZONE_SELECTED, false);
                } else {
                    ic_qzone.setImageResource(R.drawable.qzone_selected);
                    tx_qzone.setTextColor(getResources().getColor(R.color.selected_item_color));
                    editor.putBoolean(Constant.QZONE_SELECTED, true);
                }
                break;
        }
        editor.commit();
    }

    @Nullable
    @OnClick(R.id.share_btn)
    public void share(View view) {
        startActivity(new Intent(this, WXEntryActivity.class));
    }

}
