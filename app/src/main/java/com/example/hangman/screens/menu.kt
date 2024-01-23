package com.example.hangman.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.hangman.R
import com.example.hangman.navigation.AppScreens
import kotlinx.coroutines.delay
import androidx.constraintlayout.compose.ConstraintLayout


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Menu(navController: NavController) {


    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Hangman - Menu")
                }
            )
        },
        bottomBar = {
            ClickableBottomAppBarWithHiddenHelper()

        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
           MenuContent(navController)
        }
    }
}

@Composable
fun MenuContent(navController: NavController){
    var option = "Easy"
    var presses by remember { mutableStateOf(0) }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFFFAF8F3))
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize(0.5f)
                //.border(border = BorderStroke(width = 4.dp, color = Color.Red))
            ){
                Box(modifier = Modifier
                    .fillMaxWidth()){
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "logo",
                        modifier = Modifier
                            .fillMaxWidth() // Ocupa todo el ancho disponible
                            .aspectRatio(1f) // Relación de aspecto cuadrada
                            .scale(1f))
                }
                MyDropDownMenu(navController)

            }

        }
    }



@Composable
fun MyDropDownMenu(navController: NavController) {
    var selectedText by remember { mutableStateOf("Difficulty") }
    var expanded by remember { mutableStateOf(false) }
    var showHiddenBox by remember {mutableStateOf(false)}
    val difficulties = listOf("Begginer", "User", "Pretender", "Brave", "Hero")
    val updatedShowHiddenBox = rememberUpdatedState(showHiddenBox)
   Box {
       OutlinedTextField(
           value = selectedText,
           onValueChange = { selectedText = it },
           enabled = false,
           readOnly = true,
           modifier = Modifier
               .clickable { expanded = true }
               .fillMaxWidth()
       )

       DropdownMenu(
           expanded = expanded,
           onDismissRequest = { expanded = false },
           modifier = Modifier.fillMaxWidth(0.5f)
       ) {
           difficulties.forEach { difficultySelected ->
               DropdownMenuItem(text = { Text(text = difficultySelected) }, onClick = {
                   expanded = false
                   selectedText = difficultySelected


               })
           }
       }
   }
    ElevatedButton(modifier = Modifier
        .fillMaxWidth()
        .background(color = Color.Transparent)
        ,
        shape = RoundedCornerShape(10),
        onClick = {

            if (selectedText=="Difficulty"){
                showHiddenBox = true

            }else{
                navController.navigate(route = AppScreens.Game.route + "/$selectedText" )
            }

        }
    ) {
        Text(text = "Play")
    }
    if (updatedShowHiddenBox.value) {
        LaunchedEffect(true) {
            delay(4000L)
            showHiddenBox = false
        }
        HiddenBox()
    }

}

@Composable
fun HiddenBox(){
    Box {
        Text("Select an option!")
    }
}
@Composable
fun ClickableBottomAppBarWithHiddenHelper() {
    var helperVisible by remember { mutableStateOf(false) }

    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { helperVisible = !helperVisible },
            textAlign = TextAlign.Center,
            text = "Help",
            color = LocalContentColor.current,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }

    // Muestra el cuadro si helperVisible es true
    if (helperVisible) {
        LaunchedEffect(true) {
            // Inicia un temporizador para ocultar el cuadro después de 5 segundos
            delay(5000)
            helperVisible = false
        }

        HiddenHelperBox()
    }
}

        @Composable
        fun HiddenHelperBox() {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    item {
                        Text("1. Select the Difficulty:", color = Color.Black, fontSize = 24.sp)
                        Text(
                            "- Choose the game difficulty from \"Beginner,\" \"User,\" \"Pretender,\" \"Brave,\" or \"Hero.\"",
                            color = Color.Black,
                            fontSize = 14.sp
                        )
                    }

                    item {
                        Text("2. Hidden Word:", color = Color.Black, fontSize = 24.sp)
                        Text(
                            "- We'll select a hidden word based on the chosen difficulty.",
                            color = Color.Black,
                            fontSize = 14.sp
                        )
                        Text(
                            "- The word is displayed as underscores (_) representing each letter.",
                            color = Color.Black,
                            fontSize = 14.sp
                        )
                    }

                    item {
                        Text("3. Guess the Letters:", color = Color.Black, fontSize = 24.sp)
                        Text(
                            "- Click on the available letters to guess them.",
                            color = Color.Black,
                            fontSize = 14.sp
                        )
                        Text(
                            "- If the letter is in the word, all instances in the word will be revealed.",
                            color = Color.Black,
                            fontSize = 14.sp
                        )
                        Text(
                            "- If the letter is not in the word, you'll lose an attempt.",
                            color = Color.Black,
                            fontSize = 14.sp
                        )
                    }

                    item {
                        Text("4. Avoid the Hangman:", color = Color.Black, fontSize = 24.sp)
                        Text(
                            "- An image of the hangman reflects your remaining attempts.",
                            color = Color.Black,
                            fontSize = 14.sp
                        )
                        Text(
                            "- Try to guess all the letters before running out of attempts.",
                            color = Color.Black,
                            fontSize = 14.sp
                        )
                    }

                    item {
                        Text("5. Victory or Defeat:", color = Color.Black, fontSize = 24.sp)
                        Text(
                            "- You win if you guess all the letters before running out of attempts.",
                            color = Color.Black,
                            fontSize = 14.sp
                        )
                        Text(
                            "- You lose if you run out of attempts before guessing all the letters.",
                            color = Color.Black,
                            fontSize = 14.sp
                        )
                    }

                    item {
                        Text(
                            "Good luck and have fun playing Hangman!",
                            color = Color.Black,
                            fontSize = 24.sp
                        )
                    }
                }
            }
        }

