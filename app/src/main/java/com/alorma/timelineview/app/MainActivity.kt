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
        
        linerLinear.setOnClickListener {
            adapter.lineStyle = SampleLineStyle.LINE
        }
        linerDashed.setOnClickListener {
            adapter.lineStyle = SampleLineStyle.DASHED
        }
        lineMixed.setOnClickListener {
            adapter.lineStyle = SampleLineStyle.MIXED
        }
    }

    private fun buildList() {
        val list = findViewById<RecyclerView>(R.id.list)

        val firstEvent = Event(getString(R.string.item_first), 0)
        val middleEvents = (1..19).map {
            val text = getString(R.string.item_default, it)
            val type = 1
            Event(text, type)
        }
        val lastElement = Event(getString(R.string.item_last), 2)

        val items = listOf(firstEvent).plus(middleEvents).plus(lastElement)

        list.layoutManager = LinearLayoutManager(this)

        list.adapter = adapter
        adapter.submitList(items)
    }
}
