package com.example.hangman.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hangman.navigation.AppScreens


@Composable
fun FinalScreen(navController: NavController,result: String?, difficulty: String?, attempts: String?){

    Scaffold() { innerPadding ->
        Column(
            modifier = androidx.compose.ui.Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
           FinalScreenContent(navController, result,difficulty,attempts)
        }
    }
}


@Composable
fun FinalScreenContent(navController: NavController, result: String?,difficulty: String?,attempts: String?){

  Column( modifier = Modifier.fillMaxSize(),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally) {

      if (result=="Game Over"){
          tittleText(message = "Game Over")

      }else if(result == "You Win!"){
          tittleText(message ="Congratulations!\n" +
                  " You Win!\n" +
                  "With $attempts fails" )

      }
      ElevatedButton(modifier = Modifier
          .width(200.dp)
          .background(color = Color.Transparent)
          ,
          shape = RoundedCornerShape(10),
          onClick = {
              navController.navigate(route = AppScreens.Game.route + "/$difficulty" )
          }
      ) {
          Text(text = "Play again")
      }
      ElevatedButton(modifier = Modifier
          .width(200.dp)
          .background(color = Color.Transparent),

          shape = RoundedCornerShape(10),
          onClick = {
              navController.navigate(route = AppScreens.Menu.route  )
          }
      ) {
          Text(text = "Menu")
      }
  }

}

@Composable
fun tittleText(message: String){
    Text(text = message, fontSize = 30.sp, textAlign = TextAlign.Center,modifier = Modifier.padding(12.dp))
}