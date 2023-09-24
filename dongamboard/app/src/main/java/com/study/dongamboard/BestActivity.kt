package com.study.dongamboard

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.study.dongamboard.model.PostData
import java.sql.Timestamp

class BestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_best)

        this.window?.apply {
            this.statusBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        var postList : ArrayList<PostData> = arrayListOf<PostData>()
        postList.apply {
            add(PostData(0, 20200982, "베스트1", "베스트1 예제", "일반", 0, 0))
            add(PostData(1, 20200000, "베스트2", "베스트2 게시게시", "일반", 1, 0))
            add(PostData(2, 20200000, "베스트3", "베스트3 게시게시물입니다. 안녕", "일반", 3, 0))
        }

        postList.forEachIndexed { index, post ->
            var titleId = applicationContext.resources.getIdentifier("tvBestTitle" + (index + 1), "id", applicationContext.packageName)
            val tvBestTitle = findViewById<TextView>(titleId)
            tvBestTitle.text = post.title
            var contentId = applicationContext.resources.getIdentifier("tvBestContent" + (index + 1), "id", applicationContext.packageName)
            val tvBestContent = findViewById<TextView>(contentId)
            tvBestContent.text = post.content
            tvBestTitle.setOnClickListener {
                val intent = Intent(this, PostActivity::class.java)
                intent.putExtra("postData", post)
                startActivity(intent)
            }
        }

    }

}