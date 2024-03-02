package com.study.dongamboard.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.FragmentTransaction
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.onSuccess
import com.study.dongamboard.R
import com.study.dongamboard.activity.notice.NoticeListActivity
import com.study.dongamboard.activity.post.BestActivity
import com.study.dongamboard.activity.post.PostListActivity
import com.study.dongamboard.fragment.post.PostListFragment
import com.study.dongamboard.activity.user.SignInActivity
import com.study.dongamboard.activity.user.UserActivity
import com.study.dongamboard.api.APIObject
import com.study.dongamboard.api.Utils
import com.study.dongamboard.databinding.ActivityMainBinding
import com.study.dongamboard.type.BoardCategory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    init { instance = this }

    private val postListFragment = PostListFragment()
    private val bundle = Bundle()
    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        mainBinding.textNotice.setOnClickListener {
            val intent = Intent(this, NoticeListActivity::class.java)
            startActivity(intent)
        }

        mainBinding.textBest.setOnClickListener {
            val intent = Intent(this, BestActivity::class.java)
            startActivity(intent)
        }

        mainBinding.btnBoardGeneral.setOnClickListener {
            mainBinding.textMain.text = BoardCategory.자유게시판.toString()
            bundle.putSerializable("postCategory", BoardCategory.자유게시판)
            postListFragment.arguments = bundle

            supportFragmentManager.beginTransaction()
                .replace(R.id.constraintLayout4, postListFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
        }

        mainBinding.btnBoardMarket.setOnClickListener {
            val intent = Intent(this, PostListActivity::class.java)
            intent.putExtra("postCategory", BoardCategory.장터게시판)
            startActivity(intent)
        }

        mainBinding.ivUserPage.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val response = APIObject.getRetrofitAPIService.getMyInformation()
                response.onSuccess {
                    val intent = Intent(applicationContext, UserActivity::class.java)
                    startActivity(intent)
                }.onError {
                    val intent = Intent(applicationContext, SignInActivity::class.java)
                    startActivity(intent)
                }.onFailure {
                    val intent = Intent(applicationContext, SignInActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    companion object {
        val Context.tokenDataStore: DataStore<Preferences> by preferencesDataStore(
            name = "TOKEN_DATA_STORE"
        )
        val utils: Utils by lazy {
            Utils(applicationContext())
        }
        lateinit var instance: MainActivity
        fun applicationContext() : Context {
            return instance.applicationContext
        }
    }
}