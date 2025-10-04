package com.example.chefmate.api

object ApiConstant {
    var MAIN_URL = ""

    fun setMainUrl(url: String) {
        MAIN_URL = "https://${url.trim()}.loca.lt/"
    }
}
