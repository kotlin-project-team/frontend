package com.study.dongamboard.activity.post

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.study.dongamboard.R
import com.study.dongamboard.model.response.PostResponse
import com.study.dongamboard.type.BoardCategoryType

class BestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_best)

        var postList : ArrayList<PostResponse> = arrayListOf<PostResponse>()
        postList.apply {
            add(PostResponse(99997, 20200982, "베스트1", "베스트1 예제", BoardCategoryType.자유게시판, 0, 0))
            add(PostResponse(99998, 20200000, "베스트2", "베스트2 게시게시", BoardCategoryType.자유게시판, 1, 0))
            add(PostResponse(99999, 20200000, "베스트3", "베스트3 게시게시물입니다. 안녕", BoardCategoryType.자유게시판, 3, 0))
        }

        postList.forEachIndexed { index, post ->
            var titleId = applicationContext.resources.getIdentifier("tvBestTitle" + (index + 1), "id", applicationContext.packageName)
            val tvBestTitle = findViewById<TextView>(titleId)
            tvBestTitle.text = post.title
            var contentId = applicationContext.resources.getIdentifier("tvBestContent" + (index + 1), "id", applicationContext.packageName)
            val tvBestContent = findViewById<TextView>(contentId)
            tvBestContent.text = post.content
            tvBestTitle.setOnClickListener {
                Toast.makeText(this, "Post[id=" + post.id + "] 호출", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, PostActivity::class.java)
                intent.putExtra("postData", post)
                startActivity(intent)
            }
        }
    }
}