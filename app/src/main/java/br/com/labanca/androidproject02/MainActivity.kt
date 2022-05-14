package br.com.labanca.androidproject02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import br.com.labanca.androidproject02.product.ProductListViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //keeps track of the main activity life cycle ViewModelProvider(this)
        //then, when an activity occurs, gets the ProductListViewModel
        //When the activity is recreated, the model is kept apart and do not run again the get
        //val viewModel = ViewModelProvider(this).get(ProductListViewModel::class.java)

    }
}