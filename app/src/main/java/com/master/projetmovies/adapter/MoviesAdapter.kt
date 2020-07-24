package com.master.projetmovies.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.master.projetmovies.R
import com.master.projetmovies.misc.inflate
import com.master.projetmovies.model.Movie

class MoviesAdapter: RecyclerView.Adapter<MovieViewHolder>() {

    var movieListener: ((Movie) -> Unit)? = null
    var movies = mutableListOf<Movie>()
        set(value) {
            field = value
            notifyDataSetChanged()

        }
    fun appendMovies(movies: List<Movie>) {
        this.movies.addAll(movies)
        notifyItemRangeInserted(
            this.movies.size,
            movies.size - 1
        )
    }
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MovieViewHolder {

        return MovieViewHolder(
            parent.inflate(R.layout.item_movie))
    }

    override fun getItemCount() = movies.size


    override fun onBindViewHolder(holder: MovieViewHolder,
                                  position: Int) {
        val movie = movies[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener { movieListener?.invoke(movie) }
    }



}

class MovieViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    private var mTitleView: TextView = itemView.findViewById(R.id.list_title)
    private var mDescriptionView: TextView = itemView.findViewById(R.id.list_description)
    private var mNoteView: TextView= itemView.findViewById(R.id.list_note)
    private var mImageView: ImageView= itemView.findViewById(R.id.list_image)


    fun bind(movie: Movie) {
        mTitleView.text = movie.title
        mDescriptionView.text = movie.description
        //itemView.setOnClickListener { clickListener(movie)}
        mNoteView.text = movie.note.toString()
        val url = "https://image.tmdb.org/t/p/w500${movie.imageUrl}"
        Glide.with(itemView.context)
            .load(url)
            .placeholder(R.drawable.img_interstellar)
            .into(mImageView)
    }

}
