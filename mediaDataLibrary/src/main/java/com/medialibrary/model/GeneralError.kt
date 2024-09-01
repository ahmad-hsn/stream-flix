package com.medialibrary.model

import com.google.gson.annotations.SerializedName

data class GeneralError(

    @field:SerializedName("success")
    val success: Boolean? = false,

    @field:SerializedName("message")
    val message: String? = ""

)