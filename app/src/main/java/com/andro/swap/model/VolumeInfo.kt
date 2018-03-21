package com.andro.swap.model

import java.io.Serializable

class VolumeInfo : Serializable {
    var title: String? = null
    var authors: ArrayList<String>? = null
    var publisher: String? = null
    var publishedDate: String? = null
    var description: String? = null
    var industryIdentifiers: ArrayList<IndustryIdentifier>? = null
    var pageCount: String? = null
    var categories: ArrayList<String>? = null
    var maturityRating: String? = null
    var imageLinks: ImageLinks? = null
    var language: String? = null
    var infoLink: String? = null
}