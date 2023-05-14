package com.anshu.helofriend.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anshu.Helofriend.R
import com.anshu.helofriend.Model.Girl

class GirlOnlineAdapter(private val context : Context, private val onlineList: List<Girl>) :
    RecyclerView.Adapter<GirlOnlineAdapter.GirlViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GirlViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_girl_online, parent, false)
        return GirlViewHolder(view)
    }

    override fun getItemCount(): Int {
        return onlineList.size
    }

    override fun onBindViewHolder(holder: GirlViewHolder, position: Int) {
        holder.name.text = onlineList[position].fullName
        holder.category.text = onlineList[position].category
        holder.call.text = onlineList[position].call
        holder.image.setImageResource(onlineList[position].image)
    }

    class GirlViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val layout: LinearLayout = itemView.findViewById(R.id.linearLayout)
        val name: TextView = itemView.findViewById(R.id.GirlName)
        val category: TextView = itemView.findViewById(R.id.girlCategory)
        val call: TextView = itemView.findViewById(R.id.girlCall)
        val image: ImageView = itemView.findViewById(R.id.girlImage)
    }
}