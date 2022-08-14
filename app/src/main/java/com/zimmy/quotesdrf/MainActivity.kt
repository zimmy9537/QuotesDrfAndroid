package com.zimmy.quotesdrf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.zimmy.quotesdrf.api.QuoteService
import com.zimmy.quotesdrf.api.RetrofitHelper
import com.zimmy.quotesdrf.models.QuoteListItem
import com.zimmy.quotesdrf.repository.QuoteRepository
import com.zimmy.quotesdrf.viewModels.QuoteViewModel
import com.zimmy.quotesdrf.viewModels.QuoteViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: QuoteViewModel
    private val TAG = MainActivity::class.java.simpleName
    private lateinit var quoteEt: EditText
    private lateinit var authorEt: EditText
    private lateinit var post: Button
    private lateinit var delete: Button
    private lateinit var reload: FloatingActionButton
    private lateinit var idEt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        quoteEt = findViewById(R.id.quote)
        authorEt = findViewById(R.id.author)
        post = findViewById(R.id.post)
        delete = findViewById(R.id.delete)
        idEt = findViewById(R.id.deletionEt)
        reload = findViewById(R.id.reload)


        val quoteService = RetrofitHelper.getInstance().create(QuoteService::class.java)
        val repository = QuoteRepository(quoteService)


        //view model creation
        viewModel = ViewModelProvider(
            this,
            QuoteViewModelFactory(repository)
        ).get(QuoteViewModel::class.java)


        viewModel.getQuotes()
        //observe changes
        viewModel.quotes.observe(this, Observer {
            for (ele in it) {
                Log.v(TAG, "${ele.id}, with author ${ele.quoteAuthor}, and ${ele.quoteContent}")
            }
        })

        reload.setOnClickListener {
            viewModel.getQuotes()
            viewModel.quotes.observe(this, Observer {
                for (ele in it) {
                    Log.v(TAG, "${ele.id}, with author ${ele.quoteAuthor}, and ${ele.quoteContent}")
                }
            })
        }

        delete.setOnClickListener {
            viewModel.deleteQuote(idEt.text.length)
            viewModel.deleteResponse.observe(this, Observer {
                Toast.makeText(this, it.response, Toast.LENGTH_SHORT).show()
                Log.v("Response json ", it.response)
            })
            reload.performClick()
        }


        post.setOnClickListener {
            val quote = QuoteListItem(authorEt.text.toString(), quoteEt.text.toString())
            viewModel.postQuote(quote)
            //observe changes
            viewModel.response.observe(this, Observer {
                Toast.makeText(this, it.response, Toast.LENGTH_SHORT).show()
                Log.v("Response json ", it.response)
            })
        }
    }
}