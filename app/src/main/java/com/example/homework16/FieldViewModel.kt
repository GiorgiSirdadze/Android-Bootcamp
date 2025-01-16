package com.example.homework16

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.serialization.json.Json

class FieldViewModel : ViewModel() {

    private val _fieldGroups = MutableLiveData<List<List<Field>>>()
    val fieldGroups: LiveData<List<List<Field>>> = _fieldGroups

    private val _inputData = MutableLiveData<MutableMap<String, String>>(mutableMapOf())
    val inputData: LiveData<MutableMap<String, String>> = _inputData

    init {
        fetchFieldGroups()
    }

    private fun fetchFieldGroups() {

        val json = """
            [
                [
                    {"field_id":1,"hint":"UserName","field_type":"input","keyboard":"text","required":false,"is_active":true,"icon":"https://jemala.png"},
                    {"field_id":2,"hint":"Email","field_type":"input","keyboard":"text","required":true,"is_active":true,"icon":"https://jemala.png"},
                    {"field_id":3,"hint":"Phone","field_type":"input","keyboard":"number","required":true,"is_active":true,"icon":"https://jemala.png"}
                ],
                [
                    {"field_id":4,"hint":"FullName","field_type":"input","keyboard":"text","required":true,"is_active":true,"icon":"https://jemala.png"},
                    {"field_id":89,"hint":"Birthday","field_type":"input","required":false,"is_active":true,"icon":"https://jemala.png"},
                    {"field_id":898,"hint":"Gender","field_type":"chooser","required":false,"is_active":true,"icon":"https://jemala.png"}
                ]
            ]
        """
        val groups = Json.decodeFromString<List<List<Field>>>(json)
        _fieldGroups.value = groups
    }

    fun saveFieldData(hint: String, value: String) {
        _inputData.value?.put(hint, value)  // Save the input data in the map
        _inputData.value = _inputData.value // Trigger LiveData update
    }
}
