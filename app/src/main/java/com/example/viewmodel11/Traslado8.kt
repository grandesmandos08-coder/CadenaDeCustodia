package com.example.viewmodel11

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.example.viewmodel11.databinding.FragmentTraslado8Binding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class Traslado8 :  BottomSheetDialogFragment() {

    private lateinit var binding: FragmentTraslado8Binding
    private lateinit var viewModel: ViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()
        viewModel= ViewModelProvider(activity).get(ViewModel::class.java)
        binding.btn8.setOnClickListener{
            saveAction()
            onDismiss()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTraslado8Binding.inflate(inflater,container,false)

        binding.terrestre.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar si los indicios se trasladaron vía terrestre")
            builder.setMessage(
                "Debe colocar una X si los indicios se trasldaron vía terrestre, por ejemplo: en un vehículo  policial"
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }


        binding.aerea.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar si los indicios se trasladaron vía aérea")
            builder.setMessage(
                "Debe colocar una X si los indicios se trasldaron vía aérea, por ejemplo: en un helicoptero "
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }


        binding.maritima.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar si los indicios se trasladaron vía marítima")
            builder.setMessage(
                "Debe colocar una X si los indicios se trasldaron vía marítima, por ejemplo: en una moto acuática"
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        binding.condicionesSi.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar si se necestaron condiciones especiales para trasladar los indicios")
            builder.setMessage(
                "Se deben especificar las recomendaciones a considerar, por ejemplo: manéjese con precaución objeto frágil, liquido o sustancia altamente explosiva o toxica etc."
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        val tachar = resources.getStringArray(R.array.Tachar)
        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.dropdown_item,tachar)
        binding.autocompleteterrestre.setAdapter(arrayAdapter)
        binding.autocompleteaerea.setAdapter(arrayAdapter)
        binding.autocompletemaritima.setAdapter(arrayAdapter)
        binding.autocompletecondicionesSi.setAdapter(arrayAdapter)
        binding.autocompletecondicionesNo.setAdapter(arrayAdapter)

        return binding.root
    }

    private fun saveAction(){

        viewModel.terrestrevm.value = binding.autocompleteterrestre.text.toString()
        viewModel.aereavm.value = binding.autocompleteaerea.text.toString()
        viewModel.maritimavm.value = binding.autocompletemaritima.text.toString()
        viewModel.condSivm.value = binding.autocompletecondicionesSi.text.toString()
        viewModel.condNovm.value = binding.autocompletecondicionesNo.text.toString()
        viewModel.recomendacionvm.value = binding.recomedacionEt.text.toString()




    }

    private fun onDismiss(){

        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("¿Haz llenado la información necesaria?")
        builder.setNegativeButton("No"){_,_->

        }
        builder.setPositiveButton("Sí"){_,_->
            binding.autocompleteterrestre.setText("")
            binding.autocompleteaerea.setText("")
            binding.autocompletemaritima.setText("")
            binding.autocompletecondicionesSi.setText("")
            binding.autocompletecondicionesNo.setText("")
            binding.recomedacionEt.setText("")
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