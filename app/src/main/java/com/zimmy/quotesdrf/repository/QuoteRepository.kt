package com.zimmy.quotesdrf.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zimmy.quotesdrf.api.QuoteService
import com.zimmy.quotesdrf.models.QuoteList
import com.zimmy.quotesdrf.models.QuoteListItem
import com.zimmy.quotesdrf.models.Response

class QuoteRepository(private val quoteService: QuoteService) {

    private var quotesLiveData = MutableLiveData<QuoteList>()
    val quotes: LiveData<QuoteList>
        get() = quotesLiveData

    private var responseLiveData = MutableLiveData<Response>()
    val response: LiveData<Response>
        get() = responseLiveData

    private var deleteLiveData = MutableLiveData<Response>()
    val deleteResponse: LiveData<Response>
        get() = deleteLiveData

    suspend fun deleteQuotes(id: Int) {
        val result = quoteService.deleteQuote(id)
        if (result.body() != null) {
            deleteLiveData.postValue(result.body())
        }
    }


    suspend fun getQuotes() {
        val result = quoteService.getQuoteList()
        if (result.body() != null) {
            quotesLiveData.postValue(result.body())
        }
    }

    suspend fun postQuote(quote: QuoteListItem) {
        val result = quoteService.postQuote(quote)
        if (result.body() != null) {
            responseLiveData.postValue(result.body())
        }
    }
}