package com.aguna.app.model

import android.graphics.Bitmap
import android.media.Image
import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ItemCategory(
    var id : String = "",
    var name : String = "",
    var price : Int = 0,
    var number : Int = 1,
    var priceTotal : Int = price,
    var img1 : Uri? = null,
    var img2 : Uri? = null,
    var img3 : Uri? = null,
    var imgNumber : Int = 0
) : Parcelable
