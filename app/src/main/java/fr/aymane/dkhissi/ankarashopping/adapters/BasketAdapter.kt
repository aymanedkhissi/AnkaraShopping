package fr.aymane.dkhissi.ankarashopping.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.aymane.dkhissi.ankarashopping.R
import fr.aymane.dkhissi.ankarashopping.objects.Product

class BasketAdapter(
    val productList: MutableList<Product>,
    val context: Context,
    val removePanier: (Product) -> Unit,
    val updateTotalPrice: (Long,String) -> Unit
) : RecyclerView.Adapter<BasketAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_product_basket,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: BasketAdapter.ViewHolder, position: Int) {
        val product = productList[position]

        var quantity = 1

        holder.name.text = product.nom
        holder.description.text = product.description
        holder.price.text = "${product.prix} dhs"
        Glide.with(context)
            .load(product.imageUrl)
            .into(holder.image_product)
        holder.image_remove_panier.setOnClickListener {
            removePanier(product)
            productList.removeAt(position)
            notifyDataSetChanged()
        }

        holder.txt_quantity.text = quantity.toString()

        holder.image_plus_quantity.setOnClickListener {
            quantity++
            holder.txt_quantity.text = quantity.toString()
            updateTotalPrice(product.prix,"plus")
        }
        holder.image_minus_quantity.setOnClickListener {
            if (quantity>1){
                updateTotalPrice(product.prix,"minus")
            quantity--
            holder.txt_quantity.text = quantity.toString()
            }
        }





    }
    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
        val name = itemView.findViewById(R.id.txt_name) as TextView
        val description = itemView.findViewById(R.id.txt_desc) as TextView
        val txt_quantity = itemView.findViewById(R.id.txt_quantity) as TextView
        val price = itemView.findViewById(R.id.txt_price) as TextView
        val image_product = itemView.findViewById(R.id.img_product) as ImageView
        val image_remove_panier = itemView.findViewById(R.id.img_panier) as ImageView
        val image_plus_quantity = itemView.findViewById(R.id.img_add) as ImageView
        val image_minus_quantity = itemView.findViewById(R.id.img_minus) as ImageView

    }
}