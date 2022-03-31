package io.raveerocks.gdgfinder.view

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.raveerocks.gdgfinder.network.GDGService
import io.raveerocks.gdgfinder.network.GdgChapter
import io.raveerocks.gdgfinder.network.GdgChapterRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException


class GdgListViewModel : ViewModel() {

    val gdgList: LiveData<List<GdgChapter>>
        get() = _gdgList
    val regionList: LiveData<List<String>>
        get() = _regionList
    val showNeedLocation: LiveData<Boolean>
        get() = _showNeedLocation

    private val repository = GdgChapterRepository(GDGService.getGDGService())


    private var filter = FilterHolder()
    private var currentJob: Job? = null
    private val _gdgList = MutableLiveData<List<GdgChapter>>()
    private val _regionList = MutableLiveData<List<String>>()
    private val _showNeedLocation = MutableLiveData<Boolean>()


    init {
        onQueryChanged()
        viewModelScope.launch {
            _showNeedLocation.value = !repository.isFullyInitialized
        }
    }

    private fun onQueryChanged() {
        currentJob?.cancel()
        currentJob = viewModelScope.launch {
            try {
                _gdgList.value = repository.getChaptersForFilter(filter.currentValue)
                repository.getFilters().let {
                    if (it != _regionList.value) {
                        _regionList.value = it
                    }
                }
            } catch (e: IOException) {
                _gdgList.value = listOf()
            }
        }
    }

    fun onLocationUpdated(location: Location) {
        viewModelScope.launch {
            repository.onLocationChanged(location)
            onQueryChanged()
        }
    }

    fun onFilterChanged(filter: String, isChecked: Boolean) {
        if (this.filter.update(filter, isChecked)) {
            onQueryChanged()
        }
    }

    private class FilterHolder {
        var currentValue: String? = null
            private set

        fun update(changedFilter: String, isChecked: Boolean): Boolean {
            if (isChecked) {
                currentValue = changedFilter
                return true
            } else if (currentValue == changedFilter) {
                currentValue = null
                return true
            }
            return false
        }
    }

}

