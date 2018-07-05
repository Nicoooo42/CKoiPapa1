package com.demotxt.droidsrce.homedashboard;

import android.animation.Animator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.LinearLayout;

public class Home extends AppCompatActivity {
    public final static String AGE = "com.demotxt.droidsrce.homedashboard.AGE";
    CardView mycard ;
    Intent i ;
    LinearLayout ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ll = (LinearLayout) findViewById(R.id.ll);
        mycard = (CardView) findViewById(R.id.bankcardId);
        i = new Intent(this,ae.class);

        mycard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent camera = new Intent(Home.this, CameraActivity.class);
                camera.putExtra(AGE,"test_value");

                // Puis on lance l'intent !
                startActivity(camera);
            }
        });

    }
}
