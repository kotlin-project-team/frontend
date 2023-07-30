package com.study.dongamboard

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.study.dongamboard.adapter.NoticeAdapter
import com.study.dongamboard.model.NoticeData
import java.sql.Timestamp

class NoticeListActivity : AppCompatActivity() {

    lateinit var noticeAdapter: NoticeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noticelist)

        var noticeList : ArrayList<NoticeData> = arrayListOf<NoticeData>()
        noticeList.apply {
            add(NoticeData(0, "공지3", "예제", "일반",
                Timestamp(System.currentTimeMillis()), Timestamp(System.currentTimeMillis())))
            add(NoticeData(1, "공지2", "게시게시", "일반",
                Timestamp(System.currentTimeMillis()), Timestamp(System.currentTimeMillis())))
            add(NoticeData(2, "게시판 이용수칙 안내", "게시판 이용수칙 1. ... 2. ...", "일반",
                Timestamp(System.currentTimeMillis()), Timestamp(System.currentTimeMillis())))
        }

        val lvNotice = findViewById<ListView>(R.id.lvNotice)
        noticeAdapter = NoticeAdapter(this, R.layout.notice_adapter_view, noticeList)
        lvNotice.adapter = noticeAdapter
        lvNotice.setOnItemClickListener { adapterView, view, i, l ->
            Toast.makeText(this, "notice [id=" + i + "] 호출", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, NoticeActivity::class.java)
            intent.putExtra("noticeData", noticeList.get(i))
            startActivity(intent)
        }

    }

}