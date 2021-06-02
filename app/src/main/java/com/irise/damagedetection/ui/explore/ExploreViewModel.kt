package com.irise.damagedetection.ui.explore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.irise.damagedetection.dummy.DataDummy
import com.irise.damagedetection.dummy.Dummy

class ExploreViewModel : ViewModel() {
    fun getList() : List<Dummy> = DataDummy.getData()
}