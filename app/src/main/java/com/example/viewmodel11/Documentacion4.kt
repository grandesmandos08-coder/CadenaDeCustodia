package com.example.viewmodel11

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.viewmodel11.databinding.FragmentDocumentacion4Binding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class Documentacion4 : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentDocumentacion4Binding
    private lateinit var viewModel: ViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()
        viewModel= ViewModelProvider(activity).get(ViewModel::class.java)
        binding.btn4.setOnClickListener{
            saveAction()
            onDismiss()
        }
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDocumentacion4Binding.inflate(inflater,container,false)

        binding.escritoSi.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar si se documento el lugar de forma escrita")
            builder.setMessage(
                "Registro a través del cual, se establecen las generalidades del lugar (calle principal, número del domicilio, fachada, material, dimensiones y colindancias del lugar, entradas y salidas, etc.), se especifica el sitio exacto del suceso y los indicios localizados (posición y orientación), a través de elementos deductivos, completos, cronológicos y específicos."
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        binding.fotograficoSi.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar si se documentaron los indicios de forma fotográfica")
            builder.setMessage(
                "Registro en el que se capta y muestra el estado original del lugar, ofreciendo registros tangibles y corroborativos de forma objetiva, imparcial y exacta, para la validez de los indicios. "
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        binding.croquisSi.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar si se realizo croquis general y a detalle para documentar los indicios")
            builder.setMessage(
                "Es una representación gráfica, la cual proporciona una panorámica superior del lugar, se realiza a mano alzada, y contiene la orientación norte, la representación de los indicios o elementos materiales probatorios a través de simbología, y las medidas del lugar, así como de la localización de los indicios. "
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        binding.otroSi.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar si se realizo otra técnica de documentación de indicios")
            builder.setMessage(
                " Si entre las opciones no se encuentra la documentación realizada, se coloca la “X” en la opción otro y se especifica la que se elaboró, ej. representación 3D "
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        val tachar = resources.getStringArray(R.array.Tachar)

        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.dropdown_item,tachar)

        binding.autocompleteescritoSi.setAdapter(arrayAdapter)
        binding.autocompleteescritoNo.setAdapter(arrayAdapter)
        binding.autocompletefotograficoSi.setAdapter(arrayAdapter)
        binding.autocompletefotograficoNo.setAdapter(arrayAdapter)
        binding.autocompletecroquisSi.setAdapter(arrayAdapter)
        binding.autocompletecroquisNo.setAdapter(arrayAdapter)
        binding.autocompleteotroSi.setAdapter(arrayAdapter)
        binding.autocompleteotroNo.setAdapter(arrayAdapter)

        return binding.root
    }

    private fun saveAction(){

        viewModel.escritovmSi.value =binding.autocompleteescritoSi.text.toString()
        viewModel.escritovmNo.value =binding.autocompleteescritoNo.text.toString()
        viewModel.fotograficovmSi.value =binding.autocompletefotograficoSi.text.toString()
        viewModel.fotograficovmNo.value =binding.autocompletefotograficoNo.text.toString()
        viewModel.croquisvmSi.value =binding.autocompletecroquisSi.text.toString()
        viewModel.croquisvmNo.value =binding.autocompletecroquisNo.text.toString()
        viewModel.otrovmSi.value =binding.autocompleteotroSi.text.toString()
        viewModel.otrovmNo.value =binding.autocompleteotroNo.text.toString()
        viewModel.especifiquevm.value =binding.especifiqueEt.text.toString()

    }

    private fun onDismiss(){

        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("¿Haz llenado la información necesaria?")
        builder.setNegativeButton("No"){_,_->
        }
        builder.setPositiveButton("Sí"){_,_->
            binding.autocompleteescritoSi.setText("")
            binding.autocompleteescritoNo.setText("")
            binding.autocompletefotograficoSi.setText("")
            binding.autocompletefotograficoNo.setText("")
            binding.autocompletecroquisSi.setText("")
            binding.autocompletecroquisNo.setText("")
            binding.autocompleteotroSi.setText("")
            binding.autocompleteotroNo.setText("")
            binding.especifiqueEt.setText("")
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