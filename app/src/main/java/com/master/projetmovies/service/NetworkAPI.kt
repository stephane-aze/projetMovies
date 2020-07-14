package com.master.projetmovies.service


import com.master.projetmovies.service.dto.*
//import io.reactivex.rxjava3.core.Observable

import retrofit2.Call
import retrofit2.http.*

interface NetworkAPI {
    @GET("movie/popular")

    fun getPopularMovies(@Query("api_key") apiKey:String,
                  @Query("language") language:String ="fr-FR",
                  @Query("page") page: Int) : /*Observable<BaseResponse<MovieDTO>>*/Call<BaseResponse<MovieDTO>>
    @GET("movie/{movie_id}")
    fun getMovie(@Path("movie_id") movieId: Int,
                 @Query("language") language:String ="fr-FR",
                 @Query("api_key") apiKey:String
                 ): /*Observable<MovieDetailsDTO>*/Call<MovieDetailsDTO>
    @GET("movie/{movie_id}/credits")
    fun getCastMovie(@Path("movie_id") movieId: Int,@Query("api_key") apiKey:String
                     ): Call<GetCastAndStaffResponse>
}