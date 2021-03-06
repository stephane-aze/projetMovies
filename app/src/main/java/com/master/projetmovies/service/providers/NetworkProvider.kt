package com.master.projetmovies.service.providers

import android.util.Log
import com.master.projetmovies.misc.toConversionDuration
import com.master.projetmovies.misc.toConversionYear
import com.master.projetmovies.model.CastAndStaff
import com.master.projetmovies.model.Genre
import com.master.projetmovies.model.Movie
import com.master.projetmovies.service.NetworkAPI
import com.master.projetmovies.service.dto.*
import com.master.projetmovies.service.mapper.CastAndStaffMapper
import com.master.projetmovies.service.mapper.GenreMapper
import com.master.projetmovies.service.mapper.MovieMapper
//import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
//import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
const val apiKey="0cb833700f6a5927eea92e148fe0ff60"
const val movieIdInterstellar=157336
const val URL = "https://api.themoviedb.org/3/"

object NetworkProvider {
    private val networkAPI: NetworkAPI = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        //.addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(NetworkAPI::class.java)

    fun getPopularMovies(page: Int = 1,listener: NetworkListener<List<Movie>>) /*:Observable<BaseResponse<MovieDTO>>*/{
        /*return */networkAPI.getPopularMovies(page = page,apiKey = apiKey)
            .enqueue(object : Callback<BaseResponse<MovieDTO>> {
                override fun onResponse(
                    call: Call<BaseResponse<MovieDTO>>,
                    response: Response<BaseResponse<MovieDTO>>
                ) {
                    val moviesResponse:BaseResponse<MovieDTO>? = response.body()
                    moviesResponse?.let {notNullMoviesResponse->
                        val movies: List<Movie> = MovieMapper().map(notNullMoviesResponse.results)
                        listener.onSuccess(movies)
                    } ?: listener.onError(Exception())
                }

                override fun onFailure(call: Call<BaseResponse<MovieDTO>>, t: Throwable) {
                    listener.onError(t)
                }
            })
    }
    fun getTopRatedMovies(
        page: Int = 1,
        listener: NetworkListener<List<Movie>>
    ) {
        networkAPI.getTopRatedMovies(page = page,apiKey = apiKey)
            .enqueue(object : Callback<BaseResponse<MovieDTO>> {
                override fun onFailure(call: Call<BaseResponse<MovieDTO>>, t: Throwable) {
                    listener.onError(t)
                }

                override fun onResponse(
                    call: Call<BaseResponse<MovieDTO>>,
                    response: Response<BaseResponse<MovieDTO>>
                ) {
                    val moviesResponse:BaseResponse<MovieDTO>? = response.body()
                    moviesResponse?.let {notNullMoviesResponse->
                        val movies: List<Movie> = MovieMapper().map(notNullMoviesResponse.results)
                        listener.onSuccess(movies)
                    } ?: listener.onError(Exception())
                }
            })
    }
    fun getMovie(id:Long, listener: NetworkListener<Movie>) {
        networkAPI.getMovie(apiKey = apiKey,movieId = id)
            .enqueue(object : Callback<MovieDetailsDTO> {

                override fun onResponse(call: Call<MovieDetailsDTO>, response: Response<MovieDetailsDTO>) {
                    val movieDTO = response.body()
                    movieDTO?.let {
                        val movie: Movie = Movie(title = it.title,imageUrl = it.posterPath,note = it.rating,description = it.overview,id = it.id, genres = it.genres,duration = it.duration?.toConversionDuration()?:"",year = it.releaseDate?.toConversionYear()?:2014,nbReview = it.popularity?:46f)
                        listener.onSuccess(movie)
                    }
                }

                override fun onFailure(call: Call<MovieDetailsDTO>, t: Throwable) {
                    listener.onError(t)
                }
            })
    }
    fun getCastingMovie(listener: NetworkListener<CastAndStaff>, id:Long) {
        networkAPI.getCastMovie(apiKey = apiKey,movieId = id).enqueue(object :
            Callback<GetCastAndStaffResponse> {

            override fun onResponse(call: Call<GetCastAndStaffResponse>, response: Response<GetCastAndStaffResponse>) {
                val getCastAndStaffResponse = response.body()
                getCastAndStaffResponse?.let { castAndStaffResponse ->
                    val castAndStaff=CastAndStaffMapper().mapCastAndStaff(castAndStaffResponse)
                    listener.onSuccess(castAndStaff)
                }
            }

            override fun onFailure(call: Call<GetCastAndStaffResponse>, t: Throwable) {
                listener.onError(t)
            }
        })
    }
    fun getUpcomingMovies(
        page: Int = 1,
        listener: NetworkListener<List<Movie>>
    ) {
        networkAPI.getUpcomingMovies(page = page,apiKey = apiKey).enqueue(object : Callback<BaseResponse<MovieDTO>> {
                override fun onFailure(call: Call<BaseResponse<MovieDTO>>, t: Throwable) {
                    listener.onError(t)
                }

                override fun onResponse(
                    call: Call<BaseResponse<MovieDTO>>,
                    response: Response<BaseResponse<MovieDTO>>
                ) {
                    val moviesResponse:BaseResponse<MovieDTO>? = response.body()
                    moviesResponse?.let {notNullMoviesResponse->
                        val movies: List<Movie> = MovieMapper().map(notNullMoviesResponse.results)
                        listener.onSuccess(movies)
                    } ?: listener.onError(Exception())
                }

            })
    }
    fun searchMovies(
       query:String,
        listener: NetworkListener<List<Movie>>
    ) {
        networkAPI.searchMovies(query = query,apiKey = apiKey).enqueue(object : Callback<BaseResponse<MovieDTO>> {
                override fun onFailure(call: Call<BaseResponse<MovieDTO>>, t: Throwable) {
                    listener.onError(t)
                }

                override fun onResponse(
                    call: Call<BaseResponse<MovieDTO>>,
                    response: Response<BaseResponse<MovieDTO>>
                ) {
                    val moviesResponse:BaseResponse<MovieDTO>? = response.body()
                    moviesResponse?.let {notNullMoviesResponse->
                        val movies: List<Movie> = MovieMapper().map(notNullMoviesResponse.results)
                        listener.onSuccess(movies)
                    } ?: listener.onError(Exception())
                }

            })
    }
    fun searchMoviesByYear(
       query:String,
        listener: NetworkListener<List<Movie>>
    ) {
        networkAPI.searchMoviesByYear(query = query,apiKey = apiKey).enqueue(object : Callback<BaseResponse<MovieDTO>> {
                override fun onFailure(call: Call<BaseResponse<MovieDTO>>, t: Throwable) {
                    listener.onError(t)
                }

                override fun onResponse(
                    call: Call<BaseResponse<MovieDTO>>,
                    response: Response<BaseResponse<MovieDTO>>
                ) {
                    val moviesResponse:BaseResponse<MovieDTO>? = response.body()
                    moviesResponse?.let {notNullMoviesResponse->
                        val movies: List<Movie> = MovieMapper().map(notNullMoviesResponse.results)
                        listener.onSuccess(movies)
                    } ?: listener.onError(Exception())
                }

            })
    }
    fun getGenresMovies(
        listener: NetworkListener<List<Genre>>
    ) {
        networkAPI.getGenresMovies(apiKey = apiKey).enqueue(object : Callback<List<GenreDTO>> {
            override fun onFailure(call: Call<List<GenreDTO>>, t: Throwable) {
                listener.onError(t)
            }

            override fun onResponse(
                call: Call<List<GenreDTO>>,
                response: Response<List<GenreDTO>>
            ) {
                val listGenre:List<GenreDTO>? = response.body()
                listGenre?.let {notNullListGenre->
                    val genres: List<Genre> = GenreMapper().map(notNullListGenre)
                    listener.onSuccess(genres)
                } ?: listener.onError(Exception())
            }

        })
    }



}
interface NetworkListener<T>{
    fun onSuccess(data: T)
    fun onError(throwable: Throwable)
}