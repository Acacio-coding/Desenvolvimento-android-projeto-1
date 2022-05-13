package com.example.appcontabilidade.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appcontabilidade.types.ClientType

class ClientInfoViewModel : ViewModel() {

    val id: MutableLiveData<Int> = MutableLiveData(5)

    private val _name: MutableLiveData<String> = MutableLiveData("")

    val name: LiveData<String>
        get() = _name

    fun changeName(newName: String) {
        _name.value = newName
    }

    private val _address: MutableLiveData<String> = MutableLiveData("")

    val address: LiveData<String>
        get() = _address

    fun changeAddress(newAddress: String) {
        _address.value = newAddress
    }

    private val _number: MutableLiveData<String> = MutableLiveData("")

    val number: LiveData<String>
        get() = _number

    fun changeNumber(newNumber: String) {
        _number.value = newNumber
    }

    private val _debt: MutableLiveData<Double> = MutableLiveData(0.00)

    val debt: LiveData<Double>
        get() = _debt

    fun changeDebt(newDebt: Double) {
        _debt.value = newDebt
    }

    fun addClient(
        onInsertClient: (ClientType) -> Unit
    ) {
        val newClient = ClientType(
            id.value ?: return,
            name.value ?: return,
            address.value ?: return,
            number.value ?: return,
            debt.value ?: return
        )

        onInsertClient(newClient)

        var lastId = id.value ?: return
        lastId++
        id.value = lastId

        changeName("")
        changeAddress("")
        changeNumber("")
        changeDebt(0.00)
    }

    fun updateClient(
        id: Int,
        onUpdateClient: (ClientType) -> Unit
    ) {
        val client = ClientType(
            id,
            name.value ?: return,
            address.value ?: return,
            number.value ?: return,
            debt.value ?: return
        )

        onUpdateClient(client)

        changeName("")
        changeAddress("")
        changeNumber("")
        changeDebt(0.00)
    }


}