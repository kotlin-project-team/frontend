package com.study.dongamboard.activity.notice

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.study.dongamboard.R
import com.study.dongamboard.db.NoticeDB
import com.study.dongamboard.model.NoticeData

class NoticeCreateActivity : AppCompatActivity() {

    lateinit var noticeDB : NoticeDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        noticeDB = NoticeDB.getInstance(this)!!

        val createNoticeRunnable = Runnable {
            val newNotice = NoticeData(
                0,
                findViewById<EditText>(R.id.etPostCreateTitle).text.toString(),
                findViewById<EditText>(R.id.etPostCreateContent).text.toString(),
                "일반")
            noticeDB.noticeDao().insertNotice(newNotice)
            Log.d("InsertedNoticeList", noticeDB.noticeDao().findAll().toString())
        }

        val ivCreateNoticeBtn = findViewById<ImageView>(R.id.ivCreatePostBtn)
        ivCreateNoticeBtn.setOnClickListener{
            val createNoticeThread = Thread(createNoticeRunnable)
            createNoticeThread.start()
            finish()
        }
    }

    override fun onDestroy() {
        NoticeDB.destroyInstance()
        super.onDestroy()
    }
}