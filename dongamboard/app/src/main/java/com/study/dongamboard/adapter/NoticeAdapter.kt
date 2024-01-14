package com.study.dongamboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.study.dongamboard.model.response.NoticeResponse

class NoticeAdapter(private val context: Context, private val resId: Int, private val data: MutableList<NoticeResponse>) :
    ArrayAdapter<NoticeResponse>(context, resId) {

    override fun getCount(): Int {
        return data.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(resId, null)

            val holder = NoticeHolder(convertView)
            convertView!!.tag = holder
        }
        val holder = convertView.getTag() as NoticeHolder
        val tvNoticelistTitle: TextView = holder.tvNoticelistTitle

        val title = data[position].title
        tvNoticelistTitle.text = title

        return convertView
    }
}
