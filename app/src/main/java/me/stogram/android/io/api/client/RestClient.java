package me.stogram.android.io.api.client;


import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import me.stogram.android.model.auth.request.RequestLoginUser;
import me.stogram.android.model.auth.response.ResponseLoginUser;
import me.stogram.android.model.comment.request.SendComment;
import me.stogram.android.model.comment.response.ResponseComment;
import me.stogram.android.model.comment.response.ResponseComments;
import me.stogram.android.model.feed.response.ResponseFeed;
import me.stogram.android.model.post.Like;
import me.stogram.android.model.post.request.EditPost;
import me.stogram.android.model.post.request.SendPost;
import me.stogram.android.model.post.response.ResponsePost;
import me.stogram.android.model.profile.ExtendedProfile;
import me.stogram.android.util.Constants;
import retrofit.Call;
import retrofit.JacksonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Daniil Celikin on 07.11.2015.
 */
public class RestClient {
    private static APIService APIService;


    public static APIService getClient() {
        if (APIService == null) {
            OkHttpClient httpClient = new OkHttpClient();
            httpClient.networkInterceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder()
                            .addHeader("Accept", "application/json")
                            .addHeader("Content-Type", "application / json")
                            .build();
                    return chain.proceed(request);
                }
            });
            httpClient.networkInterceptors().add(new StethoInterceptor());
            Retrofit client = new Retrofit.Builder()
                    .baseUrl(Constants.API.BASE_URL)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .client(httpClient)
                    .build();
            APIService = client.create(APIService.class);
        }
        return APIService;
    }

    public interface APIService {

    /*Авторизация*/

        @POST("auth/")
        Call<ResponseLoginUser> signInApp(@Body RequestLoginUser requestLoginUser);

    /*Лента*/

        //  Получить список постов
        @GET("feed/")
        Call<ResponseFeed> getFeed(@Query("limit") Integer limit, @Query("offset") Integer offset, @Query("token") String token);

    /*Посты*/

        //  Добавить пост
        @POST("post/")
        Call<ResponsePost> addPost(@Body SendPost sendPost, @Query("token") String token);

        //  Получить пост
        @GET("post/{id}/")
        Call<ResponsePost> getPost(@Path("id") int id, @Query("token") String token);

        //  Редактировать пост
        @PUT("post/{id}/")
        Call<ResponsePost> editPost(@Path("id") int id, @Body EditPost editPost, @Query("token") String token);

        //  Удалить пост
        @DELETE("post/{id}/")
        void deletePost(@Path("id") int id, @Query("token") String token);


    /*Комментарии*/

        //    Получить комментарии
        @GET("post/{postId}/comments/")
        Call<ResponseComments> getComments(@Path("postId") int postId, @Query("limit") Integer limit, @Query("token") String token);

        //    Добавить комментарий
        @POST("post/{postId}/comment/")
        Call<ResponseComment> addComment(@Path("postId") int postId, @Body SendComment sendComment, @Query("token") String token);

        //    Редактировать комментарий
        @PUT("post/{postId}/comment/{id}/")
        Call<ResponseComment> editComment(@Path("postId") int postId, @Path("id") int id, @Body SendComment sendComment, @Query("token") String token);

        //    Удалить комментарий
        @DELETE("post/{postId}/comment/{id}/")
        void deleteComment(@Path("id") int id, @Query("token") String token);

        /*Лайк*/
        //    Поставить/снять лайк
        @POST("post/{id}/like/")
        Call<Like> setLike(@Path("id") int id, @Query("limit") Integer limit, @Query("token") String token);


    /*Профиль*/

        // Получить профиль пользователь
        @GET("profile/")
        Call<ExtendedProfile> getProfile(@Query("token") String token);

        @GET("profile/{id}/")
        Call<ExtendedProfile> getProfile(@Path("id") int id, @Query("token") String token);

    }
}
