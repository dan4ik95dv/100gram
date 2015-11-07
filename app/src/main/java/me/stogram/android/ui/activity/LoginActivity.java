package me.stogram.android.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
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
import me.stogram.android.application.App;
import me.stogram.android.io.api.client.RestClient;
import me.stogram.android.model.auth.request.RequestLoginUser;
import me.stogram.android.model.auth.response.ResponseLoginUser;
import me.stogram.android.util.Constants;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class LoginActivity extends AppCompatActivity {


    @Bind(R.id.vkButton)
    Button mButtonVK;

    @Bind(R.id.rootLayout)
    RelativeLayout rootLayout;

    @OnClick(R.id.vkButton)
    void onVkButtonClick() {
        if (!VKSdk.isLoggedIn())
        VKSdk.login(this, Constants.VK.MyScopes);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        if (VKSdk.isLoggedIn())
            startMainActivity();
    }


    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                signIn(res);
            }

            @Override
            public void onError(VKError error) {
                Toast.makeText(LoginActivity.this, getResources().getString(R.string.auth_error), Toast.LENGTH_LONG).show();
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void signIn(VKAccessToken res) {
        RestClient.APIService apiService = RestClient.getClient();
        Call<ResponseLoginUser> call = apiService.signInApp(
                new RequestLoginUser(
                        res.accessToken,
                        res.email,
                        res.userId
                )
        );
        call.enqueue(new Callback<ResponseLoginUser>() {
            @Override
            public void onResponse(Response<ResponseLoginUser> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    App.setToken(response.body().getToken());
                    startMainActivity();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(LoginActivity.this, getResources().getString(R.string.connection_error), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void startMainActivity() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }


}