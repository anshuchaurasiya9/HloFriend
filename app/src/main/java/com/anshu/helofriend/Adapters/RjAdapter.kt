package com.anshu.helofriend.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anshu.helofriend.Model.RjUser
import com.anshu.Helofriend.R
import de.hdodenhof.circleimageview.CircleImageView

class RjAdapter(private var userList: List<RjUser>) :
    RecyclerView.Adapter<RjAdapter.rjViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): rjViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_rj , parent , false)
        return rjViewHolder(view)
    }

    override fun getItemCount() :Int{
      return  userList.size

    }

    fun updateUserList(userList : List<RjUser>){

//        this.userList.clear()
//        this.userList.addAll(userList)
        notifyDataSetChanged()

    }

    override fun onBindViewHolder(holder: rjViewHolder, position: Int) {

//        holder.image.setImageResource(userList[position].image!!)
        holder.name.text = userList[position].name
        holder.title.text = userList[position].title
        holder.room.text = userList[position].room

    }
  class rjViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val name : TextView = itemView.findViewById(R.id.rjName)
        val title : TextView = itemView.findViewById(R.id.rjTitle)
        val image : CircleImageView = itemView.findViewById(R.id.rjImage)
        val room : Button = itemView.findViewById(R.id.rjRoom)
    }
}


