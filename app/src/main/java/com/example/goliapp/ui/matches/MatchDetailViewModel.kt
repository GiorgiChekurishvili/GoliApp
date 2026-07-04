package com.example.goliapp.ui.matches

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goliapp.domain.model.Match
import com.example.goliapp.repository.FavouritesRepository
import com.example.goliapp.repository.FootballRepository
import com.example.goliapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchDetailViewModel @Inject constructor(
    private val repository: FootballRepository,
    private val favouritesRepository: FavouritesRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val matchId: Int = savedStateHandle.get<Int>("matchId") ?: -1

    private val _match = MutableLiveData<Resource<Match>>()
    val match: LiveData<Resource<Match>> = _match

    private val _isFavourite = MutableLiveData<Boolean>()
    val isFavourite: LiveData<Boolean> = _isFavourite

    init {
        loadMatch()
        checkFavouriteStatus()
    }

    private fun loadMatch() {
        repository.getMatchDetail(matchId)
            .onEach { result -> _match.value = result }
            .launchIn(viewModelScope)
    }

    private fun checkFavouriteStatus() {
        viewModelScope.launch {
            _isFavourite.value = favouritesRepository.isFavourite(matchId).first()
        }
    }

    fun toggleFavourite() {
        viewModelScope.launch {
            val current = _match.value
            if (current is Resource.Success && current.data != null) {
                if (_isFavourite.value == true) {
                    favouritesRepository.removeFavourite(matchId)
                    _isFavourite.value = false
                } else {
                    favouritesRepository.addFavourite(current.data)
                    _isFavourite.value = true
                }
            }
        }
    }
}
