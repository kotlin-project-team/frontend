package com.study.dongamboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.study.dongamboard.adapter.NoticeAdapter
import com.study.dongamboard.db.NoticeDB
import com.study.dongamboard.model.NoticeData

class NoticeListActivity : AppCompatActivity() {

    lateinit var noticeAdapter: NoticeAdapter
    lateinit var lvNotice: ListView
    lateinit var noticeDB: NoticeDB
    lateinit var noticeList : ArrayList<NoticeData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noticelist)

        noticeDB = NoticeDB.getInstance(this)!!

        lvNotice = findViewById<ListView>(R.id.lvNotice)
        noticeList = arrayListOf<NoticeData>()

        lvNotice.setOnItemClickListener { adapterView, view, i, l ->
            Toast.makeText(this, "notice [id=" + i + "] 호출", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, NoticeActivity::class.java)
            intent.putExtra("noticeData", noticeList.get(i))
            startActivity(intent)
        }

        val ivNoticeCreate = findViewById<ImageView>(R.id.ivNoticeCreate)
        ivNoticeCreate.setOnClickListener {
            val intent = Intent(this, NoticeCreateActivity::class.java)
            startActivity(intent)
        }

    }

    private fun readAllNotices() {
        runOnUiThread {
            noticeList = noticeDB.noticeDao().findAll() as ArrayList<NoticeData>
            noticeAdapter = NoticeAdapter(this, R.layout.notice_adapter_view,
                noticeList as MutableList<NoticeData>
            )
            noticeAdapter.notifyDataSetChanged()
            lvNotice.adapter = noticeAdapter
            Log.d("noticeList", noticeList.toString())
        }
    }

    override fun onResume() {
        readAllNotices()
        super.onResume()
    }

    override fun onDestroy() {
        NoticeDB.destroyInstance()
        super.onDestroy()
    }


}