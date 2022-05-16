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

class FeedAdapter (val productList : List<Product>, val context : Context, val addPanier : (Product) -> Unit ) : RecyclerView.Adapter<FeedAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_product,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: FeedAdapter.ViewHolder, position: Int) {
        val product = productList[position]

        holder.name.text = product.nom
        holder.description.text = product.description
        holder.price.text = "${product.prix} dhs"
        Glide.with(context)
            .load(product.imageUrl)
            .into(holder.image_product)

        holder.image_add_panier.setOnClickListener {
            addPanier(product)
            holder.image_add_panier.setImageResource(R.drawable.ic_baseline_done_24)
        }




    }
    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
        val name = itemView.findViewById(R.id.txt_name) as TextView
        val description = itemView.findViewById(R.id.txt_desc) as TextView
        val price = itemView.findViewById(R.id.txt_price) as TextView
        val image_product = itemView.findViewById(R.id.img_product) as ImageView
        val image_add_panier = itemView.findViewById(R.id.img_panier) as ImageView

    }
}