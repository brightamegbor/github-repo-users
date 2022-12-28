package com.example.githubrepousers.app.helpers

import android.icu.number.Notation
import android.icu.number.NumberFormatter
import android.icu.number.Precision
import android.icu.text.DecimalFormat
import android.icu.text.SimpleDateFormat
import android.icu.util.ULocale
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.String.format
import java.util.*

class Utils {
    companion object {
        fun <T> debounce(
            waitMs: Long = 500L,
            coroutineScope: CoroutineScope,
            destinationFunction: (T) -> Unit
        ): (T) -> Unit {
            var debounceJob: Job? = null
            return { param: T ->
                debounceJob?.cancel()
                debounceJob = coroutineScope.launch {
                    delay(waitMs)
                    destinationFunction(param)
                }
            }
        }


        private fun currentDate(): Date {
            val calendar = Calendar.getInstance()
            return calendar.time
        }

        fun toMomentAgo(text: String?): String {
            val oneSec = 1000L
            val oneMin: Long = 60 * oneSec
            val oneHour: Long = 60 * oneMin
            val oneDay: Long = 24 * oneHour
            val oneMonth: Long = 30 * oneDay
            val oneYear: Long = 365 * oneDay

            val now = currentDate().time

            val dateString = text ?: Calendar.getInstance().time.toString()
            var formatDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(dateString).time

            if (formatDate < 1000000000000L) {
                formatDate *= 1000
            }
            if (formatDate > now || formatDate <= 0) {
                return "in the future"
            }

            val diff = now - formatDate


            val diffMin: Long = diff / oneMin
            val diffHours: Long = diff / oneHour
            val diffDays: Long = diff / oneDay
            val diffMonths: Long = diff / oneMonth
            val diffYears: Long = diff / oneYear

            return when {
                diffYears > 0 -> {
                    "$diffYears years ago"
                }
                diffMonths > 0 && diffYears < 1 -> {
                    "${(diffMonths - diffYears / 12)} months ago "
                }
                diffDays > 0 && diffMonths < 1 -> {
                    "${(diffDays - diffMonths / 30)} days ago "
                }
                diffHours > 0 && diffDays < 1 -> {
                    "${(diffHours - diffDays * 24)} hours ago "
                }
                diffMin > 0 && diffHours < 1 -> {
                    "${(diffMin - diffHours * 60)} min ago "
                }
                else -> {
                    "just now"
                }
            }
        }

        fun toCapitalize(text: String): String {
            val textCapital = text.replaceFirstChar {
                if (it.isLowerCase())
                    it.titlecase(Locale.getDefault())
                else
                    it.toString()
            }
            return textCapital
        }

        fun formatNumber(text: Long): String {
            return NumberFormatter.with()
                .notation(Notation.compactShort())
                .precision(Precision.maxSignificantDigits(3))
                .locale(ULocale.ENGLISH)
                .format(text)
                .toString();
        }
    }
}