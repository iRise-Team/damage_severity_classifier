package com.irise.damagedetection.ui.camera

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.irise.damagedetection.util.Util.result

class CameraViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = result
    }

    val text: LiveData<String> = _text
}