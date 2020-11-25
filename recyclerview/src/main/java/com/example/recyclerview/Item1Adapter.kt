package com.example.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.R
import kotlinx.android.synthetic.main.item0.view.*

class Item1Adapter(context: Context, list: List<String>) :
    RecyclerView.Adapter<Item1Adapter.ViewHolder>() {

    private val mContext:Context = context
    private val  mList:List<String> = list

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item0,parent,false))
    }

    override fun getItemCount(): Int {
       return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tvItem0.text = mList[position]
    }
}