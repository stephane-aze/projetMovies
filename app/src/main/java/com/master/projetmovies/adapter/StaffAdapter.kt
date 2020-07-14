package com.master.projetmovies.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.master.projetmovies.R
import com.master.projetmovies.misc.inflate
import com.master.projetmovies.model.Staff

class StaffAdapter: RecyclerView.Adapter<StaffViewHolder>() {

    var staff: List<Staff> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffViewHolder {
/*        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_weapon, parent, false)*/
        return StaffViewHolder(
            parent.inflate(
                R.layout.item_productor
            )
        )
    }
    override fun getItemCount() = staff.size
    override fun onBindViewHolder(holder: StaffViewHolder, position: Int) {
        //val itemList = dataList.get(position) --> Gladiator
        //holder description de ma vue
        val staff = staff[position]
        holder.bindData(staff)

    }
}
class StaffViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    //décrire le contenue d'un item
    private var typeProductor: TextView = itemView.findViewById(R.id.type_productor)
    private var nameProductor: TextView = itemView.findViewById(R.id.info_productor)
    fun bindData(staff: Staff) {
        typeProductor.text = staff.job
        //nameTxv.text = context.getString(R.string.weapon_name, actor.name)
        nameProductor.text = staff.name
    }
}