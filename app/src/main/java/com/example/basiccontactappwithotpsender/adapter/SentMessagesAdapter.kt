package com.example.basiccontactappwithotpsender.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.basiccontactappwithotpsender.databinding.SentMessagesRowsLayoutBinding
import com.example.basiccontactappwithotpsender.model.SentMessageModel
import java.text.SimpleDateFormat
import java.util.*

class SentMessagesAdapter(
    private val activity: Activity,
    private val contactsList: MutableList<SentMessageModel>
) :
    RecyclerView.Adapter<SentMessagesAdapter.ViewHolder>() {
    class ViewHolder(
        private val binding: SentMessagesRowsLayoutBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SimpleDateFormat")
        fun bind(
            messageModel: SentMessageModel,
            activity: Activity,
        ) {
            binding.messageSentTo.text = "Sent to ${messageModel.messageName}"
            binding.messageOtp.text = messageModel.messageOtp.replace("Hi. Your OTP is: ", "OTP - ")
            val yourmilliseconds = messageModel.date.toLong()
            val sdf = SimpleDateFormat("MMM dd,yyyy HH:mm")
            val resultdate = Date(yourmilliseconds)
            binding.messageTime.text = sdf.format(resultdate)
            binding.messageSentNumber.text = "Number - ${messageModel.messagePhone}"
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            SentMessagesRowsLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val apps = contactsList[position]
        holder.bind(apps, activity)
    }

    override fun getItemCount() = contactsList.size
}