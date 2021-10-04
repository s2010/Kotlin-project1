package com.bash.shoestore.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber


enum class SaveState {
    SAVE,
    NOT_SAVED
}

class ShoeViewModel: ViewModel() {

    private var _shoeList = MutableLiveData<MutableList<Shoe>>()
    val shoeList : LiveData<MutableList<Shoe>>
        get() = _shoeList

    private var _saveState = MutableLiveData<SaveState>()
    val saveState : LiveData<SaveState>
        get() = _saveState

    init {
        Timber.i("inside init" )

        _shoeList.value = mutableListOf()
        addShoe("Nike sneaker", 5.0, "Nike", "cool")
        _saveState.value = SaveState.NOT_SAVED
    }

    fun addShoe(name: String, size: Double, company: String, description: String) {
        Timber.i("calling addShoe")
        _shoeList.value?.add(Shoe(name, size, company, description))
        Timber.i("saving ...%s", _shoeList.value?.joinToString())
    }

    fun onEventSave(name: String, size: String, company: String, description: String) {
        Log.i("ShoeViewModel", "onEventSave name $name company $company")
        var sizeDouble : Double = 0.0
        try {
            sizeDouble = size.toDouble()
        } catch (e: NumberFormatException) {
            Timber.i("Invalid size entered")
        }
        addShoe(name, sizeDouble, company, description)
        _saveState.value = SaveState.SAVE
    }
    fun onEventSaveComplete() {
        _saveState.value = SaveState.NOT_SAVED
    }

}