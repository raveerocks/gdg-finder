package io.raveerocks.gdgfinder.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    val navigateToSearch: LiveData<Boolean>
        get() = _navigateToSearch

    private val _navigateToSearch = MutableLiveData<Boolean>()

    fun onFabClicked() {
        _navigateToSearch.value = true
    }

    fun onNavigatedToSearch() {
        _navigateToSearch.value = false
    }
}