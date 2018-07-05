package com.demotxt.droidsrce.homedashboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by quocnguyen on 08/07/2017.
 */
public class SplashActivity extends Activity{

    ImageView imageViewSplash;
    TextView txtAppName, textViewSplash;
    RelativeLayout relativeLayout;
    Thread SplashThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        imageViewSplash = (ImageView ) findViewById(R.id.imageViewSplash2);
        textViewSplash = (TextView ) findViewById(R.id.textViewSplash);

        startAnimations();
    }

    private void startAnimations() {

        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate);
        imageViewSplash.startAnimation(animation1);

        textViewSplash.setText("Designed by Lamanna Nicolas");

        SplashThread = new Thread(){
            @Override
            public void run() {
                super.run();

                int waited = 0;
                while (waited < 5000) {
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    waited += 100;
                }
                SplashActivity.this.finish();




                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        };

        SplashThread.start();
    }
}