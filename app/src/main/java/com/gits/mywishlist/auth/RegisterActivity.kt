package com.gits.mywishlist.auth

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.gits.mywishlist.R
import com.gits.mywishlist.databinding.ActivityRegisterBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class RegisterActivity : AppCompatActivity() {
    private val viewModel by viewModels<RegisterViewModel>()

    private lateinit var binding: ActivityRegisterBinding

    private var mLoading: LoadingDialogContent? = null

    private var attachmentBottomSheetShown: Boolean = false
    private val RC_PHOTO_PICKER = 1

    //    lateinit var file: File
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mLoading = LoadingDialogContent(this)

        binding.addImage.setOnClickListener {
            attachmentIntent("image/*", RC_PHOTO_PICKER)
            binding.tAttach.setText("Gambar Dipilih")
        }

        binding.buttonRegister.setOnClickListener {
            val namaUser = binding.textFieldName.editText?.text.toString().trim()
            val emailUser = binding.textFieldEmail.editText?.text.toString().trim()
            val usernameUser = binding.textFieldUsername.editText?.text.toString().trim()
            val passwordUser = binding.textFieldPassword.editText?.text.toString().trim()

            if (validate(namaUser, emailUser, usernameUser, passwordUser)) {
                if (selectedImageUri == null) {
                    return@setOnClickListener
                }

                val parcelFileDescriptor =
                    contentResolver.openFileDescriptor(selectedImageUri!!, "r", null)
                        ?: return@setOnClickListener

                val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
                val file = File(cacheDir, contentResolver.getFileName(selectedImageUri!!))
                val outputStream = FileOutputStream(file)
                inputStream.copyTo(outputStream)

                val requestFile: RequestBody =
                    RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)

                print("fileName : ${file.name}")

                val body = MultipartBody.Part.createFormData("photo", file.name, requestFile)
                viewModel.pushAttach(body)

                viewModel.pushAttach.observe(this, { resources ->
                    binding.tAttach.setText("fileName : ${file.name}")
                    when (resources.code) {
                        Status.LOADING -> {
                            mLoading?.show()
                        }
                        Status.SUCCESS -> {
                            val data = resources.data?.data?.id
                            if (data != null) {
                                val bodyForm = RegisterBody(
                                    namaUser,
                                    emailUser,
                                    usernameUser,
                                    passwordUser,
                                    id_image = data
                                )
                                binding.textFieldTextImage.setText("fileName : ${file.name}")
                                val api_key = viewModel.getApiKey()
                                if (api_key != null && api_key.isNotEmpty()) {
                                    viewModel.setTicket(api_key, bodyForm)
                                }
                                finish()
                            }
                        }
                        Status.ERROR -> {
                            mLoading?.hide()
                            makeToast("Gambar gagal ditambahkan")
                        }
                    }
                })

            }

        }
    }

    override fun onStart() {
        super.onStart()

        setSupportActionBar(binding.toolbar)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayShowTitleEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun attachmentIntent(type: String, requestCode: Int) {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = type
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
        startActivityForResult(
            Intent.createChooser(intent, "Complete action using"), requestCode
        ).also {
            if (attachmentBottomSheetShown == true) {
                binding.addLayout.visibility = View.GONE
                attachmentBottomSheetShown = false
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK) {
            selectedImageUri = data?.data

//            val file: File = Uri.parse(File(selectedImage).toString())

        }
    }

    private fun uploadImage() {

    }
}