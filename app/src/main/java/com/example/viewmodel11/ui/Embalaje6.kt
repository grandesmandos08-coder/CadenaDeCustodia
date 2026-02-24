package com.example.viewmodel11.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.viewmodel11.CreatePdf
import com.example.viewmodel11.R
import com.example.viewmodel11.databinding.FragmentEmbalaje6Binding
import com.example.viewmodel11.viewmodel.ViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class Embalaje6 : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentEmbalaje6Binding
    private lateinit var viewModel: ViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()
        viewModel= ViewModelProvider(activity).get(ViewModel::class.java)
        binding.btn6.setOnClickListener{
            saveAction()
            onDismiss()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentEmbalaje6Binding.inflate(inflater,container,false)

        binding.bolsa.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar los indicios que embaló en bolsas")
            builder.setMessage(
                "Se debe colocar el número de identificación del indicio que se embaló en bolsas(plástico/papel), por ejemplo: elementos balísticos "
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        binding.caja.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar los indicios que embaló en cajas")
            builder.setMessage(
                "Se debe colocar el número de identificación del indicio que se embaló en cajas, por ejemplo: armas de fuego "
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        binding.recipiente.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar los indicios que embaló en recipientes")
            builder.setMessage(
                "Se debe colocar el número de identificación del indicio que se embaló en recipientes, por ejemplo: fluidos corporales"
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        return binding.root
    }

    private fun saveAction(){

        viewModel.bolsavm.value =binding.bolsaEt.text.toString()
        viewModel.cajavm.value =binding.cajaEt.text.toString()
        viewModel.recipientevm.value =binding.recipienteEt.text.toString()



    }

    private fun onDismiss(){

        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("¿Haz llenado la información necesaria?")
        builder.setNegativeButton("No"){_,_->

        }
        builder.setPositiveButton("Sí"){_,_->
            binding.bolsaEt.setText("")
            binding.cajaEt.setText("")
            binding.recipienteEt.setText("")
            dismiss()

            val fragment = CreatePdf()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.FragmentContainer, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }


}