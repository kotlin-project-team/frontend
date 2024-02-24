package com.study.dongamboard.api

import android.content.Context
import android.util.Log
import com.skydoves.sandwich.StatusCode
import com.study.dongamboard.type.ResponseStatus

class Utils(context: Context) {
    private val TAG = "TEST"
    private val DEBUG = true

    fun logD(message: String) {
        if (DEBUG) {
            Log.d(TAG, message)
        }
    }

    fun logD(statusCode: StatusCode) {
        var successMsg = ""
        when (statusCode.code) {
            ResponseStatus.SUCCESS.code -> successMsg = ResponseStatus.SUCCESS.message
            ResponseStatus.CREATED.code -> successMsg = ResponseStatus.CREATED.message
        }
        if (DEBUG) {
            Log.d(TAG, "$statusCode $successMsg")
        }
    }

    fun logE(message: String) {
        if (DEBUG) {
            Log.e(TAG, message)
        }
    }

    fun logE(statusCode: StatusCode): String {
        var errorMsg = ""
        when (statusCode.code) {
            ResponseStatus.BAD_REQUEST.code -> errorMsg = ResponseStatus.BAD_REQUEST.message
            ResponseStatus.UNAUTHORIZED.code -> errorMsg = ResponseStatus.UNAUTHORIZED.message
            ResponseStatus.FORBIDDEN.code -> errorMsg = ResponseStatus.FORBIDDEN.message
            ResponseStatus.NOT_FOUND.code -> errorMsg = ResponseStatus.NOT_FOUND.message
            ResponseStatus.CONFLICT.code -> errorMsg = ResponseStatus.CONFLICT.message
            ResponseStatus.INTERNET_SERVER_ERROR.code -> errorMsg = ResponseStatus.INTERNET_SERVER_ERROR.message
            else -> errorMsg = statusCode.toString()
        }
        if (DEBUG) {
            Log.e(TAG, "$statusCode $errorMsg")
        }
        return errorMsg
    }
}