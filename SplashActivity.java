package com.example.gcoaquira.aplicacionuptbus;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.gcoaquira.aplicacionuptbus.utils.AlertDialogBuilder;
import com.example.gcoaquira.aplicacionuptbus.utils.CommonHelper;

public class SplashActivity extends AppCompatActivity {
    int RC_SIGN_IN = 123;

    private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        iv=(ImageView)findViewById(R.id.iv);
        Animation myanim= AnimationUtils.loadAnimation(this,R.anim.mitransicion);
        iv.startAnimation(myanim);

        try {
            CommonHelper.checkLocationPermission(this);
            /*
            ZF
            boolean isServiceRunning = isMyServiceRunning(DriverService.class);
            if (!isServiceRunning)
                startService(new Intent(SplashActivity.this, DriverService.class));
            */
        } catch (Exception c) {
            c.printStackTrace();
        }

        final Intent i=new Intent(this,LoginActivity.class);
        Thread timer =new Thread(){
            public void run(){
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();

                }
            }
        };
        timer.start();

    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        try {
            ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                if (serviceClass.getName().equals(service.service.getClassName())) {
                    return true;
                }
            }
        } catch (Exception exception) {
            AlertDialogBuilder.show(SplashActivity.this, exception.getMessage());
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            if(resultCode == RESULT_OK){
                /*
                ZF
                IdpResponse idpResponse = IdpResponse.fromResultIntent(data);
                String phone = idpResponse.getPhoneNumber();
                if(phone != null)
                    tryLogin(phone);
                */
                return;
            }
            /*
            ZF
            AlerterHelper.showError(SplashActivity.this, getString(R.string.login_failed));
            */
        }
    }
}
