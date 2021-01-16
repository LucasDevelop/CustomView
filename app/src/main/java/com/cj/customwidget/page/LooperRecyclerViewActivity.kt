package com.cj.customwidget.page

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.cj.customwidget.R
import com.cj.customwidget.ext.p
import com.cj.customwidget.widget.LooperLinearLayoutManager
import kotlinx.android.synthetic.main.activity_looper_recycler_view.*

/**
 * File LooperRecyclerViewActivity.kt
 * Date 2020/12/10
 * Author lucas
 * Introduction 无限循环列表
 */
class LooperRecyclerViewActivity : AppCompatActivity() {

    val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_looper_recycler_view)
        val looperLinearLayoutManager = LooperLinearLayoutManager(this,LinearLayoutManager.VERTICAL,false) as LinearLayoutManager
//        val looperLinearLayoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        v_list.layoutManager = looperLinearLayoutManager
        val looperAdapter = LooperAdapter()
        looperAdapter.addData(List(4) { (it + 1).toString() }.toMutableList())
        v_list.adapter = looperAdapter
        looperAdapter.bindToRecyclerView(v_list)
        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(v_list)
        looperAdapter.registerAdapterDataObserver(object :RecyclerView.AdapterDataObserver(){
            override fun onChanged() {
                super.onChanged()
                "onChanged".p()
            }
        })
        v_list.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val pFirst = linearLayoutManager.findFirstVisibleItemPosition()
                val pLast = linearLayoutManager.findLastVisibleItemPosition()
                if (RecyclerView.SCROLL_STATE_IDLE == newState){
                    "pFirst:$pFirst,pLast:$pLast".p()
                }
            }
        })
//        v_list.scrollToPosition(2)
//        handler.postDelayed({
//            "notifyDataSetChanged".p()
//            looperAdapter.notifyDataSetChanged()
//        },3000)
    }

    class LooperAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_text) {

        val colors = arrayOf(Color.RED, Color.GREEN, Color.BLUE,Color.YELLOW)

        override fun convert(holder: BaseViewHolder, item: String) {
            "onBindViewHolder:${holder.adapterPosition}".p()
            (holder.itemView as TextView).apply {
                text = data[holder.adapterPosition]
                setTextColor(Color.WHITE)
                setBackgroundColor(colors[holder.adapterPosition % 4])
            }
        }
    }
}