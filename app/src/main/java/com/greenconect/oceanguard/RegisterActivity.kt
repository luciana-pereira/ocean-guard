package com.greenconect.oceanguard

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream
import java.util.UUID

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference

    private lateinit var emailRegister: EditText
    private lateinit var passwordRegister: EditText
    private lateinit var buttonRegister: Button
    private lateinit var linkLogin: TextView
    private lateinit var userName: EditText
    private lateinit var surname: EditText
    private lateinit var radioButtonFemale: RadioButton
    private lateinit var radioButtonMale: RadioButton
    private lateinit var checkBoxPrivacyPolicy: CheckBox
    private lateinit var dateOfBirth: EditText
    private lateinit var imageView: ImageView
    private lateinit var imageUrl: String
    private val PICK_IMAGE_REQUEST = 1
    private var filePath: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initializeComponentsFirebase()
        initializeComponents()
        setupTextWatchers()
        setupListeners()
        disableButton()
        validateFields()
    }

    private fun initializeComponentsFirebase() {
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()
        storageReference = storage.reference
    }

    private fun initializeComponents() {
        emailRegister = findViewById(R.id.emailRegister)
        passwordRegister = findViewById(R.id.passwordRegister)
        buttonRegister = findViewById(R.id.buttonRegister)
        linkLogin = findViewById(R.id.linkLogin)
        userName = findViewById(R.id.editTextName)
        surname = findViewById(R.id.editTextSobrenome)
        radioButtonFemale = findViewById(R.id.radioButtonFemale)
        radioButtonMale = findViewById(R.id.radioButtonMale)
        checkBoxPrivacyPolicy = findViewById(R.id.checkBoxPrivacyPolicy)
        dateOfBirth = findViewById(R.id.editTextDate)
        imageView = findViewById(R.id.imageView)

        val buttonChooseImage: Button = findViewById(R.id.buttonChooseImage)
        buttonChooseImage.setOnClickListener {
            showFileChooser()
        }
    }

    private fun showFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            var filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                imageView.setImageBitmap(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun uploadImage() {
        if (filePath != null) {
            val ref: StorageReference = storageReference.child("images/" + UUID.randomUUID().toString())
            val stream = ByteArrayOutputStream()
            val bitmap = (imageView.drawable).toBitmap()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            val byteArray = stream.toByteArray()

            ref.putBytes(byteArray)
                .addOnSuccessListener { task ->
                    ref.downloadUrl
                        .addOnSuccessListener { downloadUri ->
                            val downloadUrl = downloadUri.toString()
                            saveDataToFirestore(downloadUrl)
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(
                                this,
                                "Erro ao obter URL de download: ${e.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                            e.printStackTrace()
                        }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(
                        this,
                        "Erro no upload da imagem: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    e.printStackTrace()
                }
        }
    }

    private fun saveDataToFirestore(imageUrl: String) {
        val user  = hashMapOf(
            "imageUrl" to imageUrl,
            "name" to userName.text.toString(),
            "surname" to surname.text.toString(),
            "email" to emailRegister.text.toString(),
            "password" to passwordRegister.text.toString(),
            "gender" to if (radioButtonFemale.isChecked) "Feminino" else "Masculino",
            "dateOfBirth" to dateOfBirth.text.toString()
        )

        val userId = auth.currentUser?.uid

        if (userId != null) {
            firestore.collection("users").document(userId)
                .set(user)
                .addOnSuccessListener {
                    Log.d(ContentValues.TAG, "DocumentSnapshot added")
                    Toast.makeText(
                        this,
                        "Cadastro realizado com sucesso!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .addOnFailureListener { e ->
                    Log.w(ContentValues.TAG, "Error adding document", e)
                    Toast.makeText(
                        this,
                        "Erro ao realizar cadastro: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }

    private fun validateFields() {
        val isEmailValid = emailRegister.text.isNotEmpty()
        val isPasswordValid = passwordRegister.text.isNotEmpty()
        val isPasswordValidContent = passwordRegister.text.toString()
        val isUserName = userName.text.isNotEmpty()
        val isSurname = surname.text.isNotEmpty()
        val isFemale = radioButtonFemale.isChecked
        val isMale = radioButtonMale.isChecked
        val isDateOfBirthDate = dateOfBirth.text.isNotEmpty()
        val isPrivacyPolicy = checkBoxPrivacyPolicy.isChecked

        buttonRegister.isEnabled = isUserName &&
                isSurname &&
                isEmailValid &&
                (isPasswordValid && isPasswordValidContent.length >= 6) &&
                (isFemale || isMale) &&
                isDateOfBirthDate &&
                isPrivacyPolicy
    }

    private fun setupTextWatchers() {
        emailRegister.addTextChangedListener(watcher)
        passwordRegister.addTextChangedListener(watcher)
        userName.addTextChangedListener(watcher)
        surname.addTextChangedListener(watcher)
        dateOfBirth.addTextChangedListener(watcher)
    }

    private fun setupListeners() {
        buttonRegister.setOnClickListener {
            val email = emailRegister.text.toString()
            val password = passwordRegister.text.toString()
            uploadImage()
            registerUser(email, password)
        }

        linkLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun disableButton() {
        buttonRegister.isEnabled = false
    }

    private fun getRealPathFromURI(contentUri: Uri): String {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(contentUri, proj, null, null, null)
        val columnIndex = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val path = cursor.getString(columnIndex)
        cursor.close()
        return path
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid
                    if (userId != null) {

                        val user = hashMapOf(
                                "imageUrl" to "",
                                "name" to userName.text.toString(),
                                "surname" to surname.text.toString(),
                                "email" to emailRegister.text.toString(),
                                "password" to passwordRegister.text.toString(),
                                "gender" to if (radioButtonFemale.isChecked) "Feminino" else "Masculino",
                                "dateOfBirth" to dateOfBirth.text.toString()
                        )

                        firestore.collection("user").document(userId)
                            .set(user)
                            .addOnSuccessListener {
                                Log.d(ContentValues.TAG, "DocumentSnapshot added")
                                Toast.makeText(
                                    this,
                                    "Cadastro realizado com sucesso!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            .addOnFailureListener { e ->
                                Log.w(ContentValues.TAG, "Error adding document", e)
                                Toast.makeText(
                                    this,
                                    "Erro ao realizar cadastro: ${e.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    } else {
                        Log.e("SignUpActivity", "Erro ao criar usuário: ${task.exception?.message}")
                    }
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        baseContext, "Erro ao criar usuário: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private val watcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

        override fun afterTextChanged(editable: Editable) {
            validateFields()
        }
    }
}
