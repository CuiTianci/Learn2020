package com.example.recyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val item0s = arrayListOf("1", "2", "3")
        val item1s = arrayListOf("1", "2", "3")
        val item00s = arrayListOf("4", "5", "6")
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = RecyclerView.VERTICAL
        val item0Adapter = Item0Adapter(this, item0s)
        val item1Adapter = Item1Adapter(this, item1s)
        val item00Adapter = Item0Adapter(this, item00s)
        val concatAdapter = ConcatAdapter(item0Adapter, item1Adapter, item00Adapter)
        rvMain.adapter = concatAdapter
        rvMain.layoutManager = layoutManager
    }
}
