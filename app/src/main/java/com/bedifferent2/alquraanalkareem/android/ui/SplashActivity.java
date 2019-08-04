package com.bedifferent2.alquraanalkareem.android.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bedifferent2.alquraanalkareem.android.QuranDataActivity;

public class SplashActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    new Handler().postDelayed(new Runnable(){
      @Override
      public void run() {
                /* Create an Intent that will start the Menu-Activity. */
        Intent mainIntent = new Intent(SplashActivity.this , QuranDataActivity.class);
        SplashActivity.this.startActivity(mainIntent);
        SplashActivity.this.finish();
      }
    }, 3000);

  }

}
