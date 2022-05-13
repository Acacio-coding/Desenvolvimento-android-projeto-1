package com.example.appcontabilidade.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appcontabilidade.types.ClientType

class ClientListViewModel : ViewModel() {

    private val _clientList: MutableLiveData<List<ClientType>> = MutableLiveData(
        listOf(
            ClientType(
                0,
                "Teste0",
                "Teste",
                "(99) 9 9999-9999",
                0.00
            ),
            ClientType(
                1,
                "Teste1",
                "Teste",
                "(99) 9 9999-9999",
                0.00
            ),
            ClientType(
                2,
                "Teste2",
                "Teste",
                "(99) 9 9999-9999",
                0.00
            ),
            ClientType(
                3,
                "Teste3",
                "Teste",
                "(99) 9 9999-9999",
                0.00
            ),
            ClientType(
                4,
                "Teste4",
                "Teste",
                "(99) 9 9999-9999",
                0.00
            )
        )
    )

    private val _filterBy: MutableLiveData<String> = MutableLiveData("")

    val filterBy: LiveData<String>
        get() = _filterBy

    fun changeFilterBy(term: String) {
        _filterBy.value = term
    }

    val clientList: LiveData<List<ClientType>>
        get() {
            return if (_filterBy.value == "")
                _clientList
            else {
                val list : List<ClientType> = _clientList.value?.filter { client ->
                    client.name.contains(filterBy.value ?: "")
                } ?: listOf()

                MutableLiveData(list)
            }
        }

    fun getClient(id: Int): ClientType {
        _clientList.value?.forEach { client ->
            if (id == client.id)
                return client
        }
        return ClientType(
            -1,
            "",
            "",
            "",
            0.00
        )
    }

    fun addClient(client: ClientType) {
        val list: MutableList<ClientType> = _clientList.value?.toMutableList() ?: return

        list.add(client)

        _clientList.value = list
    }

    fun updateClient(updatedClient: ClientType) {
        var pos = -1

        _clientList.value?.forEachIndexed { index, client ->
            if (updatedClient.id == client.id)
                pos = index
        }

        val list: MutableList<ClientType> = _clientList.value?.toMutableList() ?: return
        list.removeAt(pos)
        list.add(pos, updatedClient)

        _clientList.value = list
    }

   fun deleteClient(id: Int) {
       var pos = -1

       _clientList.value?.forEachIndexed { index, client ->
           if (id == client.id)
               pos = index
       }

       val list: MutableList<ClientType> = _clientList.value?.toMutableList() ?: return
       list.removeAt(pos)
       _clientList.value = list
   }
}