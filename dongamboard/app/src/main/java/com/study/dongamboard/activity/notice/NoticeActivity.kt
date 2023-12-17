package com.study.dongamboard.activity.notice

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.study.dongamboard.R
import com.study.dongamboard.api.APIObject
import com.study.dongamboard.model.response.NoticeResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoticeActivity : AppCompatActivity() {

    private lateinit var notice: NoticeResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice)

        val toolbar: Toolbar = findViewById(R.id.toolbarNotice)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
        }

        notice = intent.getSerializableExtra("noticeData") as NoticeResponse

        val tvNoticeTitle = findViewById<TextView>(R.id.tvNoticeTitle)
        val tvNoticeContent = findViewById<TextView>(R.id.tvNoticeContent)

        tvNoticeTitle.text = "공지" + notice.id.toString()
        tvNoticeContent.text = notice.content
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_notice, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.miUpdateNotice -> {
                CoroutineScope(Dispatchers.IO).launch {
                    val intent = Intent(applicationContext, NoticeUpdateActivity::class.java)
                    intent.putExtra("noticeData", notice)
                    startActivity(intent)
                    // TODO: get notice by id 기능 추가 시 reload Notice
                }
            }
            R.id.miDeleteNotice ->{
                CoroutineScope(Dispatchers.IO).launch {
                    APIObject.getRetrofitAPIService.deleteNotice(notice.id)
                    finish()
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }
}