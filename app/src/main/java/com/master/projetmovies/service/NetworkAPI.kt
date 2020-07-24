package com.master.projetmovies.service


import com.master.projetmovies.service.dto.*
//import io.reactivex.rxjava3.core.Observable

import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.*

interface NetworkAPI {
    @GET("movie/popular")

    fun getPopularMovies(@Query("api_key") apiKey:String,
                  @Query("language") language:String ="fr-FR",
                  @Query("page") page: Int) : /*Observable<BaseResponse<MovieDTO>>*/Call<BaseResponse<MovieDTO>>
    @GET("movie/{movie_id}")
    fun getMovie(@Path("movie_id") movieId: Long,
                 @Query("language") language:String ="fr-FR",
                 @Query("api_key") apiKey:String
                 ): /*Observable<MovieDetailsDTO>*/Call<MovieDetailsDTO>
    @GET("movie/{movie_id}/credits")
    fun getCastMovie(@Path("movie_id") movieId: Long,@Query("api_key") apiKey:String
                     ): Call<GetCastAndStaffResponse>
    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language:String ="fr-FR",

        @Query("page") page: Int

    ): Call<BaseResponse<MovieDTO>>
    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String,

        @Query("page") page: Int
    ): Call<BaseResponse<MovieDTO>>
    @GET("genre/movie/list")
    fun getGenresMovies(
        @Query("api_key") apiKey: String
    ):Call<List<GenreDTO>>
    @GET("search/movie")
    fun searchMovies(
        @Query("query") query: String,
        @Query("api_key") apiKey: String
    ): Call<BaseResponse<MovieDTO>>
    @GET("discover/movie")
    fun searchMoviesByYear(
        @Query("year") query: String,
        @Query("api_key") apiKey: String
    ): Call<BaseResponse<MovieDTO>>
}