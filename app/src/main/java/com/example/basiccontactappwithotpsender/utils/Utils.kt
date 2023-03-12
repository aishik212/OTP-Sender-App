package com.example.basiccontactappwithotpsender.utils

import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.basiccontactappwithotpsender.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Utils {
    companion object {
        fun loadImage(view: ImageView, avatar: BitmapDrawable) {
            CoroutineScope(Dispatchers.Main).launch {
                Glide.with(view).load(avatar).fitCenter().into(view)
            }
        }

        val colorList =
            listOf(
                R.color.seed,
                R.color.md_theme_light_primary,
                R.color.md_theme_light_secondary,
                R.color.md_theme_dark_primary,
                R.color.md_theme_dark_secondary,
                R.color.md_theme_light_inversePrimary,
                R.color.md_theme_light_surfaceTint,
            )


    }
}