package com.study.dongamboard

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.study.dongamboard.adapter.CommentAdapter
import com.study.dongamboard.db.CommentDB
import com.study.dongamboard.model.CommentData
import com.study.dongamboard.model.PostData
import java.lang.Thread.sleep

class PostActivity : AppCompatActivity() {

    lateinit var commentAdapter: CommentAdapter
    lateinit var lvCmt: ListView
    lateinit var commentDB: CommentDB
    lateinit var commentList: ArrayList<CommentData>

    lateinit var post: PostData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        val tvPostTitle = findViewById<TextView>(R.id.tvPostlistTitle)
        val tvLikes = findViewById<TextView>(R.id.tvPostlistLikes)
        val tvCmtCnt = findViewById<TextView>(R.id.tvCmtCnt)
        val tvPostContent = findViewById<TextView>(R.id.tvPostContent)

        post = intent.getSerializableExtra("postData") as PostData

        tvPostTitle.text = post.title
        tvLikes.text = "[솜솜픽 " + post.likes.toString() + "]"
        tvCmtCnt.text = "[댓글 " + "0" + "]"
        tvPostContent.text = post.content

        commentDB = CommentDB.getInstance(this)!!

        lvCmt = findViewById<ListView>(R.id.lvCmt)
        commentList = arrayListOf<CommentData>()

        val tempId = commentDB.commentDao().findAll().size
        val createCommentRunnable = Runnable {
            val newComment =
                CommentData(
                    tempId,
                    post.id,
                    findViewById<EditText>(R.id.etCmtCnt).text.toString(),
                    "익명"
                )
            commentDB.commentDao().insertComment(newComment)
        }

        val ivCreateCmt = findViewById<ImageView>(R.id.ivCreateCmtBtn)
        ivCreateCmt.setOnClickListener{
            if (findViewById<EditText>(R.id.etCmtCnt).text.isEmpty()) {
                Toast.makeText(this, "내용을 입력하세요", Toast.LENGTH_SHORT).show()
            } else {
                val createCommentThread = Thread(createCommentRunnable)
                createCommentThread.start()
                findViewById<EditText>(R.id.etCmtCnt).setText("")
                hideKeyboard(this)
                readAllComments()
            }
        }

        lvCmt.setOnItemClickListener { adapterView, view, i, l ->
            val comment = commentList.get(i)
            val builder = AlertDialog.Builder(this)
            builder.setTitle("댓글 삭제")
                .setMessage("댓글을 삭제하시겠습니까?")
                .setPositiveButton("삭제", DialogInterface.OnClickListener { dialogInterface, i ->
                    commentDB.commentDao().deleteComment(comment)
                    commentList.remove(comment)
                    commentAdapter.notifyDataSetChanged()
                })
                .setNegativeButton("취소", null)
                .show()
        }

    }

    private fun readAllComments() {
        commentList.clear()
        runOnUiThread {
            commentList = commentDB.commentDao().findCommentsByPostId(post.id) as ArrayList<CommentData>
            commentAdapter = CommentAdapter(this, R.layout.comment_adapter_view,
                commentList as MutableList<CommentData>
            )
            commentAdapter.notifyDataSetChanged()
            lvCmt.adapter = commentAdapter
        }
    }

    fun hideKeyboard(activity: PostActivity){
        val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity.window.decorView.applicationWindowToken, 0)
    }

    override fun onResume() {
        readAllComments()
        super.onResume()
    }

    override fun onDestroy() {
        CommentDB.destroyInstance()
        super.onDestroy()
    }


}