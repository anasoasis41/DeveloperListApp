package com.riahi.developerlistapp.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.riahi.developerlistapp.R
import com.riahi.developerlistapp.data.Developer

class MainRecyclerAdapter (val context: Context,
                           val developers: List<Developer>,
                           val itemListener: DeveloperItemListener):
    RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder>()
{
    override fun getItemCount() = developers.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val layoutId = R.layout.developer_list_item
        val view = inflater.inflate(layoutId, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val developer = developers[position]
        with(holder) {
            nameText?.let {
                it.text = developer.name
                it.contentDescription = developer.name
            }
            Glide.with(context)
                .load(developer.picture)
                .centerCrop()
                .circleCrop()
                .into(developerImage)
            itemView.setOnClickListener {
                itemListener.onItemClick(developer)
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameText = itemView.findViewById<TextView>(R.id.developerNameText)
        val developerImage = itemView.findViewById<ImageView>(R.id.developerImage)
    }

    interface DeveloperItemListener {
        fun onItemClick(developer: Developer)
    }
}