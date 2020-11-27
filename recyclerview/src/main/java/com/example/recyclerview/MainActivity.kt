package com.example.recyclerview

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_CONDE_TO_SECOND = 0
    }

    private lateinit var adapter: Item0Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvMain.layoutManager = LinearLayoutManager(this)
        val list = listOf(
            VideoData("aaa", "000"),
            VideoData("bbb", "111"),
            VideoData("ccc", "222"),
            VideoData("ddd", "333"),
            VideoData("eee", "444"),
            VideoData("fff", "555"),
            VideoData("ggg", "666"),
            VideoData("hhh", "777"),
        )
        adapter = Item0Adapter()
        rvMain.adapter = adapter
        adapter.submitList(list)
        //配置拖拽效果。
        val itemTouchCallback = SimpleItemTouchHelperCallback(adapter)
        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(rvMain)
        btnToSecond.setOnClickListener {
            startSecond()
        }
    }

    /**
     * 跳转SecondActivity
     */
    private fun startSecond() {
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra(SecondActivity.KEY_VIDEOS, ArrayList(adapter.currentList))
        startActivityForResult(intent, REQUEST_CONDE_TO_SECOND)
    }

    /**
     * 获取SecondActivity的返回结果。
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CONDE_TO_SECOND && resultCode == Activity.RESULT_OK) {
            data?.let {
                val videoData =
                    it.getParcelableArrayListExtra<VideoData>(SecondActivity.KEY_VIDEOS)
                videoData?.let { videoArray ->
                    adapter.submitList(videoArray)
                }
            }
        }
    }
}
