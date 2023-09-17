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
    lateinit var lvPost: ListView
    var postDB: PostDB? = null
    var postList: ArrayList<PostData>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_postlist)

        this.window?.apply {
            this.statusBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        postDB = PostDB.getInstance(this)

        lvPost = findViewById<ListView>(R.id.lvPost)
        postList = arrayListOf<PostData>()

        lvPost.setOnItemClickListener { adapterView, view, i, l ->
            Toast.makeText(this, "post [id=" + i + "] 호출", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, PostActivity::class.java)
            intent.putExtra("postData", postList!!.get(i))
            startActivity(intent)
        }

        val ivPostCreate = findViewById<ImageView>(R.id.ivPostCreate)
        ivPostCreate.setOnClickListener {
            val intent = Intent(this, PostCreateActivity::class.java)
            startActivity(intent)
        }

    }

    private fun readAllPosts() {
        runOnUiThread {
            postList = (postDB!!.postDao().findAll() as ArrayList<PostData>?)!!
            postAdapter = PostAdapter(this, R.layout.post_adapter_view,
                postList as MutableList<PostData>
            )
            postAdapter.notifyDataSetChanged()
            lvPost.adapter = postAdapter
            Log.d("postList", postList.toString())
        }
    }

    override fun onResume() {
        readAllPosts()
        super.onResume()
    }

    override fun onDestroy() {
        PostDB.destroyInstance()
        super.onDestroy()
    }


}