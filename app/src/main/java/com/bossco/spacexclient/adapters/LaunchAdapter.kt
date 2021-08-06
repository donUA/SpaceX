package com.bossco.spacexclient.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bossco.spacexclient.R
import com.bossco.spacexclient.adapters.LaunchAdapter.*
import com.bossco.spacexclient.databinding.ItemLaunchBinding
import com.bossco.spacexclient.interfaces.MainInterface
import com.bossco.spacexclient.models.Launch
import com.github.zawadz88.materialpopupmenu.popupMenu

/**
 * Created by Don Muthiani on 8/6/21.
 * Copyright (c) 2021 Accuret. All rights reserved.

 */

class LaunchAdapter(private val mainInterface: MainInterface): ListAdapter<Launch, LaunchViewHolder>(LaunchDiff()) {

    class LaunchViewHolder(private val binding: ItemLaunchBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Launch, mainInterface: MainInterface){

            binding.apply {

                launch = item
                item.launch_success?.let {
                    isLaunchSuccess = it
                }
                setClickListener {
                    val popupMenu = popupMenu {
                        section {
                            item {
                                label = "Open article"
                                icon = R.drawable.ic_open //optional
                                callback = { //optional
                                    mainInterface.openArticle(item)
                                }
                            }
                        }
                        section {
                            item {
                                label = "Open Wikipedia"
                                icon = R.drawable.ic_wikipedia //optional
                                callback = { //optional
                                    mainInterface.openWikipedia(item)
                                }
                            }
                        }
                        section {
                            item {
                                label = "Open Video"
                                icon = R.drawable.ic_video //optional
                                callback = { //optional
                                    mainInterface.openVideo(item)
                                }
                            }
                        }
                    }
                    popupMenu.show(binding.mainView.context, binding.mainView)
                }
            }


        }

    }

    class LaunchDiff(): DiffUtil.ItemCallback<Launch>(){
        override fun areItemsTheSame(oldItem: Launch, newItem: Launch): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Launch, newItem: Launch): Boolean {
            return oldItem==newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchViewHolder {
        return LaunchViewHolder(
            ItemLaunchBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }


    override fun onBindViewHolder(holder: LaunchViewHolder, position: Int) {
        holder.bind(getItem(position), mainInterface)
    }
}