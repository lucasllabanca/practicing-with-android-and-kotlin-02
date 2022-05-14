package br.com.labanca.androidproject02.Product

import android.util.Log
import androidx.lifecycle.ViewModel
import br.com.labanca.androidproject02.network.SalesApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private const val TAG = "ProductListViewModel"

class ProductListViewModel: ViewModel() {

    //Job to run some task
    private var viewModelJob = Job()

    //passing the scope and where I want it to run
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getProducts()
    }

    private fun getProducts() {

        Log.i(TAG, "Preparing to request products list")
        coroutineScope.launch { //Creating a separated thread
            val getProductsDeferred = SalesApi.retrofitService.getProducts()

            Log.i(TAG, "Loading products")
            val productsList = getProductsDeferred.await()

            Log.i(TAG, "Number of products ${productsList.size}")
        }

        Log.i(TAG, "Products list requested")
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}