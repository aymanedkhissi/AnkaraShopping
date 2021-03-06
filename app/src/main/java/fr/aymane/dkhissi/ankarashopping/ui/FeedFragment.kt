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
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import fr.aymane.dkhissi.ankarashopping.R
import fr.aymane.dkhissi.ankarashopping.adapters.FeedAdapter
import fr.aymane.dkhissi.ankarashopping.databinding.FragmentFeedBinding
import fr.aymane.dkhissi.ankarashopping.objects.Product


class FeedFragment : Fragment() {

    private lateinit var binding: FragmentFeedBinding
    private lateinit var auth: FirebaseAuth
    val listProduits = mutableListOf<Product>()
    var documentId = false




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(requireContext())
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentUser = auth.currentUser
        val db = Firebase.firestore

        if(currentUser != null){
            db.collection("produits")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {

                        val produit = Product(
                            nom = document.data.get("nom") as String,
                            description = document.data.get("description") as String,
                            prix = document.data.get("prix") as Long,
                            imageUrl = document.data.get("image") as String,
                        )
                        listProduits.add(produit)
                        // this creates a vertical layout Manager
                        binding.myRecycler.layoutManager = LinearLayoutManager(activity)
                        // This will pass the ArrayList to our Adapter
                        val adapter = FeedAdapter(listProduits,requireContext(), addPanier = {
                            addPanier(it)
                        })
                        // Setting the Adapter with the recyclerview
                        binding.myRecycler.adapter = adapter

                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("TAG", "Error getting documents.", exception)
                }
        }

        binding.imgBasket.setOnClickListener {
             findNavController().navigate(R.id.action_feedFragment_to_myBasketFragment)
            listProduits.clear()
        }


    }

    private fun addPanier (product : Product) {
        val currentUser = auth.currentUser
        val db = Firebase.firestore
        documentId = false

        // on cherche si le produit existe d??ja sur le panier sur la BDD
        db.collection("users").document(currentUser!!.email.toString()).collection("produits")
            .whereEqualTo("nom", product.nom)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    documentId = true
                }
            }
            .addOnFailureListener { exception ->
                Log.w("TAG2", "Error getting documents: ", exception)
            }
        //----------------------------------------------

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({

            //--------Ajout?? produit au panier
            if (! documentId ) {
                db.collection("users").document(currentUser!!.email.toString()).collection("produits")
                    .add(product)

                Toast.makeText(requireContext(),"Produit ajout?? au pani??",Toast.LENGTH_LONG).show()
            //-------------------------------
            //--------Produit existe d??ja
            }else{
                Toast.makeText(requireContext(),"Produit d??ja dans le pani??",Toast.LENGTH_LONG).show()

            }
            //-----------------------------
        }, 1000)



    }

}