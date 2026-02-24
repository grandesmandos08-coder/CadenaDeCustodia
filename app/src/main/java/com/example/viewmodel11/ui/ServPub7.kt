package com.example.viewmodel11.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.example.viewmodel11.CreatePdf
import com.example.viewmodel11.R
import com.example.viewmodel11.databinding.FragmentServPub7Binding
import com.example.viewmodel11.viewmodel.ViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ServPub7 : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentServPub7Binding
    private lateinit var viewModel: ViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()
        viewModel= ViewModelProvider(activity).get(ViewModel::class.java)
        binding.btn7.setOnClickListener{
            saveAction()
            onDismiss()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentServPub7Binding.inflate(inflater, container,false)

        binding.nombreComp1.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar Nombre Completo")
            builder.setMessage(
                "Nombre completo del personal interviniente en el lugar y que tiene contacto con los indicios "
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        binding.institucionComp1.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar Institución completa")
            builder.setMessage(
                "Institución del personal interviniente en el lugar y que tiene contacto con los indicios, por ejemplo: Comisaría de Seguridad Pública y Tránsito Municipal de Coacalco"
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        binding.cargoComp1.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar cargo completo")
            builder.setMessage(
                "Cargo del personal interviniente en el lugar y que tiene contacto con los indicios, por ejemplo: Policía "
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        binding.etapaComp1.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar la etapa(s) del procesamiento en la que participo")
            builder.setMessage(
                "SOLO COLOQUE LA ETAPA(S) EN LA QUE PARTICIPO (puede ser una o varias): Observación / Identificación / Documentación / Recolección / Embalaje / Sellado / Etiquetado / Inventario y recomendaciones para el traslado "
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        val etapas = resources.getStringArray(R.array.Etapas)

        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item,etapas)

        binding.etpEt1.setAdapter(arrayAdapter)
        binding.etpEt2.setAdapter(arrayAdapter)
        binding.etpEt3.setAdapter(arrayAdapter)
        binding.etpEt4.setAdapter(arrayAdapter)
        binding.etpEt5.setAdapter(arrayAdapter)
        binding.etpEt6.setAdapter(arrayAdapter)
        binding.etpEt7.setAdapter(arrayAdapter)

        return binding.root
    }

    private fun saveAction(){

        viewModel.servpubvm1.value =binding.servpubEt1.text.toString()
        viewModel.instvm1.value =binding.instEt1.text.toString()
        viewModel.crgvm1.value =binding.crgEt1.text.toString()
        viewModel.etpvm1.value =binding.etpEt1.text.toString()
        viewModel.servpubvm2.value =binding.servpubEt2.text.toString()
        viewModel.instvm2.value =binding.instEt2.text.toString()
        viewModel.crgvm2.value =binding.crgEt2.text.toString()
        viewModel.etpvm2.value =binding.etpEt2.text.toString()
        viewModel.servpubvm3.value =binding.servpubEt3.text.toString()
        viewModel.instvm3.value =binding.instEt3.text.toString()
        viewModel.crgvm3.value =binding.crgEt3.text.toString()
        viewModel.etpvm3.value =binding.etpEt3.text.toString()
        viewModel.servpubvm4.value =binding.servpubEt4.text.toString()
        viewModel.instvm4.value =binding.instEt4.text.toString()
        viewModel.crgvm4.value =binding.crgEt4.text.toString()
        viewModel.etpvm4.value =binding.etpEt4.text.toString()
        viewModel.servpubvm5.value =binding.servpubEt5.text.toString()
        viewModel.instvm5.value =binding.instEt5.text.toString()
        viewModel.crgvm5.value =binding.crgEt5.text.toString()
        viewModel.etpvm5.value =binding.etpEt5.text.toString()
        viewModel.servpubvm6.value =binding.servpubEt6.text.toString()
        viewModel.instvm6.value =binding.instEt6.text.toString()
        viewModel.crgvm6.value =binding.crgEt6.text.toString()
        viewModel.etpvm6.value =binding.etpEt6.text.toString()
        viewModel.servpubvm7.value =binding.servpubEt7.text.toString()
        viewModel.instvm7.value =binding.instEt7.text.toString()
        viewModel.crgvm7.value =binding.crgEt7.text.toString()
        viewModel.etpvm7.value =binding.etpEt7.text.toString()



    }

    private fun onDismiss(){

        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("¿Haz llenado la información necesaria?")
        builder.setNegativeButton("No"){_,_->

        }
        builder.setPositiveButton("Sí"){_,_->
            binding.servpubEt1.setText("")
            binding.instEt1.setText("")
            binding.crgEt1.setText("")
            binding.etpEt1.setText("")
            binding.servpubEt2.setText("")
            binding.instEt2.setText("")
            binding.crgEt2.setText("")
            binding.etpEt2.setText("")
            binding.servpubEt3.setText("")
            binding.instEt3.setText("")
            binding.crgEt3.setText("")
            binding.etpEt3.setText("")
            binding.servpubEt4.setText("")
            binding.instEt4.setText("")
            binding.crgEt4.setText("")
            binding.etpEt4.setText("")
            binding.servpubEt5.setText("")
            binding.instEt5.setText("")
            binding.crgEt5.setText("")
            binding.etpEt5.setText("")
            binding.servpubEt6.setText("")
            binding.instEt6.setText("")
            binding.crgEt6.setText("")
            binding.etpEt6.setText("")
            binding.servpubEt7.setText("")
            binding.instEt7.setText("")
            binding.crgEt7.setText("")
            binding.etpEt7.setText("")

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