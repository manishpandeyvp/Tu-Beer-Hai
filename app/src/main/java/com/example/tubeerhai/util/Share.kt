package com.example.tubeerhai.util

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.widget.Toast
import com.example.tubeerhai.data.model.BeerModel

object Share {
    fun directWhatsAppShare(model: BeerModel, activity: Activity) {
        val text = "Hey! I got to know about this awesome beer \"${model.name}\". It got a really nice tagline saying \"${model.tagline}\" and was first brewed on ${model.firstBrewed}"
        val intent = Intent("android.intent.action.MAIN")
        intent.putExtra("android.intent.extra.TEXT", text)
        intent.action = "android.intent.action.SEND"
        intent.setPackage("com.whatsapp")
        intent.type = "text/plain"
        try {
            activity.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(activity, "Oops! Whatsapp not installed! :(", Toast.LENGTH_LONG).show()
        }
    }
}