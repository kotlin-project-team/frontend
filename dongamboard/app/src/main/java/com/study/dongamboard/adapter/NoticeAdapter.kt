package com.study.dongamboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.study.dongamboard.model.NoticeData
import com.study.dongamboard.model.response.NoticeResponse

class NoticeAdapter(private val context: Context, val resId: Int, val datas: MutableList<NoticeResponse>) :
    ArrayAdapter<NoticeData>(context, resId) {

    override fun getCount(): Int {
        return datas.size
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

        // TODO: 추후 title 추가
//        val title = datas[position].title
        val title = "공지" + datas[position].id.toString()
        tvNoticelistTitle.text = title

        return convertView
    }
}
