package com.alorma.timelineview.app

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alorma.timeline.property.LineStyle
import com.alorma.timeline.property.PointStyle
import kotlinx.android.synthetic.main.item_main.view.*

class EventsAdapter : ListAdapter<Event, EventsAdapter.ViewHolderItem>(DIFF_CALLBACK) {

    var lineStyle: SampleLineStyle = SampleLineStyle.MIXED
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var lineColor: SampleLineColor = SampleLineColor.MIXED
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var pointStyle: SamplePointStyle = SamplePointStyle.MIXED
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
        holder.bind(getItem(position),
                lineStyle, lineColor,
                pointStyle)
    }

    class ViewHolderItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(event: Event,
                 lineStyle: SampleLineStyle,
                 lineColor: SampleLineColor,
                 pointStyle: SamplePointStyle) {
            itemView.textView.text = event.name

            configureTimeline(lineStyle, lineColor, event, pointStyle)
        }

        private fun configureTimeline(lineStyle: SampleLineStyle,
                                      lineColor: SampleLineColor,
                                      event: Event,
                                      pointStyle: SamplePointStyle) {
            itemView.timeline.configureLine {
                val lineSty = when (lineStyle) {
                    is SampleLineStyle.LINE -> LineStyle.LINEAR
                    is SampleLineStyle.DASHED -> LineStyle.DASHED
                    is SampleLineStyle.MIXED -> if ((adapterPosition % 2) == 0) {
                        LineStyle.LINEAR
                    } else {
                        LineStyle.DASHED
                    }
                }
                setLineStyle(lineSty)

                val color = when (lineColor) {
                    SampleLineColor.RED -> Color.RED
                    SampleLineColor.GREEN -> Color.GREEN
                    SampleLineColor.MIXED -> if ((adapterPosition % 2) == 0) {
                        Color.RED
                    } else {
                        Color.GREEN
                    }
                }

                setLineColor(color)

                event.lineWidth?.let { width -> setLineWidth(width) }
            }

            itemView.timeline.configurePoint {
                val pointSty = when (pointStyle) {
                    is SamplePointStyle.CIRCLE -> PointStyle.CIRCLE
                    is SamplePointStyle.SQUARE -> PointStyle.SQUARE
                    is SamplePointStyle.MIXED -> if ((adapterPosition % 2) == 0) {
                        PointStyle.CIRCLE
                    } else {
                        PointStyle.SQUARE
                    }
                }

                setPointStyle(pointSty)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Event>() {
            override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean =
                    oldItem == newItem

            override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean =
                    oldItem.name == newItem.name
        }
    }
}