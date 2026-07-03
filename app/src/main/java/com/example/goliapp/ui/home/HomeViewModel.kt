package com.example.goliapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.goliapp.domain.model.League
import com.example.goliapp.domain.model.Match
import com.example.goliapp.repository.MatchRepository
import com.example.goliapp.repository.StandingsRepository
import com.example.goliapp.utils.Constants
import com.example.goliapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val matchRepository: MatchRepository
) : ViewModel() {

    private val _matches = MutableStateFlow<Resource<List<Match>>>(Resource.Loading)
    val matches: StateFlow<Resource<List<Match>>> = _matches

    private val _selectedLeagueId = MutableStateFlow<Int?>(null)
    val selectedLeagueId: StateFlow<Int?> = _selectedLeagueId

    val leagues = listOf(
        League(0, "All", "", "", null, Constants.CURRENT_SEASON),
        League(Constants.PREMIER_LEAGUE_ID, "Premier League", "", "England", null, Constants.CURRENT_SEASON),
        League(Constants.LA_LIGA_ID, "La Liga", "", "Spain", null, Constants.CURRENT_SEASON),
        League(Constants.SERIE_A_ID, "Serie A", "", "Italy", null, Constants.CURRENT_SEASON),
        League(Constants.BUNDESLIGA_ID, "Bundesliga", "", "Germany", null, Constants.CURRENT_SEASON),
        League(Constants.LIGUE_1_ID, "Ligue 1", "", "France", null, Constants.CURRENT_SEASON),
        League(Constants.CHAMPIONS_LEAGUE_ID, "UCL", "", "Europe", null, Constants.CURRENT_SEASON),
    )

    init {
        loadTodayMatches()
    }

    fun loadTodayMatches() {
        viewModelScope.launch {
            matchRepository.getTodayMatches().collect { result ->
                _matches.value = result
            }
        }
    }

    fun selectLeague(leagueId: Int?) {
        _selectedLeagueId.value = leagueId
        if (leagueId == null || leagueId == 0) {
            loadTodayMatches()
        } else {
            viewModelScope.launch {
                matchRepository.getMatchesByLeague(leagueId).collect { result ->
                    _matches.value = result
                }
            }
        }
    }
}