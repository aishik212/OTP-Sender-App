package com.example.basiccontactappwithotpsender.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.basiccontactappwithotpsender.BuildConfig
import com.example.basiccontactappwithotpsender.dao.MessageDao
import com.example.basiccontactappwithotpsender.databinding.SendMessageLayoutBinding
import com.example.basiccontactappwithotpsender.model.Contact
import com.example.basiccontactappwithotpsender.model.SentMessageModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import javax.inject.Inject
import kotlin.math.roundToInt


@AndroidEntryPoint
class SendMessageActivity : AppCompatActivity() {
    lateinit var inflate: SendMessageLayoutBinding

    @Inject
    lateinit var messageDao: MessageDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inflate = SendMessageLayoutBinding.inflate(layoutInflater)
        setContentView(inflate.root)

        inflate.backButton.setOnClickListener {
            finish()
        }
        val contactDetails = intent.extras?.get("ContactDetails") as Contact
        inflate.contactName.text = contactDetails.name
        inflate.contactDp.text = contactDetails.name[0].toString()
        inflate.contactPhone.text = contactDetails.phone

        updateOtp()

        inflate.sendMessage.setOnClickListener {
            sendMessage(inflate.otpTv.text.toString(), contactDetails)
        }

    }

    private fun updateOtp() {
        CoroutineScope(Dispatchers.Main).launch {

            var otp = ""
            for (i in 1..6) {
                otp += (Math.random() * 9).roundToInt()
            }

            inflate.otpTv.text = "Hi. Your OTP is: $otp "
        }
    }

    @Inject
    lateinit var okHttpClient: OkHttpClient

    private fun sendMessage(message: String, contact: Contact) {
        runOnUiThread {
            Snackbar.make(inflate.root, "Sending Message", Snackbar.LENGTH_SHORT).show()
        }
        val mediaType: MediaType? = "application/x-www-form-urlencoded".toMediaTypeOrNull()
        val body: RequestBody =
            RequestBody.create(mediaType, "Body=$message&From=+15673623079&To=${contact.phone}")
        val request: Request = Request.Builder()
            .url("https://api.twilio.com/2010-04-01/Accounts/${BuildConfig.ACCOUNT_ID}/Messages.json")
            .method("POST", body)
            .addHeader(
                "Authorization",
                "Basic ${BuildConfig.API_KEY}"
            )
            .addHeader("Content-Type", "application/x-www-form-urlencoded")
            .build()
        CoroutineScope(Dispatchers.IO).launch {
            val response: Response = okHttpClient.newCall(request).execute()
            when (response.code) {
                201 -> {
                    runOnUiThread {
                        Snackbar.make(inflate.root, "Message Sent", Snackbar.LENGTH_SHORT).show()
                    }
                    messageDao.insert(
                        SentMessageModel(
                            date = "${System.currentTimeMillis()}",
                            messageSubject = message,
                            messagePhone = contact.phone,
                            messageName = contact.name,
                            messageOtp = inflate.otpTv.text.toString()
                        )
                    )
                }
                400 -> {
                    val body1 = response.body
                    if (body1 != null) {
                        try {
                            val jsonObject = JSONObject(body1.string())
                            val code = jsonObject.get("code").toString()
                            when (code) {
                                "21211" -> {
                                    snackBar(inflate.root, "Invalid Phone Number")
                                }
                                "21408" -> {
                                    snackBar(inflate.root, "Unable To Send To Number")
                                }
                            }
                        } catch (e: Exception) {
                            snackBar(inflate.root)
                        }
                    } else {
                        snackBar(inflate.root)
                    }
                }
                else -> {
                    snackBar(inflate.root)
                }
            }
            updateOtp()
        }
    }

    private fun snackBar(view: View, errorText: String = "Oops, Some Error Occured") {
        Snackbar.make(
            view,
            errorText,
            Snackbar.LENGTH_SHORT
        )
            .show()
    }
}
