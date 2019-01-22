package com.alorma.timelineview.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val adapter = EventsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buildList()

        lineLinear.setOnClickListener {
            adapter.lineStyle = SampleLineStyle.LINE
        }
        lineDashed.setOnClickListener {
            adapter.lineStyle = SampleLineStyle.DASHED
        }
        lineMixed.setOnClickListener {
            adapter.lineStyle = SampleLineStyle.MIXED
        }

        lineRed.setOnClickListener {
            adapter.lineColor = SampleLineColor.RED
        }
        lineGreen.setOnClickListener {
            adapter.lineColor = SampleLineColor.GREEN
        }
        lineColorMix.setOnClickListener {
            adapter.lineColor = SampleLineColor.MIXED
        }

        pointCircle.setOnClickListener {
            adapter.pointStyle = SamplePointStyle.CIRCLE
        }
        pointSquare.setOnClickListener {
            adapter.pointStyle = SamplePointStyle.SQUARE
        }
        pointMixed.setOnClickListener {
            adapter.pointStyle = SamplePointStyle.MIXED
        }
    }

    private fun buildList() {
        val list = findViewById<RecyclerView>(R.id.list)

        val firstEvent = Event(getString(R.string.item_first),
                SampleLineVPosition.START,
                SampleLineHPosition.START)
        val middleEvents = (1..19).map {
            val text = getString(R.string.item_default, it)
            Event(text, lineWidth = 24f)
        }
        val lastElement = Event(getString(R.string.item_last),
                SampleLineVPosition.END,
                SampleLineHPosition.END)

        val items = listOf(firstEvent).plus(middleEvents).plus(lastElement)

        list.layoutManager = LinearLayoutManager(this)

        list.adapter = adapter
        adapter.submitList(items)
    }
}
