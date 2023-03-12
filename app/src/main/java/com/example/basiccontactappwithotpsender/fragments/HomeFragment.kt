package com.example.basiccontactappwithotpsender.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.basiccontactappwithotpsender.adapter.ContactsAdapter
import com.example.basiccontactappwithotpsender.databinding.HomeFragmentLayoutBinding
import com.example.basiccontactappwithotpsender.model.ContactsGson
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader

class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    lateinit var inflate: HomeFragmentLayoutBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        inflate = HomeFragmentLayoutBinding.inflate(layoutInflater, container, false)
        return inflate.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Retrieve all contacts from JSON
        activity?.apply {
            val open = assets.open("fake_contacts.json")
            val bufferedReader = BufferedReader(InputStreamReader(open))
            var readLine = bufferedReader.readLine()

            var contactList = ""

            while (readLine != null) {
                contactList += readLine.replace("\n", "")
                readLine = bufferedReader.readLine()
            }
            val replace = contactList.replace("  ", " ")
            val contactsList = Gson().fromJson(replace, ContactsGson::class.java)
            val contactsList1 = contactsList.contacts

            val contactsAdapter = ContactsAdapter(
                activity = this,
                contactsList = contactsList1
            )
            inflate.contactsRv.adapter = contactsAdapter
            contactsAdapter.notifyDataSetChanged()

        }

    }
}