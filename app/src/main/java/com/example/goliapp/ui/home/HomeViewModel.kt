package com.example.goliapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goliapp.domain.model.League
import com.example.goliapp.domain.model.Match
import com.example.goliapp.repository.FavouritesRepository
import com.example.goliapp.repository.FootballRepository
import com.example.goliapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: FootballRepository,
    private val favouritesRepository: FavouritesRepository
) : ViewModel() {

    private val _matches = MutableLiveData<Resource<List<Match>>>()
    val matches: LiveData<Resource<List<Match>>> = _matches

    private val _leagues = MutableLiveData<Resource<List<League>>>()
    val leagues: LiveData<Resource<List<League>>> = _leagues

    private var selectedLeagueId: Int? = null

    init {
        loadTodayMatches()
        loadLeagues()
    }

    fun loadTodayMatches() {
        repository.getTodayMatches(selectedLeagueId)
            .onEach { result -> _matches.value = result }
            .launchIn(viewModelScope)
    }

    fun loadLeagues() {
        repository.getLeagues()
            .onEach { result -> _leagues.value = result }
            .launchIn(viewModelScope)
    }

    fun onLeagueSelected(leagueId: Int?) {
        selectedLeagueId = leagueId
        loadTodayMatches()
    }

    fun refresh() {
        loadTodayMatches()
    }

    fun toggleFavouriteQuick(match: Match) {
        viewModelScope.launch {
            if (favouritesRepository.isFavourite(match.id)) {
                favouritesRepository.removeFavourite(match.id)
            } else {
                favouritesRepository.addFavourite(match)
            }
        }
    }
}