package com.andro.swap.model

import java.io.Serializable

class User : Serializable {
    var name: String? = null
    var email: String? = null
    var uid: String? = null
    var bookCollection: HashMap<String, BookItem>? = null
}
