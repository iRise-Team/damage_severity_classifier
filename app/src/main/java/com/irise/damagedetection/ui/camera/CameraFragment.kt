package com.irise.damagedetection.ui.camera

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.irise.damagedetection.R
import com.irise.damagedetection.databinding.FragmentCameraBinding
import com.irise.damagedetection.online.IApi
import com.irise.damagedetection.online.UploadRequestBody
import com.irise.damagedetection.online.UploadResponse
import com.irise.damagedetection.util.Util.getFileName
import com.irise.damagedetection.util.Util.result
import com.irise.damagedetection.util.Util.snackbar
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

@Suppress("DEPRECATION")
class CameraFragment : Fragment(), UploadRequestBody.UploadCallback {
    private lateinit var cameraViewModel: CameraViewModel
    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!
    private var mUri: Uri? = null
    private var selectedImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        cameraViewModel =
            ViewModelProvider(this).get(CameraViewModel::class.java)
        result = resources.getString(R.string.result)
        _binding = FragmentCameraBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textView: TextView = binding.label
        cameraViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })

        binding.apply {
            btnGallery.setOnClickListener {
                openImageChooser()
            }

            btnCamera.setOnClickListener {
                openCamera()
            }

            buttonUpload.setOnClickListener {
                uploadImage()
            }
        }
    }

    private fun openCamera() {
        val capturedImage = File(requireContext().externalCacheDir, "Photo.jpg")
        if (capturedImage.exists()) {
            capturedImage.delete()
        }
        capturedImage.createNewFile()
        mUri = if (Build.VERSION.SDK_INT >= 24) {
            FileProvider.getUriForFile(
                requireContext(), "com.irise.damagedetection.provider",
                capturedImage
            )
        } else {
            Uri.fromFile(capturedImage)
        }

        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri)
        startActivityForResult(intent, REQUEST_CODE_CAMERA)
    }

    private fun openImageChooser() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it, REQUEST_CODE_PICK_IMAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_PICK_IMAGE -> {
                    selectedImageUri = data?.data
                    binding.imageView.setImageURI(selectedImageUri)
                }
                REQUEST_CODE_CAMERA -> {
                    binding.imageView.setImageDrawable(null)
                    selectedImageUri = mUri
                    binding.imageView.setImageURI(selectedImageUri)
                }
            }
        }
    }

    private fun uploadImage() {
        if (selectedImageUri == null) {
            binding.root.snackbar(resources.getString(R.string.select_image_first))
            return
        }
        val parcelFileDescriptor =
            context?.contentResolver?.openFileDescriptor(selectedImageUri!!, "r", null) ?: return

        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val file = File(
            requireContext().cacheDir,
            requireContext().contentResolver.getFileName(selectedImageUri!!)
        )
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)

        binding.progressBar.progress = 0
        val body = UploadRequestBody(file, "image", this)
        IApi().uploadImage(
            MultipartBody.Part.createFormData(
                "image",
                file.name,
                body
            ),
            RequestBody.create(MediaType.parse("multipart/form-data"), "json")
        ).enqueue(object : Callback<UploadResponse> {
            override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                binding.root.snackbar(t.message!!)
                binding.progressBar.progress = 0
            }

            override fun onResponse(
                call: Call<UploadResponse>,
                response: Response<UploadResponse>
            ) {
                response.body()?.let {
                    result = it.toString().subSequence(21, 22).toString()
                    result = when (result) {
                        "0" -> {
                            resources.getString(R.string.none)
                        }
                        "1" -> {
                            resources.getString(R.string.mild)
                        }
                        else -> {
                            resources.getString(R.string.severe)
                        }
                    }
                    binding.label.text = result
                    binding.progressBar.progress = 100
                }
            }
        })
    }


    override fun onProgressUpdate(percentage: Int) {
        binding.progressBar.progress = percentage
    }

    companion object {
        const val REQUEST_CODE_PICK_IMAGE = 101
        const val REQUEST_CODE_CAMERA = 1
    }

    override fun onDestroyView() {
        super.onDestroyView()
        result = resources.getString(R.string.result)
        _binding = null
    }
}