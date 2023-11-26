package com.study.dongamboard.activity.user

import android.content.Intent
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.study.dongamboard.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etPhoneNum = findViewById<EditText>(R.id.etPhoneNum)
        etPhoneNum.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        btnLogin.setOnClickListener {
            finish()
        }

        val btnJoin = findViewById<Button>(R.id.btnJoin)
        btnJoin.setOnClickListener {
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }
    }
}