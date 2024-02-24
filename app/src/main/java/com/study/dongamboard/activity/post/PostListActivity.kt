package com.study.dongamboard.activity.post

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.lakue.pagingbutton.LakuePagingButton
import com.lakue.pagingbutton.OnPageSelectListener
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.onSuccess
import com.study.dongamboard.R
import com.study.dongamboard.adapter.PostAdapter
import com.study.dongamboard.api.APIObject
import com.study.dongamboard.api.Utils
import com.study.dongamboard.model.response.PostResponse
import com.study.dongamboard.type.BoardCategory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostListActivity : AppCompatActivity() {

    private val utils: Utils by lazy {
        Utils(this)
    }

    private lateinit var category: BoardCategory
    private lateinit var postAdapter: PostAdapter
    private lateinit var lvPost: ListView
    private lateinit var postList: ArrayList<PostResponse>

    private val displayPageItemSize = 6
    private var maxPageSize = 1
    private var nowPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_postlist)

        category = intent.getSerializableExtra("postCategory") as BoardCategory
        val tvPostListCategory = findViewById<TextView>(R.id.tvPostListCategory)
        tvPostListCategory.text = category.toString()

        lvPost = findViewById<ListView>(R.id.lvPostActiList)
        postList = arrayListOf<PostResponse>()

        setPager()
        readAllPostsByPage()

        lvPost.setOnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(this, PostActivity::class.java)
            intent.putExtra("postData", postList[i])
            intent.putExtra("category", category)
            startActivity(intent)
        }

        val ivPostCreate = findViewById<ImageView>(R.id.ivPostCreate)
        ivPostCreate.setOnClickListener {
            val intent = Intent(this, PostCreateActivity::class.java)
            intent.putExtra("postCategory", category)
            startActivity(intent)
            nowPage = 1
        }

        val swipe = findViewById<SwipeRefreshLayout>(R.id.swipePostlistLayout)
        swipe.setOnRefreshListener {
            setPager()
            readAllPostsByPage()
            swipe.isRefreshing = false
        }
    }

    private fun setPager() {
        CoroutineScope(Dispatchers.Main).launch {
            val response = APIObject.getRetrofitAPIService.getAllPost(0, 0, category)
            response.onSuccess {
                if (data.result.postCount == 0) {
                    maxPageSize = 1
                } else {
                    maxPageSize = data.result.postCount / displayPageItemSize
                }
                if (data.result.postCount % displayPageItemSize > 0) {
                    maxPageSize++
                }
                paging()

                utils.logD("size" + data.result.postCount.toString() + " " + displayPageItemSize)
                utils.logD("maxPageSize: $maxPageSize")
                utils.logD(statusCode)
            }.onError {
                val errorMsg = utils.logE(statusCode)
                Toast.makeText(applicationContext, errorMsg, Toast.LENGTH_SHORT).show()
            }.onFailure {
                utils.logE(this)
            }
        }
    }

    private fun readAllPostsByPage() {
        CoroutineScope(Dispatchers.Main).launch {
            val response = APIObject.getRetrofitAPIService.getAllPost(displayPageItemSize, nowPage - 1, category)
            response.onSuccess {
                postList = data.result.posts as ArrayList<PostResponse>
                postAdapter = PostAdapter(applicationContext, R.layout.post_adapter_view,
                    postList as MutableList<PostResponse>
                )
                postAdapter.notifyDataSetChanged()
                lvPost.adapter = postAdapter

                utils.logD(statusCode)
            }.onError {
                val errorMsg = utils.logE(statusCode)
                Toast.makeText(applicationContext, errorMsg, Toast.LENGTH_SHORT).show()
            }.onFailure {
                utils.logE(this)
            }
        }
    }

    private fun paging() {
        val btnLpbPager = findViewById<LakuePagingButton>(R.id.btnLpbPager)
        var displayPageSize = 5

        if (maxPageSize < 5) {
            displayPageSize = maxPageSize
        }
        btnLpbPager.setPageItemCount(displayPageSize)
        btnLpbPager.addBottomPageButton(maxPageSize, nowPage)

        btnLpbPager.setOnPageSelectListener(object : OnPageSelectListener {
            override fun onPageBefore(clicked: Int) {
                nowPage = clicked
                btnLpbPager.addBottomPageButton(maxPageSize, clicked)
                readAllPostsByPage()
            }

            override fun onPageCenter(clicked: Int) {
                nowPage = clicked
                readAllPostsByPage()
            }

            override fun onPageNext(clicked: Int) {
                nowPage = clicked
                btnLpbPager.addBottomPageButton(maxPageSize, clicked)
                readAllPostsByPage()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        setPager()
        readAllPostsByPage()
    }
}