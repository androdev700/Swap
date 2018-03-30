package com.andro.swap.model

import java.io.Serializable

class BookItem : Serializable {
    var id: String? = null
    var selfLink: String? = null
    var volumeInfo: VolumeInfo? = null
    var owners: HashMap<String, Any>? = null
}
