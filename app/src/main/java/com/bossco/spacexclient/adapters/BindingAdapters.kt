/*
 * *  * Created by Don Muthiani on 5/8/20 9:14 PM  * Copyright (c) 2020 Julla. All rights reserved.  * Last modified 5/8/20 9:13 PM
 */

package com.bossco.spacexclient.adapters

import android.animation.ObjectAnimator
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bossco.spacexclient.R
import com.bossco.spacexclient.models.Info
import com.bossco.spacexclient.models.Rocket
import com.bossco.spacexclient.utils.DateFormatter
import com.bumptech.glide.Glide
import java.util.*


@BindingAdapter("bindInfo")
fun bindCompanyInfo(view: TextView, info: Info?) {
    info?.let {
        view.text = view.context.getString(
            R.string.info_text_template,
            info.name,
            info.founder,
            info.founded.toString(),
            info.employees.toString(),
            info.launch_sites.toString(),
            info.valuation
        )
    }
}

@BindingAdapter("bindDateTime")
fun bindDateTime(view: TextView, date: Long?) {



    date?.let {
        val calender = Calendar.getInstance().apply { timeInMillis = it*1000L }

        val formattedDate =
            DateFormatter.format(calender.time, DateFormatter.Template.STRING_DAY_MONTH_YEAR)

        val formattedTime =
            DateFormatter.format(calender.time, DateFormatter.Template.TIME)

        view.text = view.context.getString(R.string.set_launch_date, formattedDate, formattedTime)
    }

}

@BindingAdapter("bindRocket")
fun bindRocket(view: TextView, rocket: Rocket?) {

    rocket?.let {
            view.text = view.context.getString(
                R.string.rocket_details,
                it.rocket_name,
                it.rocket_type
            )

    }

}



@BindingAdapter("bindMissionImage")
fun bindMissionImage(view: ImageView, image_url: String?) {
    image_url?.let {
        Glide.with(view.context)
            .load(image_url)
            .into(view)
    }



}

@BindingAdapter("bindDaysTitle")
fun bindDaysTitle(view: TextView, date: Long?) {
    date?.let {

        view.text = when (it > Date().time) {
            true -> {
                "Days from now: "
            }
            false -> {
                "Days since now: "
            }
        }
    }
}

@BindingAdapter("bindDaysText")
fun bindDaysText(view: TextView, date: Long?) {
    date?.let {

        view.text = when (it > Date().time) {
            true -> {
                val days = (((it - Date().time) / (1000 * 60 * 60 * 24))).toInt()
                "$days days"

            }
            false -> {
                val days = (((Date().time - it) / (1000 * 60 * 60 * 24))).toInt()
                "$days days"
            }
        }
    }
}

@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean?) {

    isGone?.let {

        when (it) {
            true -> {
                val animator = ObjectAnimator.ofFloat(view, View.ALPHA, 0f)
                animator.duration = 500
                animator.start()
                view.visibility = View.GONE
            }
            else -> {
                val animator = ObjectAnimator.ofFloat(view, View.ALPHA, 1f)
                animator.duration = 500
                animator.start()
                view.visibility = View.VISIBLE
            }
        }
    }
}




