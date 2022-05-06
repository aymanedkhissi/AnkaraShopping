package fr.aymane.dkhissi.ankarashopping.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.aymane.dkhissi.ankarashopping.R
import fr.aymane.dkhissi.ankarashopping.databinding.FragmentLogSignBinding
import fr.aymane.dkhissi.ankarashopping.databinding.FragmentSignUpBinding


class SignUpFragment : Fragment() {


    private lateinit var binding: FragmentSignUpBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater)
        return binding.root
    }

}