package me.stogram.android.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.devspark.robototextview.widget.RobotoTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.stogram.android.R;
import me.stogram.android.application.App;
import me.stogram.android.io.api.client.RestClient;
import me.stogram.android.model.profile.ExtendedProfile;
import me.stogram.android.util.Constants;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class ProfileFragment extends Fragment {

    @Bind(R.id.userImage)
    ImageView userImage;
    @Bind(R.id.userName)
    RobotoTextView userName;
    @Bind(R.id.userStatus)
    RobotoTextView userStatus;
    @Bind(R.id.userUrl)
    RobotoTextView userUrl;

    public static ProfileFragment init() {
        return new ProfileFragment();
    }

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        getProfile();
        return view;
    }

    private void getProfile() {
        RestClient.APIService apiService = RestClient.getClient();
        Call<ExtendedProfile> call = apiService.getProfile(App.getToken());
        call.enqueue(new Callback<ExtendedProfile>() {
            @Override
            public void onResponse(Response<ExtendedProfile> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    if (response.body() != null) {
                        Glide.with(getContext()).load(Constants.API.HOST_URL + response.body().getThumbPhoto())
                                .centerCrop().
                                diskCacheStrategy(DiskCacheStrategy.ALL)
                                .dontAnimate()
                                .into(userImage);

                        userName.setText(response.body().getLastName() + " " + response.body().getFirstName());
                        userStatus.setText(response.body().getStatus() != null ? response.body().getStatus() : "");
                        userUrl.setText(response.body().getUrl() != null ? response.body().getStatus() : "");
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getContext(), getResources().getString(R.string.connection_error), Toast.LENGTH_LONG).show();
            }
        });
    }

}
