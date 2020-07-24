package com.master.projetmovies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.master.projetmovies.R
import com.master.projetmovies.misc.inflate
import com.master.projetmovies.model.Movie


class MovieThumbnailAdapter: RecyclerView.Adapter<MovieThumbnailViewHolder>(){

    var movieListener: ((Movie) -> Unit)? = null
    var movies = mutableListOf<Movie>()
        set(value) {
            field = value
            notifyDataSetChanged()
            notifyItemRangeInserted(
                this.movies.size,
                movies.size - 1
            )
        }
    fun appendMovies(movies: List<Movie>) {
        this.movies.addAll(movies)
        notifyItemRangeInserted(
            this.movies.size,
            movies.size - 1
        )
    }
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MovieThumbnailViewHolder {

        return MovieThumbnailViewHolder(
                parent.inflate(R.layout.thumbnail_movie))
    }

    override fun getItemCount() = movies.size


    override fun onBindViewHolder(holder: MovieThumbnailViewHolder,
                                  position: Int) {
        val movie = movies[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener { movieListener?.invoke(movie) }
    }



}
class MovieThumbnailViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

    private val textView : TextView = itemView.findViewById(R.id.thumbnail_title_movie)
    private val imageView: ImageView = itemView.findViewById(R.id.thumbnail_image_movie)

    fun bind(movie: Movie) {
        textView.text = movie.title
        val url =  "https://image.tmdb.org/t/p/w342${movie.imageUrl}"
        Glide.with(itemView)
            .load(url)
            .placeholder(R.drawable.img_interstellar)
            .transform(CenterCrop())
            .into(imageView)


    }

}