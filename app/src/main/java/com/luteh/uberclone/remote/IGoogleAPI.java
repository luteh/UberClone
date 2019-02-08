package com.luteh.uberclone.remote;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Luthfan Maftuh on 07/02/2019.
 * Email luthfanmaftuh@gmail.com
 */
public interface IGoogleAPI {
    @GET
    Call<String> getPath(@Url String url);
}
