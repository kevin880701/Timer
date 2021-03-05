package com.android.notesk.Adapter

import android.content.Context
import android.graphics.Color.red
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hr.timer.Model.TimeRecord
import com.hr.timer.R
import com.hr.timer.Util.Main.MainActivity.Companion.timeArray

class TimeAdapter(mContext : Context) : RecyclerView.Adapter<TimeAdapter.ViewHolder>() {
    var mContext = mContext

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_record, parent, false)

        val metrics = mContext.resources.displayMetrics
        val widthPixels = metrics.heightPixels
        val layoutParams = view.layoutParams
        layoutParams.height = (widthPixels * 0.075).toInt()

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position == 0){
            holder.no.setTextColor(mContext.getColor(R.color.cobaltBlue))
            holder.time.setTextColor(mContext.getColor(R.color.cobaltBlue))
            holder.timeGap.setTextColor(mContext.getColor(R.color.cobaltBlue))
        }else{
            holder.no.setTextColor(mContext.getColor(R.color.black))
            holder.time.setTextColor(mContext.getColor(R.color.black))
            holder.timeGap.setTextColor(mContext.getColor(R.color.black))
        }
        holder.no.text = timeArray.get(position).no.toString()
        holder.time.text = timeArray.get(position).time
        holder.timeGap.text = timeArray.get(position).timeGap

    }

    override fun getItemCount(): Int {
        return timeArray.size
    }

    fun addItem(timeRecord: TimeRecord) {
        timeArray.add(timeRecord)
        notifyItemInserted(timeArray.size-10)
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val no: TextView = v.findViewById(R.id.textNo)
        val time: TextView = v.findViewById(R.id.textTime)
        val timeGap: TextView = v.findViewById(R.id.textTimeGap)
    }
}