package fr.aymane.dkhissi.ankarashopping.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import fr.aymane.dkhissi.ankarashopping.R
import fr.aymane.dkhissi.ankarashopping.databinding.FragmentLogSignBinding
import fr.aymane.dkhissi.ankarashopping.databinding.FragmentSplashScreenBinding


class LogSignFragment : Fragment() {


    private lateinit var binding: FragmentLogSignBinding


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

        binding.buttonSignup.setOnClickListener {
            findNavController().navigate(R.id.action_logSignFragment_to_signUpFragment)
        }

    }






}