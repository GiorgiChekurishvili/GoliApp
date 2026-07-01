package com.example.goliapp.ui.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.goliapp.domain.model.Match
import com.example.goliapp.repository.FavouritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val favouritesRepository: FavouritesRepository
) : ViewModel() {

    val favourites: LiveData<List<Match>> = favouritesRepository.getAllFavourites().asLiveData()

    fun removeFavourite(match: Match) {
        viewModelScope.launch {
            favouritesRepository.removeFavourite(match.id)
        }
    }
}
