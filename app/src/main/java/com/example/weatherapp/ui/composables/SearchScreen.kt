package com.example.weatherapp.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.IconCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.domain.entity.Current
import com.example.domain.entity.Day
import com.example.domain.entity.Location
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.poppins_bold
import com.example.weatherapp.ui.theme.poppins_regular
import com.example.weatherapp.viewmodel.CurrentWeatherCiewmodel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController:NavController,model: CurrentWeatherCiewmodel = hiltViewModel()) {
    val locactionstate by model.currentweatherlocation.collectAsState()
    val currentstate by model.currentweathercurrent.collectAsState()
    val forcaststate by model.Forcastweathercurrent.collectAsState()
    val rain2 by model.itWillRain.collectAsState()


    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        colorResource(
                            id = R.color.color1
                        ), colorResource(id = R.color.color2), colorResource(id = R.color.color3)
                    )
                )
            )
    ) {
      IconButton(onClick = { navController.navigateUp() }, modifier = Modifier.padding(end = 330.dp).fillMaxWidth()) {
          Icon(Icons.Outlined.ArrowBack, contentDescription ="", tint = Color.White )

      }

        Image(painter = painterResource(id = R.drawable.moon), contentDescription ="", modifier = Modifier
            .padding(top = 20.dp)
            .width(240.dp)
            .height(100.dp), contentScale = ContentScale.Crop)
        MySearch(modifier = Modifier.padding(top = 17.dp, start = 20.dp, end = 20.dp),hint = "Search...."){
            if (it.isEmpty()){
                model.clearWeatherData()
            }
            else {
                model.getcurrentweather("05dbc44ab35d4509b46104404240605",it)
                model.getForcastweather("05dbc44ab35d4509b46104404240605",it)


            }
            }
        locactionstate?.let { currentstate?.let { it1 -> forcaststate?.let { it2 -> SearchBody(modifier = Modifier.padding(top = 15.dp), location = it, current = it1, day = it2.day) } } }

    }

    }






@Composable
fun SearchBody(modifier: Modifier,location: Location, current: Current,day: Day) {
    var rain by remember { mutableStateOf(day.daily_will_it_rain) }
    Column {


        Row(
            modifier = modifier
                .padding(horizontal = 15.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = location.name,
                color = Color.White,
                fontFamily = poppins_bold,
                fontSize = 20.sp,
                modifier = Modifier
            )
            Text(
                text = "${current.temp_c.toInt()}°C",
                color = Color.White,
                fontFamily = poppins_bold,
                fontSize = 20.sp,
                modifier = Modifier
            )
        }

        Row(
            modifier = modifier
                .padding(horizontal = 15.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = location.country,
                color = Color.White,
                fontFamily = poppins_regular,
                fontSize = 15.sp,
                modifier = Modifier
            )

            SubcomposeAsyncImage(model = ImageRequest.Builder(LocalContext.current)
                .data("https:${current.condition.icon}").build(),
                contentDescription = "icon",
                modifier = Modifier
                    .width(40.dp)
                    .height(35.dp),
                contentScale = ContentScale.Crop,
                loading = {
                    CircularProgressIndicator(modifier = Modifier.size(40.dp))
                }
            )


        }


            val composition by rememberLottieComposition(
                if (day.daily_will_it_rain == 1) {
                    LottieCompositionSpec.RawRes(R.raw.rain)
                } else {
                    LottieCompositionSpec.RawRes(R.raw.sun)
                }
            )
            val animationState = animateLottieCompositionAsState(
                composition = composition,
                iterations = LottieConstants.IterateForever
            )

            LottieAnimation(
                composition = composition,
                progress = { animationState.progress },
                modifier = Modifier.fillMaxHeight(),
                contentScale = ContentScale.FillHeight
            )


    }
}


@Composable
fun MySearch(modifier: Modifier = Modifier, hint: String,onSearch:(String)->Unit) {

    var text by rememberSaveable {
        mutableStateOf("")
    }

    Box(modifier = modifier.padding(horizontal = 10.dp, vertical = 15.dp)) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(4.dp, RoundedCornerShape(10.dp))
                .clip(AbsoluteRoundedCornerShape(7.dp)),
            value = text,
            placeholder = {
                Text(text = hint)
            },
            onValueChange = {
                text = it
                onSearch(it)
            },
            maxLines = 1,
            prefix = {
                Icon(Icons.Outlined.Search, contentDescription = "search")
            },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                Color.Gray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }

}

@Composable
fun Runbody(){

}