package com.alorma.timelineview.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alorma.timeline.property.LineStyle
import kotlinx.android.synthetic.main.item_main.view.*

class EventsAdapter : ListAdapter<Event, EventsAdapter.ViewHolderItem>(DIFF_CALLBACK) {

    var lineStyle: SampleLineStyle = SampleLineStyle.LINE
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderItem {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_main, parent, false)
        return ViewHolderItem(view)
    }

    override fun onBindViewHolder(holder: ViewHolderItem, position: Int) {
        holder.bind(getItem(position), lineStyle)
    }

    class ViewHolderItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(event: Event, lineStyle: SampleLineStyle) {
            itemView.textView.text = event.name

            val style = when (lineStyle) {
                is SampleLineStyle.LINE -> LineStyle.LINEAR
                is SampleLineStyle.DASHED -> LineStyle.DASHED
                is SampleLineStyle.MIXED -> if ((adapterPosition % 2) == 0) {
                    LineStyle.LINEAR
                } else {
                    LineStyle.DASHED
                }
            }

            itemView.timeline.setLineStyle(style)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Event>() {
            override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean =
                    oldItem == newItem

            override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean =
                    oldItem.name == newItem.name
                            && oldItem.type == newItem.type
        }
    }

}