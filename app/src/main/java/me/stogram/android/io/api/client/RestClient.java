package me.stogram.android.io.api.client;


import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

import javax.xml.transform.Templates;

import me.stogram.android.model.auth.request.RequestLoginUser;
import me.stogram.android.model.auth.response.ResponseLoginUser;
import me.stogram.android.model.comment.request.SendComment;
import me.stogram.android.model.comment.response.ResponseComment;
import me.stogram.android.model.comment.response.ResponseComments;
import me.stogram.android.model.feed.response.ResponseFeed;
import me.stogram.android.model.post.Like;
import me.stogram.android.model.post.request.EditPost;
import me.stogram.android.model.post.response.ResponsePost;
import me.stogram.android.model.profile.ExtendedProfile;
import me.stogram.android.model.templates.response.ResponseTemplates;
import me.stogram.android.util.Constants;
import retrofit.Call;
import retrofit.JacksonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
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
        @Headers({"Accept: application/json", "Content-Type: application / json"})
        @POST("auth/")
        Call<ResponseLoginUser> signInApp(@Body RequestLoginUser requestLoginUser);

    /*Лента*/

        //  Получить список постов
        @Headers({"Accept: application/json", "Content-Type: application / json"})
        @GET("feed/")
        Call<ResponseFeed> getFeed(@Query("limit") Integer limit, @Query("offset") Integer offset, @Query("token") String token);

    /*Посты*/

        //  Добавить пост
        @Multipart
        @POST("post/")
        Call<ResponsePost> addPost(@Part("origin\"; filename=\"image.jpg\" ") RequestBody  origin, @Part("body") RequestBody  body, @Query("token") String token);

        //  Получить пост
        @Headers({"Accept: application/json", "Content-Type: application / json"})
        @GET("post/{id}/")
        Call<ResponsePost> getPost(@Path("id") String id, @Query("token") String token);

        //  Редактировать пост
        @Headers({"Accept: application/json", "Content-Type: application / json"})
        @PUT("post/{id}/")
        Call<ResponsePost> editPost(@Path("id") String id, @Body EditPost editPost, @Query("token") String token);

        //  Удалить пост
        @Headers({"Accept: application/json", "Content-Type: application / json"})
        @DELETE("post/{id}/")
        void deletePost(@Path("id") String id, @Query("token") String token);


    /*Комментарии*/

        //    Получить комментарии
        @Headers({"Accept: application/json", "Content-Type: application / json"})
        @GET("post/{postId}/comments/")
        Call<ResponseComments> getComments(@Path("postId") String postId, @Query("limit") Integer limit, @Query("token") String token);

        //    Добавить комментарий
        @Headers({"Accept: application/json", "Content-Type: application / json"})
        @POST("post/{postId}/comment/")
        Call<ResponseComments> addComment(@Path("postId") String postId, @Body SendComment sendComment, @Query("token") String token);

        //    Редактировать комментарий
        @Headers({"Accept: application/json", "Content-Type: application / json"})
        @PUT("post/{postId}/comment/{id}/")
        Call<ResponseComment> editComment(@Path("postId") String postId, @Path("id") String id, @Body SendComment sendComment, @Query("token") String token);

        //    Удалить комментарий
        @Headers({"Accept: application/json", "Content-Type: application / json"})
        @DELETE("post/{postId}/comment/{id}/")
        void deleteComment(@Path("id") String id, @Query("token") String token);

        /*Лайк*/
        //    Поставить/снять лайк
        @Headers({"Accept: application/json", "Content-Type: application / json"})
        @GET("post/{id}/like/")
        Call<Like> setLike(@Path("id") String id, @Query("token") String token);


    /*Профиль*/

        // Получить профиль пользователь
        @Headers({"Accept: application/json", "Content-Type: application / json"})
        @GET("profile/")
        Call<ExtendedProfile> getProfile(@Query("token") String token);

        @Headers({"Accept: application/json", "Content-Type: application / json"})
        @GET("profile/{id}/")
        Call<ExtendedProfile> getProfile(@Path("id") String id, @Query("token") String token);

    /*Шаблоны*/
        //Получить список шаблонов
        @Headers({"Accept: application/json", "Content-Type: application / json"})
        @GET("templates/")
        Call<ResponseTemplates> getTemplates(@Query("token") String token);
    }
}
