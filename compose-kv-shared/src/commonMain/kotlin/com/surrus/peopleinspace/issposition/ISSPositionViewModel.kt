package com.surrus.peopleinspace.issposition

import com.surrus.common.remote.IssPosition
import com.surrus.common.repository.PeopleInSpaceRepositoryInterface
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import net.smarttuner.kaffeeverde.lifecycle.ViewModel
import net.smarttuner.kaffeeverde.lifecycle.viewModelScope

data class ISSPositionUiState(
    val position: IssPosition = IssPosition(0.0,0.0),
    val isLoading: Boolean = false,
    val userMessage: Int? = null
)

class ISSPositionViewModel(
    peopleInSpaceRepository: PeopleInSpaceRepositoryInterface
) : ViewModel() {

    val uiState = peopleInSpaceRepository.pollISSPosition()
        .map {
            ISSPositionUiState(it)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ISSPositionUiState(isLoading = true))

}
