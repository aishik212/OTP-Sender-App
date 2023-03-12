package com.example.basiccontactappwithotpsender.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.basiccontactappwithotpsender.databinding.ContactDetailsLayoutBinding
import com.example.basiccontactappwithotpsender.model.Contact
import com.example.basiccontactappwithotpsender.utils.Utils

class ContactDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflate = ContactDetailsLayoutBinding.inflate(layoutInflater)
        setContentView(inflate.root)

        inflate.backButton.setOnClickListener {
            finish()
        }

        val contactDetails = intent.extras?.get("ContactDetails") as Contact

        inflate.sendMessage.setOnClickListener {
            val intent = Intent(this, SendMessageActivity::class.java)
            intent.putExtra("ContactDetails", contactDetails as java.io.Serializable)
            startActivity(intent)
        }

        inflate.contactName.text = contactDetails.name
        inflate.contactDp.text = contactDetails.name[0].toString()
        inflate.contactPhone.text = contactDetails.phone
        inflate.cardView.setCardBackgroundColor(
            ContextCompat.getColor(
                this,
                Utils.colorList.random()
            )
        )

    }
}
