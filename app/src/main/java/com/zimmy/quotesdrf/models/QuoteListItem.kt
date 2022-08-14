package com.zimmy.quotesdrf.models

data class QuoteListItem(
    val id: Int,
    val quoteAuthor: String,
    val quoteContent: String
){
    constructor(quoteAuthor: String,quoteContent: String):this(0,quoteAuthor,quoteContent)
}