package com.luteh.uberclone.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.luteh.uberclone.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Luthfan Maftuh on 17/12/2018.
 * Email luthfanmaftuh@gmail.com
 */
public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // init font
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Arkhip_font.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        unbinder = ButterKnife.bind(this);

        onInit();
    }

    protected void onInit() {

    }

    public final void startActivityFromRight(Class clazz) {
        startActivity(clazz);
        overridePendingTransition(R.anim.anim_enter_right, R.anim.anim_sticky);
    }

    public final void startActivity(Class clazz) {
        startActivity(clazz, null);
    }

    public final void startActivity(Class clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public final void finishToRight() {
        finish();
        overridePendingTransition(R.anim.anim_sticky, R.anim.anim_leave_right);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
