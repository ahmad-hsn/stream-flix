package com.streamflix.utils

fun String.getYearFromDataString(): String {
    return this.split("-")[0]
}