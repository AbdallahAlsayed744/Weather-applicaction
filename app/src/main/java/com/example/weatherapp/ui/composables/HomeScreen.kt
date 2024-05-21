package com.example.weatherapp.ui.composables

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.data.utilis.Scree
import com.example.domain.entity.Astro
import com.example.domain.entity.Current
import com.example.domain.entity.Day
import com.example.domain.entity.Location
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.poppins_bold
import com.example.weatherapp.ui.theme.poppins_regular
import com.example.weatherapp.viewmodel.CurrentWeatherCiewmodel
import com.google.android.gms.location.LocationServices

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController,vmodel: CurrentWeatherCiewmodel= hiltViewModel()) {
    val locationstate by vmodel.currentweatherlocation.collectAsState()
    val curentstate by vmodel.currentweathercurrent.collectAsState()
    val forcaststate by vmodel.Forcastweathercurrent.collectAsState()
    val loading by vmodel.loading.collectAsState()

    var isNetworkAvailable by remember { mutableStateOf(true) }

    var retryAttempted by remember { mutableStateOf(false) }

    isNetworkAvailable = isNetworkAvailable(context = LocalContext.current)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        colorResource(
                            id = R.color.color1
                        ), colorResource(id = R.color.color2), colorResource(id = R.color.color3)
                    )
                )
            ),

        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Row (horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
            .padding(horizontal = 60.dp, vertical = 10.dp)
            .fillMaxWidth()){
            Text(text = "Search", color = Color.White, fontFamily = poppins_bold,modifier = Modifier)
            IconButton(
                onClick = {
                    navController.navigate(Scree.Searchscreen.route)
                }, modifier = Modifier

                    .width(30.dp)
                    .height(30.dp)) {
                Icon(painter = painterResource(id = R.drawable.baseline_search_24), tint = Color.White, contentDescription = "")

            }

        }

        Image(
            painter = painterResource(id = R.drawable.weather),
            contentDescription = "image",
            modifier = Modifier
                .width(200.dp)
                .height(180.dp),
            contentScale = ContentScale.Crop
        )



        if (loading) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier

                .fillMaxSize()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .height(35.dp)
                        .width(35.dp), color = Color.White
                )

            }
        }

        LocationScreen()



        if (isNetworkAvailable){

            locationstate?.let { curentstate?.let { it1 -> forcaststate?.let { it2 -> HomeScreenbody(location = it, current = it1, days = it2.day, astro = it2.astro) } } }

        }
        else{
            RetrySection(error = "No Internet Avilable") {
                retryAttempted = true

            }
        }

    }
    if (retryAttempted) {
        LaunchedEffect(retryAttempted) {
            retryAttempted = false
        }

    }


}

@Composable
fun Card(current:Current,days: Day,astro: Astro){
    androidx.compose.material3.Card (modifier = Modifier
        .padding(horizontal = 25.dp)
        .fillMaxWidth()
        .height(235.dp), colors = CardDefaults.cardColors(colorResource(id = R.color.color1)), shape = RoundedCornerShape(10.dp)
    ){
        Column(modifier = Modifier.padding(top = 10.dp)) {

            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(start = 8.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.humudity),
                        contentDescription = "",
                        modifier = Modifier
                            .width(50.dp)
                            .height(50.dp),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = "Humidity",
                        color = Color.White,
                        fontFamily = poppins_regular,
                        fontSize = 15.sp
                    )
                    Text(
                        text = current.humidity.toString() + "%",
                        color = Color.White,
                        fontFamily = poppins_regular,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(start = 18.dp)
                    )
                }
                Column() {
                    Image(
                        painter = painterResource(id = R.drawable.wind),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(start = 15.dp)
                            .width(50.dp)
                            .height(50.dp),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = "Wind Speed",
                        color = Color.White,
                        fontFamily = poppins_regular,
                        fontSize = 15.sp
                    )
                    Text(
                        text = current.wind_kph.toString() + " km/h",
                        color = Color.White,
                        fontFamily = poppins_regular,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(start = 9.dp)
                    )
                }

                Column() {
                    Image(
                        painter = painterResource(id = R.drawable.pressure),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .width(50.dp)
                            .height(50.dp),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = "Pressure",
                        color = Color.White,
                        fontFamily = poppins_regular,
                        fontSize = 15.sp
                    )
                    Text(
                        text = current.pressure_mb.toString() + " mb",
                        color = Color.White,
                        fontFamily = poppins_regular,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

            }
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(start = 8.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.sunrisee),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .width(50.dp)
                            .height(50.dp),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = "Sun Rise",
                        color = Color.White,
                        fontFamily = poppins_regular,
                        fontSize = 15.sp
                    )
                    Text(
                        text = astro.sunrise,
                        color = Color.White,
                        fontFamily = poppins_regular,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(start = 0.dp)
                    )
                }
                Column() {
                    Image(
                        painter = painterResource(id = R.drawable.sunsset),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(start = 27.dp)
                            .width(50.dp)
                            .height(50.dp),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = "Sun Set",
                        modifier = Modifier.padding(start = 22.dp),
                        color = Color.White,
                        fontFamily = poppins_regular,
                        fontSize = 15.sp
                    )
                    Text(
                        text = astro.sunset,
                        color = Color.White,
                        fontFamily = poppins_regular,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(start = 18.dp)
                    )
                }

                Column() {
                    Image(
                        painter = painterResource(id = R.drawable.moonrise),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(start = 15.dp)
                            .width(50.dp)
                            .height(50.dp),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = "Moon Rise",
                        color = Color.White,
                        fontFamily = poppins_regular,
                        fontSize = 15.sp
                    )
                    Text(
                        text = astro.moonrise,
                        color = Color.White,
                        fontFamily = poppins_regular,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }


            }
        }

    }
}

@Composable
fun HomeScreenbody(location: Location,current:Current,days:Day,astro: Astro) {
    Text(text = "${current.temp_c.toInt()}°C", fontWeight = FontWeight.Bold, color = Color.White, fontFamily = poppins_bold, fontSize = 33.sp)
    Row (modifier = Modifier
        .padding(start = 73.dp)
        .fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {

        Text(text = location.name, fontWeight = FontWeight.Bold, color = Color.White, fontFamily = poppins_bold, fontSize = 22.sp)
        SubcomposeAsyncImage(model =ImageRequest.Builder(LocalContext.current).data("https:${current.condition.icon}").build(), contentDescription = "icon"
            , modifier = Modifier
                .width(70.dp)
                .height(55.dp),
            contentScale = ContentScale.Crop, loading = {
                CircularProgressIndicator(modifier = Modifier.size(40.dp))
            }
        )
    }
    Text(text = location.localtime, color = Color.White, fontFamily = poppins_regular, fontSize = 20.sp)
    Text(text = "${current.condition.text} Sky", color = Color.White, fontFamily = poppins_regular, fontSize = 20.sp)
    Row (modifier = Modifier
        .padding(horizontal = 50.dp)
        .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        Text(text ="Max ${days.maxtemp_c.toInt()}°C", color = Color.White, fontFamily = poppins_regular, fontSize = 17.sp)
        Text(text ="Min ${days.mintemp_c.toInt()}°C", color = Color.White, fontFamily = poppins_regular, fontSize = 17.sp)
    }
    Spacer(modifier = Modifier.height(20.dp))
    Card(current,days,astro)
    var icon by remember { mutableStateOf(false) }
    Text(text = "Today Forecast", fontFamily = poppins_bold, color = Color.White, fontSize = 16.sp, modifier = Modifier.padding(top = 10.dp))
    Image(painter = painterResource(id = if (icon) R.drawable.baseline_arrow_circle_down_24 else R.drawable.baseline_arrow_circle_up_24), contentDescription ="", modifier = Modifier
        .padding(top = 15.dp)
        .width(30.dp)
        .height(30.dp)
        .clickable {
            icon = !icon

        }

    )
    if (icon) {
        BottomSheetDialoge() {
            icon = false
        }
    }







}

@Composable
fun LocationScreen(vmodel: CurrentWeatherCiewmodel= hiltViewModel()) {
    val context = LocalContext.current
    var location by remember { mutableStateOf("Your location") }

    // Create a permission launcher
    val requestPermissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isGranted: Boolean ->
                if (isGranted) {
                    // Permission granted, update the location
                    getCurrentLocation(context) { lat, long ->
                        location = "Latitude: $lat, Longitude: $long"
                        vmodel.getcurrentweather("05dbc44ab35d4509b46104404240605","$lat,$long")
                        vmodel.getForcastweather("05dbc44ab35d4509b46104404240605","$lat,$long")



                    }
                }
            })

    LaunchedEffect(true) {
        // Request location permission when the screen is first displayed
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    Column(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


    }
}

private fun hasLocationPermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}

@SuppressLint("MissingPermission")
private fun getCurrentLocation(context: Context, callback: (Double, Double) -> Unit) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    fusedLocationClient.lastLocation
        .addOnSuccessListener { location ->
            if (location != null) {
                val lat = location.latitude
                val long = location.longitude
                callback(lat, long)
            }
        }
        .addOnFailureListener { exception ->
            // Handle location retrieval failure
            exception.printStackTrace()
        }
}

@Composable
fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities != null &&
                (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
    } else {
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}


@Composable
fun RetrySection(
    error: String,
    onRetry: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize(),verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        val composition by rememberLottieComposition(
                LottieCompositionSpec.RawRes(R.raw.wifi)
        )
        val animationState = animateLottieCompositionAsState(
            composition = composition,
            iterations = LottieConstants.IterateForever
        )

        LottieAnimation(
            composition = composition,
            progress = { animationState.progress },
            modifier = Modifier.padding(bottom = 90.dp).height(160.dp)

        )
        Button(
            onClick = { onRetry() },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue,
                contentColor = colorResource(id = R.color.white)
            )
        ) {
            Text(text = "Retry")
        }
    }
}


