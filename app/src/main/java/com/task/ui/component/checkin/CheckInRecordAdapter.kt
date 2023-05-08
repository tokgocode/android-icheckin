package com.task.ui.component.checkin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.task.R
import com.task.data.dto.checkin.CheckInRecordOutputDto
import com.task.utils.TimeUtil

class CheckInRecordAdapter(
    var items: List<CheckInRecordOutputDto>,
    var context: Context
) : RecyclerView.Adapter<CheckInRecordAdapter.CheckInRecordViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckInRecordViewHolder {
        return CheckInRecordViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_list_item_checkin_record, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CheckInRecordViewHolder, position: Int) {
        holder.bind(items[position], context)
    }

    class CheckInRecordViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvDateTime: TextView = itemView.findViewById(R.id.tvDateTime)
        private val tvClassroom: TextView = itemView.findViewById(R.id.tvClassroom)

        fun bind(data: CheckInRecordOutputDto, context: Context) {
            tvDateTime.text = TimeUtil.formatUtcTime(data.dateTime)
            tvClassroom.text = data.classroomName
        }
    }
}
