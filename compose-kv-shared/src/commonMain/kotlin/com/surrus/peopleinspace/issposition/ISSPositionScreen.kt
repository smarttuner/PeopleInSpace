package com.surrus.peopleinspace.issposition

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import com.surrus.common.remote.IssPosition
import net.smarttuner.kaffeeverde.compose.ui.platform.LocalLifecycleOwner
import net.smarttuner.kaffeeverde.lifecycle.compose.ExperimentalLifecycleComposeApi
import net.smarttuner.kaffeeverde.lifecycle.compose.collectAsStateWithLifecycle


const val ISSPositionMapTag = "ISSPositionMap"

val IssPositionKey = SemanticsPropertyKey<IssPosition>("IssPosition")
var SemanticsPropertyReceiver.observedIssPosition by IssPositionKey

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun ISSPositionScreen(viewModel: ISSPositionViewModel) {

    val lifecycleOwner = LocalLifecycleOwner.current


//    val context = LocalContext.current
//    val map = remember { MapView(context) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("ISS Position") })
        }
    ) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {

            Box {
                Text("Not implemented yet")
//                AndroidView({ map }, modifier = Modifier
//                        .fillMaxHeight().testTag(ISSPositionMapTag)
//                        .semantics { observedIssPosition = uiState.position }
//                ){ map ->
//                    map.zoomController.setVisibility(CustomZoomButtonsController.Visibility.SHOW_AND_FADEOUT)
//                    map.setMultiTouchControls(true)
//
//                    val mapController = map.controller
//                    mapController.setZoom(5.0)
//                    val issPositionPoint = GeoPoint(uiState.position.latitude, uiState.position.longitude)
//                    mapController.setCenter(issPositionPoint)
//
//                    map.overlays.clear()
//                    val stationMarker = Marker(map)
//                    stationMarker.position = issPositionPoint
//                    stationMarker.title = "ISS"
//                    map.overlays.add(stationMarker)
//                }
            }
        }
    }
}
