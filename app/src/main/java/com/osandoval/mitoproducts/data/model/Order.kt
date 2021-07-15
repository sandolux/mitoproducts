package com.osandoval.mitoproducts.data.model

data class OrderEntity(
    val uid: Int=0,
    val products: List<ProductEntity>

)

/*
* @Insert
     public void insertWithFriends(User user, List<User> friends);
* */