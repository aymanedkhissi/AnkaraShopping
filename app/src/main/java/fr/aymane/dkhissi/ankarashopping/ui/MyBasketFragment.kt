package fr.aymane.dkhissi.ankarashopping.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import fr.aymane.dkhissi.ankarashopping.R
import fr.aymane.dkhissi.ankarashopping.adapters.BasketAdapter
import fr.aymane.dkhissi.ankarashopping.databinding.FragmentMyBasketBinding
import fr.aymane.dkhissi.ankarashopping.objects.Product


class MyBasketFragment : Fragment() {

    private lateinit var binding: FragmentMyBasketBinding

    private lateinit var auth: FirebaseAuth
    val listProduits = mutableListOf<Product>()
    var documentId : String? = null
    var totalPrice : Long = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyBasketBinding.inflate(inflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentUser = auth.currentUser
        val db = Firebase.firestore

        binding.btnPay.setOnClickListener {

            val action = MyBasketFragmentDirections.actionMyBasketFragmentToPaymentFragment( totalPrice )
            findNavController().navigate(action)
            listProduits.clear()
        }

        if(currentUser != null){
            db.collection("users").document(currentUser.email.toString()).collection("produits")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {

                        val produit = Product(
                            nom = document.data.get("nom") as String,
                            description = document.data.get("description") as String,
                            prix =  document.data.get("prix") as Long,
                            imageUrl = document.data["imageUrl"] as String,
                        )
                        totalPrice += produit.prix
                        listProduits.add(produit)

                        // this creates a vertical layout Manager
                        binding.basketRecyclerView.layoutManager = LinearLayoutManager(activity)

                        // This will pass the ArrayList to our Adapter
                        val adapter = BasketAdapter(listProduits,requireContext(), removePanier = {
                            removeFromBasket(it)
                        },
                        updateTotalPrice = { price , state ->
                            updateTotalePrice(price, state)
                        }
                            )

                        // Setting the Adapter with the recyclerview
                        binding.basketRecyclerView.adapter = adapter

                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("TAG", "Error getting documents.", exception)
                }
        }

    }

    private fun updateTotalePrice(price: Long, state: String) {
        if (state == "plus"){
            totalPrice += price
        }else{
            totalPrice -= price
        }
    }


    private fun removeFromBasket (product : Product) {
        val currentUser = auth.currentUser
        val db = Firebase.firestore
        documentId = null

        db.collection("users").document(currentUser!!.email.toString()).collection("produits")
            .whereEqualTo("nom", product.nom)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    documentId = document.id
                }
            }
            .addOnFailureListener { exception ->
                Log.w("TAG2", "Error getting documents: ", exception)
            }

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({

        db.collection("users").document(currentUser!!.email.toString()).collection("produits")
            .document(documentId!!).delete()


        Toast.makeText(requireContext(),"Produit suprimé",Toast.LENGTH_LONG).show()

        }, 1000)
    }

}