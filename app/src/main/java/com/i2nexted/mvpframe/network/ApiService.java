package com.i2nexted.mvpframe.network;

import com.i2nexted.mvpframe.api.response.HttpResult;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * 用于一般的请求
 * */

public interface ApiService {



    @GET("{page}/{size}")
    Observable<HttpResult<List<ItemGank>>> getGank(@Path("page") int page, @Path("size") int size);

}
