package fr.aymane.dkhissi.ankarashopping.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import fr.aymane.dkhissi.ankarashopping.adapters.RecyclerAdapter
import fr.aymane.dkhissi.ankarashopping.databinding.FragmentFeedBinding
import fr.aymane.dkhissi.ankarashopping.objects.Product


class FeedFragment : Fragment() {

    private lateinit var binding: FragmentFeedBinding
    private lateinit var auth: FirebaseAuth
    val listProduits = mutableListOf<Product>()



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
                            prix =  document.data.get("prix") as String,
                            imageUrl = document.data.get("image") as String,
                        )
                        listProduits.add(produit)

                        // this creates a vertical layout Manager
                        binding.myRecycler.layoutManager = LinearLayoutManager(activity)

                        // This will pass the ArrayList to our Adapter
                        val adapter = RecyclerAdapter(listProduits,requireContext())

                        // Setting the Adapter with the recyclerview
                        binding.myRecycler.adapter = adapter

                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("TAG", "Error getting documents.", exception)
                }
        }


    }


}