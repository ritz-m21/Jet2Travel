package com.jet2.jet2travelblog

import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime

class Utility {
    companion object {

        @ExperimentalTime
        fun getDiff(input: String): String? {
            val dateFormatter: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            dateFormatter.setLenient(false)
            val today = Date()
            val newDate: String = dateFormatter.format(today)
            getDateDiff(dateFormatter, input, newDate)?.let {
                println("Days: " + it / 30)
                return when (it) {
                    in 0..30 -> String.format("%s days", it)
                    else -> String.format("%s months", it / 30)
                }
            }
        }

        @ExperimentalTime
        fun getDateDiff(format: SimpleDateFormat, oldDate: String, newDate: String): Long {
            return try {
                DurationUnit.DAYS.convert(
                    format.parse(newDate).time - format.parse(oldDate).time,
                    DurationUnit.MILLISECONDS
                )
            } catch (e: Exception) {
                e.printStackTrace()
                0
            }
        }

        fun getLikes(input: Int): String {
            return when (input) {
                in 0..999 -> String.format("%s Likes", input)
                else -> String.format("%.2fK Likes", (input.toDouble() / 1000L))
            }
        }

        fun getComments(input: Int): String {
            return when (input) {
                in 0..999 -> String.format("%s Comments", input)
                else -> String.format("%.2fK Comments", input.toDouble() / 1000L)
            }
        }
    }

}