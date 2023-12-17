package com.study.dongamboard.activity.notice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.lakue.pagingbutton.LakuePagingButton
import com.lakue.pagingbutton.OnPageSelectListener
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onSuccess
import com.study.dongamboard.R
import com.study.dongamboard.adapter.NoticeAdapter
import com.study.dongamboard.api.APIObject
import com.study.dongamboard.db.NoticeDB
import com.study.dongamboard.model.response.NoticeResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoticeListActivity : AppCompatActivity() {

    private lateinit var noticeAdapter: NoticeAdapter
    private lateinit var lvNotice: ListView
    private lateinit var noticeDB: NoticeDB
    private lateinit var noticeList : ArrayList<NoticeResponse>

    private val displayPageItemSize = 6
    private var maxPageSize = 5       // TODO: notice 개수 처리
    private var nowPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noticelist)

        noticeDB = NoticeDB.getInstance(this)!!

        lvNotice = findViewById<ListView>(R.id.lvNotice)
        noticeList = arrayListOf<NoticeResponse>()

        setPager()
        readAllNoticesByPage()

        lvNotice.setOnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(this, NoticeActivity::class.java)
            intent.putExtra("noticeData", noticeList[i])
            startActivity(intent)
        }

        val ivNoticeCreate = findViewById<ImageView>(R.id.ivNoticeCreate)
        ivNoticeCreate.setOnClickListener {
            val intent = Intent(this, NoticeCreateActivity::class.java)
            startActivity(intent)
        }

        var swipe = findViewById<SwipeRefreshLayout>(R.id.swipeNoticelistLayout)
        swipe.setOnRefreshListener {
            readAllNoticesByPage()
            swipe.isRefreshing = false
        }
    }

    private fun setPager() {
        CoroutineScope(Dispatchers.Main).launch {
            val response = APIObject.getRetrofitAPIService.getAllNotice(0, 0)
            response.onSuccess {
                maxPageSize = data.size / displayPageItemSize
                Log.d("size", data.size.toString() + " " + displayPageItemSize)
                Log.d("maxPageSize", maxPageSize.toString())
                if (data.isNotEmpty() && data.size % displayPageItemSize > 0) {
                    maxPageSize++
                    paging()
                }
            }.onError {
                Log.e("statusCode", statusCode.code.toString() + " " + statusCode.toString())
                // TODO: status code에 따른 처리
            }
        }
    }

    private fun readAllNoticesByPage() {
        CoroutineScope(Dispatchers.Main).launch {
            val response = APIObject.getRetrofitAPIService.getAllNotice(displayPageItemSize, nowPage - 1)
            response.onSuccess {
                noticeList = data as ArrayList<NoticeResponse>
                noticeAdapter = NoticeAdapter(applicationContext, R.layout.notice_adapter_view,
                    noticeList as MutableList<NoticeResponse>
                )
                noticeAdapter.notifyDataSetChanged()
                lvNotice.adapter = noticeAdapter
            }.onError {
                Log.e("statusCode", statusCode.code.toString() + " " + statusCode.toString())
                // TODO: status code 처리
            }
        }
    }

    private fun paging() {
        val btnLpbPager = findViewById<LakuePagingButton>(R.id.btnLpbPager)
        var displayPageSize = 5

        if (maxPageSize < 5) {
            displayPageSize = maxPageSize
        }
        btnLpbPager.setPageItemCount(displayPageSize)
        btnLpbPager.addBottomPageButton(maxPageSize, nowPage)

        btnLpbPager.setOnPageSelectListener(object : OnPageSelectListener {
            override fun onPageBefore(clicked: Int) {
                nowPage = clicked
                btnLpbPager.addBottomPageButton(maxPageSize, clicked)
                readAllNoticesByPage()
            }

            override fun onPageCenter(clicked: Int) {
                nowPage = clicked
                readAllNoticesByPage()
            }

            override fun onPageNext(clicked: Int) {
                nowPage = clicked
                btnLpbPager.addBottomPageButton(maxPageSize, clicked)
                readAllNoticesByPage()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        readAllNoticesByPage()
    }

    override fun onDestroy() {
        super.onDestroy()
        NoticeDB.destroyInstance()
    }
}