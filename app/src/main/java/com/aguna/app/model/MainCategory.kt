package com.aguna.app.model

data class MainCategory(
        var image : String? = "",
        var title : String? = "",
        var subCategory: ArrayList<SubCategory>
)