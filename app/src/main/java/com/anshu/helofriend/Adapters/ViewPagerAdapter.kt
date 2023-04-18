package com.anshu.helofriend.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.airbnb.lottie.LottieAnimationView
import com.anshu.Helofriend.R

class ViewPagerAdapter(val context: Context) : PagerAdapter() {

    var layoutInflater: LayoutInflater? = null

    //array of image, Title and description

    val imgArray = arrayOf(
        R.raw.real_profile,
        R.raw.video_call,
        R.raw.safety,
        R.raw.no_share,
        R.raw.chat_room
    )

    val headArray = arrayOf(
        "Make Real Friends",
        "High quality Audio & Video calls",
        "100 % Safe And secure",
        "We don't share your any information",
        "Create room for group chats"
    )

    val arrayDescription = arrayOf(
        "Our team work harder to verify every single person, keep connecting everyone is genuine here",
        "Interact and say HeloFriend to your friends with our High-Quality Audio and Video calls",
        "Our team takes strict action against those who violates term and conditions",
        "We do not share your data with anyone on any condition",
        "You can discuss with your friends in Chat Room with voice messages and many more"
    )

    //JsonReader.setLenient(true)

    override fun getCount(): Int {
        return headArray.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as ConstraintLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater!!.inflate(R.layout.slider, container, false)

        val img = view.findViewById<LottieAnimationView>(R.id.animation_view)
        val header = view.findViewById<TextView>(R.id.header)
        val desc = view.findViewById<TextView>(R.id.description)

        img.setAnimation(imgArray[position])
        header.text = (headArray[position])
        desc.text = (arrayDescription[position])

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }
}


