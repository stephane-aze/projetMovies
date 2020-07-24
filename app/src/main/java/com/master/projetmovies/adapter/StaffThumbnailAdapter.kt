package com.master.projetmovies.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.master.projetmovies.R
import com.master.projetmovies.misc.inflate
import com.master.projetmovies.model.Staff

class StaffThumbnailAdapter: RecyclerView.Adapter<StaffThumbnailHolder>() {

    var staff: List<Staff> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffThumbnailHolder {
        return StaffThumbnailHolder(
            parent.inflate(
                R.layout.item_productor_details
            )
        )
    }
    override fun getItemCount() = staff.size
    override fun onBindViewHolder(holder: StaffThumbnailHolder, position: Int) {
        //val itemList = dataList.get(position) --> Gladiator
        //holder description de ma vue
        val staff = staff[position]
        holder.bindData(staff)

    }
}
class StaffThumbnailHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    //décrire le contenue d'un item
    private var typeProductor: TextView = itemView.findViewById(R.id.type_productor)
    private var nameProductor: TextView = itemView.findViewById(R.id.info_productor)
    private var picturePersonStaff: ImageView = itemView.findViewById(R.id.thumbnail_image_staff)
    fun bindData(staff: Staff) {
        val context = itemView.context
        typeProductor.text = staff.job
        //nameTxv.text = context.getString(R.string.weapon_name, actor.name)
        nameProductor.text = staff.name
        Glide.with(context)
            .load("https://image.tmdb.org/t/p/w500${staff.profilePath}")
            .error(R.drawable.ic_baseline_person_24)
            .into(picturePersonStaff)
    }
}