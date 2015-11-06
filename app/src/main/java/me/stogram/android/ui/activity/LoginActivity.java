package me.stogram.android.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.stogram.android.R;

import me.stogram.android.util.Constants;

public class LoginActivity extends AppCompatActivity {


    @Bind(R.id.vkButton)
    Button mButtonVK;

    @Bind(R.id.rootLayout)
    RelativeLayout rootLayout;

    @OnClick(R.id.vkButton)
    void onVkButtonClick() {
        VKSdk.login(this, Constants.VK.MyScopes);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

//        ViewTreeObserver vto = waveView.getViewTreeObserver();
//        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                waveView.setBorder(0, getResources().getColor(android.R.color.transparent));
//                waveView.setWaterLevelRatio(.8f);
//                waveView.setShapeType(WaveView.ShapeType.SQUARE);
//                waveView.setWaveColor(getResources().getColor(R.color.wave_primary), getResources().getColor(R.color.wave_secondary));
//                waveView.setShowWave(true);
//                mWaveHelper = new WaveHelper(waveView);
//                mWaveHelper.start();
//                rootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//            }
//        });


    }


    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume () {
        super.onResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                Log.d("AccessToken",res.accessToken);
                finish();
            }

            @Override
            public void onError(VKError error) {
                Toast.makeText(LoginActivity.this, getResources().getString(R.string.error), Toast.LENGTH_LONG).show();
            }
        })){
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}