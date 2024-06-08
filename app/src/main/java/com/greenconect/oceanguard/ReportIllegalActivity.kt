package com.greenconect.oceanguard

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class ReportIllegalActivity : AppCompatActivity() {

    private lateinit var editTextDescription: EditText
    private lateinit var buttonAddImage: Button
    private lateinit var buttonSendEmail: Button
    private lateinit var imageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_illegal)

        initializeComponents()
        setupListeners()
    }

    private fun initializeComponents() {
        editTextDescription = findViewById(R.id.editTextDescription)
        buttonAddImage = findViewById(R.id.buttonAddImage)
        buttonSendEmail = findViewById(R.id.buttonSendEmail)
    }

    private fun setupListeners() {
        buttonAddImage.setOnClickListener {
            // Aqui vou terminar de iplementar lógica para anexar imagem,
            // podemos reutilizar o código de captura de imagem.
            dispatchTakePictureIntent()
        }

        buttonSendEmail.setOnClickListener {
            sendReportEmail()
        }
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_PICK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK) {
            data?.data?.let {
                imageUri = it
            }
        }
    }

    private fun sendReportEmail() {
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        val userEmail = user?.email

        val description = editTextDescription.text.toString()

        val subject = "Denúncia de Atividade de Pesca Ilegal Utilizando Dados da Global Fishing Watch do App Ocean Guard"
        val body = "Descrição: $description"

        val emailIntent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_EMAIL, arrayOf("linhaverde.sede@ibama.gov.br"))
            putExtra(Intent.EXTRA_CC, arrayOf(userEmail))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
            putExtra(Intent.EXTRA_STREAM, imageUri)
        }

        startActivity(Intent.createChooser(emailIntent, "Enviar email de denúncia"))
    }

    companion object {
        private const val REQUEST_IMAGE_PICK = 1
    }

}