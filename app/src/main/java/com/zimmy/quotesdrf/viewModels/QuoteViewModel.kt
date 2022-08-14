package com.zimmy.quotesdrf.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zimmy.quotesdrf.models.QuoteList
import com.zimmy.quotesdrf.models.QuoteListItem
import com.zimmy.quotesdrf.models.Response
import com.zimmy.quotesdrf.repository.QuoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuoteViewModel(private val repository: QuoteRepository) : ViewModel() {

    fun getQuotes() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getQuotes()
        }
    }

    val quotes: LiveData<QuoteList>
        get() = repository.quotes

    val response: LiveData<Response>
        get() = repository.response

    val deleteResponse:LiveData<Response>
    get()=repository.deleteResponse// changes made here, check delete quote if working

    fun deleteQuote(id: Int){
        viewModelScope.launch (Dispatchers.IO){
            repository.deleteQuotes(id)
        }
    }

    fun postQuote(quote: QuoteListItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.postQuote(quote)
        }
    }
}