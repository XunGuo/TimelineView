package com.alorma.timelineview.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alorma.timeline.TimelineView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buildList()
    }

    private fun buildList() {
        val list = findViewById<RecyclerView>(R.id.list)

        val items = mutableListOf<Event>()

        items.add(Event(getString(R.string.item_first), TimelineView.TYPE_START))

        (0..19).map {
            val text = getString(R.string.item_default, it)
            val type = TimelineView.TYPE_MIDDLE
            Event(text, type)
        }.forEach {
            items.add(it)
        }

        items.add(Event(getString(R.string.item_last), TimelineView.TYPE_END))

        list.layoutManager = LinearLayoutManager(this)

        val adapter = EventsAdapter()
        list.adapter = adapter
        adapter.submitList(items)
    }
}
