package com.anshu.helofriend.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Visibility
import com.anshu.Helofriend.R
import com.anshu.Helofriend.databinding.ActivityWalletBinding
import com.anshu.helofriend.Adapters.WalletAdapter
import com.anshu.helofriend.Model.Wallet

class WalletActivity : AppCompatActivity() {

    private lateinit var recView: RecyclerView
    private lateinit var coins: Array<String>
    private lateinit var discount: Array<String>
    private lateinit var price: Array<String>
    private lateinit var itemArrayList: ArrayList<Wallet>


    lateinit var binding: ActivityWalletBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWalletBinding.inflate(layoutInflater)
        setContentView(binding.root)



        coins = arrayOf(
            "168000",
            "34000",
            "15000",
            "5500",
            "2500",
            "1200",
            "440",
            "200",
            "90",
            "40"

        )
        discount = arrayOf(
            "save 60%",
            "save 60%",
            "save 40%",
            "save 33%",
            "save 30%",
            "save 25%",
            "save 20%",
            "save 10%",
            "save 10%",
            "save 10%")

        price = arrayOf(
            "49999",
            "9999",
            "4999",
            "1999",
            "999",
            "499",
            "199",
            "99",
            "49",
            "25"

        )



        recView = binding.recWallet
        recView.layoutManager = GridLayoutManager(this,3)
        recView.setHasFixedSize(true)

        itemArrayList = arrayListOf()
        getData()
//        addDataToList()
        recView.adapter = WalletAdapter(itemArrayList)



    }

    private fun getData() {
        for (i in coins.indices) {
            val wallet = Wallet(coins[i], discount[i], price[i])
            itemArrayList.add(wallet)

        }


    }

  /*  private fun addDataToList() {
        itemArrayList.add(Wallet("38000", "60%", "9999"))
        itemArrayList.add(Wallet("38000", "60%", "9999"))
        itemArrayList.add(Wallet("38000", "60%", "9999"))
    }*/
}