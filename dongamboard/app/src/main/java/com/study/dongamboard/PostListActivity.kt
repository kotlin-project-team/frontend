package com.study.dongamboard

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.study.dongamboard.adapter.PostAdapter
import com.study.dongamboard.api.ApiObject
import com.study.dongamboard.db.PostDB
import com.study.dongamboard.model.PostData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostListActivity : AppCompatActivity() {

    lateinit var category: String
    lateinit var postAdapter: PostAdapter
    lateinit var lvPost: ListView
    lateinit var postDB: PostDB
    lateinit var postList: ArrayList<PostData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_postlist)

        category = intent.getStringExtra("postCategory") as String
        val tvPostListCategory = findViewById<TextView>(R.id.tvPostListCategory)
        tvPostListCategory.setText(category)

        postDB = PostDB.getInstance(this)!!

        lvPost = findViewById<ListView>(R.id.lvPost)
        postList = arrayListOf<PostData>()
        readAllPosts()

        lvPost.setOnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(this, PostActivity::class.java)
            intent.putExtra("postData", postList.get(i))
            startActivity(intent)
        }

        val ivPostCreate = findViewById<ImageView>(R.id.ivPostCreate)
        ivPostCreate.setOnClickListener {
            val intent = Intent(this, PostCreateActivity::class.java)
            startActivity(intent)
        }

        var swipe = findViewById<SwipeRefreshLayout>(R.id.swipePostlistLayout)
        swipe.setOnRefreshListener {
            readAllPosts()
            swipe.isRefreshing = false
        }
    }

    private fun readAllPosts() {
        CoroutineScope(Dispatchers.Main).launch {
            postList = ApiObject.getRetrofitAPIService.getAllPost() as ArrayList<PostData>
            postAdapter = PostAdapter(applicationContext, R.layout.post_adapter_view,
                postList as MutableList<PostData>
            )
            postAdapter.notifyDataSetChanged()
            lvPost.adapter = postAdapter
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