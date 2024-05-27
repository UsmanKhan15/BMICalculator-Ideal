package com.example.bmicalculator_ideal.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.bmicalculator_ideal.R

class Utils {
    companion object {

        var heightType = 0
        var weightType = 0

        var heightIncm = 0
        var heightFt = 0
        var heightInches = 0

        var weightInKg = 0
        var weightLb = 0

        var gender = "Male"

        var langaugeSetting = false
        var langaugeSettingVisited = false

        const val REQUEST_IMAGE_PICK = 100

        fun contactUs(context: Context) {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:ayesha.sharif48@gmail.com")
            intent.putExtra(Intent.EXTRA_SUBJECT, "Subject of the email")
            context.startActivity(intent)
        }

        fun openPlayStoreForRating(context: Context) {
            val appPackageName = context.packageName
            try {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=$appPackageName")
                    )
                )
            } catch (e: android.content.ActivityNotFoundException) {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                    )
                )
            }
        }

        fun openLink(context: Context, link: String) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(link)
            context.startActivity(intent)
        }

        fun shareApp(context: Context) {
            val appName = context.getString(R.string.app_name)
            val appStoreUrl =
                "https://play.google.com/store/apps/details?id=com.raiwatechnologies.bmicalculator.idealweighttracker"

            val message = if (appStoreUrl.isNotBlank()) {
                "Hey! Check out this awesome app, $appName:\n$appStoreUrl"
            } else {
                "Hey! Check out this cool app, $appName. Install it now!"
            }

            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, message)

            val chooserIntent = Intent.createChooser(shareIntent, "Share $appName")
            context.startActivity(chooserIntent)
        }
    }
}