package com.study.dongamboard

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.study.dongamboard.adapter.PostAdapter
import com.study.dongamboard.model.PostData
import java.sql.Timestamp

class PostListActivity : AppCompatActivity() {

    lateinit var postAdapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_postlist)

        var postList : ArrayList<PostData> = arrayListOf<PostData>()
        postList.apply {
            add(PostData(0, 20200982, "게시글1", "예제", "일반", 0, 0,
                Timestamp(System.currentTimeMillis()), Timestamp(System.currentTimeMillis())))
            add(PostData(1, 20200000, "게시글2", "게시게시", "일반", 1, 0,
                Timestamp(System.currentTimeMillis()), Timestamp(System.currentTimeMillis())))
            add(PostData(2, 20200000, "게시글3", "게시게시물입니다. 안녕", "일반", 3, 0,
                Timestamp(System.currentTimeMillis()), Timestamp(System.currentTimeMillis())))
        }

        val lvPost = findViewById<ListView>(R.id.lvPost)
        postAdapter = PostAdapter(this, R.layout.post_adapter_view, postList)
        lvPost.adapter = postAdapter
        lvPost.setOnItemClickListener { adapterView, view, i, l ->
            Toast.makeText(this, "post [id=" + i + "] 호출", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, PostActivity::class.java)
            intent.putExtra("postData", postList.get(i))
            startActivity(intent)
        }

        val ivPostCreate = findViewById<ImageView>(R.id.ivPostCreate)
        ivPostCreate.setOnClickListener {
            val intent = Intent(this, PostCreateActivity::class.java)
            startActivity(intent)
        }

    }

}