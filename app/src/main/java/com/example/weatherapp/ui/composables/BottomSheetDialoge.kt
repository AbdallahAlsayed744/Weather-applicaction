package com.example.weatherapp.ui.composables


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet

import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.domain.entity.Hour
import com.example.weatherapp.ui.theme.poppins_regular
import com.example.weatherapp.viewmodel.CurrentWeatherCiewmodel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetDialoge(vmodel: CurrentWeatherCiewmodel = hiltViewModel(),onDismiss: () -> Unit) {
    val forcaststate by vmodel.Forcastweathercurrent.collectAsState()


    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(190.dp),
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        forcaststate?.let { Bottomsheetrowlazy(hour = it.hour) }
    }


}

@Composable
fun Bottomsheetitem(hour:Hour){
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceAround) {
        Text(text = "${hour.temp_c.toInt()}°C", fontSize = 15.sp, fontFamily = poppins_regular,modifier = Modifier.padding(start = 39.dp))
        SubcomposeAsyncImage(model = ImageRequest.Builder(LocalContext.current).data("https:${hour.condition.icon}").build(), contentDescription = "",modifier = Modifier.padding(start = 39.dp).width(40.dp).height(40.dp))
        Text(text = hour.time, fontSize = 12.sp, fontFamily = poppins_regular,modifier = Modifier.padding(start = 14.dp))




    }

}

@Composable
fun Bottomsheetrowlazy(hour:List<Hour>){
    LazyRow(contentPadding = PaddingValues(5.dp), modifier = Modifier.padding(horizontal = 16.dp)){

        items(hour){
            Bottomsheetitem(hour = it)

        }
    }

}
