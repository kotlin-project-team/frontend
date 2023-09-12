package com.study.dongamboard

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.study.dongamboard.adapter.PostAdapter
import com.study.dongamboard.db.PostDB
import com.study.dongamboard.model.PostData


class PostListActivity : AppCompatActivity() {

    lateinit var postAdapter: PostAdapter
    var postDB : PostDB? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_postlist)

        this.window?.apply {
            this.statusBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        postDB = PostDB.getInstance(this)

        var postList : ArrayList<PostData> = arrayListOf<PostData>()
        postList.apply {
            add(PostData(0, 20200982, "게시글1", "예제", "일반", 0, 0/*,
                Timestamp(System.currentTimeMillis()), Timestamp(System.currentTimeMillis())*/))
            add(PostData(1, 20200000, "게시글2", "게시게시", "일반", 1, 0/*,
                Timestamp(System.currentTimeMillis()), Timestamp(System.currentTimeMillis())*/))
            add(PostData(2, 20200000, "게시글3", "게시게시물입니다. 안녕", "일반", 3, 0/*,
                Timestamp(System.currentTimeMillis()), Timestamp(System.currentTimeMillis())*/))
        }

        val lvPost = findViewById<ListView>(R.id.lvPost)

        runOnUiThread {
            postList = (postDB!!.postDao().findAll() as ArrayList<PostData>?)!!
            postAdapter = PostAdapter(this, R.layout.post_adapter_view,
                postList as MutableList<PostData>
            )
            postAdapter.notifyDataSetChanged()
            lvPost.adapter = postAdapter
            Log.d("postList", postList.toString())
        }

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

    override fun onDestroy() {
        PostDB.destroyInstance()
        super.onDestroy()
    }

}