package com.example.hangman.screens

import androidx.annotation.DrawableRes
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.hangman.R
import com.example.hangman.navigation.AppScreens
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.core.content.res.TypedArrayUtils.getResourceId
import com.example.hangman.ui.theme.HangmanTheme

enum class ColorState {
    GRAY,
    GREEN,
    RED
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Game(navController: NavController, difficulty: String?) {
    Scaffold() { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            GameContent(navController, difficulty)
        }
    }
}

@Composable
fun GameContent(navController: NavController, difficulty: String?) {
    val letters: List<String> = ('A'..'Z').map { it.toString() }

    val Begginer: List<String> = listOf("DOG", "CAT", "RAT", "BAT", "")
    val User: List<String> = listOf("FISH", "DUCK", "GOAT", "SWAN", "BEAR")
    val Pretender: List<String> = listOf("EAGLE", "TIGER", "SHEEP", "KOALA", "HORSE")
    val Brave: List<String> = listOf("BEAVER", "PARROT", "TURTLE", "RABBIT", "JAGUAR")
    val Hero: List<String> = listOf("DOLPHIN", "PENGUIN", "ELEPHANT", "OCTOPUS", "WARLUS")

    var attempts by remember { mutableStateOf(0) }
    val lifeImageList = listOf(
        R.drawable.clean,
        R.drawable.herewego,
        R.drawable.isgoingbad,
        R.drawable.middledead,
        R.drawable.twostepstodeath,
        R.drawable.almostdead,
        R.drawable.dead
    )
    var lives = lifeImageList.size
    val currentLifeImageIndex = attempts.coerceIn(0, lifeImageList.size - 1)



    fun getRandomWord(difficulty: String?): String {
        val selectedList = when (difficulty) {
            "Begginer" -> Begginer
            "User" -> User
            "Pretender" -> Pretender
            "Brave" -> Brave
            "Hero" -> Hero
            else -> throw IllegalArgumentException("Invalid difficulty")
        }

        return selectedList.random()
    }
    val hiddenWord = remember(difficulty) { getRandomWord(difficulty) }

    val correctGuesses = remember { mutableStateListOf<Boolean>() }
    correctGuesses.addAll(hiddenWord.map { false })


    val allLettersGuessed = correctGuesses.count { it } == hiddenWord.length

    if (allLettersGuessed) {
        val att = attempts.toString()
        navController.navigate(
            route = "${AppScreens.FinalScreen.route}/You Win!/$difficulty/$att"
        )
    } else if (attempts == 6) {
        val att = attempts.toString()
        navController.navigate(
            route = "${AppScreens.FinalScreen.route}/Game Over/$difficulty/$att"
        )
    }

    Column {
        difficulty?.let { Text("Difficulty : $it") }
        //Text(text = hiddenWord)


        Box(
            modifier = Modifier
                .height(200.dp)
        ) {
            Image(
                modifier = Modifier
                    .size(400.dp),
                painter = painterResource(lifeImageList[currentLifeImageIndex]),
                contentDescription = "attempts = $attempts and $lives lives"
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            for (i in 0..6) {
                LetterContainer(letter = letters[i], hiddenWord, attempts, correctGuesses) { attempts++ }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            for (i in 7..13) {
                LetterContainer(letter = letters[i], hiddenWord, attempts, correctGuesses) { attempts++ }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            for (i in 14..20) {
                LetterContainer(letter = letters[i], hiddenWord, attempts, correctGuesses) { attempts++ }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            for (i in 21..25) {
                LetterContainer(letter = letters[i], hiddenWord, attempts, correctGuesses) { attempts++ }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            for (i in hiddenWord.indices) {
                HiddenLetterContainer(hiddenWord[i].toString(), correctGuesses[i])
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Attempts: $attempts", fontSize = 20.sp)
        }

    }


}
@Composable
fun LetterContainer(
    letter: String,
    gameWord: String,
    attempts: Int,
    correctGuesses: MutableList<Boolean>,
    onAttempt: () -> Unit
) {
    var clicked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .border(
                width = 2.dp,
                color = Color.LightGray,
                shape = MaterialTheme.shapes.extraSmall
            )
            .clickable {
                if (!clicked) {
                    clicked = true

                    if (gameWord.contains(letter)) {
                        for (i in gameWord.indices) {
                            if (gameWord[i].toString() == letter) {
                                correctGuesses[i] = true
                            }
                        }
                    } else {
                        onAttempt()
                    }
                }
            }
    ) {
        val colorState = when {
            !clicked -> Color(0xFFFAF8F3)
            gameWord.contains(letter) -> Color.Green
            else -> Color.Red
        }

        Box(
            modifier = Modifier
                .size(40.dp)
                .background(colorState)
                .padding(all = 4.dp)
        ) {
            Text(
                text = letter,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(20.dp)
            )
        }
    }
}

@Composable
fun HiddenLetterContainer(letter: String, isCorrectGuess: Boolean) {
    var letterState by remember { mutableStateOf("_") }


    if (isCorrectGuess) {
        letterState = letter
    }

    Column(
        modifier = Modifier
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .padding(all = 4.dp)
        ) {
            Text(
                text = letterState,
                modifier = Modifier
                    .align(Alignment.Center),
                fontSize = 30.sp
            )
        }
    }
}