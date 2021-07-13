package com.example.sensors_notifications

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sensors_notifications.room.TextEntity

class TextAdapter: ListAdapter<TextEntity, ViewHolder>(ItemComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.text_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}
class ItemComparator: DiffUtil.ItemCallback<TextEntity>() {
    override fun areItemsTheSame(oldItem: TextEntity, newItem: TextEntity): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: TextEntity, newItem: TextEntity): Boolean {
        return oldItem.uid == newItem.uid
    }

}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textView = itemView.findViewById<TextView>(R.id.view_text)

    fun bind(textItem: TextEntity) {
        textView.text = textItem.text
    }
}