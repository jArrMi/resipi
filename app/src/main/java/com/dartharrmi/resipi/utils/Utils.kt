package com.dartharrmi.resipi.utils

import android.content.Context
import android.text.Spanned
import androidx.core.text.HtmlCompat
import com.dartharrmi.resipi.R

object Utils {

    fun formatServings(servings: Int, context: Context) = context.getString(R.string.item_recipe_serving, servings)

    fun formatReadyTime(readyTime: Int, context: Context) = context.getString(
            R.string.item_recipe_ready_time, parseMinutes(readyTime)
    )

    private fun parseMinutes(totalMinutes: Int): String {
        val hours = totalMinutes / 60
        val minutes = totalMinutes % 60

        val hoursFormatted = if (hours < 10) "0${hours}" else "$hours"
        val minutesFormatted = if (minutes < 10) "0${minutes}" else "$minutes"
        return "$hoursFormatted:$minutesFormatted"
    }

    fun formatSummary(summary: String): Spanned = HtmlCompat.fromHtml(summary, HtmlCompat.FROM_HTML_MODE_LEGACY)

}