package com.surrus.peopleinspace.persondetails

import com.surrus.common.remote.Assignment
import com.surrus.common.repository.PeopleInSpaceRepositoryInterface
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import net.smarttuner.kaffeeverde.lifecycle.ViewModel
import net.smarttuner.kaffeeverde.lifecycle.viewModelScope

data class PersonDetailsUiState(
    val person: Assignment? = null,
    val isLoading: Boolean = false,
    val userMessage: Int? = null
)

class PersonDetailsViewModel(
    personName: String,
    peopleInSpaceRepository: PeopleInSpaceRepositoryInterface,
) : ViewModel() {

    val uiState = peopleInSpaceRepository.fetchPeopleAsFlow()
        .map { list ->
            val person = list.find { it.name == personName }
            PersonDetailsUiState(person)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PersonDetailsUiState(isLoading = true))

}
