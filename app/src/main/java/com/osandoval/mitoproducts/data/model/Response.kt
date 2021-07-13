package com.osandoval.mitoproducts.data.model

data class Response(
    val data: List<Product?>,
    val status: Boolean=false,
    val message: String=""
)
