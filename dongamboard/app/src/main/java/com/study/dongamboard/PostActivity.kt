package com.study.dongamboard

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.study.dongamboard.model.PostData

class PostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        val post = intent.getSerializableExtra("postData") as PostData?

        val tvPostTitle = findViewById<TextView>(R.id.tvPostlistTitle)
        val tvLikes = findViewById<TextView>(R.id.tvPostlistLikes)
        val tvCmtCnt = findViewById<TextView>(R.id.tvCmtCnt)
        val tvPostContent = findViewById<TextView>(R.id.tvPostContent)

        tvPostTitle.text = post?.title
        tvLikes.text = "[솜솜픽 " + post?.likes.toString() + "]"
        tvCmtCnt.text = "[댓글 " + "0" + "]"
        tvPostContent.text = post?.content

    }

}