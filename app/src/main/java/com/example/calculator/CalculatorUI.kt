package com.example.calculator

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun CalculatorUI(viewModel: CalculatorViewmodel = CalculatorViewmodel()) {

    var equationText = viewModel.equationText.observeAsState("")
    var resultText = viewModel.resultText.observeAsState("0")
    var flag by viewModel.flag

    val textSize2 by animateIntAsState(
        targetValue = if (flag) 40 else 20,
        animationSpec = tween(200),
        label = "animatedText"
    )
    val textSize by animateIntAsState(
        targetValue = if (flag) 25 else 40,
        animationSpec = tween(200),
        label = "animatedText"
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = equationText.value?:"",

                modifier = Modifier
                    .align(Alignment.End)
                    .padding(16.dp),
                fontSize = textSize.sp,
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    color = Color(0xFF625D5D)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))
            Spacer(modifier = Modifier.height(16.dp))
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = resultText.value?:"0",

                modifier = Modifier
                    .align(Alignment.End)
                    .padding(16.dp),
                fontSize = textSize2.sp,
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    color = Color.White,
                )
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier
                    .weight(1f)
                    .padding(3.dp),
                verticalArrangement = Arrangement.Bottom,

                ) {
                items(ButtonList) {
                CalculatorBtn(symbol = it, viewModel = viewModel)
                }
            }
        }
    }
}

val ButtonList = listOf(
    "AC", "%", "Del", "/",
    "7", "8", "9", "*",
    "4", "5", "6", "-",
    "1", "2", "3", "+",
    "00", "0", ".", "="
)


@Composable
fun CalculatorBtn(symbol: String,viewModel: CalculatorViewmodel) {

    var isClicked by remember {
        mutableStateOf(false)
    }
    val animateSize by animateFloatAsState(
        targetValue = if (isClicked) 1.05f else 1.1f,
        animationSpec = spring(),
        label = "animatedBtn"
    )

    LaunchedEffect(isClicked) {
        if(isClicked){
            delay(100)
            isClicked = !isClicked
        }
    }

    Box() {
        FloatingActionButton(
            onClick = {
                isClicked = !isClicked
                viewModel.onBtnClick(symbol)
            } ,
            modifier = Modifier
                .graphicsLayer {
                    scaleX = animateSize
                    scaleY = animateSize
                }
                .size(75.dp)
                .padding(5.dp),
            shape = CircleShape,
            containerColor = getColor(symbol),
            elevation = FloatingActionButtonDefaults.elevation(10.dp)
        ) {
            Text(text = symbol, fontSize = 24.sp, color = Color.White)
        }
    }
}

fun getColor(symbol: String): Color {
    if (symbol == "AC" || symbol == "%" || symbol == "Del" || symbol == "=") {
        return Color(0xFFD79300)
    } else if (symbol == "/" || symbol == "*" || symbol == "-" || symbol == "+") {
        return Color(0xFF736C6C)
    }
    return Color(0xFF2A2626)
}


@Preview
@Composable
fun CalculatorPreview() {
    CalculatorUI()
}

