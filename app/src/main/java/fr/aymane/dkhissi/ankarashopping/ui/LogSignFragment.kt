package fr.aymane.dkhissi.ankarashopping.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import fr.aymane.dkhissi.ankarashopping.R
import fr.aymane.dkhissi.ankarashopping.databinding.FragmentLogSignBinding
import fr.aymane.dkhissi.ankarashopping.databinding.FragmentSplashScreenBinding


class LogSignFragment : Fragment() {

    private lateinit var binding: FragmentLogSignBinding
    private lateinit var auth: FirebaseAuth

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
        binding = FragmentLogSignBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLogin.setOnClickListener {
            if (checkForm()) {
                val email: String = binding.editTextTextEmailAddress.text.toString()
                val password: String = binding.editTextTextPassword.text.toString()
                login(email, password)
            }
        }
        binding.buttonSignup.setOnClickListener {
            findNavController().navigate(R.id.action_logSignFragment_to_signUpFragment)

        }

    }


    private fun login (email : String , password : String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    findNavController().navigate(R.id.action_logSignFragment_to_feedFragment)
                    Toast.makeText(requireContext(), "Authentication success.",
                        Toast.LENGTH_SHORT).show()

                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(requireContext(), "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }
            }
    }


    private fun checkForm(): Boolean {

        if (binding.editTextTextEmailAddress.text.isNullOrEmpty()) {
            binding.editTextTextEmailAddress.error = "Email is mandatory"
            return false
        } else if (binding.editTextTextPassword.text.length < 6) {
            binding.editTextTextPassword.error = "Minimum password 6 digits"
            return false
        } else {
            return true
        }
    }





}