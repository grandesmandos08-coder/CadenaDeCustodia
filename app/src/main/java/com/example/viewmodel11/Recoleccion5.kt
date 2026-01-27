package com.example.viewmodel11

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.viewmodel11.databinding.FragmentRecoleccion5Binding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class Recoleccion5 : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentRecoleccion5Binding
    private lateinit var viewModel: ViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()
        viewModel= ViewModelProvider(activity).get(ViewModel::class.java)
        binding.btn5.setOnClickListener{
            saveAction()
            onDismiss()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecoleccion5Binding.inflate(inflater,container,false)

        binding.manual.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar los indicios que recolectó con guantes")
            builder.setMessage(
                "Se debe colocar el número de identificación del indicio que se recolectó con las manos(con guantes) "
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        binding.instrumental.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar los indicios que recolectó con instrumentos")
            builder.setMessage(
                "Se debe colocar el número de identificación del indicio que se recolectó con algún instrumento, por ejemplo: pinzas plastica. "
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        return binding.root
    }

    private fun saveAction(){

        viewModel.rmanualvm.value =binding.manualEt.text.toString()
        viewModel.rinstrumentalvm.value =binding.instrumentalEt.text.toString()


    }

    private fun onDismiss(){

        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("¿Haz llenado la información necesaria?")
        builder.setNegativeButton("No"){_,_->

        }
        builder.setPositiveButton("Sí"){_,_->
            binding.manualEt.setText("")
            binding.instrumentalEt.setText("")
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