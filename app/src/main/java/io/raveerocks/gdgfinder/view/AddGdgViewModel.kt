package io.raveerocks.gdgfinder.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddGdgViewModel : ViewModel() {

    val showSnackBarEvent: LiveData<Boolean?>
        get() = _showSnackBarEvent

    private var _showSnackBarEvent = MutableLiveData<Boolean?>()

    fun onSubmitApplication() {
        _showSnackBarEvent.value = true
    }

    fun doneShowingSnackBar() {
        _showSnackBarEvent.value = null
    }

}
