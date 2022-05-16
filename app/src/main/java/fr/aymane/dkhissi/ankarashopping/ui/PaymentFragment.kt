package fr.aymane.dkhissi.ankarashopping.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import fr.aymane.dkhissi.ankarashopping.R
import fr.aymane.dkhissi.ankarashopping.databinding.FragmentFeedBinding
import fr.aymane.dkhissi.ankarashopping.databinding.FragmentPaymentBinding


class PaymentFragment : Fragment() {


    private lateinit var binding: FragmentPaymentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args : PaymentFragmentArgs by navArgs ()


        binding.txt.text =  args.totalPrice.toString()


    }

}