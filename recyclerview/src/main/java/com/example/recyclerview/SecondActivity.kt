package com.example.recyclerview

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    companion object {
        const val KEY_VIDEOS = "KEY_VIDEOS"
    }

    private lateinit var adapter: Item1Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        rvSecond.layoutManager = LinearLayoutManager(this)
        adapter = Item1Adapter()
        rvSecond.adapter = adapter
        val data = intent.getParcelableArrayListExtra<VideoData>(KEY_VIDEOS)
        data?.let {
            adapter.submitList(it)
        }
        val itemTouchCallback = SimpleItemTouchHelperCallback(adapter)
        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(rvSecond)
    }

    // TODO: 2020/11/27 最好监听到有移动，才回传数据。
    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra(KEY_VIDEOS, ArrayList(adapter.currentList))
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}