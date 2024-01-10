package com.example.alp.utils

import java.text.NumberFormat
import java.util.Locale

fun rupiahFormat(number: Number): String {
    val localeID = Locale("in", "ID")
    val format = NumberFormat.getCurrencyInstance(localeID).apply {
        maximumFractionDigits = 0 // Do not show any decimal places
    }
    return format.format(number)
        .replace(",00", "")
}