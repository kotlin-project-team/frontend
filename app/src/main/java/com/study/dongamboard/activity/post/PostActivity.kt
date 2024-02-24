package com.study.dongamboard.activity.post

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.AbsListView
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.onSuccess
import com.study.dongamboard.R
import com.study.dongamboard.adapter.CommentAdapter
import com.study.dongamboard.api.APIObject
import com.study.dongamboard.api.Utils
import com.study.dongamboard.model.request.CommentRequest
import com.study.dongamboard.model.response.CommentResponse
import com.study.dongamboard.model.response.PostResponse
import com.study.dongamboard.type.BoardCategory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostActivity : AppCompatActivity() {

    private val utils: Utils by lazy {
        Utils(this)
    }

    private lateinit var post: PostResponse
    private lateinit var category: BoardCategory
    private lateinit var commentAdapter: CommentAdapter
    private lateinit var lvCmt: ListView
    private lateinit var commentList: ArrayList<CommentResponse>

    private val displayCommentSize = 5
    private var lastCommentId = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        val toolbar: Toolbar = findViewById(R.id.toolbarPost)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
        }

        post = intent.getSerializableExtra("postData") as PostResponse
        category = intent.getSerializableExtra("category") as BoardCategory
        reloadPost()

        lvCmt = findViewById<ListView>(R.id.lvCmt)
        commentList = arrayListOf<CommentResponse>()

        val ivCreateCmt = findViewById<ImageView>(R.id.ivCreateCmtBtn)
        ivCreateCmt.setOnClickListener{
            val content = findViewById<EditText>(R.id.etCmtCnt).text
            if (content.isEmpty()) {
                Toast.makeText(this, "내용을 입력하세요", Toast.LENGTH_SHORT).show()
            } else {
                val commentReq = CommentRequest(content.toString())
                createComment(commentReq)
            }
        }

        lvCmt.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScrollStateChanged(p0: AbsListView?, p1: Int) {
                if (!lvCmt.canScrollVertically(1)) {
                    val lastY = lvCmt.scrollY
                    lastCommentId = commentList[commentList.size - 1].id
                    readCommentsByScroll()
                    lvCmt.scrollY = lastY
                }
                utils.logD("scrollY: ${lvCmt.scrollY.toString()}")
            }

            override fun onScroll(p0: AbsListView?, p1: Int, p2: Int, p3: Int) {
            }
        })

        lvCmt.setOnItemClickListener { adapterView, view, i, l ->
            val comment = commentList[i]
            val builder = AlertDialog.Builder(this)
            builder.setTitle("댓글 삭제")
                .setMessage("댓글을 삭제하시겠습니까?")
                .setPositiveButton("삭제", DialogInterface.OnClickListener { dialogInterface, i ->
                    //TODO: Comment delete 동작
                    commentList.remove(comment)
                    commentAdapter.notifyDataSetChanged()
                })
                .setNegativeButton("취소", null)
                .show()
        }

        val ivPostLike = findViewById<ImageView>(R.id.ivPostLike)
        ivPostLike.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                APIObject.getRetrofitAPIService.clickPostLike(post.id)
                reloadPost()
            }
        }
    }

    private fun reloadPost() {
        val tvPostTitle = findViewById<TextView>(R.id.tvPostTitle)
        val tvPostContent = findViewById<TextView>(R.id.tvPostContent)
        val tvLikes = findViewById<TextView>(R.id.tvPostLikes)
        val tvCmtCnt = findViewById<TextView>(R.id.tvCmtCnt)
        val tvPostCategory = findViewById<TextView>(R.id.tvPostCategory)

        tvPostTitle.text = post.title
        tvLikes.text = "[솜솜픽 " + post.likes.toString() + "]"
        tvCmtCnt.text = "[댓글 " + "0" + "]"
        tvPostContent.text = post.content
        tvPostCategory.text = category.toString()

        CoroutineScope(Dispatchers.Main).launch {
            val response = APIObject.getRetrofitAPIService.getPostById(post.id)
            response.onSuccess {
                post = data.result
                tvPostTitle.text = post.title
                tvLikes.text = "[솜솜픽 " + post.likes.toString() + "]"
                tvCmtCnt.text = "[댓글 " + "0" + "]"
                tvPostContent.text = post.content
                tvPostCategory.text = category.toString()

                utils.logD(statusCode)
            }.onError {
                val errorMsg = utils.logE(statusCode)
                Toast.makeText(applicationContext, errorMsg, Toast.LENGTH_SHORT).show()
            }.onFailure {
                utils.logE(this)
            }
        }
    }

    private fun readCommentsByScroll() {
        CoroutineScope(Dispatchers.Main).launch {
            val response = APIObject.getRetrofitAPIService.getAllComment(post.id, displayCommentSize, 0, lastCommentId)
            response.onSuccess {
                commentList.addAll(data as ArrayList<CommentResponse>)
                commentAdapter = CommentAdapter(applicationContext, R.layout.comment_adapter_view,
                    commentList as MutableList<CommentResponse>
                )
                commentAdapter.notifyDataSetChanged()
                lvCmt.adapter = commentAdapter

                utils.logD("commentList: $commentList")
                utils.logD(statusCode)
            }.onError {
                val errorMsg = utils.logE(statusCode)
                Toast.makeText(applicationContext, errorMsg, Toast.LENGTH_SHORT).show()
            }.onFailure {
                utils.logE(this)
            }
        }
    }

    private fun createComment(commentReq: CommentRequest) {
        CoroutineScope(Dispatchers.Main).launch {
            val response = APIObject.getRetrofitAPIService.createComment(post.id, commentReq)
            response.onSuccess {
                findViewById<EditText>(R.id.etCmtCnt).setText("")
                hideKeyboard(applicationContext as PostActivity)
                readCommentsByScroll()

                utils.logD(statusCode)
            }.onError {
                val errorMsg = utils.logE(statusCode)
                Toast.makeText(applicationContext, errorMsg, Toast.LENGTH_SHORT).show()
            }.onFailure {
                utils.logE(this)
            }
        }
    }

    fun hideKeyboard(activity: PostActivity){
        val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity.window.decorView.applicationWindowToken, 0)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        CoroutineScope(Dispatchers.Main).launch {
            val response = APIObject.getRetrofitAPIService.getMyInformation()
            response.onSuccess {
                if (data.result.studentId == post.user.studentId) {
                    menuInflater.inflate(R.menu.menu_post, menu)
                }
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.miUpdatePost -> {
                val intent = Intent(applicationContext, PostUpdateActivity::class.java)
                intent.putExtra("postData", post)
                intent.putExtra("category", category)
                startActivity(intent)
            }
            R.id.miDeletePost ->{
                CoroutineScope(Dispatchers.IO).launch {
                    val response = APIObject.getRetrofitAPIService.deletePost(post.id)
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
        reloadPost()
        readCommentsByScroll()
    }
}