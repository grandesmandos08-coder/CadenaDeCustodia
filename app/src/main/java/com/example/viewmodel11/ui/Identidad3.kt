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
import com.example.viewmodel11.databinding.FragmentIdentidad3Binding
import com.example.viewmodel11.viewmodel.ViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar
import java.util.Locale


class Identidad3 : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentIdentidad3Binding
    private lateinit var viewModel: ViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()
        viewModel= ViewModelProvider(activity).get(ViewModel::class.java)
        binding.btn3.setOnClickListener{
            saveAction()
            onDismiss()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentIdentidad3Binding.inflate(inflater,container,false)

        binding.identificacion1.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar número/letra de identificación del indicio.")
            builder.setMessage(
                "Se asigna una letra, número o combinación de ambos, al indicio obtenido en el lugar de intervención mediante la inspección realizada  en un hecho probable delictivo. "
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        binding.descripcion1.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar descripción detallada del indicio.")
            builder.setMessage(
                "En este apartado se realiza una descripción objetiva del indicio en la que se enlistan los rasgos generales, es importante dejar asentadas las particularidades que diferencien al indicio de otros. "
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        binding.ubicacion1.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar donde se encontró el indicio.")
            builder.setMessage(
                " Se debe establecer donde se obtuvo el indicio: en el lugar de intervención (sobre el piso, mesa, a un costado de un poste, debajo del cadáver etc.,); en el indiciado (bolsa derecha del pantalón, en su chamarra, zapato etc.)"
            )
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        binding.horaRecoleccion1.setStartIconOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Colocar la hora de la recolección del indicio.")
            builder.setMessage(
                "Es en el momento en el que se hace la recolección (lugar de intervención) o el aseguramiento de los indicios."
            )
            builder.setPositiveButton("Colocar hora"){_,_->
                showTimePicker1()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()

        }

        binding.horaRecoleccion2.setStartIconOnClickListener{

                showTimePicker2()

        }

        binding.horaRecoleccion3.setStartIconOnClickListener{

                showTimePicker3()

        }

        binding.horaRecoleccion4.setStartIconOnClickListener{

                showTimePicker4()

        }

        binding.horaRecoleccion5.setStartIconOnClickListener{

                showTimePicker5()

        }

        val indicios = resources.getStringArray(R.array.Indicios)

        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item,indicios)

        binding.identificacionET1.setAdapter(arrayAdapter)
        binding.identificacionET2.setAdapter(arrayAdapter)
        binding.identificacionET3.setAdapter(arrayAdapter)
        binding.identificacionET4.setAdapter(arrayAdapter)
        binding.identificacionET5.setAdapter(arrayAdapter)





        return binding.root

    }

    private fun saveAction(){

        viewModel.identificacionvm1.value =binding.identificacionET1.text.toString()
        viewModel.descripcionvm1.value =binding.descripcionET1.text.toString()
        viewModel.ubicacionvm1.value =binding.ubicacionET1.text.toString()
        viewModel.recoleccionvm1.value =binding.recoleccionET1.text.toString()

        viewModel.identificacionvm2.value =binding.identificacionET2.text.toString()
        viewModel.descripcionvm2.value =binding.descripcionET2.text.toString()
        viewModel.ubicacionvm2.value =binding.ubicacionET2.text.toString()
        viewModel.recoleccionvm2.value =binding.recoleccionET2.text.toString()

        viewModel.identificacionvm3.value =binding.identificacionET3.text.toString()
        viewModel.descripcionvm3.value =binding.descripcionET3.text.toString()
        viewModel.ubicacionvm3.value =binding.ubicacionET3.text.toString()
        viewModel.recoleccionvm3.value =binding.recoleccionET3.text.toString()

        viewModel.identificacionvm4.value =binding.identificacionET4.text.toString()
        viewModel.descripcionvm4.value =binding.descripcionET4.text.toString()
        viewModel.ubicacionvm4.value =binding.ubicacionET4.text.toString()
        viewModel.recoleccionvm4.value =binding.recoleccionET4.text.toString()

        viewModel.identificacionvm5.value =binding.identificacionET5.text.toString()
        viewModel.descripcionvm5.value =binding.descripcionET5.text.toString()
        viewModel.ubicacionvm5.value =binding.ubicacionET5.text.toString()
        viewModel.recoleccionvm5.value =binding.recoleccionET5.text.toString()




    }

    private fun onDismiss(){

        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("¿Haz llenado la información necesaria?")
        builder.setNegativeButton("No"){_,_->

        }
        builder.setPositiveButton("Sí"){_,_->

            binding.identificacionET1.setText("")
            binding.descripcionET1.setText("")
            binding.ubicacionET1.setText("")
            binding.recoleccionET1.setText("")

            binding.identificacionET2.setText("")
            binding.descripcionET2.setText("")
            binding.ubicacionET2.setText("")
            binding.recoleccionET2.setText("")

            binding.identificacionET3.setText("")
            binding.descripcionET3.setText("")
            binding.ubicacionET3.setText("")
            binding.recoleccionET3.setText("")

            binding.identificacionET4.setText("")
            binding.descripcionET4.setText("")
            binding.ubicacionET4.setText("")
            binding.recoleccionET4.setText("")

            binding.identificacionET5.setText("")
            binding.descripcionET5.setText("")
            binding.ubicacionET5.setText("")
            binding.recoleccionET5.setText("")

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

    private fun showTimePicker1() {
        val calendar = Calendar.getInstance()
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(calendar.get(Calendar.HOUR_OF_DAY))
            .setMinute(calendar.get(Calendar.MINUTE))
            .setTitleText("Select Time")
            .build()

        picker.addOnPositiveButtonClickListener {
            val selectedHour = picker.hour
            val selectedMinute = picker.minute
            val selectedTime = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute)
            binding.recoleccionET1.setText(selectedTime)
        }

        picker.show(requireActivity().supportFragmentManager, "time_picker")
    }

    private fun showTimePicker2() {
        val calendar = Calendar.getInstance()
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(calendar.get(Calendar.HOUR_OF_DAY))
            .setMinute(calendar.get(Calendar.MINUTE))
            .setTitleText("Select Time")
            .build()

        picker.addOnPositiveButtonClickListener {
            val selectedHour = picker.hour
            val selectedMinute = picker.minute
            val selectedTime = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute)
            binding.recoleccionET2.setText(selectedTime)
        }

        picker.show(requireActivity().supportFragmentManager, "time_picker")
    }

    private fun showTimePicker3() {
        val calendar = Calendar.getInstance()
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(calendar.get(Calendar.HOUR_OF_DAY))
            .setMinute(calendar.get(Calendar.MINUTE))
            .setTitleText("Select Time")
            .build()

        picker.addOnPositiveButtonClickListener {
            val selectedHour = picker.hour
            val selectedMinute = picker.minute
            val selectedTime = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute)
            binding.recoleccionET3.setText(selectedTime)
        }

        picker.show(requireActivity().supportFragmentManager, "time_picker")
    }

    private fun showTimePicker4() {
        val calendar = Calendar.getInstance()
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(calendar.get(Calendar.HOUR_OF_DAY))
            .setMinute(calendar.get(Calendar.MINUTE))
            .setTitleText("Select Time")
            .build()

        picker.addOnPositiveButtonClickListener {
            val selectedHour = picker.hour
            val selectedMinute = picker.minute
            val selectedTime = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute)
            binding.recoleccionET4.setText(selectedTime)
        }

        picker.show(requireActivity().supportFragmentManager, "time_picker")
    }

    private fun showTimePicker5() {
        val calendar = Calendar.getInstance()
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(calendar.get(Calendar.HOUR_OF_DAY))
            .setMinute(calendar.get(Calendar.MINUTE))
            .setTitleText("Select Time")
            .build()

        picker.addOnPositiveButtonClickListener {
            val selectedHour = picker.hour
            val selectedMinute = picker.minute
            val selectedTime = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute)
            binding.recoleccionET5.setText(selectedTime)
        }

        picker.show(requireActivity().supportFragmentManager, "time_picker")
    }


}