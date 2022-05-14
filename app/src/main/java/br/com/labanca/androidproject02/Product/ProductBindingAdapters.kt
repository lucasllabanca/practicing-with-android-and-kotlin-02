package br.com.labanca.androidproject02.Product

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.labanca.androidproject02.network.Product

@BindingAdapter("productsList")
fun bindProductsList(recyclerView: RecyclerView, products: List<Product>?) {
    val adapter = recyclerView.adapter as ProductAdapter
    adapter.submitList(products)
}

@BindingAdapter("productPrice")
fun bindProductPrice(txtProductPrice: TextView, productPrice: Double?) {
    txtProductPrice.text = productPrice?.let {"$ " + "%.2f".format(it)}
}