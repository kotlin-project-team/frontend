package com.study.dongamboard

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.study.dongamboard.db.NoticeDB
import com.study.dongamboard.model.NoticeData

class NoticeCreateActivity : AppCompatActivity() {

    var noticeDB : NoticeDB? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        this.window?.apply {
            this.statusBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        noticeDB = NoticeDB.getInstance(this)

        var tempId = 0
        tempId = noticeDB?.noticeDao()?.findAll()?.size!!
        val createNoticeRunnable = Runnable {
            val newNotice = NoticeData(
                tempId,
                findViewById<EditText>(R.id.etPostCreateTitle).text.toString(),
                findViewById<EditText>(R.id.etPostCreateContent).text.toString(),
                "일반")
            noticeDB?.noticeDao()?.insertNotice(newNotice)
            Log.d("InsertedNoticeList", noticeDB?.noticeDao()?.findAll().toString())
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