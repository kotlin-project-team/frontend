package com.study.dongamboard.activity.user

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onSuccess
import com.study.dongamboard.api.APIObject
import com.study.dongamboard.databinding.PasswordDialogViewBinding
import com.study.dongamboard.model.request.CheckPasswordForMyPageRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PasswordDialog(private val cancelEvent: () -> Unit, private val applicationContext: Context): DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = PasswordDialogViewBinding.inflate(inflater, container, false)

        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT)
        dialog?.setCancelable(true)

        binding.btnConfirmPW.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val password = binding.etUserpagePassword.text.toString()
                val passwordReq = CheckPasswordForMyPageRequest(password)
                val response = APIObject.getRetrofitAPIService.checkPasswordForMyPage(passwordReq)
                response.onSuccess {
                    val intent = Intent(applicationContext, UserUpdateActivity::class.java)
                    startActivity(intent)
                }.onError {
                    cancelEvent()
                    Log.d("statusCode", statusCode.toString())
                    Log.d("error", errorBody.toString())
                }
                dialog?.dismiss()
            }
        }

        return binding.root
    }
}