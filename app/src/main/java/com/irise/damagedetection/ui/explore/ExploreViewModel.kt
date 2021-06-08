package com.irise.damagedetection.ui.explore

import androidx.lifecycle.ViewModel
import com.irise.damagedetection.dummy.DataDummy
import com.irise.damagedetection.dummy.Dummy

class ExploreViewModel : ViewModel() {
    fun getLists(): List<Dummy> = DataDummy.getData()
}