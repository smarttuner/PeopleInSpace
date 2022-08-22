package com.surrus.peopleinspace.persondetails

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.surrus.common.repository.PeopleInSpaceRepositoryInterface
import net.smarttuner.kaffeeverde.core.annotation.SuppressLint
import net.smarttuner.kaffeeverde.lifecycle.compose.ExperimentalLifecycleComposeApi
import net.smarttuner.kaffeeverde.lifecycle.compose.collectAsStateWithLifecycle
import net.smarttuner.kaffeeverde.lifecycle.ui.viewModel

@OptIn(ExperimentalLifecycleComposeApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun PersonDetailsScreen(
    viewModel: PersonDetailsViewModel,
    popBack: () -> Unit) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(uiState.person?.name ?: "") },
                navigationIcon = {
                    IconButton(onClick = { popBack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            uiState.person?.let { person ->
                Text(person.name, style = MaterialTheme.typography.h4)
                Spacer(modifier = Modifier.size(12.dp))

                val imageUrl = person.personImageUrl ?: ""
                if (imageUrl.isNotEmpty()) {
//                    AsyncImage(
//                        model = person.personImageUrl,
//                        contentDescription = person.name,
//                        contentScale = ContentScale.Fit,
//                        modifier = Modifier.size(240.dp)
//                    )
                }
                Spacer(modifier = Modifier.size(24.dp))

                val bio = person.personBio ?: ""
                Text(bio, style = MaterialTheme.typography.body1)
            }
        }
    }
}


