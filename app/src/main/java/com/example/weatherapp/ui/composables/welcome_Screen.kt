package com.example.weatherapp.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.data.local.datastore.on_obordingevent
import com.example.data.utilis.Scree
import com.example.weatherapp.R
import com.example.weatherapp.ui.theme.poppins_bold

@Composable
fun WelcomeScreen(event:(on_obordingevent)->Unit,navController: NavController){
    Column(modifier = Modifier
        .fillMaxSize()
        .background(
            brush = Brush.linearGradient(
                colors = listOf(
                    colorResource(
                        id = R.color.color1
                    ), colorResource(id = R.color.color2), colorResource(id = R.color.color3)
                )
            )
        ), verticalArrangement = Arrangement.Top, horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {

      Image(painter = painterResource(id = R.drawable.weather), contentDescription = "image", modifier = Modifier
          .width(320.dp)
          .height(280.dp), contentScale = ContentScale.Crop)


        MyText()

        MyButton(text = "Get Start") {

            event(on_obordingevent.saveappentry)
            navController.navigate(Scree.Home.route)

        }

    }
}


@Composable
fun MyText(){
    Column (modifier = Modifier.padding(top = 70.dp)){
        Text(text = "Weather" ,color = colorResource(id = R.color.white), modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center ,fontSize = 30.sp, fontFamily = poppins_bold)
        Text(text = "ForeCasts" ,color = colorResource(id = R.color.forecast_color), modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center ,fontSize = 35.sp, fontFamily = poppins_bold)

    }
}

@Composable
fun MyButton(text:String,onclick:()->Unit){
    Button(onClick = { onclick() }, modifier = Modifier.padding(top = 120.dp).fillMaxWidth(0.6f),colors = ButtonDefaults.buttonColors(containerColor = colorResource(
        id = R.color.forecast_color
    ), contentColor = colorResource(
        id = R.color.white
    ))) {
        Text(text = text, modifier = Modifier.padding(top = 8.dp),color = colorResource(id = R.color.white), fontSize = 20.sp, fontFamily = poppins_bold)

    }

}