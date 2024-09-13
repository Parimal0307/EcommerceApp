package com.example.ecommerce2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class ProductAdapter(
    private val listOfProducts: List<Product>,
    private val context: Context
): RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(
        itemView: View
    ): RecyclerView.ViewHolder(itemView){
        val prodImg: ImageView = itemView.findViewById(R.id.prod_img)
        val prodName: TextView = itemView.findViewById(R.id.prod_name)
        val prodPrice: TextView = itemView.findViewById(R.id.prod_price)
        val prodDesc: TextView = itemView.findViewById(R.id.prod_desc)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_layout, parent, false)
        return ProductViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfProducts.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentProduct = listOfProducts[position]
        holder.prodName.text = currentProduct.productName
        holder.prodPrice.text = currentProduct.productPrice
        holder.prodDesc.text = currentProduct.productDesc
        Glide.with(context)
            .load(currentProduct.productImg)
            .into(holder.prodImg)
    }
}