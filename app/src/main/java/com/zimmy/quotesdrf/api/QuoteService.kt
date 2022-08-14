package com.zimmy.quotesdrf.api

import com.zimmy.quotesdrf.models.QuoteList
import com.zimmy.quotesdrf.models.QuoteListItem
import retrofit2.Response
import retrofit2.http.*

interface QuoteService {

    @GET("quote/list/")
    suspend fun getQuoteList(): Response<QuoteList>

    @POST("quote/quote-create/")
    suspend fun postQuote(@Body quote: QuoteListItem): Response<com.zimmy.quotesdrf.models.Response>

    @DELETE("quote/{id}/")
    suspend fun deleteQuote(@Path("id") id: Int): Response<com.zimmy.quotesdrf.models.Response>
}