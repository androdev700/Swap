package com.andro.swap.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class VolumeInfo {

    @PrimaryKey
    val id: String? = null

    @ColumnInfo(name = "name")
    val title: String? = null

    @ColumnInfo(name = "authors")
    val authors: ArrayList<String>? = null

    @ColumnInfo(name = "publisher")
    val publisher: String? = null

    @ColumnInfo(name = "publishedDate")
    val publishedDate: String? = null

    @ColumnInfo(name = "description")
    val description: String? = null

    @ColumnInfo(name = "industryIdentifiers")
    val industryIdentifiers: ArrayList<IndustryIdentifier>? = null

    @ColumnInfo(name = "pageCount")
    val pageCount: String? = null

    @ColumnInfo(name = "categories")
    val categories: ArrayList<String>? = null

    @ColumnInfo(name = "maturityRating")
    val maturityRating: String? = null

    @ColumnInfo(name = "imageLinks")
    val imageLinks: ImageLinks? = null

    @ColumnInfo(name = "language")
    val language: String? = null

    @ColumnInfo(name = "infoLink")
    val infoLink: String? = null
}