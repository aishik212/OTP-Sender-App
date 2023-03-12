package com.example.basiccontactappwithotpsender.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.basiccontactappwithotpsender.MainActivity
import com.example.basiccontactappwithotpsender.adapter.SentMessagesAdapter
import com.example.basiccontactappwithotpsender.dao.MessageDao
import com.example.basiccontactappwithotpsender.databinding.MessagesFragmentLayoutBinding
import com.example.basiccontactappwithotpsender.model.SentMessageModel
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MessagesFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @Inject
    lateinit var messageDao: MessageDao

    lateinit var inflate: MessagesFragmentLayoutBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        inflate = MessagesFragmentLayoutBinding.inflate(layoutInflater, container, false)
        return inflate.root
    }

    lateinit var contactsAdapter: SentMessagesAdapter
    val messageModels: MutableList<SentMessageModel> = mutableListOf()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.apply {

            contactsAdapter = SentMessagesAdapter(
                activity = this@apply,
                contactsList = messageModels
            )
            inflate.contactsRv.adapter = contactsAdapter

            MainActivity.inflate.tabLayout.addOnTabSelectedListener(object :
                TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.apply {
                        if (tab.position == 1) {
                            activity?.apply {
                                CoroutineScope(Dispatchers.IO).launch {
                                    messageModels.clear()
                                    val allMessage = messageDao.getAllMessage()
                                    messageModels.addAll(allMessage)
                                }.invokeOnCompletion {
                                    runOnUiThread {
                                        contactsAdapter.notifyDataSetChanged()
                                    }
                                }
                            }
                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    tab?.apply {
                        if (tab.position == 1) {
                            activity?.apply {
                                CoroutineScope(Dispatchers.IO).launch {
                                    messageModels.clear()
                                    val allMessage = messageDao.getAllMessage()
                                    messageModels.addAll(allMessage)
                                }.invokeOnCompletion {
                                    runOnUiThread {
                                        contactsAdapter.notifyDataSetChanged()
                                    }
                                }
                            }
                        }
                    }

                }
            })


        }

    }

    override fun onResume() {
        super.onResume()
        activity?.apply {
            CoroutineScope(Dispatchers.IO).launch {
                messageModels.clear()
                val allMessage = messageDao.getAllMessage()
                messageModels.addAll(allMessage)
            }.invokeOnCompletion {
                runOnUiThread {
                    contactsAdapter.notifyDataSetChanged()
                }
            }
        }
    }
}