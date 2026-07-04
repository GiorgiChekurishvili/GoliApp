package com.example.goliapp.ui.standings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goliapp.domain.model.Standing
import com.example.goliapp.repository.StandingsRepository
import com.example.goliapp.utils.Constants
import com.example.goliapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class StandingsViewModel @Inject constructor(
    private val repository: StandingsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _standings = MutableLiveData<Resource<List<Standing>>>()
    private val _leagueId = MutableStateFlow(savedStateHandle.get<Int>("leagueId") ?: 39)
    val standings: LiveData<Resource<List<Standing>>> = _standings
    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val standingsState = _leagueId.flatMapLatest { leagueId ->
        repository.getStandings(leagueId, Constants.CURRENT_SEASON)
    }.stateIn(viewModelScope, SharingStarted.Lazily, Resource.Loading)

    private var currentLeagueId: Int = DEFAULT_LEAGUE_ID

    init {
        loadStandings(currentLeagueId)
    }

    fun loadStandings(leagueId: Int) {
        currentLeagueId = leagueId
        repository.getStandings(leagueId)
            .onEach { result -> _standings.value = result }
            .launchIn(viewModelScope)
    }
    fun updateLeague(newLeagueId: Int) {
        _leagueId.value = newLeagueId
    }
    fun refresh() = loadStandings(currentLeagueId)

    companion object {
        const val DEFAULT_LEAGUE_ID = 39
    }
}