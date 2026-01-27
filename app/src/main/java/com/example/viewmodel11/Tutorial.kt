package com.example.viewmodel11

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.viewmodel11.databinding.FragmentEmbalaje6Binding
import com.example.viewmodel11.databinding.FragmentTutorialBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class Tutorial : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentTutorialBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTutorialBinding.inflate(inflater,container,false)
        //return inflater.inflate(R.layout.fragment_tutorial, container, false)

        return binding.root
    }

}