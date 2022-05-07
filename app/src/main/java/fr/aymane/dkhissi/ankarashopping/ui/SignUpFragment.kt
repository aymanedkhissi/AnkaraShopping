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
import fr.aymane.dkhissi.ankarashopping.databinding.FragmentSignUpBinding


class SignUpFragment : Fragment() {


    private lateinit var binding: FragmentSignUpBinding

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
        binding = FragmentSignUpBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.buttonSignup.setOnClickListener {
            if (checkForm()) {
                val email: String = binding.editEmail.text.toString()
                val password: String = binding.editPassword.text.toString()
                signup(email, password)
            }
        }

    }

    private fun signup(email: String, password: String) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    findNavController().navigate(R.id.action_signUpFragment_to_feedFragment)
                    Toast.makeText(
                        activity, "Authentication Success.",
                        Toast.LENGTH_SHORT
                    ).show()
                    //val user = auth.currentUser
                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(
                        activity, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
    }

    private fun checkForm(): Boolean {

        if (binding.editEmail.text.isNullOrEmpty()) {
            binding.editEmail.error = "Email is mandatory"
            return false
        } else if (binding.editPassword.text.length < 6) {
            binding.editPassword.error = "Minimum password 6 digits"
            return false
        } else if (binding.editPassword.text.toString() != binding.editConfirmPassword.text.toString()) {
            binding.editConfirmPassword.error = "Passwords not matching "
            return false
        } else {
            return true
        }
    }


}