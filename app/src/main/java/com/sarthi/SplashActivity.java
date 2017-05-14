package com.sarthi;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sarthi.Helper.Const;
import com.sarthi.Helper.DisplayUtils;
import com.sarthi.Login.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            DisplayUtils.makeStatusBarTransperent(true, getWindow());
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(getSharedPreferences(Const.SHAREDPREFERENCE_NAME,MODE_PRIVATE).getString(Const.USER_ID,null) == null){
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        },1500);
    }

}
