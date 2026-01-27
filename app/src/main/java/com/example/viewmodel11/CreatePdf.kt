package com.example.viewmodel11

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.viewmodel11.R
import com.example.viewmodel11.databinding.FragmentCreatePdfBinding
import com.example.viewmodel11.databinding.FragmentGenerales1Binding
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.itextpdf.text.BaseColor
import com.itextpdf.text.Document
import com.itextpdf.text.DocumentException
import com.itextpdf.text.Element
import com.itextpdf.text.Font
import com.itextpdf.text.Image
import com.itextpdf.text.PageSize
import com.itextpdf.text.Paragraph
import com.itextpdf.text.Phrase
import com.itextpdf.text.pdf.ColumnText
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CreatePdf : BottomSheetDialogFragment() {

    private var interstitialAd: InterstitialAd? = null
    private lateinit var binding: FragmentCreatePdfBinding
    private lateinit var viewModel: ViewModel


    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isAceptado ->
        if (isAceptado) Toast.makeText(requireActivity(), "Permiso concedido", Toast.LENGTH_SHORT)
            .show()
        else Toast.makeText(requireActivity(), "Permiso denegado", Toast.LENGTH_SHORT).show()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()

        MobileAds.initialize(requireContext()) { initializationStatus ->
            Log.d("AdMob", "AdMob initialized: $initializationStatus")
        }

        loadInterstitialAd()

        initListener()



        viewModel = ViewModelProvider(activity).get(ViewModel::class.java)

        viewModel.referenciavm.observe(this) {

            binding.tvreferencia.text = String.format(it)

            /**De Aquí se envía el formato de texto en el texView del Et del Fragment**/
        }

        viewModel.institucionvm.observe(this) {

            binding.tvinstitucion.text = String.format(it)
        }

        viewModel.foliovm.observe(this) {

            binding.tvfolio.text = String.format(it)
        }

        viewModel.lugarvm.observe(this) {

            binding.tvLugInt.text = String.format(it)
        }

        viewModel.fechavm.observe(this) {

            binding.tvfecha.text = String.format(it)
        }

        viewModel.horavm.observe(this) {

            binding.tvhora.text = String.format(it)
        }

        viewModel.localizacionvm.observe(this) {

            binding.tvlocalizacion.text = String.format(it)
        }

        viewModel.descubrimientovm.observe(this) {

            binding.tvdescubrimiento.text = String.format(it)
        }

        viewModel.aportacionvm.observe(this) {

            binding.tvaportacion.text = String.format(it)
        }

        viewModel.identificacionvm1.observe(this) {

            binding.tvidentificacion1.text = String.format(it)
        }

        viewModel.descripcionvm1.observe(this) {

            binding.tvdescripcion1.text = String.format(it)
        }

        viewModel.ubicacionvm1.observe(this) {

            binding.tvubicacion1.text = String.format(it)
        }

        viewModel.recoleccionvm1.observe(this) {

            binding.tvrecoleccion1.text = String.format(it)
        }

        viewModel.identificacionvm2.observe(this) {

            binding.tvidentificacion2.text = String.format(it)
        }

        viewModel.descripcionvm2.observe(this) {

            binding.tvdescripcion2.text = String.format(it)
        }

        viewModel.ubicacionvm2.observe(this) {

            binding.tvubicacion2.text = String.format(it)
        }

        viewModel.recoleccionvm2.observe(this) {

            binding.tvrecoleccion2.text = String.format(it)
        }

        viewModel.identificacionvm3.observe(this) {

            binding.tvidentificacion3.text = String.format(it)
        }

        viewModel.descripcionvm3.observe(this) {

            binding.tvdescripcion3.text = String.format(it)
        }

        viewModel.ubicacionvm3.observe(this) {

            binding.tvubicacion3.text = String.format(it)
        }

        viewModel.recoleccionvm3.observe(this) {

            binding.tvrecoleccion3.text = String.format(it)
        }

        viewModel.identificacionvm4.observe(this) {

            binding.tvidentificacion4.text = String.format(it)
        }

        viewModel.descripcionvm4.observe(this) {

            binding.tvdescripcion4.text = String.format(it)
        }

        viewModel.ubicacionvm4.observe(this) {

            binding.tvubicacion4.text = String.format(it)
        }

        viewModel.recoleccionvm4.observe(this) {

            binding.tvrecoleccion4.text = String.format(it)
        }

        viewModel.identificacionvm5.observe(this) {

            binding.tvidentificacion5.text = String.format(it)
        }

        viewModel.descripcionvm5.observe(this) {

            binding.tvdescripcion5.text = String.format(it)
        }

        viewModel.ubicacionvm5.observe(this) {

            binding.tvubicacion5.text = String.format(it)
        }

        viewModel.recoleccionvm5.observe(this) {

            binding.tvrecoleccion5.text = String.format(it)
        }

        viewModel.escritovmSi.observe(this) {

            binding.tvescritoSi.text = String.format(it)
        }

        viewModel.escritovmNo.observe(this) {

            binding.tvescritoNo.text = String.format(it)
        }

        viewModel.fotograficovmSi.observe(this) {

            binding.tvfotograficoSi.text = String.format(it)
        }

        viewModel.fotograficovmNo.observe(this) {

            binding.tvfotograficoNo.text = String.format(it)
        }

        viewModel.croquisvmSi.observe(this) {

            binding.tvcroquisSi.text = String.format(it)
        }

        viewModel.croquisvmNo.observe(this) {

            binding.tvcroquisNo.text = String.format(it)
        }

        viewModel.otrovmSi.observe(this) {

            binding.tvOtroSi.text = String.format(it)
        }

        viewModel.otrovmNo.observe(this) {

            binding.tvOtroNo.text = String.format(it)
        }

        viewModel.especifiquevm.observe(this) {

            binding.tvespecifique.text = String.format(it)
        }

        viewModel.rmanualvm.observe(this) {

            binding.tvrmanual.text = String.format(it)
        }

        viewModel.rinstrumentalvm.observe(this) {

            binding.tvrinstrumental.text = String.format(it)
        }

        viewModel.bolsavm.observe(this) {

            binding.tvbolsa.text = String.format(it)
        }

        viewModel.cajavm.observe(this) {

            binding.tvcaja.text = String.format(it)
        }

        viewModel.recipientevm.observe(this) {

            binding.tvrecipiente.text = String.format(it)
        }

        viewModel.servpubvm1.observe(this) {

            binding.tvservpub1.text = String.format(it)
        }

        viewModel.instvm1.observe(this) {

            binding.tvinst1.text = String.format(it)
        }

        viewModel.crgvm1.observe(this) {

            binding.tvcrg1.text = String.format(it)
        }

        viewModel.etpvm1.observe(this) {

            binding.tvetp1.text = String.format(it)
        }

        viewModel.servpubvm2.observe(this) {

            binding.tvservpub2.text = String.format(it)
        }

        viewModel.instvm2.observe(this) {

            binding.tvinst2.text = String.format(it)
        }

        viewModel.crgvm2.observe(this) {

            binding.tvcrg2.text = String.format(it)
        }

        viewModel.etpvm2.observe(this) {

            binding.tvetp2.text = String.format(it)
        }
        viewModel.servpubvm3.observe(this) {

            binding.tvservpub3.text = String.format(it)
        }

        viewModel.instvm3.observe(this) {

            binding.tvinst3.text = String.format(it)
        }

        viewModel.crgvm3.observe(this) {

            binding.tvcrg3.text = String.format(it)
        }

        viewModel.etpvm3.observe(this) {

            binding.tvetp3.text = String.format(it)
        }

        viewModel.servpubvm4.observe(this) {

            binding.tvservpub4.text = String.format(it)
        }

        viewModel.instvm4.observe(this) {

            binding.tvinst4.text = String.format(it)
        }

        viewModel.crgvm4.observe(this) {

            binding.tvcrg4.text = String.format(it)
        }

        viewModel.etpvm4.observe(this) {

            binding.tvetp4.text = String.format(it)
        }

        viewModel.servpubvm5.observe(this) {

            binding.tvservpub5.text = String.format(it)
        }

        viewModel.instvm5.observe(this) {

            binding.tvinst5.text = String.format(it)
        }

        viewModel.crgvm5.observe(this) {

            binding.tvcrg5.text = String.format(it)
        }

        viewModel.etpvm5.observe(this) {

            binding.tvetp5.text = String.format(it)
        }
        viewModel.servpubvm6.observe(this) {

            binding.tvservpub6.text = String.format(it)
        }

        viewModel.instvm6.observe(this) {

            binding.tvinst6.text = String.format(it)
        }

        viewModel.crgvm6.observe(this) {

            binding.tvcrg6.text = String.format(it)
        }

        viewModel.etpvm6.observe(this) {

            binding.tvetp6.text = String.format(it)
        }

        viewModel.servpubvm7.observe(this) {

            binding.tvservpub7.text = String.format(it)
        }

        viewModel.instvm7.observe(this) {

            binding.tvinst7.text = String.format(it)
        }

        viewModel.crgvm7.observe(this) {

            binding.tvcrg7.text = String.format(it)
        }

        viewModel.etpvm7.observe(this) {

            binding.tvetp7.text = String.format(it)
        }

        viewModel.terrestrevm.observe(this) {

            binding.tvterrestre.text = String.format(it)
        }

        viewModel.aereavm.observe(this) {

            binding.tvaerea.text = String.format(it)
        }

        viewModel.maritimavm.observe(this) {

            binding.tvmaritima.text = String.format(it)
        }

        viewModel.condSivm.observe(this) {

            binding.tvConSi.text = String.format(it)
        }

        viewModel.condNovm.observe(this) {

            binding.tvconNo.text = String.format(it)
        }

        viewModel.recomendacionvm.observe(this) {

            binding.tvRecom.text = String.format(it)
        }


      /*  binding.btnCrearPdf.setOnClickListener {

            verificarPermisos(it)
        }
*/

    }

    private fun initListener() {
        binding.btnCrearPdf.setOnClickListener {
            if (interstitialAd != null) {
                interstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        // El usuario cerró el anuncio, genera el PDF
                        interstitialAd != null
                        loadInterstitialAd() // Cargar un nuevo anuncio para futuras interacciones
                        iniciarDescargaPDF()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        // Si falla al mostrar el anuncio, genera el PDF directamente
                        Log.e("InterstitialAd", "Error al mostrar anuncio: ${adError.message}")
                        iniciarDescargaPDF()
                    }
                }
                interstitialAd?.show(requireActivity())
            } else {
                // Si no hay un anuncio cargado, genera el PDF directamente
                iniciarDescargaPDF()
            }
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCreatePdfBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[ViewModel::class.java]

        return binding.root
    }

    private fun iniciarDescargaPDF() {
        Toast.makeText(
            context,
            "Se está generando tu Registro de Cadena de Custodia, por favor espera un momento...",
            Toast.LENGTH_LONG
        ).show()

        crearPDF(
                binding.tvreferencia.text.toString(),
                binding.tvinstitucion.text.toString(),
                binding.tvfolio.text.toString(),
                binding.tvLugInt.text.toString(),
                binding.tvfecha.text.toString(),
                binding.tvhora.text.toString(),
                binding.tvlocalizacion.text.toString(),
                binding.tvdescubrimiento.text.toString(),
                binding.tvaportacion.text.toString(),
                binding.tvidentificacion1.text.toString(),
                binding.tvdescripcion1.text.toString(),
                binding.tvubicacion1.text.toString(),
                binding.tvrecoleccion1.text.toString(),
                binding.tvidentificacion2.text.toString(),
                binding.tvdescripcion2.text.toString(),
                binding.tvubicacion2.text.toString(),
                binding.tvrecoleccion2.text.toString(),
                binding.tvidentificacion3.text.toString(),
                binding.tvdescripcion3.text.toString(),
                binding.tvubicacion3.text.toString(),
                binding.tvrecoleccion3.text.toString(),
                binding.tvidentificacion4.text.toString(),
                binding.tvdescripcion4.text.toString(),
                binding.tvubicacion4.text.toString(),
                binding.tvrecoleccion4.text.toString(),
                binding.tvidentificacion5.text.toString(),
                binding.tvdescripcion5.text.toString(),
                binding.tvubicacion5.text.toString(),
                binding.tvrecoleccion5.text.toString(),

                binding.tvescritoSi.text.toString(),
                binding.tvescritoNo.text.toString(),
                binding.tvfotograficoSi.text.toString(),
                binding.tvfotograficoNo.text.toString(),
                binding.tvcroquisSi.text.toString(),
                binding.tvcroquisNo.text.toString(),
                binding.tvOtroSi.text.toString(),
                binding.tvOtroNo.text.toString(),
                binding.tvespecifique.text.toString(),
                binding.tvrmanual.text.toString(),
                binding.tvrinstrumental.text.toString(),

                binding.tvbolsa.text.toString(),
                binding.tvcaja.text.toString(),
                binding.tvrecipiente.text.toString(),

                binding.tvservpub1.text.toString(),
                binding.tvinst1.text.toString(),
                binding.tvcrg1.text.toString(),
                binding.tvetp1.text.toString(),
                binding.tvservpub2.text.toString(),
                binding.tvinst2.text.toString(),
                binding.tvcrg2.text.toString(),
                binding.tvetp2.text.toString(),
                binding.tvservpub3.text.toString(),
                binding.tvinst3.text.toString(),
                binding.tvcrg3.text.toString(),
                binding.tvetp3.text.toString(),
                binding.tvservpub4.text.toString(),
                binding.tvinst4.text.toString(),
                binding.tvcrg4.text.toString(),
                binding.tvetp4.text.toString(),
                binding.tvservpub5.text.toString(),
                binding.tvinst5.text.toString(),
                binding.tvcrg5.text.toString(),
                binding.tvetp5.text.toString(),
                binding.tvservpub6.text.toString(),
                binding.tvinst6.text.toString(),
                binding.tvcrg6.text.toString(),
                binding.tvetp6.text.toString(),
                binding.tvservpub7.text.toString(),
                binding.tvinst7.text.toString(),
                binding.tvcrg7.text.toString(),
                binding.tvetp7.text.toString(),

                binding.tvterrestre.text.toString(),
                binding.tvaerea.text.toString(),
                binding.tvmaritima.text.toString(),
                binding.tvConSi.text.toString(),
                binding.tvconNo.text.toString(),
                binding.tvRecom.text.toString()
            )
        }



    fun crearPDF(
        tvreferencia: String?,
        tvinstitucion: String?,
        tvfolio: String?,
        tvLugInt: String?,
        tvfecha: String?,
        tvhora: String?,
        tvlocalizacion: String?,
        tvdescubrimiento: String?,
        tvaportacion: String?,
        tvidentificacion1: String?,
        tvdescripcion1: String?,
        tvubicacion1: String?,
        tvrecoleccion1: String?,
        tvidentificacion2: String?,
        tvdescripcion2: String?,
        tvubicacion2: String?,
        tvrecoleccion2: String?,
        tvidentificacion3: String?,
        tvdescripcion3: String?,
        tvubicacion3: String?,
        tvrecoleccion3: String?,
        tvidentificacion4: String?,
        tvdescripcion4: String?,
        tvubicacion4: String?,
        tvrecoleccion4: String?,
        tvidentificacion5: String?,
        tvdescripcion5: String?,
        tvubicacion5: String?,
        tvrecoleccion5: String?,

        tvescritoSi: String?,
        tvescritoNo: String?,
        tvfotograficoSi: String?,
        tvfotograficoNo: String?,
        tvcroquisSi: String?,
        tvcroquisNo: String?,
        tvOtroSi: String?,
        tvOtroNo: String?,
        tvespecifique: String?,
        tvrmanual: String?,
        tvrinstrumental: String?,

        tvbolsa: String?,
        tvcaja: String?,
        tvrecipiente: String?,
        tvservpub1: String?,
        tvinst1: String?,
        tvcrg1: String?,
        tvetp1: String?,
        tvservpub2: String?,
        tvinst2: String?,
        tvcrg2: String?,
        tvetp2: String?,
        tvservpub3: String?,
        tvinst3: String?,
        tvcrg3: String?,
        tvetp3: String?,
        tvservpub4: String?,
        tvinst4: String?,
        tvcrg4: String?,
        tvetp4: String?,
        tvservpub5: String?,
        tvinst5: String?,
        tvcrg5: String?,
        tvetp5: String?,
        tvservpub6: String?,
        tvinst6: String?,
        tvcrg6: String?,
        tvetp6: String?,
        tvservpub7: String?,
        tvinst7: String?,
        tvcrg7: String?,
        tvetp7: String?,

        tvterrestre: String?,
        tvaerea: String?,
        tvmaritima: String?,
        tvConSi: String?,
        tvconNo: String?,
        tvRecom: String?)
    {


        val fileName = "Registro de Cadena.pdf"
        val subDirName = "Registros de Cadena de Custodia"
        val pdfUri: Uri?

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Para API 29+ (Android 10+)
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                put(MediaStore.MediaColumns.RELATIVE_PATH, "Download/$subDirName")
                put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
            }
            pdfUri = requireContext().contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
        } else {
            // Para API < 29 (Android 9 y anteriores)
            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val subDir = File(downloadsDir, subDirName)
            if (!subDir.exists()) subDir.mkdirs()

            val archivo = File(subDir, fileName)
            pdfUri = FileProvider.getUriForFile(
                requireContext(),
                "${requireContext().packageName}.fileProvider",
                archivo
            )
        }

        if (pdfUri != null) {
            try {
                val outputStream = requireContext().contentResolver.openOutputStream(pdfUri)
                if (outputStream != null) {
                    val document = com.itextpdf.text.Document()
                    val writer = com.itextpdf.text.pdf.PdfWriter.getInstance(document, outputStream)

                    document.pageSize = com.itextpdf.text.PageSize.A4
                    document.setMargins(45f, 70f, 22f, 40f)

                    document.open()
                    document.newPage()



            val table = PdfPTable(1)
            table.widthPercentage = 100F
            //table.setWidths(new float[]{2,7,2});
            table.defaultCell.border = PdfPCell.NO_BORDER
            table.defaultCell.verticalAlignment = Element.ALIGN_RIGHT
            table.defaultCell.horizontalAlignment = Element.ALIGN_TOP
            var cell: PdfPCell
            run {


                val res: Resources = resources
                @SuppressLint("UseCompatLoadingForDrawables") val d: Drawable =
                    res.getDrawable(R.drawable.cadena)
                //Drawable d= ContextCompat.getDrawable(mContext, R.mipmap.logo);
                val bmp: Bitmap = (d as BitmapDrawable).bitmap
                val stream: ByteArrayOutputStream = ByteArrayOutputStream()
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val logo: Image = Image.getInstance(stream.toByteArray())
                logo.widthPercentage = 90F
                logo.scaleToFit(140F, 60F)
                cell = PdfPCell(logo)
                cell.horizontalAlignment = Element.ALIGN_RIGHT
                cell.verticalAlignment = Element.ALIGN_TOP
                cell.isUseAscender = true
                cell.border = PdfPCell.NO_BORDER
                cell.setPadding(1f)
                table.addCell(cell)
                document.add(table)
            }

            addEmptyLine(document, 2)

            val NumeroReferencia = PdfPTable(2)
            NumeroReferencia.horizontalAlignment = Element.ALIGN_RIGHT
            NumeroReferencia.widthPercentage = 100F
            NumeroReferencia.setWidths(floatArrayOf(4f, 2.3f))

            run {
                val res: Resources = resources
                @SuppressLint("UseCompatLoadingForDrawables") val d: Drawable =
                    res.getDrawable(R.drawable.rcc)
                val bmp: Bitmap = (d as BitmapDrawable).bitmap
                val stream: ByteArrayOutputStream = ByteArrayOutputStream()
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val logo: Image = Image.getInstance(stream.toByteArray())
                logo.widthPercentage = 100F
                logo.scaleToFit(200F, 100F)
                val cell1: PdfPCell = PdfPCell(logo)
                cell1.rowspan = 2
                cell1.horizontalAlignment = Element.ALIGN_CENTER
                cell1.verticalAlignment = Element.ALIGN_MIDDLE
                cell1.isUseAscender = true
                cell1.border = PdfPCell.NO_BORDER
                NumeroReferencia.addCell(cell1)
                val cell2: PdfPCell = PdfPCell()
                cell2.horizontalAlignment = Element.ALIGN_RIGHT
                cell2.verticalAlignment = Element.ALIGN_TOP
                cell2.isUseAscender = true
                cell2.rowspan = 1
                cell2.backgroundColor = Verde
                cell2.borderWidth = 0.7f
                cell2.setPadding(1f)
                val temp: Paragraph = Paragraph(
                    "No. de referencia",
                    Font(Font.FontFamily.HELVETICA, 10.0f, Font.BOLD, BaseColor.WHITE)
                )
                temp.alignment = Element.ALIGN_CENTER
                cell2.addElement(temp)
                NumeroReferencia.addCell(cell2)

                val cell3: PdfPCell = PdfPCell()
                cell3.horizontalAlignment = Element.ALIGN_RIGHT
                cell3.verticalAlignment = Element.ALIGN_TOP
                cell3.isUseAscender = true
                cell3.backgroundColor = BaseColor.WHITE
                cell3.borderWidth = 0.7f
                cell3.rowspan = 1
                cell3.setPadding(3f)
                val NoRef: Paragraph =
                    Paragraph(
                        "",
                        Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK)
                    )
                NoRef.add(tvreferencia)
                NoRef.spacingAfter = 1.0f
                NoRef.alignment = Element.ALIGN_CENTER
                cell3.addElement(NoRef)
                NumeroReferencia.addCell(cell3)
                document.add(NumeroReferencia)
            }

            addEmptyLine(document, 2)

            val header = PdfPTable(4)
            //headerRow.setKeepTogether(true);
            header.widthPercentage = 100F
            header.setWidths(intArrayOf(2, 2, 4, 3))
            header.addCell(createCell("Institución o unidad  administrativa", 1, 1))
            header.addCell(createCell("Folio o \n llamado", 1, 1))
            header.addCell(createCell("Lugar de intervención", 1, 1))
            header.addCell(createCell("Fecha y hora de \n intervención", 1, 1))

            val firstRow = PdfPTable(4)
            firstRow.widthPercentage = 100F
            firstRow.setWidths(intArrayOf(2, 2, 4, 3))
            val cell6 = PdfPCell()
            cell6.rowspan = 2
            //Aquí va la institución
            val inst: Paragraph =
                Paragraph(
                    "",
                    Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK)
                )
            inst.add(tvinstitucion)
            inst.spacingAfter = 1.0f
            inst.alignment = Element.ALIGN_CENTER
            cell6.addElement(inst)
            firstRow.addCell(cell6)
            val cell7 = PdfPCell()
            cell7.rowspan = 2

            val fol: Paragraph =
                Paragraph(
                    "",
                    Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK)
                )
            fol.add(tvfolio)
            fol.spacingAfter = 1.0f
            fol.alignment = Element.ALIGN_CENTER
            cell7.addElement(fol)
            firstRow.addCell(cell7)
            val cell8 = PdfPCell()
            cell8.rowspan = 4
            cell8.isUseAscender = true
            cell8.setPadding(3f)


            val lugInt =
                Paragraph(
                    "",
                    Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK)
                )
            lugInt.add(tvLugInt)
            lugInt.spacingAfter = 1.0f
            lugInt.alignment = Element.ALIGN_JUSTIFIED_ALL
            cell8.addElement(lugInt)
            firstRow.addCell(cell8)
            val cell11 = PdfPCell()
            cell11.rowspan = 1

            val fecha =
                Paragraph(
                    "",
                    Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK)
                )
            fecha.add(tvfecha)
            fecha.spacingAfter = 1.0f
            fecha.alignment = Element.ALIGN_CENTER
            cell11.addElement(fecha)
            firstRow.addCell(cell11)
            val cell12 = PdfPCell()
            cell12.rowspan = 1

            val hora =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            hora.add(tvhora)
            hora.spacingAfter = 1.0f
            hora.alignment = Element.ALIGN_CENTER
            cell12.addElement(hora)
            firstRow.addCell(cell12)

            document.add(header)
            document.add(firstRow)

            addMediaEmptyLine(document, 1)

            val instrucciones = PdfPTable(2)
            instrucciones.horizontalAlignment = Element.ALIGN_LEFT
            instrucciones.widthPercentage = 100F
            instrucciones.setWidths(intArrayOf(3, 6))
            val cell2 = PdfPCell()
            cell2.horizontalAlignment = Element.ALIGN_LEFT
            cell2.verticalAlignment = Element.ALIGN_MIDDLE
            cell2.isUseAscender = true
            cell2.border = PdfPCell.NO_BORDER
            cell2.setPadding(3f)
            val temp = Paragraph(
                "Inicio de la cadena de custodia. ",
                Font(Font.FontFamily.HELVETICA, 10.0f, Font.BOLD, BaseColor.BLACK)
            )
            temp.alignment = Element.ALIGN_RIGHT
            cell2.addElement(temp)
            instrucciones.addCell(cell2)
            val cell3 = PdfPCell()
            cell3.horizontalAlignment = Element.ALIGN_LEFT
            cell3.verticalAlignment = Element.ALIGN_MIDDLE
            cell3.isUseAscender = true
            cell3.border = PdfPCell.NO_BORDER
            cell3.setPadding(3f)
            val temp1 = Paragraph(
                "(Marque con “X” el motivo por el cual comienza el registro). ",
                Font(Font.FontFamily.HELVETICA, 8.0f, Font.NORMAL, BaseColor.BLACK)
            )
            temp1.alignment = Element.ALIGN_LEFT
            cell3.addElement(temp1)
            instrucciones.addCell(cell3)
            document.add(instrucciones)

            addMediaEmptyLine(document, 1)

            val inicio = PdfPTable(6)
            inicio.widthPercentage = 100F
            inicio.setWidths(floatArrayOf(1.5f, 1f, 1.5f, 1f, 1.5f, 1f))
            val cell15 = PdfPCell(Phrase("Localización", FONT_CELL))
            cell15.isUseAscender = true
            cell15.backgroundColor = Verde
            cell15.setPadding(6f)
            cell15.horizontalAlignment = Element.ALIGN_CENTER
            cell15.verticalAlignment = Element.ALIGN_MIDDLE
            inicio.addCell(cell15)

            val cell14 = PdfPCell()
            val loc =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            loc.add(tvlocalizacion)
            loc.spacingAfter = 2.0f
            loc.alignment = Element.ALIGN_CENTER
            cell14.addElement(loc)
            cell14.setPadding(6f)
            inicio.addCell(cell14)

            val cell19 = PdfPCell(Phrase("Descubrimiento", FONT_CELL))
            cell19.isUseAscender = true
            cell19.backgroundColor = Verde
            cell19.setPadding(6f)
            cell19.horizontalAlignment = Element.ALIGN_CENTER
            cell19.verticalAlignment = Element.ALIGN_MIDDLE
            inicio.addCell(cell19)

            val cell18 = PdfPCell()
            val desc=
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            desc.add(tvdescubrimiento)
            desc.spacingAfter = 2.0f
            desc.alignment = Element.ALIGN_CENTER
            cell18.addElement(desc)
            cell18.setPadding(6f)
            inicio.addCell(cell18)

            val cell20 = PdfPCell(Phrase("Aportación", FONT_CELL))
            cell20.isUseAscender = true
            cell20.backgroundColor = Verde
            cell20.setPadding(6f)
            cell20.horizontalAlignment = Element.ALIGN_CENTER
            cell20.verticalAlignment = Element.ALIGN_MIDDLE
            inicio.addCell(cell20)

            val cell16 = PdfPCell()
            val apor =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            apor.add(tvaportacion)
            apor.spacingAfter = 2.0f
            apor.alignment = Element.ALIGN_CENTER
            cell16.addElement(apor)
            cell16.setPadding(6f)
            inicio.addCell(cell16)

            document.add(inicio)

            addMediaEmptyLine(document, 1)

            val instrucciones2 = PdfPTable(2)
            instrucciones2.horizontalAlignment = Element.ALIGN_LEFT
            instrucciones2.widthPercentage = 100F
            instrucciones2.setWidths(floatArrayOf(1.6f, 7f))
            val cell21 = PdfPCell(
                Phrase(
                    "1.    Identidad.",
                    Font(Font.FontFamily.HELVETICA, 10.0f, Font.BOLD, BaseColor.BLACK)
                )
            )
            cell21.isUseAscender = true
            cell21.border = PdfPCell.NO_BORDER
            cell21.setPadding(3f)
            cell21.horizontalAlignment = Element.ALIGN_RIGHT
            cell21.verticalAlignment = Element.ALIGN_MIDDLE
            instrucciones2.addCell(cell21)
            val cell22 = PdfPCell(
                Phrase(
                    "(Número, letra o combinación alfanumérica asignada al indicio o elemento material probatorio, descripción general,\n",
                    Font(Font.FontFamily.HELVETICA, 7.5f, Font.NORMAL, BaseColor.BLACK)
                )
            )
            cell22.isUseAscender = true
            cell22.border = PdfPCell.NO_BORDER
            cell22.setPadding(3f)
            cell22.horizontalAlignment = Element.ALIGN_JUSTIFIED_ALL
            cell22.verticalAlignment = Element.ALIGN_MIDDLE
            instrucciones2.addCell(cell22)
            val cell17 = PdfPCell()
            val temp2 = Paragraph(
                "incluyendo en su caso el estado o condición original en el momento de su recolección, ubicación en el lugar de intervención y hora de \n" +
                        "recolección. Relacione la identificación por secuencias cuando se trate de indicios o elementos materiales probatorios del mismo tipo \n" +
                        "o clase; en caso contrario, registre individualmente. Cancele los espacios sobrantes)",
                Font(Font.FontFamily.HELVETICA, 7.4f, Font.NORMAL, BaseColor.BLACK)
            )
            temp2.alignment = Element.ALIGN_LEFT
            temp2.indentationLeft = 35.4f
            cell17.addElement(temp2)
            cell17.border = PdfPCell.NO_BORDER
            cell17.isUseAscender = true
            cell17.colspan = 2
            //cell17.setPadding(2f);
            cell17.horizontalAlignment = Element.ALIGN_TOP
            cell17.verticalAlignment = Element.ALIGN_TOP
            instrucciones2.addCell(cell17)
            document.add(instrucciones2)

            addMediaEmptyLine(document, 1)

            val header2 = PdfPTable(4)
            //headerRow.setKeepTogether(true);
            header2.widthPercentage = 100F
            header2.setWidths(floatArrayOf(2f, 4f, 2f, 1.5f))
            header2.addCell(createCell("Identificación", 1, 1))
            header2.addCell(createCell("Descripción", 1, 1))
            header2.addCell(createCell("Ubicación \n" + "en el lugar", 1, 1))
            header2.addCell(createCell("Hora de\n" + "recolección", 1, 1))

            document.add(header2)

            val row1 = PdfPTable(4)
            run {

                row1.widthPercentage = 100F
                row1.setWidths(floatArrayOf(2f, 4f, 2f, 1.5f))
                val cell23: PdfPCell = PdfPCell()
                val ident1 =
                    Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
                ident1.add(tvidentificacion1)
                ident1.spacingAfter = 2.0f
                ident1.alignment = Element.ALIGN_CENTER
                cell23.addElement(ident1)
                cell23.setPadding(6f)
                row1.addCell(cell23)

                val cell24: PdfPCell = PdfPCell()
                val desc1 =
                    Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
                desc1.add(tvdescripcion1)
                desc1.spacingAfter = 2.0f
                desc1.alignment = Element.ALIGN_CENTER
                cell24.addElement(desc1)
                row1.addCell(cell24)

                val cell25: PdfPCell = PdfPCell()
                val ubi1 =
                    Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
                ubi1.add(tvubicacion1)
                ubi1.spacingAfter = 2.0f
                ubi1.alignment = Element.ALIGN_CENTER
                cell25.addElement(ubi1)
                row1.addCell(cell25)

                val cell26: PdfPCell = PdfPCell()
                val hora1 =
                    Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
                hora1.add(tvrecoleccion1)
                hora1.spacingAfter = 2.0f
                hora1.alignment = Element.ALIGN_CENTER
                cell26.addElement(hora1)
                cell26.setPadding(6f)
                row1.addCell(cell26)

                document.add(row1)
            }
            val row2 = PdfPTable(4)
            run {
                row2.widthPercentage = 100F
                row2.setWidths(floatArrayOf(2f, 4f, 2f, 1.5f))

                val cell23: PdfPCell = PdfPCell()
                val ident2 =
                    Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
                ident2.add(tvidentificacion2)
                ident2.spacingAfter = 2.0f
                ident2.alignment = Element.ALIGN_CENTER
                cell23.addElement(ident2)
                cell23.setPadding(6f)
                row2.addCell(cell23)

                val cell24: PdfPCell = PdfPCell()
                val desc2 =
                    Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
                desc2.add(tvdescripcion2)
                desc2.spacingAfter = 2.0f
                desc2.alignment = Element.ALIGN_CENTER
                cell24.addElement(desc2)
                row2.addCell(cell24)

                val cell25: PdfPCell = PdfPCell()
                val ubi2 =
                    Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
                ubi2.add(tvubicacion2)
                ubi2.spacingAfter = 2.0f
                ubi2.alignment = Element.ALIGN_CENTER
                cell25.addElement(ubi2)
                row2.addCell(cell25)

                val cell26: PdfPCell = PdfPCell()
                val hora2 =
                    Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
                hora2.add(tvrecoleccion2)
                hora2.spacingAfter = 2.0f
                hora2.alignment = Element.ALIGN_CENTER
                cell26.addElement(hora2)
                cell26.setPadding(6f)
                row2.addCell(cell26)

                document.add(row2)
            }
            val row3 = PdfPTable(4)
            run {
                row3.widthPercentage = 100F
                row3.setWidths(floatArrayOf(2f, 4f, 2f, 1.5f))

                val cell23: PdfPCell = PdfPCell()
                val ident3 =
                    Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
                ident3.add(tvidentificacion3)
                ident3.spacingAfter = 2.0f
                ident3.alignment = Element.ALIGN_CENTER
                cell23.addElement(ident3)
                cell23.setPadding(6f)
                row3.addCell(cell23)

                val cell24: PdfPCell = PdfPCell()
                val desc3 =
                    Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
                desc3.add(tvdescripcion3)
                desc3.spacingAfter = 2.0f
                desc3.alignment = Element.ALIGN_CENTER
                cell24.addElement(desc3)
                row3.addCell(cell24)

                val cell25: PdfPCell = PdfPCell()
                val ubi3 =
                    Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
                ubi3.add(tvubicacion3)
                ubi3.spacingAfter = 2.0f
                ubi3.alignment = Element.ALIGN_CENTER
                cell25.addElement(ubi3)
                row3.addCell(cell25)

                val cell26: PdfPCell = PdfPCell()
                val hora3 =
                    Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
                hora3.add(tvrecoleccion3)
                hora3.spacingAfter = 2.0f
                hora3.alignment = Element.ALIGN_CENTER
                cell26.addElement(hora3)
                cell26.setPadding(6f)
                row3.addCell(cell26)

                document.add(row3)
            }
            val row4 = PdfPTable(4)
            run {
                row4.widthPercentage = 100F
                row4.setWidths(floatArrayOf(2f, 4f, 2f, 1.5f))

                val cell23: PdfPCell = PdfPCell()
                val ident4 =
                    Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
                ident4.add(tvidentificacion4)
                ident4.spacingAfter = 2.0f
                ident4.alignment = Element.ALIGN_CENTER
                cell23.addElement(ident4)
                cell23.setPadding(6f)
                row4.addCell(cell23)

                val cell24: PdfPCell = PdfPCell()
                val desc4 =
                    Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
                desc4.add(tvdescripcion4)
                desc4.spacingAfter = 2.0f
                desc4.alignment = Element.ALIGN_CENTER
                cell24.addElement(desc4)
                row4.addCell(cell24)

                val cell25: PdfPCell = PdfPCell()
                val ubi4 =
                    Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
                ubi4.add(tvubicacion4)
                ubi4.spacingAfter = 2.0f
                ubi4.alignment = Element.ALIGN_CENTER
                cell25.addElement(ubi4)
                row4.addCell(cell25)

                val cell26: PdfPCell = PdfPCell()
                val hora4 =
                    Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
                hora4.add(tvrecoleccion4)
                hora4.spacingAfter = 2.0f
                hora4.alignment = Element.ALIGN_CENTER
                cell26.addElement(hora4)
                cell26.setPadding(6f)
                row4.addCell(cell26)

                document.add(row4)
            }
            val row5 = PdfPTable(4)
            run {
                row5.widthPercentage = 100F
                row5.setWidths(floatArrayOf(2f, 4f, 2f, 1.5f))

                val cell23: PdfPCell = PdfPCell()
                val ident5 =
                    Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
                ident5.add(tvidentificacion5)
                ident5.spacingAfter = 2.0f
                ident5.alignment = Element.ALIGN_CENTER
                cell23.addElement(ident5)
                cell23.setPadding(6f)
                row5.addCell(cell23)

                val cell24: PdfPCell = PdfPCell()
                val desc5 =
                    Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
                desc5.add(tvdescripcion5)
                desc5.spacingAfter = 2.0f
                desc5.alignment = Element.ALIGN_CENTER
                cell24.addElement(desc5)
                row5.addCell(cell24)

                val cell25: PdfPCell = PdfPCell()
                val ubi5 =
                    Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
                ubi5.add(tvubicacion5)
                ubi5.spacingAfter = 2.0f
                ubi5.alignment = Element.ALIGN_CENTER
                cell25.addElement(ubi5)
                row5.addCell(cell25)

                val cell26: PdfPCell = PdfPCell()
                val hora5 =
                    Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
                hora5.add(tvrecoleccion5)
                hora5.spacingAfter = 2.0f
                hora5.alignment = Element.ALIGN_CENTER
                cell26.addElement(hora5)
                cell26.setPadding(6f)
                row5.addCell(cell26)
                document.add(row5)
            }

            addMediaEmptyLine(document, 1)

            val instrucciones3 = PdfPTable(2)
            run {
                instrucciones3.horizontalAlignment = Element.ALIGN_LEFT
                instrucciones3.widthPercentage = 100F
                instrucciones3.setWidths(floatArrayOf(2.0f, 7f))
                val cell28: PdfPCell = PdfPCell(
                    Phrase(
                        "2. Documentación.",
                        Font(Font.FontFamily.HELVETICA, 10.0f, Font.BOLD, BaseColor.BLACK)
                    )
                )
                cell28.isUseAscender = true
                cell28.border = PdfPCell.NO_BORDER
                //cell28.setPadding(3f);
                cell28.horizontalAlignment = Element.ALIGN_RIGHT
                cell28.verticalAlignment = Element.ALIGN_BASELINE
                instrucciones3.addCell(cell28)
                val cell29: PdfPCell = PdfPCell(
                    Phrase(
                        "(Marque con “X” los métodos empleados o especifique cualquier otro en caso necesario).",
                        Font(Font.FontFamily.HELVETICA, 7.8f, Font.NORMAL, BaseColor.BLACK)
                    )
                )
                cell29.isUseAscender = true
                cell29.border = PdfPCell.NO_BORDER
                //cell29.setPadding(3f);
                cell29.horizontalAlignment = Element.ALIGN_LEFT
                cell29.verticalAlignment = Element.ALIGN_MIDDLE
                instrucciones3.addCell(cell29)
                document.add(instrucciones3)
            }

            addMediaEmptyLine(document, 1)

            val outerTable = PdfPTable(1)
            outerTable.widthPercentage = 100F
            outerTable.horizontalAlignment = Element.ALIGN_LEFT
            val innerTable = PdfPTable(12)
            innerTable.widthPercentage = 100F
            innerTable.spacingAfter = 4.0f
            innerTable.setWidths(floatArrayOf(1.5f, 1f, 1f, 1f, 2.3f, 1f, 1f, 1f, 1.5f, 1f, 1f, 1f))
            val cell1 = PdfPCell(
                Phrase(
                    "Escrito:   Sí",
                    Font(Font.FontFamily.HELVETICA, 8.0f, Font.NORMAL, BaseColor.BLACK)
                )
            )
            cell1.horizontalAlignment = Element.ALIGN_CENTER
            cell1.verticalAlignment = Element.ALIGN_MIDDLE
            cell1.border = PdfPCell.NO_BORDER
            cell1.isUseAscender = false
            innerTable.addCell(cell1)

            val cell30 =
                PdfPCell()
            val escSi =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            escSi.add(tvescritoSi)
            escSi.spacingAfter = 2.0f
            escSi.alignment = Element.ALIGN_CENTER
            cell30.addElement(escSi)
            cell30.horizontalAlignment = Element.ALIGN_CENTER
            cell30.verticalAlignment = Element.ALIGN_MIDDLE
            cell30.borderWidth = 1F
            innerTable.addCell(cell30)

            val cell31 =
                PdfPCell(Phrase("No", Font(Font.FontFamily.HELVETICA, 8.0f, Font.NORMAL, BaseColor.BLACK)))
            cell31.horizontalAlignment = Element.ALIGN_RIGHT
            cell31.verticalAlignment = Element.ALIGN_MIDDLE
            cell31.border = PdfPCell.NO_BORDER
            innerTable.addCell(cell31)

            val cell4 =
                PdfPCell()
            val escNo =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            escNo.add(tvescritoNo)
            escNo.spacingAfter = 2.0f
            escNo.alignment = Element.ALIGN_CENTER
            cell4.addElement(escNo)
            cell4.horizontalAlignment = Element.ALIGN_LEFT
            cell4.verticalAlignment = Element.ALIGN_LEFT
            cell4.borderWidth = 1F
            innerTable.addCell(cell4)

            val cell5 = PdfPCell(
                Phrase(
                    "Fotográfico:  Sí",
                    Font(Font.FontFamily.HELVETICA, 8.0f, Font.NORMAL, BaseColor.BLACK)
                )
            )
            cell5.horizontalAlignment = Element.ALIGN_CENTER
            cell5.verticalAlignment = Element.ALIGN_MIDDLE
            cell5.border = PdfPCell.NO_BORDER
            innerTable.addCell(cell5)

            val cell32 =
                PdfPCell()
            val fotSi =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            fotSi.add(tvfotograficoSi)
            fotSi.spacingAfter = 2.0f
            fotSi.alignment = Element.ALIGN_CENTER
            cell32.addElement(fotSi)
            cell32.horizontalAlignment = Element.ALIGN_LEFT
            cell32.verticalAlignment = Element.ALIGN_LEFT
            cell32.borderWidth = 1F
            innerTable.addCell(cell32)

            val cell33 =
                PdfPCell(Phrase("No ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.NORMAL, BaseColor.BLACK)))
            cell33.horizontalAlignment = Element.ALIGN_RIGHT
            cell33.verticalAlignment = Element.ALIGN_MIDDLE
            cell33.border = PdfPCell.NO_BORDER
            innerTable.addCell(cell33)

            val cell34 =
                PdfPCell()
            val fotNo =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            fotNo.add(tvfotograficoNo)
            fotNo.spacingAfter = 2.0f
            fotNo.alignment = Element.ALIGN_CENTER
            cell34.addElement(fotNo)
            cell34.horizontalAlignment = Element.ALIGN_LEFT
            cell34.verticalAlignment = Element.ALIGN_LEFT
            cell34.borderWidth = 1F
            innerTable.addCell(cell34)

            val cell9 = PdfPCell(
                Phrase(
                    "Croquis:  Sí",
                    Font(Font.FontFamily.HELVETICA, 8.0f, Font.NORMAL, BaseColor.BLACK)
                )
            )
            cell9.horizontalAlignment = Element.ALIGN_CENTER
            cell9.verticalAlignment = Element.ALIGN_MIDDLE
            cell9.border = PdfPCell.NO_BORDER
            innerTable.addCell(cell9)

            val cell10 =
                PdfPCell()
            val croqSi =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            croqSi.add(tvcroquisSi)
            croqSi.spacingAfter = 2.0f
            croqSi.alignment = Element.ALIGN_CENTER
            cell10.addElement(croqSi)
            cell10.horizontalAlignment = Element.ALIGN_LEFT
            cell10.verticalAlignment = Element.ALIGN_LEFT
            cell10.borderWidth = 1F
            innerTable.addCell(cell10)

            val cell35 =
                PdfPCell(Phrase("No ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.NORMAL, BaseColor.BLACK)))
            cell35.horizontalAlignment = Element.ALIGN_RIGHT
            cell35.verticalAlignment = Element.ALIGN_MIDDLE
            cell35.border = PdfPCell.NO_BORDER
            innerTable.addCell(cell35)

            val cell36 =
                PdfPCell()
            val croqNo =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            croqNo.add(tvcroquisNo)
            croqNo.spacingAfter = 2.0f
            croqNo.alignment = Element.ALIGN_CENTER
            cell36.addElement(croqNo)
            cell36.horizontalAlignment = Element.ALIGN_LEFT
            cell36.verticalAlignment = Element.ALIGN_LEFT
            cell36.borderWidth = 1F
            innerTable.addCell(cell36)

            val secondRow = PdfPTable(4)
            secondRow.widthPercentage = 31.5f
            secondRow.spacingAfter = 4.0f
            secondRow.horizontalAlignment = Element.ALIGN_LEFT
            secondRow.setWidths(floatArrayOf(1.5f, 1f, 1f, 1f))
            val cell13 = PdfPCell(
                Phrase(
                    "Otro:       Sí",
                    Font(Font.FontFamily.HELVETICA, 8.0f, Font.NORMAL, BaseColor.BLACK)
                )
            )
            cell13.horizontalAlignment = Element.ALIGN_CENTER
            cell13.verticalAlignment = Element.ALIGN_MIDDLE
            cell13.border = PdfPCell.NO_BORDER
            secondRow.addCell(cell13)

            val cell37 =
                PdfPCell()
            val otrSi =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            otrSi.add(tvOtroSi)
            otrSi.spacingAfter = 2.0f
            otrSi.alignment = Element.ALIGN_CENTER
            cell37.addElement(otrSi)
            cell37.horizontalAlignment = Element.ALIGN_LEFT
            cell37.verticalAlignment = Element.ALIGN_LEFT
            cell37.borderWidth = 1F
            secondRow.addCell(cell37)

            val cell38 =
                PdfPCell(Phrase("No ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.NORMAL, BaseColor.BLACK)))
            cell38.horizontalAlignment = Element.ALIGN_RIGHT
            cell38.verticalAlignment = Element.ALIGN_MIDDLE
            cell38.border = PdfPCell.NO_BORDER
            secondRow.addCell(cell38)

            val cell39 =
                PdfPCell()
            val otrNo =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            otrNo.add(tvOtroNo)
            otrNo.spacingAfter = 2.0f
            otrNo.alignment = Element.ALIGN_CENTER
            cell39.addElement(otrNo)
            cell39.horizontalAlignment = Element.ALIGN_CENTER
            cell39.verticalAlignment = Element.ALIGN_MIDDLE
            cell39.borderWidth = 1F
            secondRow.addCell(cell39)

            val thirdRow = PdfPTable(2)
            thirdRow.widthPercentage = 70.0f
            thirdRow.spacingAfter = 4.0f
            thirdRow.horizontalAlignment = Element.ALIGN_LEFT
            thirdRow.setWidths(floatArrayOf(1.6f, 9.2f))
            val cell40 = PdfPCell(
                Phrase(
                    "Especifique:",
                    Font(Font.FontFamily.HELVETICA, 8.0f, Font.NORMAL, BaseColor.BLACK)
                )
            )
            cell40.horizontalAlignment = Element.ALIGN_CENTER
            cell40.verticalAlignment = Element.ALIGN_MIDDLE
            cell40.border = PdfPCell.NO_BORDER
            thirdRow.addCell(cell40)

            val cell41 =
                PdfPCell()
            val esp =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            esp.add(tvespecifique)
            esp.spacingAfter = 2.0f
            esp.alignment = Element.ALIGN_CENTER
            cell41.addElement(esp)
            cell41.horizontalAlignment = Element.ALIGN_LEFT
            cell41.verticalAlignment = Element.ALIGN_LEFT
            cell41.border = 2
            thirdRow.addCell(cell41)

            val cell42 = PdfPCell()
            cell42.setPadding(5F)

            cell42.addElement(innerTable)
            cell42.addElement(secondRow)
            cell42.addElement(thirdRow)

            outerTable.addCell(cell42)

            document.add(outerTable)

            addMediaEmptyLine(document, 1)

            val instrucciones4 = PdfPTable(2)
            instrucciones4.horizontalAlignment = Element.ALIGN_LEFT
            instrucciones4.widthPercentage = 100F
            instrucciones4.setWidths(floatArrayOf(1.6f, 7f))
            val cell43 = PdfPCell(
                Phrase(
                    "3.    Recolección.",
                    Font(Font.FontFamily.HELVETICA, 10.0f, Font.BOLD, BaseColor.BLACK)
                )
            )
            cell43.isUseAscender = true
            cell43.border = PdfPCell.NO_BORDER
            cell43.setPadding(3f)
            cell43.horizontalAlignment = Element.ALIGN_RIGHT
            cell43.verticalAlignment = Element.ALIGN_MIDDLE
            instrucciones4.addCell(cell43)
            val cell44 = PdfPCell(
                Phrase(
                    "(Coloque el número, letra o combinación de los indicios o elementos materiales probatorios de acuerdo a las\n",
                    Font(Font.FontFamily.HELVETICA, 7.5f, Font.NORMAL, BaseColor.BLACK)
                )
            )
            cell44.isUseAscender = true
            cell44.border = PdfPCell.NO_BORDER
            cell44.setPadding(3f)
            cell44.horizontalAlignment = Element.ALIGN_JUSTIFIED_ALL
            cell44.verticalAlignment = Element.ALIGN_MIDDLE
            instrucciones4.addCell(cell44)
            val cell45 = PdfPCell()
            val temp3 = Paragraph(
                "condiciones de cómo fueron levantados según corresponda. Puede emplear intervalos). \n",
                Font(Font.FontFamily.HELVETICA, 7.4f, Font.NORMAL, BaseColor.BLACK)
            )
            temp3.alignment = Element.ALIGN_LEFT
            temp3.indentationLeft = 23.0f
            cell45.addElement(temp3)
            cell45.border = PdfPCell.NO_BORDER
            cell45.isUseAscender = true
            cell45.colspan = 2
            cell45.horizontalAlignment = Element.ALIGN_TOP
            cell45.verticalAlignment = Element.ALIGN_TOP
            instrucciones4.addCell(cell45)
            document.add(instrucciones4)

            addMediaEmptyLine(document, 1)

            val header3 = PdfPTable(2)
            header3.widthPercentage = 100F
            header3.addCell(createCell("Manual", 1, 1))
            header3.addCell(createCell("Instrumental", 1, 1))
            val header4 = PdfPTable(2)
            header4.widthPercentage = 100F

            val cell46 =
                PdfPCell()
            val man =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            man.add(tvrmanual)
            man.spacingAfter = 2.0f
            man.alignment = Element.ALIGN_CENTER
            cell46.addElement(man)
            cell46.horizontalAlignment = Element.ALIGN_CENTER
            cell46.verticalAlignment = Element.ALIGN_MIDDLE
            header4.addCell(cell46)

            val cell47 =
                PdfPCell()
            val instru =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            instru.add(tvrinstrumental)
            instru.spacingAfter = 2.0f
            instru.alignment = Element.ALIGN_CENTER
            cell47.addElement(instru)
            cell47.horizontalAlignment = Element.ALIGN_CENTER
            cell47.verticalAlignment = Element.ALIGN_MIDDLE
            header4.addCell(cell47)

            document.add(header3)
            document.add(header4)

            addEmptyLine(document, 1)

            ColumnText.showTextAligned(
                writer.directContent,
                Element.ALIGN_LEFT,
                Phrase(
                    "Registro de Cadena de Custodia",
                    Font(Font.FontFamily.HELVETICA, 9.0f, Font.NORMAL, BaseColor.BLACK)
                ),50f,55f,0f
            )

            ColumnText.showTextAligned(
                writer.directContent,
                Element.ALIGN_RIGHT,
                Phrase(
                    "Pagina__de__",
                    Font(Font.FontFamily.HELVETICA, 9.0f, Font.NORMAL, BaseColor.BLACK)
                ),520f,55f,0f
            )

            document.newPage()


            run {
                val table1: PdfPTable = PdfPTable(1)
                table1.widthPercentage = 100F
                table1.defaultCell.border = PdfPCell.NO_BORDER
                table1.defaultCell.verticalAlignment = Element.ALIGN_RIGHT
                table1.defaultCell.horizontalAlignment = Element.ALIGN_TOP
                val cell50: PdfPCell
                val res: Resources = resources
                @SuppressLint("UseCompatLoadingForDrawables") val d: Drawable =
                    res.getDrawable(R.drawable.cadena)
                val bmp: Bitmap = (d as BitmapDrawable).bitmap
                val stream: ByteArrayOutputStream = ByteArrayOutputStream()
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val logo: Image = Image.getInstance(stream.toByteArray())
                logo.widthPercentage = 90F
                logo.scaleToFit(140F, 60F)
                cell50 = PdfPCell(logo)
                cell50.horizontalAlignment = Element.ALIGN_RIGHT
                cell50.verticalAlignment = Element.ALIGN_TOP
                cell50.isUseAscender = true
                cell50.border = PdfPCell.NO_BORDER
                cell50.setPadding(2f)
                table1.addCell(cell50)
                document.add(table1)
            }
            addEmptyLine(document, 2)
            run {
                val table2: PdfPTable = PdfPTable(2)
                table2.widthPercentage = 100F
                table2.setWidths(floatArrayOf(4f, 2.3f))
                table2.defaultCell.border = PdfPCell.NO_BORDER
                table2.defaultCell.verticalAlignment = Element.ALIGN_RIGHT
                table2.defaultCell.horizontalAlignment = Element.ALIGN_RIGHT
                val cell53: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 14.0f, Font.NORMAL, BaseColor.BLACK)))
                cell53.horizontalAlignment = Element.ALIGN_LEFT
                cell53.verticalAlignment = Element.ALIGN_LEFT
                cell53.border = PdfPCell.NO_BORDER
                cell53.rowspan = 2
                table2.addCell(cell53)
                val cell51: PdfPCell = PdfPCell()
                cell51.horizontalAlignment = Element.ALIGN_RIGHT
                cell51.verticalAlignment = Element.ALIGN_TOP
                cell51.isUseAscender = true
                cell51.rowspan = 1
                cell51.backgroundColor = Verde
                cell51.borderWidth = 0.7f
                cell51.setPadding(3f)
                val temp4: Paragraph = Paragraph(
                    "No. de referencia",
                    Font(Font.FontFamily.HELVETICA, 10.0f, Font.BOLD, BaseColor.WHITE)
                )
                temp4.alignment = Element.ALIGN_CENTER
                cell51.addElement(temp4)
                table2.addCell(cell51)
                val cell52: PdfPCell = PdfPCell()
                cell52.horizontalAlignment = Element.ALIGN_RIGHT
                cell52.verticalAlignment = Element.ALIGN_TOP
                cell52.isUseAscender = true
                cell52.backgroundColor = BaseColor.WHITE
                cell52.borderWidth = 0.7f
                cell52.rowspan = 1
                cell52.setPadding(3f)
                val titulo_para1: Paragraph =
                    Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
                titulo_para1.add(tvreferencia)
                titulo_para1.spacingAfter = 2.0f
                titulo_para1.alignment = Element.ALIGN_CENTER
                cell52.addElement(titulo_para1)
                table2.addCell(cell52)
                document.add(table2)
            }

            addEmptyLine(document, 1)

            val instrucciones5 = PdfPTable(2)
            instrucciones5.horizontalAlignment = Element.ALIGN_LEFT
            instrucciones5.widthPercentage = 100F
            instrucciones5.setWidths(floatArrayOf(2.5f, 7f))
            val cell54 = PdfPCell(
                Phrase(
                    "4.    Empaque/Embalaje.",
                    Font(Font.FontFamily.HELVETICA, 10.0f, Font.BOLD, BaseColor.BLACK)
                )
            )
            cell54.isUseAscender = true
            cell54.border = PdfPCell.NO_BORDER
            cell54.setPadding(3f)
            cell54.horizontalAlignment = Element.ALIGN_RIGHT
            cell54.verticalAlignment = Element.ALIGN_MIDDLE
            instrucciones5.addCell(cell54)
            val cell55 = PdfPCell(
                Phrase(
                    "(Coloque el número, letra o combinación de los indicios o elementos materiales de acuerdo al tipo de\n",
                    Font(Font.FontFamily.HELVETICA, 7.5f, Font.NORMAL, BaseColor.BLACK)
                )
            )
            cell55.isUseAscender = true
            cell55.border = PdfPCell.NO_BORDER
            cell55.setPadding(3f)
            cell55.horizontalAlignment = Element.ALIGN_JUSTIFIED_ALL
            cell55.verticalAlignment = Element.ALIGN_MIDDLE
            instrucciones5.addCell(cell55)
            val cell56 = PdfPCell()
            val temp5 = Paragraph(
                "embalaje que se empleó para su preservación o conservación, según corresponda. Puede emplear intervalos). \n",
                Font(Font.FontFamily.HELVETICA, 7.4f, Font.NORMAL, BaseColor.BLACK)
            )
            temp5.alignment = Element.ALIGN_LEFT
            temp5.indentationLeft = 27.5f
            cell56.addElement(temp5)
            cell56.border = PdfPCell.NO_BORDER
            cell56.isUseAscender = true
            cell56.colspan = 2
            cell56.horizontalAlignment = Element.ALIGN_TOP
            cell56.verticalAlignment = Element.ALIGN_TOP
            instrucciones5.addCell(cell56)
            document.add(instrucciones5)

            addMediaEmptyLine(document, 1)

            val header5 = PdfPTable(3)
            header5.widthPercentage = 100F
            header5.addCell(createCell("Bolsa", 1, 1))
            header5.addCell(createCell("Caja", 1, 1))
            header5.addCell(createCell("Recipientes", 1, 1))
            val header6 = PdfPTable(3)
            header6.widthPercentage = 100F

            val cell57 =
                PdfPCell()
            val bol =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            bol.add(tvbolsa)
            bol.spacingAfter = 2.0f
            bol.alignment = Element.ALIGN_CENTER
            cell57.addElement(bol)
            cell57.horizontalAlignment = Element.ALIGN_CENTER
            cell57.verticalAlignment = Element.ALIGN_MIDDLE
            header6.addCell(cell57)

            val cell58 =
                PdfPCell()
            val caj =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            caj.add(tvcaja)
            caj.spacingAfter = 2.0f
            caj.alignment = Element.ALIGN_CENTER
            cell58.addElement(caj)
            cell58.horizontalAlignment = Element.ALIGN_CENTER
            cell58.verticalAlignment = Element.ALIGN_MIDDLE
            header6.addCell(cell58)

            val cell59 =
                PdfPCell()
            val rec =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            rec.add(tvrecipiente)
            rec.spacingAfter = 2.0f
            rec.alignment = Element.ALIGN_CENTER
            cell59.addElement(rec)
            cell59.horizontalAlignment = Element.ALIGN_CENTER
            cell59.verticalAlignment = Element.ALIGN_MIDDLE
            header6.addCell(cell59)
            document.add(header5)
            document.add(header6)

            addMediaEmptyLine(document, 1)

            val instrucciones6 = PdfPTable(2)
            instrucciones6.horizontalAlignment = Element.ALIGN_LEFT
            instrucciones6.widthPercentage = 100F
            instrucciones6.setWidths(floatArrayOf(2.5f, 7f))
            val cell60 = PdfPCell(
                Phrase(
                    "5.    Servidores públicos.",
                    Font(Font.FontFamily.HELVETICA, 10.0f, Font.BOLD, BaseColor.BLACK)
                )
            )
            cell60.isUseAscender = true
            cell60.border = PdfPCell.NO_BORDER
            cell60.setPadding(3f)
            cell60.horizontalAlignment = Element.ALIGN_RIGHT
            cell60.verticalAlignment = Element.ALIGN_MIDDLE
            instrucciones6.addCell(cell60)
            val cell61 = PdfPCell(
                Phrase(
                    " (Todo servidor público que haya participado en el procesamiento de los indicios o elementos materiales\n",
                    Font(Font.FontFamily.HELVETICA, 7.5f, Font.NORMAL, BaseColor.BLACK)
                )
            )
            cell61.isUseAscender = true
            cell61.border = PdfPCell.NO_BORDER
            cell61.setPadding(3f)
            cell61.horizontalAlignment = Element.ALIGN_JUSTIFIED_ALL
            cell61.verticalAlignment = Element.ALIGN_MIDDLE
            instrucciones6.addCell(cell61)
            val cell62 = PdfPCell()
            val temp6 = Paragraph(
                "probatorios deberá escribir su nombre completo, la Institución  a  la  que  pertenece, su cargo, la  etapa  del  procesamiento  en  la  que \n" +
                        "intervino y su firma autógrafa. Se deberán cancelar los espacios sobrantes). \n",
                Font(Font.FontFamily.HELVETICA, 7.5f, Font.NORMAL, BaseColor.BLACK)
            )
            temp6.alignment = Element.ALIGN_LEFT
            temp6.indentationLeft = 22.5f
            cell62.addElement(temp6)
            cell62.border = PdfPCell.NO_BORDER
            cell62.isUseAscender = true
            cell62.colspan = 2
            cell62.horizontalAlignment = Element.ALIGN_TOP
            cell62.verticalAlignment = Element.ALIGN_TOP
            instrucciones6.addCell(cell62)
            document.add(instrucciones6)
            addEmptyLine(document, 1)

            val header7 = PdfPTable(4)
            header7.widthPercentage = 100F
            header7.setWidths(floatArrayOf(3.5f, 1.6f, 1.3f, 1.3f))
            header7.addCell(createCell("Nombre Completo", 1, 1))
            header7.addCell(createCell("Institución y cargo", 1, 1))
            header7.addCell(createCell("Etapa", 1, 1))
            val cell98 = PdfPCell(Phrase("Firma", FONT_CELL))
            cell98.isUseAscender = true
            cell98.backgroundColor = Verde
            cell98.setPadding(12f)
            cell98.horizontalAlignment = Element.ALIGN_CENTER
            cell98.verticalAlignment = Element.ALIGN_MIDDLE
            header7.addCell(cell98)

            val header8 = PdfPTable(4)
            header8.widthPercentage = 100F
            header8.setWidths(floatArrayOf(3.5f, 1.6f, 1.3f, 1.3f))
            val cell63 =
                PdfPCell()
            val nom1 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            nom1.add(tvservpub1)
            nom1.spacingAfter = 1.0f
            nom1.alignment = Element.ALIGN_CENTER
            cell63.addElement(nom1)
            cell63.horizontalAlignment = Element.ALIGN_CENTER
            cell63.verticalAlignment = Element.ALIGN_MIDDLE
            cell63.rowspan = 2
            header8.addCell(cell63)
            val cell64 =
                PdfPCell()
            val insti1 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            insti1.add(tvinst1)
            insti1.spacingAfter = 1.0f
            insti1.alignment = Element.ALIGN_CENTER
            cell64.addElement(insti1)
            cell64.setPadding(4f)
            cell64.horizontalAlignment = Element.ALIGN_CENTER
            cell64.verticalAlignment = Element.ALIGN_MIDDLE
            cell64.rowspan = 1
            header8.addCell(cell64)
            val cell65 =
                PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 14.0f, Font.BOLD, BaseColor.BLACK)))
            val etp1 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            etp1.add(tvetp1)
            etp1.spacingAfter = 1.0f
            etp1.alignment = Element.ALIGN_CENTER
            cell65.addElement(etp1)
            cell65.horizontalAlignment = Element.ALIGN_CENTER
            cell65.verticalAlignment = Element.ALIGN_MIDDLE
            cell65.rowspan = 2
            header8.addCell(cell65)

            val cell66 =
                PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 14.0f, Font.BOLD, BaseColor.BLACK)))
            cell66.horizontalAlignment = Element.ALIGN_CENTER
            cell66.verticalAlignment = Element.ALIGN_MIDDLE
            cell66.setPadding(6f)
            cell66.rowspan = 2
            header8.addCell(cell66)

            val cell67 =
                PdfPCell()
            val crg1 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            crg1.add(tvcrg1)
            crg1.spacingAfter = 1.0f
            crg1.alignment = Element.ALIGN_CENTER
            cell67.addElement(crg1)
            cell67.horizontalAlignment = Element.ALIGN_CENTER
            cell67.verticalAlignment = Element.ALIGN_MIDDLE
            cell67.rowspan = 1
            header8.addCell(cell67)
            document.add(header7)
            document.add(header8)

            val header9 = PdfPTable(4)
            header9.widthPercentage = 100F
            header9.setWidths(floatArrayOf(3.5f, 1.6f, 1.3f, 1.3f))

            val cell68 =
                PdfPCell()
            val nom2 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            nom2.add(tvservpub2)
            nom2.spacingAfter = 2.0f
            nom2.alignment = Element.ALIGN_CENTER
            cell68.addElement(nom2)
            cell68.horizontalAlignment = Element.ALIGN_CENTER
            cell68.verticalAlignment = Element.ALIGN_MIDDLE
            cell68.rowspan = 2
            header9.addCell(cell68)

            val cell69 =
                PdfPCell()
            val insti2 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            insti2.add(tvinst2)
            insti2.spacingAfter = 2.0f
            insti2.alignment = Element.ALIGN_CENTER
            cell69.addElement(insti2)
            cell69.setPadding(5f)
            cell69.horizontalAlignment = Element.ALIGN_CENTER
            cell69.verticalAlignment = Element.ALIGN_MIDDLE
            cell69.rowspan = 1
            header9.addCell(cell69)

            val cell70 =
                PdfPCell()
            val etp2 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            etp2.add(tvetp2)
            etp2.spacingAfter = 2.0f
            etp2.alignment = Element.ALIGN_CENTER
            cell70.addElement(etp2)
            cell70.horizontalAlignment = Element.ALIGN_CENTER
            cell70.verticalAlignment = Element.ALIGN_MIDDLE
            cell70.rowspan = 2
            header9.addCell(cell70)

            val cell71 =
                PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 14.0f, Font.BOLD, BaseColor.BLACK)))
            cell71.horizontalAlignment = Element.ALIGN_CENTER
            cell71.verticalAlignment = Element.ALIGN_MIDDLE
            cell71.setPadding(8f)
            cell71.rowspan = 2
            header9.addCell(cell71)

            val cell72 =
                PdfPCell()
            val crg2 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            crg2.add(tvcrg2)
            crg2.spacingAfter = 2.0f
            crg2.alignment = Element.ALIGN_CENTER
            cell72.addElement(crg2)
            cell72.horizontalAlignment = Element.ALIGN_CENTER
            cell72.verticalAlignment = Element.ALIGN_MIDDLE
            cell72.rowspan = 1
            header9.addCell(cell72)
            document.add(header9)

            val header10 = PdfPTable(4)
            header10.widthPercentage = 100F
            header10.setWidths(floatArrayOf(3.5f, 1.6f, 1.3f, 1.3f))
            val cell73 =
                PdfPCell()
            val nom3 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            nom3.add(tvservpub3)
            nom3.spacingAfter = 2.0f
            nom3.alignment = Element.ALIGN_CENTER
            cell73.addElement(nom3)
            cell73.horizontalAlignment = Element.ALIGN_CENTER
            cell73.verticalAlignment = Element.ALIGN_MIDDLE
            cell73.rowspan = 2
            header10.addCell(cell73)

            val cell74 =
                PdfPCell()
            val insti3 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            insti3.add(tvinst3)
            insti3.spacingAfter = 2.0f
            insti3.alignment = Element.ALIGN_CENTER
            cell74.addElement(insti3)
            cell74.setPadding(5f)
            cell74.horizontalAlignment = Element.ALIGN_CENTER
            cell74.verticalAlignment = Element.ALIGN_MIDDLE
            cell74.rowspan = 1
            header10.addCell(cell74)

            val cell75 =
                PdfPCell()
            val etp3 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            etp3.add(tvetp3)
            etp3.spacingAfter = 2.0f
            etp3.alignment = Element.ALIGN_CENTER
            cell75.addElement(etp3)
            cell75.horizontalAlignment = Element.ALIGN_CENTER
            cell75.verticalAlignment = Element.ALIGN_MIDDLE
            cell75.rowspan = 2
            header10.addCell(cell75)

            val cell76 =
                PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 14.0f, Font.BOLD, BaseColor.BLACK)))
            cell76.horizontalAlignment = Element.ALIGN_CENTER
            cell76.verticalAlignment = Element.ALIGN_MIDDLE
            cell76.setPadding(8f)
            cell76.rowspan = 2
            header10.addCell(cell76)
            val cell77 =
                PdfPCell()
            val crg3 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            crg3.add(tvcrg3)
            crg3.spacingAfter = 2.0f
            crg3.alignment = Element.ALIGN_CENTER
            cell77.addElement(crg3)
            cell77.horizontalAlignment = Element.ALIGN_CENTER
            cell77.verticalAlignment = Element.ALIGN_MIDDLE
            cell77.rowspan = 1
            header10.addCell(cell77)
            document.add(header10)

            val header11 = PdfPTable(4)
            header11.widthPercentage = 100F
            header11.setWidths(floatArrayOf(3.5f, 1.6f, 1.3f, 1.3f))
            val cell78 =
                PdfPCell()
            val nom4 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            nom4.add(tvservpub4)
            nom4.spacingAfter = 2.0f
            nom4.alignment = Element.ALIGN_CENTER
            cell78.addElement(nom4)
            cell78.horizontalAlignment = Element.ALIGN_CENTER
            cell78.verticalAlignment = Element.ALIGN_MIDDLE
            cell78.rowspan = 2
            header11.addCell(cell78)

            val cell79 =
                PdfPCell()
            val insti4 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            insti4.add(tvinst4)
            insti4.spacingAfter = 2.0f
            insti4.alignment = Element.ALIGN_CENTER
            cell79.addElement(insti4)
            cell79.setPadding(5f)
            cell79.horizontalAlignment = Element.ALIGN_CENTER
            cell79.verticalAlignment = Element.ALIGN_MIDDLE
            cell79.rowspan = 1
            header11.addCell(cell79)

            val cell80 =
                PdfPCell()
            val etp4 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            etp4.add(tvetp4)
            etp4.spacingAfter = 2.0f
            etp4.alignment = Element.ALIGN_CENTER
            cell80.addElement(etp4)
            cell80.horizontalAlignment = Element.ALIGN_CENTER
            cell80.verticalAlignment = Element.ALIGN_MIDDLE
            cell80.rowspan = 2
            header11.addCell(cell80)

            //Firma4
            val cell81 =
                PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 14.0f, Font.BOLD, BaseColor.BLACK)))
            cell81.horizontalAlignment = Element.ALIGN_CENTER
            cell81.verticalAlignment = Element.ALIGN_MIDDLE
            cell81.setPadding(8f)
            cell81.rowspan = 2
            header11.addCell(cell81)

            val cell82 =
                PdfPCell()
            val crg4 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            crg4.add(tvcrg4)
            crg4.spacingAfter = 2.0f
            crg4.alignment = Element.ALIGN_CENTER
            cell82.addElement(crg4)
            cell82.horizontalAlignment = Element.ALIGN_CENTER
            cell82.verticalAlignment = Element.ALIGN_MIDDLE
            cell82.rowspan = 1
            header11.addCell(cell82)
            document.add(header11)

            val header12 = PdfPTable(4)
            header12.widthPercentage = 100F
            header12.setWidths(floatArrayOf(3.5f, 1.6f, 1.3f, 1.3f))
            val cell83 =
                PdfPCell()
            val nom5 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            nom5.add(tvservpub5)
            nom5.spacingAfter = 2.0f
            nom5.alignment = Element.ALIGN_CENTER
            cell83.addElement(nom5)
            cell83.horizontalAlignment = Element.ALIGN_CENTER
            cell83.verticalAlignment = Element.ALIGN_MIDDLE
            cell83.rowspan = 2
            header12.addCell(cell83)

            val cell84 =
                PdfPCell()
            val insti5 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            insti5.add(tvinst5)
            insti5.spacingAfter = 2.0f
            insti5.alignment = Element.ALIGN_CENTER
            cell84.addElement(insti5)
            cell84.setPadding(5f)
            cell84.horizontalAlignment = Element.ALIGN_CENTER
            cell84.verticalAlignment = Element.ALIGN_MIDDLE
            cell84.rowspan = 1
            header12.addCell(cell84)
            val cell85 =
                PdfPCell()
            val etp5 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            etp5.add(tvetp5)
            etp5.spacingAfter = 2.0f
            etp5.alignment = Element.ALIGN_CENTER
            cell85.addElement(etp5)
            cell85.horizontalAlignment = Element.ALIGN_CENTER
            cell85.verticalAlignment = Element.ALIGN_MIDDLE
            cell85.rowspan = 2
            header12.addCell(cell85)
            val cell86 =
                PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 14.0f, Font.BOLD, BaseColor.BLACK)))
            cell86.horizontalAlignment = Element.ALIGN_CENTER
            cell86.verticalAlignment = Element.ALIGN_MIDDLE
            cell86.setPadding(6f)
            cell86.rowspan = 2
            header12.addCell(cell86)

            val cell87 =
                PdfPCell()
            val crg5 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            crg5.add(tvcrg5)
            crg5.spacingAfter = 1.0f
            crg5.alignment = Element.ALIGN_CENTER
            cell87.addElement(crg5)
            cell87.horizontalAlignment = Element.ALIGN_CENTER
            cell87.verticalAlignment = Element.ALIGN_MIDDLE
            cell87.rowspan = 1
            header12.addCell(cell87)
            document.add(header12)

            val header13 = PdfPTable(4)
            header13.widthPercentage = 100F
            header13.setWidths(floatArrayOf(3.5f, 1.6f, 1.3f, 1.3f))
            val cell88 =
                PdfPCell()
            val nom6 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            nom6.add(tvservpub6)
            nom6.spacingAfter = 2.0f
            nom6.alignment = Element.ALIGN_CENTER
            cell88.addElement(nom6)
            cell88.horizontalAlignment = Element.ALIGN_CENTER
            cell88.verticalAlignment = Element.ALIGN_MIDDLE
            cell88.rowspan = 2
            header13.addCell(cell88)

            val cell89 =
                PdfPCell()
            val insti6 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            insti6.add(tvinst6)
            insti6.spacingAfter = 2.0f
            insti6.alignment = Element.ALIGN_CENTER
            cell89.addElement(insti6)
            cell89.setPadding(5f)
            cell89.horizontalAlignment = Element.ALIGN_CENTER
            cell89.verticalAlignment = Element.ALIGN_MIDDLE
            cell89.rowspan = 1
            header13.addCell(cell89)

            val cell90 =
                PdfPCell()
            val etp6 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            etp6.add(tvetp6)
            etp6.spacingAfter = 2.0f
            etp6.alignment = Element.ALIGN_CENTER
            cell90.addElement(etp6)
            cell90.horizontalAlignment = Element.ALIGN_CENTER
            cell90.verticalAlignment = Element.ALIGN_MIDDLE
            cell90.rowspan = 2
            header13.addCell(cell90)

            val cell91 =
                PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 14.0f, Font.BOLD, BaseColor.BLACK)))
            cell91.horizontalAlignment = Element.ALIGN_CENTER
            cell91.verticalAlignment = Element.ALIGN_MIDDLE
            cell91.setPadding(8f)
            cell91.rowspan = 2
            header13.addCell(cell91)

            val cell92 =
                PdfPCell()
            val crg6 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            crg6.add(tvcrg6)
            crg6.spacingAfter = 2.0f
            crg6.alignment = Element.ALIGN_CENTER
            cell92.addElement(crg6)
            cell92.horizontalAlignment = Element.ALIGN_CENTER
            cell92.verticalAlignment = Element.ALIGN_MIDDLE
            cell92.rowspan = 1
            header13.addCell(cell92)
            document.add(header13)

            val header14 = PdfPTable(4)
            header14.widthPercentage = 100F
            header14.setWidths(floatArrayOf(3.5f, 1.6f, 1.3f, 1.3f))
            val cell93 =
                PdfPCell()
            val nom7 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            nom7.add(tvservpub7)
            nom7.spacingAfter = 2.0f
            nom7.alignment = Element.ALIGN_CENTER
            cell93.addElement(nom7)
            cell93.horizontalAlignment = Element.ALIGN_CENTER
            cell93.verticalAlignment = Element.ALIGN_MIDDLE
            cell93.rowspan = 2
            header14.addCell(cell93)

            val cell94 =
                PdfPCell()
            val insti7 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            insti7.add(tvinst7)
            insti7.spacingAfter = 2.0f
            insti7.alignment = Element.ALIGN_CENTER
            cell94.addElement(insti7)
            cell94.setPadding(5f)
            cell94.horizontalAlignment = Element.ALIGN_CENTER
            cell94.verticalAlignment = Element.ALIGN_MIDDLE
            cell94.rowspan = 1
            header14.addCell(cell94)

            val cell95 =
                PdfPCell()
            val etp7 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            etp7.add(tvetp7)
            etp7.spacingAfter = 2.0f
            etp7.alignment = Element.ALIGN_CENTER
            cell95.addElement(etp7)
            cell95.horizontalAlignment = Element.ALIGN_CENTER
            cell95.verticalAlignment = Element.ALIGN_MIDDLE
            cell95.rowspan = 2
            header14.addCell(cell95)

            val cell96 =
                PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 14.0f, Font.BOLD, BaseColor.BLACK)))
            cell96.horizontalAlignment = Element.ALIGN_CENTER
            cell96.verticalAlignment = Element.ALIGN_MIDDLE
            cell96.setPadding(8f)
            cell96.rowspan = 2
            header14.addCell(cell96)

            val cell97 =
                PdfPCell()
            val crg7 =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            crg7.add(tvcrg7)
            crg7.spacingAfter = 2.0f
            crg7.alignment = Element.ALIGN_CENTER
            cell97.addElement(crg7)
            cell97.horizontalAlignment = Element.ALIGN_CENTER
            cell97.verticalAlignment = Element.ALIGN_MIDDLE
            cell97.rowspan = 1
            header14.addCell(cell97)
            document.add(header14)

            addMediaEmptyLine(document, 1)

            val instrucciones7 = PdfPTable(2)
            instrucciones7.horizontalAlignment = Element.ALIGN_LEFT
            instrucciones7.widthPercentage = 100F
            instrucciones7.setWidths(floatArrayOf(1.25f, 7f))
            val cell99 = PdfPCell(
                Phrase(
                    "6.    Traslado.",
                    Font(Font.FontFamily.HELVETICA, 10.0f, Font.BOLD, BaseColor.BLACK)
                )
            )
            cell99.isUseAscender = true
            cell99.border = PdfPCell.NO_BORDER
            cell99.setPadding(3f)
            cell99.horizontalAlignment = Element.ALIGN_RIGHT
            cell99.verticalAlignment = Element.ALIGN_MIDDLE
            instrucciones7.addCell(cell99)
            val cell100 = PdfPCell(
                Phrase(
                    "  (Marque con “X” la vía empleada. En caso de ser necesaria alguna condición especial para la conservación o\n",
                    Font(Font.FontFamily.HELVETICA, 7.5f, Font.NORMAL, BaseColor.BLACK)
                )
            )
            cell100.isUseAscender = true
            cell100.border = PdfPCell.NO_BORDER
            cell100.setPadding(3f)
            cell100.horizontalAlignment = Element.ALIGN_JUSTIFIED_ALL
            cell100.verticalAlignment = Element.ALIGN_MIDDLE
            instrucciones7.addCell(cell100)
            val cell101 = PdfPCell()
            val temp7 = Paragraph(
                "preservación  de  un  indicio  o  elemento  material  probatorio  en  particular,  el  personal  pericial  o  policial  con  capacidades  para  el \n" +
                        "procesar, según sea el caso, deberá recomendarla). \n",
                Font(Font.FontFamily.HELVETICA, 7.5f, Font.NORMAL, BaseColor.BLACK)
            )
            temp7.alignment = Element.ALIGN_LEFT
            temp7.indentationLeft = 22.5f
            cell101.addElement(temp7)
            cell101.border = PdfPCell.NO_BORDER
            cell101.isUseAscender = true
            cell101.colspan = 2
            cell101.horizontalAlignment = Element.ALIGN_TOP
            cell101.verticalAlignment = Element.ALIGN_TOP
            instrucciones7.addCell(cell101)
            document.add(instrucciones7)

            val outerTable1 = PdfPTable(1)
            outerTable1.widthPercentage = 100F
            outerTable1.horizontalAlignment = Element.ALIGN_LEFT
            val innerTable1 = PdfPTable(8)
            innerTable1.widthPercentage = 100F
            innerTable1.spacingAfter = 4.0f
            innerTable1.setWidths(floatArrayOf(1.6f, 2.0f, 0.8f, 3.0f, 0.8f, 3.0f, 0.8f, 0.5f))
            val cell102 = PdfPCell(
                Phrase(
                    "a) Vía:",
                    Font(Font.FontFamily.HELVETICA, 9.0f, Font.NORMAL, BaseColor.BLACK)
                )
            )
            cell102.horizontalAlignment = Element.ALIGN_CENTER
            cell102.verticalAlignment = Element.ALIGN_MIDDLE
            cell102.paddingLeft = 8.0f
            cell102.border = PdfPCell.NO_BORDER
            cell102.isUseAscender = false
            innerTable1.addCell(cell102)
            val cell103 = PdfPCell(
                Phrase(
                    "Terrestre  ",
                    Font(Font.FontFamily.HELVETICA, 9.0f, Font.NORMAL, BaseColor.BLACK)
                )
            )
            cell103.horizontalAlignment = Element.ALIGN_RIGHT
            cell103.verticalAlignment = Element.ALIGN_MIDDLE
            cell103.paddingRight = 10.0f
            cell103.border = PdfPCell.NO_BORDER
            innerTable1.addCell(cell103)

            val cell104 =
                PdfPCell()
            val terr =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            terr.add(tvterrestre)
            terr.spacingAfter = 2.0f
            terr.alignment = Element.ALIGN_CENTER
            cell104.addElement(terr)
            cell104.horizontalAlignment = Element.ALIGN_CENTER
            cell104.verticalAlignment = Element.ALIGN_MIDDLE
            cell104.borderWidth = 1F
            innerTable1.addCell(cell104)

            val cell105 = PdfPCell(
                Phrase(
                    "Aérea  ",
                    Font(Font.FontFamily.HELVETICA, 9.0f, Font.NORMAL, BaseColor.BLACK)
                )
            )
            cell105.horizontalAlignment = Element.ALIGN_RIGHT
            cell105.paddingRight = 8.0f
            cell105.verticalAlignment = Element.ALIGN_MIDDLE
            cell105.border = PdfPCell.NO_BORDER
            innerTable1.addCell(cell105)

            val cell106 =
                PdfPCell()
            val aer =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            aer.add(tvaerea)
            aer.spacingAfter = 2.0f
            aer.alignment = Element.ALIGN_CENTER
            cell106.addElement(aer)
            cell106.horizontalAlignment = Element.ALIGN_LEFT
            cell106.verticalAlignment = Element.ALIGN_LEFT
            cell106.borderWidth = 1F
            innerTable1.addCell(cell106)

            val cell107 = PdfPCell(
                Phrase(
                    "Marítima  ",
                    Font(Font.FontFamily.HELVETICA, 9.0f, Font.NORMAL, BaseColor.BLACK)
                )
            )
            cell107.horizontalAlignment = Element.ALIGN_RIGHT
            cell107.verticalAlignment = Element.ALIGN_MIDDLE
            cell107.paddingRight = 10.0f
            cell107.border = PdfPCell.NO_BORDER
            innerTable1.addCell(cell107)

            val cell108 =
                PdfPCell()
            val mar =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            mar.add(tvmaritima)
            mar.spacingAfter = 2.0f
            mar.alignment = Element.ALIGN_CENTER
            cell108.addElement(mar)
            cell108.horizontalAlignment = Element.ALIGN_LEFT
            cell108.verticalAlignment = Element.ALIGN_LEFT
            cell108.borderWidth = 1F
            innerTable1.addCell(cell108)

            val cell109 = PdfPCell()
            cell109.horizontalAlignment = Element.ALIGN_LEFT
            cell109.verticalAlignment = Element.ALIGN_LEFT
            cell109.border = PdfPCell.NO_BORDER
            innerTable1.addCell(cell109)

            val secondRow1 = PdfPTable(6)
            secondRow1.widthPercentage = 100F
            secondRow1.spacingAfter = 4.0f
            secondRow1.horizontalAlignment = Element.ALIGN_LEFT
            secondRow1.setWidths(floatArrayOf(7.0f, 0.85f, 0.85f, 3.2f, 0.85f, 0.5f))
            val cell110 = PdfPCell(
                Phrase(
                    "b) Se requieren condiciones especiales para su traslado:",
                    Font(Font.FontFamily.HELVETICA, 9.0f, Font.NORMAL, BaseColor.BLACK)
                )
            )
            cell110.horizontalAlignment = Element.ALIGN_RIGHT
            cell110.verticalAlignment = Element.ALIGN_MIDDLE
            cell110.paddingRight = 2.0f
            cell110.border = PdfPCell.NO_BORDER
            secondRow1.addCell(cell110)
            val cell111 =
                PdfPCell(Phrase("No ", Font(Font.FontFamily.HELVETICA, 9.0f, Font.NORMAL, BaseColor.BLACK)))
            cell111.horizontalAlignment = Element.ALIGN_CENTER
            cell111.verticalAlignment = Element.ALIGN_MIDDLE
            cell111.border = PdfPCell.NO_BORDER
            secondRow1.addCell(cell111)

            val cell112 =
                PdfPCell()
            val conNo =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            conNo.add(tvconNo)
            conNo.spacingAfter = 2.0f
            conNo.alignment = Element.ALIGN_CENTER
            cell112.addElement(conNo)
            cell112.horizontalAlignment = Element.ALIGN_LEFT
            cell112.verticalAlignment = Element.ALIGN_LEFT
            cell112.borderWidth = 1F
            secondRow1.addCell(cell112)

            val cell113 =
                PdfPCell(Phrase("Sí ", Font(Font.FontFamily.HELVETICA, 9.0f, Font.NORMAL, BaseColor.BLACK)))
            cell113.horizontalAlignment = Element.ALIGN_RIGHT
            cell113.verticalAlignment = Element.ALIGN_MIDDLE
            cell113.paddingRight = 8.5f
            cell113.border = PdfPCell.NO_BORDER
            secondRow1.addCell(cell113)

            val cell114 =
                PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 10.0f, Font.BOLD, BaseColor.BLACK)))
            val conSi =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            conSi.add(tvConSi)
            conSi.spacingAfter = 2.0f
            conSi.alignment = Element.ALIGN_CENTER
            cell114.addElement(conSi)
            cell114.horizontalAlignment = Element.ALIGN_LEFT
            cell114.verticalAlignment = Element.ALIGN_LEFT
            cell114.borderWidth = 1F
            secondRow1.addCell(cell114)

            val cell115 = PdfPCell()
            cell115.horizontalAlignment = Element.ALIGN_LEFT
            cell115.verticalAlignment = Element.ALIGN_LEFT
            cell115.border = PdfPCell.NO_BORDER
            secondRow1.addCell(cell115)

            val cell116 = PdfPCell()
            cell116.setPadding(5F)
            cell116.addElement(innerTable1)
            cell116.addElement(secondRow1)
            outerTable1.addCell(cell116)
            document.add(outerTable1)
            val outerTable2 = PdfPTable(1)
            outerTable2.widthPercentage = 100F
            outerTable2.horizontalAlignment = Element.ALIGN_LEFT
            val instrucciones8 = PdfPTable(1)
            instrucciones8.horizontalAlignment = Element.ALIGN_LEFT
            instrucciones8.widthPercentage = 100F
            val cell117 = PdfPCell(
                Phrase(
                    "Recomendaciones: ",
                    Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK)
                )
            )
            cell117.isUseAscender = true
            cell117.border = PdfPCell.NO_BORDER
            cell117.horizontalAlignment = Element.ALIGN_LEFT
            cell117.verticalAlignment = Element.ALIGN_TOP
            instrucciones8.addCell(cell117)
            val cell118 = PdfPCell()
            val recom =
                Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
            recom.add(tvRecom)
            recom.spacingAfter = 2.0f
            recom.alignment = Element.ALIGN_LEFT
            recom.indentationLeft = 20f
            cell118.addElement(recom)

            cell118.border = PdfPCell.NO_BORDER
            cell118.setPadding(6F)
            cell118.isUseAscender = true
            cell118.horizontalAlignment = Element.ALIGN_TOP
            cell118.verticalAlignment = Element.ALIGN_TOP
            instrucciones8.addCell(cell118)
            val cell119 = PdfPCell()
            cell119.setPadding(1F)
            cell119.addElement(instrucciones8)
            outerTable2.addCell(cell119)
            document.add(outerTable2)
            addEmptyLine(document, 2)

            ColumnText.showTextAligned(
                writer.directContent,
                Element.ALIGN_LEFT,
                Phrase(
                    "Registro de Cadena de Custodia",
                    Font(Font.FontFamily.HELVETICA, 9.0f, Font.NORMAL, BaseColor.BLACK)
                ),50f,55f,0f
            )

            ColumnText.showTextAligned(
                writer.directContent,
                Element.ALIGN_RIGHT,
                Phrase(
                    "Pagina__de__",
                    Font(Font.FontFamily.HELVETICA, 9.0f, Font.NORMAL, BaseColor.BLACK)
                ),520f,55f,0f
            )

            document.newPage()


            //LOGO
            run {
                val table1: PdfPTable = PdfPTable(1)
                table1.widthPercentage = 100F
                table1.defaultCell.border = PdfPCell.NO_BORDER
                table1.defaultCell.verticalAlignment = Element.ALIGN_RIGHT
                table1.defaultCell.horizontalAlignment = Element.ALIGN_TOP
                val cell50: PdfPCell
                val res: Resources = resources
                @SuppressLint("UseCompatLoadingForDrawables") val d: Drawable =
                    res.getDrawable(R.drawable.cadena)
                val bmp: Bitmap = (d as BitmapDrawable).bitmap
                val stream: ByteArrayOutputStream = ByteArrayOutputStream()
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val logo: Image = Image.getInstance(stream.toByteArray())
                logo.widthPercentage = 90F
                logo.scaleToFit(140F, 60F)
                cell50 = PdfPCell(logo)
                cell50.horizontalAlignment = Element.ALIGN_RIGHT
                cell50.verticalAlignment = Element.ALIGN_TOP
                cell50.isUseAscender = true
                cell50.border = PdfPCell.NO_BORDER
                cell50.setPadding(2f)
                table1.addCell(cell50)
                document.add(table1)
            }
            addMediaEmptyLine(document, 1)
            run {
                val table2: PdfPTable = PdfPTable(2)
                table2.widthPercentage = 100F
                table2.setWidths(floatArrayOf(4f, 2.3f))
                table2.defaultCell.border = PdfPCell.NO_BORDER
                table2.defaultCell.verticalAlignment = Element.ALIGN_RIGHT
                table2.defaultCell.horizontalAlignment = Element.ALIGN_RIGHT
                val cell53: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 14.0f, Font.BOLD, BaseColor.BLACK)))
                cell53.horizontalAlignment = Element.ALIGN_LEFT
                cell53.verticalAlignment = Element.ALIGN_LEFT
                cell53.border = PdfPCell.NO_BORDER
                cell53.rowspan = 2
                table2.addCell(cell53)
                val cell51: PdfPCell = PdfPCell()
                cell51.horizontalAlignment = Element.ALIGN_RIGHT
                cell51.verticalAlignment = Element.ALIGN_TOP
                cell51.isUseAscender = true
                cell51.rowspan = 1
                cell51.backgroundColor = Verde
                cell51.borderWidth = 0.7f
                //cell.setBorder(PdfPCell.NO_BORDER);
                cell51.setPadding(3f)
                val temp4: Paragraph = Paragraph(
                    "No. de referencia",
                    Font(Font.FontFamily.HELVETICA, 10.0f, Font.BOLD, BaseColor.WHITE)
                )
                temp4.alignment = Element.ALIGN_CENTER
                cell51.addElement(temp4)
                table2.addCell(cell51)
                val cell52: PdfPCell = PdfPCell()
                cell52.horizontalAlignment = Element.ALIGN_RIGHT
                cell52.verticalAlignment = Element.ALIGN_TOP
                cell52.isUseAscender = true
                cell52.backgroundColor = BaseColor.WHITE
                cell52.borderWidth = 0.7f
                cell52.rowspan = 1
                cell52.setPadding(3f)
                val titulo_para1: Paragraph =
                    Paragraph("", Font(Font.FontFamily.HELVETICA, 10.0f, Font.NORMAL, BaseColor.BLACK))
                titulo_para1.add(tvreferencia)
                titulo_para1.spacingAfter = 2.0f
                titulo_para1.alignment = Element.ALIGN_CENTER
                cell52.addElement(titulo_para1)
                table2.addCell(cell52)
                document.add(table2)
            }
            addMediaEmptyLine(document, 1)

            //Instrucciones trazabilidad
            run {
                val instrucciones9: PdfPTable = PdfPTable(2)
                instrucciones9.horizontalAlignment = Element.ALIGN_LEFT
                instrucciones9.widthPercentage = 100F
                instrucciones9.setWidths(floatArrayOf(4.2f, 8.6f))
                val cell122: PdfPCell = PdfPCell(
                    Phrase(
                        "7.   Continuidad y trazabilidad.",
                        Font(Font.FontFamily.HELVETICA, 10.0f, Font.BOLD, BaseColor.BLACK)
                    )
                )
                cell122.isUseAscender = true
                cell122.border = PdfPCell.NO_BORDER
                cell122.setPadding(3f)
                cell122.horizontalAlignment = Element.ALIGN_RIGHT
                cell122.verticalAlignment = Element.ALIGN_MIDDLE
                instrucciones9.addCell(cell122)
                val cell123: PdfPCell = PdfPCell(
                    Phrase(
                        "(Fecha y hora de la entrega-recepción, nombre completo de quien entrega y de quien recibe los\n",
                        Font(Font.FontFamily.HELVETICA, 7.3f, Font.NORMAL, BaseColor.BLACK)
                    )
                )
                cell123.isUseAscender = true
                cell123.border = PdfPCell.NO_BORDER
                cell123.setPadding(3f)
                cell123.horizontalAlignment = Element.ALIGN_JUSTIFIED_ALL
                cell123.verticalAlignment = Element.ALIGN_MIDDLE
                instrucciones9.addCell(cell123)
                val cell124: PdfPCell = PdfPCell()
                val temp9: Paragraph = Paragraph(
                    (" indicios  o  elementos  materiales  probatorios  en  los  cambios  de  custodia  que  realicen,  institución  a  la  que  pertenecen, cargo  o \n" +
                            "identificación dentro de la misma, propósito de la transferencia, firmas autógrafas y lugar  de  permanencia  en  la  actividad  respectiva. \n" +
                            "Anote las  observaciones  relacionadas con el embalaje,  el  indicio  o  elementos  material  probatorio  o  cualquier  otra  que  considere \n" +
                            "necesario realizar. Agregue cuantas hojas sean necesarias.  Cancele  los  espacios sobrantes  después de que se haya cumplido con el \n" +
                            "destino final del indicio o elemento material probatorio)\n"),
                    Font(Font.FontFamily.HELVETICA, 7.5f, Font.NORMAL, BaseColor.BLACK)
                )
                temp9.alignment = Element.ALIGN_LEFT
                temp9.indentationLeft = 22.5f
                cell124.addElement(temp9)
                cell124.border = PdfPCell.NO_BORDER
                cell124.isUseAscender = true
                cell124.colspan = 2
                //cell17.setPadding(2f);
                cell124.horizontalAlignment = Element.ALIGN_TOP
                cell124.verticalAlignment = Element.ALIGN_TOP
                instrucciones9.addCell(cell124)
                document.add(instrucciones9)
            }
            addMediaEmptyLine(document, 1)

            //Trazabilidad
            run {
                val header15: PdfPTable = PdfPTable(4)
                //headerRow.setKeepTogether(true);
                header15.widthPercentage = 100F
                header15.setWidths(floatArrayOf(2.9f, 6.8f, 3.0f, 1.1f))
                header15.addCell(
                    createCell2(
                        "Fecha y hora de \n" +
                                "entrega recepción", 1, 1
                    )
                )
                header15.addCell(
                    createCell2(
                        "Nombre, institución y cargo o identificación de \n" +
                                "quien entrega", 1, 1
                    )
                )
                header15.addCell(createCell2("Actividad/propósito", 1, 1))
                header15.addCell(createCell2("Firma", 1, 1))
                val header16: PdfPTable = PdfPTable(4)
                //headerRow.setKeepTogether(true);
                header16.widthPercentage = 100F
                header16.setWidths(floatArrayOf(2.9f, 6.8f, 3.0f, 1.1f))
                val cell125: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell125.rowspan = 2
                header16.addCell(cell125)
                val cell126: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell126.rowspan = 1
                header16.addCell(cell126)
                val cell127: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell127.rowspan = 1
                header16.addCell(cell127)
                val cell128: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell128.rowspan = 2
                header16.addCell(cell128)
                val cell129: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell129.rowspan = 1
                header16.addCell(cell129)
                val cell130: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell130.rowspan = 1
                header16.addCell(cell130)
                document.add(header15)
                document.add(header16)
                val header17: PdfPTable = PdfPTable(4)
                //headerRow.setKeepTogether(true);
                header17.widthPercentage = 100F
                header17.setWidths(floatArrayOf(2.9f, 6.8f, 3.0f, 1.1f))
                header17.addCell(
                    createCell2(
                        "Lugar de \n" +
                                "permanencia", 1, 1
                    )
                )
                header17.addCell(
                    createCell2(
                        "Nombre, institución y cargo o identificación de \n" +
                                "quien entrega", 1, 1
                    )
                )
                header17.addCell(createCell2("Actividad/propósito", 1, 1))
                header17.addCell(createCell2("Firma", 1, 1))
                val header18: PdfPTable = PdfPTable(4)
                //headerRow.setKeepTogether(true)
                header18.widthPercentage = 100F
                header18.setWidths(floatArrayOf(2.9f, 6.8f, 3.0f, 1.1f))
                val cell131: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell131.rowspan = 2
                header18.addCell(cell131)
                val cell132: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell132.rowspan = 1
                header18.addCell(cell132)
                val cell133: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell133.rowspan = 1
                header18.addCell(cell133)
                val cell134: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell134.rowspan = 2
                header18.addCell(cell134)
                val cell135: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell135.rowspan = 1
                header18.addCell(cell135)
                val cell137: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell137.rowspan = 1
                header18.addCell(cell137)
                document.add(header17)
                document.add(header18)
                val header19: PdfPTable = PdfPTable(1)
                //headerRow.setKeepTogether(true);
                header19.widthPercentage = 100F
                header19.addCell(createCell2("Observaciones", 1, 1))
                val cell136: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                header19.addCell(cell136)
                val cell138: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell138.rowspan = 1
                header19.addCell(cell138)
                document.add(header19)
            }
            run {
                val header15: PdfPTable = PdfPTable(4)
                //headerRow.setKeepTogether(true);
                header15.widthPercentage = 100F
                header15.setWidths(floatArrayOf(2.9f, 6.8f, 3.0f, 1.1f))
                header15.addCell(
                    createCell2(
                        "Fecha y hora de \n" +
                                "entrega recepción", 1, 1
                    )
                )
                header15.addCell(
                    createCell2(
                        "Nombre, institución y cargo o identificación de \n" +
                                "quien entrega", 1, 1
                    )
                )
                header15.addCell(createCell2("Actividad/propósito", 1, 1))
                header15.addCell(createCell2("Firma", 1, 1))
                val header16: PdfPTable = PdfPTable(4)
                //headerRow.setKeepTogether(true);
                header16.widthPercentage = 100F
                header16.setWidths(floatArrayOf(2.9f, 6.8f, 3.0f, 1.1f))
                val cell125: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell125.rowspan = 2
                header16.addCell(cell125)
                val cell126: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell126.rowspan = 1
                header16.addCell(cell126)
                val cell127: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell127.rowspan = 1
                header16.addCell(cell127)
                val cell128: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell128.rowspan = 2
                header16.addCell(cell128)
                val cell129: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell129.rowspan = 1
                header16.addCell(cell129)
                val cell130: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell130.rowspan = 1
                header16.addCell(cell130)
                document.add(header15)
                document.add(header16)
                val header17: PdfPTable = PdfPTable(4)
                //headerRow.setKeepTogether(true);
                header17.widthPercentage = 100F
                header17.setWidths(floatArrayOf(2.9f, 6.8f, 3.0f, 1.1f))
                header17.addCell(
                    createCell2(
                        "Lugar de \n" +
                                "permanencia", 1, 1
                    )
                )
                header17.addCell(
                    createCell2(
                        "Nombre, institución y cargo o identificación de \n" +
                                "quien entrega", 1, 1
                    )
                )
                header17.addCell(createCell2("Actividad/propósito", 1, 1))
                header17.addCell(createCell2("Firma", 1, 1))
                val header18: PdfPTable = PdfPTable(4)
                //headerRow.setKeepTogether(true)
                header18.widthPercentage = 100F
                header18.setWidths(floatArrayOf(2.9f, 6.8f, 3.0f, 1.1f))
                val cell131: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell131.rowspan = 2
                header18.addCell(cell131)
                val cell132: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell132.rowspan = 1
                header18.addCell(cell132)
                val cell133: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell133.rowspan = 1
                header18.addCell(cell133)
                val cell134: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell134.rowspan = 2
                header18.addCell(cell134)
                val cell135: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell135.rowspan = 1
                header18.addCell(cell135)
                val cell137: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell137.rowspan = 1
                header18.addCell(cell137)
                document.add(header17)
                document.add(header18)
                val header19: PdfPTable = PdfPTable(1)
                //headerRow.setKeepTogether(true);
                header19.widthPercentage = 100F
                header19.addCell(createCell2("Observaciones", 1, 1))
                val cell136: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                header19.addCell(cell136)
                val cell138: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell138.rowspan = 1
                header19.addCell(cell138)
                document.add(header19)
            }
            run {
                val header15: PdfPTable = PdfPTable(4)
                //headerRow.setKeepTogether(true);
                header15.widthPercentage = 100F
                header15.setWidths(floatArrayOf(2.9f, 6.8f, 3.0f, 1.1f))
                header15.addCell(
                    createCell2(
                        "Fecha y hora de \n" +
                                "entrega recepción", 1, 1
                    )
                )
                header15.addCell(
                    createCell2(
                        "Nombre, institución y cargo o identificación de \n" +
                                "quien entrega", 1, 1
                    )
                )
                header15.addCell(createCell2("Actividad/propósito", 1, 1))
                header15.addCell(createCell2("Firma", 1, 1))
                val header16: PdfPTable = PdfPTable(4)
                //headerRow.setKeepTogether(true);
                header16.widthPercentage = 100F
                header16.setWidths(floatArrayOf(2.9f, 6.8f, 3.0f, 1.1f))
                val cell125: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell125.rowspan = 2
                header16.addCell(cell125)
                val cell126: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell126.rowspan = 1
                header16.addCell(cell126)
                val cell127: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell127.rowspan = 1
                header16.addCell(cell127)
                val cell128: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell128.rowspan = 2
                header16.addCell(cell128)
                val cell129: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell129.rowspan = 1
                header16.addCell(cell129)
                val cell130: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell130.rowspan = 1
                header16.addCell(cell130)
                document.add(header15)
                document.add(header16)
                val header17: PdfPTable = PdfPTable(4)
                //headerRow.setKeepTogether(true);
                header17.widthPercentage = 100F
                header17.setWidths(floatArrayOf(2.9f, 6.8f, 3.0f, 1.1f))
                header17.addCell(
                    createCell2(
                        "Lugar de \n" +
                                "permanencia", 1, 1
                    )
                )
                header17.addCell(
                    createCell2(
                        "Nombre, institución y cargo o identificación de \n" +
                                "quien entrega", 1, 1
                    )
                )
                header17.addCell(createCell2("Actividad/propósito", 1, 1))
                header17.addCell(createCell2("Firma", 1, 1))
                val header18: PdfPTable = PdfPTable(4)
                //headerRow.setKeepTogether(true)
                header18.widthPercentage = 100F
                header18.setWidths(floatArrayOf(2.9f, 6.8f, 3.0f, 1.1f))
                val cell131: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell131.rowspan = 2
                header18.addCell(cell131)
                val cell132: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell132.rowspan = 1
                header18.addCell(cell132)
                val cell133: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell133.rowspan = 1
                header18.addCell(cell133)
                val cell134: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell134.rowspan = 2
                header18.addCell(cell134)
                val cell135: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell135.rowspan = 1
                header18.addCell(cell135)
                val cell137: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell137.rowspan = 1
                header18.addCell(cell137)
                document.add(header17)
                document.add(header18)
                val header19: PdfPTable = PdfPTable(1)
                //headerRow.setKeepTogether(true);
                header19.widthPercentage = 100F
                header19.addCell(createCell2("Observaciones", 1, 1))
                val cell136: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                header19.addCell(cell136)
                val cell138: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell138.rowspan = 1
                header19.addCell(cell138)
                document.add(header19)
            }
            run {
                val header15: PdfPTable = PdfPTable(4)
                //headerRow.setKeepTogether(true);
                header15.widthPercentage = 100F
                header15.setWidths(floatArrayOf(2.9f, 6.8f, 3.0f, 1.1f))
                header15.addCell(
                    createCell2(
                        "Fecha y hora de \n" +
                                "entrega recepción", 1, 1
                    )
                )
                header15.addCell(
                    createCell2(
                        "Nombre, institución y cargo o identificación de \n" +
                                "quien entrega", 1, 1
                    )
                )
                header15.addCell(createCell2("Actividad/propósito", 1, 1))
                header15.addCell(createCell2("Firma", 1, 1))
                val header16: PdfPTable = PdfPTable(4)
                //headerRow.setKeepTogether(true);
                header16.widthPercentage = 100F
                header16.setWidths(floatArrayOf(2.9f, 6.8f, 3.0f, 1.1f))
                val cell125: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell125.rowspan = 2
                header16.addCell(cell125)
                val cell126: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell126.rowspan = 1
                header16.addCell(cell126)
                val cell127: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell127.rowspan = 1
                header16.addCell(cell127)
                val cell128: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell128.rowspan = 2
                header16.addCell(cell128)
                val cell129: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell129.rowspan = 1
                header16.addCell(cell129)
                val cell130: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell130.rowspan = 1
                header16.addCell(cell130)
                document.add(header15)
                document.add(header16)
                val header17: PdfPTable = PdfPTable(4)
                //headerRow.setKeepTogether(true);
                header17.widthPercentage = 100F
                header17.setWidths(floatArrayOf(2.9f, 6.8f, 3.0f, 1.1f))
                header17.addCell(
                    createCell2(
                        "Lugar de \n" +
                                "permanencia", 1, 1
                    )
                )
                header17.addCell(
                    createCell2(
                        "Nombre, institución y cargo o identificación de \n" +
                                "quien entrega", 1, 1
                    )
                )
                header17.addCell(createCell2("Actividad/propósito", 1, 1))
                header17.addCell(createCell2("Firma", 1, 1))
                val header18: PdfPTable = PdfPTable(4)
                //headerRow.setKeepTogether(true)
                header18.widthPercentage = 100F
                header18.setWidths(floatArrayOf(2.9f, 6.8f, 3.0f, 1.1f))
                val cell131: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell131.rowspan = 2
                header18.addCell(cell131)
                val cell132: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell132.rowspan = 1
                header18.addCell(cell132)
                val cell133: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell133.rowspan = 1
                header18.addCell(cell133)
                val cell134: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell134.rowspan = 2
                header18.addCell(cell134)
                val cell135: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell135.rowspan = 1
                header18.addCell(cell135)
                val cell137: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 8.0f, Font.BOLD, BaseColor.BLACK)))
                cell137.rowspan = 1
                header18.addCell(cell137)
                document.add(header17)
                document.add(header18)
                val header19: PdfPTable = PdfPTable(1)
                //headerRow.setKeepTogether(true);
                header19.widthPercentage = 100F
                header19.addCell(createCell2("Observaciones", 1, 1))
                val cell136: PdfPCell =
                    PdfPCell(Phrase(" ", Font(Font.FontFamily.HELVETICA, 18.0f, Font.BOLD, BaseColor.BLACK)))
                header19.addCell(cell136)
                document.add(header19)
            }
            addMediaEmptyLine(document, 1)


            ColumnText.showTextAligned(
                writer.directContent,
                Element.ALIGN_LEFT,
                Phrase(
                    "Se anexa registro de trazabilidad        Si [  ]     No   [  ]",
                    Font(Font.FontFamily.HELVETICA, 9.0f, Font.NORMAL, BaseColor.BLACK)
                ),50f,70f,0f
            )

            ColumnText.showTextAligned(
                writer.directContent,
                Element.ALIGN_LEFT,
                Phrase(
                    "Registro de Cadena de Custodia",
                    Font(Font.FontFamily.HELVETICA, 9.0f, Font.NORMAL, BaseColor.BLACK)
                ),50f,55f,0f
            )

            ColumnText.showTextAligned(
                writer.directContent,
                Element.ALIGN_RIGHT,
                Phrase(
                    "Pagina__de__",
                    Font(Font.FontFamily.HELVETICA, 9.0f, Font.NORMAL, BaseColor.BLACK)
                ),520f,55f,0f
            )


                    document.close()
                    writer.close()
                    outputStream.close()

                    Toast.makeText(requireContext(), "PDF guardado con éxito", Toast.LENGTH_LONG).show()

                    // Abrir el archivo PDF
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        setDataAndType(pdfUri, "application/pdf")
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    }

                    if (intent.resolveActivity(requireContext().packageManager) != null) {
                        startActivity(intent)
                    } else {
                        Toast.makeText(requireContext(), "No hay aplicaciones para abrir PDF", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "No se pudo abrir el flujo de salida", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error al crear el PDF: ${e.message}", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(requireContext(), "Error al crear el URI del archivo", Toast.LENGTH_LONG).show()
        }
    }

    fun createCell(content: String?, colspan: Int, rowspan: Int): PdfPCell {
        val cell = PdfPCell(Phrase(content, FONT_CELL))
        cell.colspan = colspan
        cell.rowspan = rowspan
        cell.isUseAscender = true
        cell.backgroundColor = Verde
        cell.setPadding(6f)
        cell.horizontalAlignment = Element.ALIGN_CENTER
        cell.verticalAlignment = Element.ALIGN_MIDDLE
        return cell
    }

    fun createCell2(content: String?, colspan: Int, rowspan: Int): PdfPCell {
        val cell = PdfPCell(Phrase(content, FONT_CELL2))
        cell.colspan = colspan
        cell.rowspan = rowspan
        cell.isUseAscender = true
        cell.backgroundColor = Verde
        cell.setPadding(6f)
        cell.horizontalAlignment = Element.ALIGN_CENTER
        cell.verticalAlignment = Element.ALIGN_MIDDLE
        return cell
    }

    private fun loadInterstitialAd() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            requireContext(),
            "ca-app-pub-1547415092159211/4321397732", // Reemplaza con tu ID de anuncio
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                    Log.d("AdMob", "Anuncio intersticial cargado correctamente")

                    // Configura un listener para el anuncio
                    interstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                        override fun onAdDismissedFullScreenContent() {
                            Log.d("AdMob", "Anuncio intersticial cerrado")
                            interstitialAd = null // Libera la referencia después de cerrar
                        }

                        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                            Log.e("AdMob", "Error al mostrar el anuncio: ${adError.message}")
                            interstitialAd = null
                        }
                    }
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    Log.e("AdMob", "Error al cargar el anuncio: ${loadAdError.message}")
                    interstitialAd = null
                }
            }
        )
    }




    private fun requestPermission() {
        // requesting permissions if not provided.
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.size > 0) {

                // after requesting permissions we are showing
                // users a toast message of permission granted.
                val writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED
                if (writeStorage && readStorage) {
                    Toast.makeText(requireActivity(), "Permission Granted..", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireActivity(), "Permission Denied.", Toast.LENGTH_SHORT).show()
                    //finish()
                }
            }
        }
    }

    private val currentTime: String
        get() = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
    private val todaysDate: String
        get() = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())

    companion object {
        private val PERMISSION_REQUEST_CODE = 200
        private val Verde: BaseColor = BaseColor(21, 109, 24)
        private val FONT_CELL: Font =
            Font(Font.FontFamily.HELVETICA, 9.5f, Font.BOLD, BaseColor.WHITE)
        private val FONT_CELL2: Font =
            Font(Font.FontFamily.HELVETICA, 8.5f, Font.BOLD, BaseColor.WHITE)

        @Throws(DocumentException::class)
        private fun addEmptyLine(document: Document, number: Int) {
            for (i in 0 until number) {
                document.add(Paragraph(" "))
            }
        }

        @Throws(DocumentException::class)
        private fun addMediaEmptyLine(document: Document, number: Int) {
            for (i in 0 until number) {
                document.add(
                    Paragraph(
                        " ",
                        Font(Font.FontFamily.HELVETICA, 6.0f, Font.NORMAL, BaseColor.BLACK)
                    )
                )
            }
        }
    }

}



