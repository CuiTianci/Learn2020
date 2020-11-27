package com.example.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class Item0Adapter :
    ListAdapter<VideoData, Item0Adapter.ViewHolder>(object : DiffUtil.ItemCallback<VideoData>() {
        override fun areItemsTheSame(oldItem: VideoData, newItem: VideoData): Boolean {
            return newItem.name == oldItem.name
        }

        override fun areContentsTheSame(oldItem: VideoData, newItem: VideoData): Boolean {
            return newItem == oldItem
        }
    }), ItemTouchHelperAdapter {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(data: VideoData) {
            itemView.findViewById<TextView>(R.id.tvItem0).text = data.name
            itemView.findViewById<TextView>(R.id.tvDesc).text = data.desc
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item0, parent, false))
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        val mutableList = currentList.toMutableList()
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(mutableList, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(mutableList, i, i - 1)
            }
        }
        submitList(mutableList)
    }

    override fun onItemDismiss(position: Int) {
        val mutableList = currentList.toMutableList()
        mutableList.removeAt(position)
        submitList(mutableList)
    }
}