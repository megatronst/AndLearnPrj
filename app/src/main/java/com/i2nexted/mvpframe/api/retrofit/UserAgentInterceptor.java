package com.i2nexted.mvpframe.api.retrofit;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/24 0024.
 */

public class UserAgentInterceptor implements Interceptor {
    private final String userAgent ;

    public UserAgentInterceptor(String userAgent){
        this.userAgent = userAgent ;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        String USER_AGENT = "User-Agent";
        Request requestWithUserAgent = originalRequest.newBuilder()
                .removeHeader(USER_AGENT)
                .addHeader(USER_AGENT, userAgent)
                .build() ;
        return chain.proceed(requestWithUserAgent);
    }
}