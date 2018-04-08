package com.androiddev.ngweyeeshoon.kotlinsample.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.androiddev.ngweyeeshoon.kotlinsample.R
import com.androiddev.ngweyeeshoon.kotlinsample.data.Android
import kotlinx.android.synthetic.main.recycler_view_row.view.*
import android.graphics.Color

class DataAdapter(private val dataList: ArrayList<Android>, private val listener: Listener) : RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    private val colors: Array<String> = arrayOf("#EF5350", "#EC407A", "#AB47BC", "#7E57C2", "#5C6BC0", "#42A5F5")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_row,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = dataList.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position],listener,colors,position)
    }

    interface Listener {
        fun onItemClick(android: Android)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(android: Android, listener: Listener, colors: Array<String>, position: Int){
            itemView.tv_name.text = android.name
            itemView.tv_api_level.text = android.apiLevel
            itemView.tv_version.text = android.version
            itemView.setBackgroundColor(Color.parseColor(colors[position % 6]))

        itemView.setOnClickListener {
            listener.onItemClick(android)
        }

        }

    }


}