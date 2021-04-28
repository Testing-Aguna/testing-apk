package com.aguna.app.model

data class SubCategory (
        var id : String = "",
        var title : String? = "",
        var price : Int? = 0,
        var items : ArrayList<SubCategory> = ArrayList(),
        )