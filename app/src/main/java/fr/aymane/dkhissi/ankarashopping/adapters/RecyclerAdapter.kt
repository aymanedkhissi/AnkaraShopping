package fr.aymane.dkhissi.ankarashopping.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.aymane.dkhissi.ankarashopping.R
import fr.aymane.dkhissi.ankarashopping.objects.Product

class RecyclerAdapter (val productList : List<Product>, val context : Context) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_product,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        val product = productList[position]

        holder.name.text = product.nom
        holder.description.text = product.description
        holder.price.text = product.prix
        Glide.with(context)
            .load(product.imageUrl)
            .into(holder.image)




    }
    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
        val name = itemView.findViewById(R.id.txt_name) as TextView
        val description = itemView.findViewById(R.id.txt_desc) as TextView
        val price = itemView.findViewById(R.id.txt_price) as TextView
        val image = itemView.findViewById(R.id.img_product) as ImageView

    }
}