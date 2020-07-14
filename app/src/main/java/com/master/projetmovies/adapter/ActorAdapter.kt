package com.master.projetmovies.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.master.projetmovies.R
import com.master.projetmovies.misc.inflate
import com.master.projetmovies.model.Actor

class ActorAdapter: RecyclerView.Adapter<ActorViewHolder>() {

    var actors: List<Actor> = listOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
/*        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_weapon, parent, false)*/
        return ActorViewHolder(
            parent.inflate(
                R.layout.item_actor
            )
        )
    }
    override fun getItemCount() = actors.size
    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        //val itemList = dataList.get(position) --> Gladiator
        //holder description de ma vue
        val actor = actors[position]
        holder.bindData(actor)

    }
}
class ActorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    //décrire le contenue d'un item
    private var pictureImv: ImageView = itemView.findViewById(R.id.thumbnail_image_actor)
    private var nameTxv: TextView = itemView.findViewById(R.id.name_actor)
    private var roleTxv: TextView = itemView.findViewById(R.id.character_actor)
    fun bindData(actor: Actor) {
        val context = itemView.context
        Glide.with(context)
            .load("https://image.tmdb.org/t/p/w500${actor.thumbnail}")
            .into(pictureImv)
        roleTxv.text = actor.characterName
        //nameTxv.text = context.getString(R.string.weapon_name, actor.name)
        nameTxv.text = actor.name
    }
}