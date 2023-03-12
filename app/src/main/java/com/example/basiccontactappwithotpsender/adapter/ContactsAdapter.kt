package com.example.basiccontactappwithotpsender.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.basiccontactappwithotpsender.activities.ContactDetailsActivity
import com.example.basiccontactappwithotpsender.databinding.ContactRowsLayoutBinding
import com.example.basiccontactappwithotpsender.model.Contact
import com.example.basiccontactappwithotpsender.utils.Utils

class ContactsAdapter(
    private val activity: Activity,
    private val contactsList: List<Contact>
) :
    RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {
    class ViewHolder(
        private val binding: ContactRowsLayoutBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            contactModel: Contact,
            activity: Activity,
        ) {
            binding.contactName.text = contactModel.name
            binding.contactDp.text = contactModel.name[0].toString()
            binding.cardView.setCardBackgroundColor(
                ContextCompat.getColor(
                    activity,
                    Utils.colorList.random()
                )
            )

            binding.root.setOnClickListener {
                val intent = Intent(activity, ContactDetailsActivity::class.java)
                intent.putExtra("ContactDetails", contactModel as java.io.Serializable)
                activity.startActivity(intent)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ContactRowsLayoutBinding.inflate(
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