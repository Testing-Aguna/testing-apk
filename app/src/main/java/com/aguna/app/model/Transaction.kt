package com.aguna.app.model

data class Transaction(
    var id : String = "",
    var userId : String = "",
    var items : ArrayList<ItemCategory> = ArrayList(),
    var totalPrice : Int = 0,
    var type : String = ""
)
