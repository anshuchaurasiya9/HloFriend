package com.anshu.helofriend.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.anshu.Helofriend.R
import com.anshu.helofriend.Activity.addCoinWalletActivity
import com.anshu.helofriend.Adapters.WalletAdapter.walletViewHolder
import com.anshu.helofriend.Model.Wallet

class WalletAdapter(var walletList: List<Wallet>):
    RecyclerView.Adapter<walletViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): walletViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_wallet , parent , false)
        return walletViewHolder(view)
    }

    override fun getItemCount(): Int {
        return walletList.size
    }

    override fun onBindViewHolder(holder: walletViewHolder, position: Int) {
        holder.coins.text = walletList[position].coins.toString()
        holder.discount.text = walletList[position].discount.toString()
        holder.price.text = walletList[position].price.toString()


        val cont = holder.constraint_row.context
        holder.cardView.setOnClickListener {
            val intent = Intent(it.context, addCoinWalletActivity::class.java)

            intent.putExtra("coins",walletList[position].coins.toString())
            intent.putExtra("price",walletList[position].price.toString())

            it.context.startActivity(intent)

            Toast.makeText(cont, "the the package of ${walletList[position].coins.toString()} is clicked", Toast.LENGTH_SHORT).show()
    }
        holder.constraint_row.setOnLongClickListener(View.OnLongClickListener {
            Toast.makeText(cont, "the package of ${walletList[position].price.toString()} is long clicked", Toast.LENGTH_SHORT).show()

            return@OnLongClickListener true
        })
        holder.cardView.startAnimation(AnimationUtils.loadAnimation(holder.cardView.context, R.anim.scale_up))
    }



   inner class walletViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

       val coins : TextView = itemView.findViewById(R.id.itemCoins)
        val discount : TextView = itemView.findViewById(R.id.itemDiscount)
        val price : TextView = itemView.findViewById(R.id.itemPrice)

       val constraint_row : ConstraintLayout = itemView.findViewById(R.id.constraint_row)
       val cardView : CardView = itemView.findViewById(R.id.cardView)
    }

}