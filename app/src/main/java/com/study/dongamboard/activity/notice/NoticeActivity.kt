package com.study.dongamboard.activity.notice

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.onSuccess
import com.study.dongamboard.R
import com.study.dongamboard.api.APIObject
import com.study.dongamboard.api.Utils
import com.study.dongamboard.model.response.NoticeResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoticeActivity : AppCompatActivity() {

    private lateinit var notice: NoticeResponse
    private val utils: Utils by lazy {
        Utils(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice)

        val toolbar: Toolbar = findViewById(R.id.toolbarNotice)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
        }

        notice = intent.getSerializableExtra("noticeData") as NoticeResponse
        reloadNotice()

        val tvNoticeTitle = findViewById<TextView>(R.id.tvNoticeTitle)
        val tvNoticeContent = findViewById<TextView>(R.id.tvNoticeContent)

        tvNoticeTitle.text = notice.title
        tvNoticeContent.text = notice.content
    }

    private fun reloadNotice() {
        val tvNoticeTitle = findViewById<TextView>(R.id.tvNoticeTitle)
        val tvNoticeContent = findViewById<TextView>(R.id.tvNoticeContent)

        tvNoticeTitle.text = notice.title
        tvNoticeContent.text = notice.content

        CoroutineScope(Dispatchers.Main).launch {
            val response = APIObject.getRetrofitAPIService.getNoticeById(notice.id)
            response.onSuccess {
                notice = data
                tvNoticeTitle.text = notice.title
                tvNoticeContent.text = notice.content

                utils.logD(statusCode)
            }.onError {
                val errorMsg = utils.logE(statusCode)
                Toast.makeText(applicationContext, errorMsg, Toast.LENGTH_SHORT).show()
            }.onFailure {
                utils.logE(this)
            }
        }
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
                }
            }
            R.id.miDeleteNotice ->{
                CoroutineScope(Dispatchers.IO).launch {
                    val response = APIObject.getRetrofitAPIService.deleteNotice(notice.id)
                    response.onSuccess {
                        utils.logD(statusCode)
                        finish()
                    }.onError {
                        val errorMsg = utils.logE(statusCode)
                        Toast.makeText(applicationContext, errorMsg, Toast.LENGTH_SHORT).show()
                    }.onFailure {
                        utils.logE(this)
                    }
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        reloadNotice()
    }
}