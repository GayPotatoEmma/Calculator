package com.gaypotatoemma.calc

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.objecthunter.exp4j.ExpressionBuilder
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import java.text.DecimalFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        setContent {
            val context = LocalContext.current
            val isSystemInDarkTheme = isSystemInDarkTheme()

            val colorScheme = if (isSystemInDarkTheme) {
                dynamicDarkColorScheme(context)
            } else {
                dynamicLightColorScheme(context)
            }

            val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
            windowInsetsController.isAppearanceLightStatusBars = !isSystemInDarkTheme

            MaterialTheme(colorScheme = colorScheme) {
                Surface(
                    modifier = Modifier.background(MaterialTheme.colorScheme.background)
                ) {
                    CalculatorScreen()
                }
            }
        }
    }
}

@Composable
fun isSystemInDarkTheme(): Boolean {
    val currentNightMode = LocalContext.current.resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK
    return currentNightMode == android.content.res.Configuration.UI_MODE_NIGHT_YES
}

@Composable
fun CalculatorScreen() {
    var input by remember { mutableStateOf("") }
    var calculation by remember { mutableStateOf("") }
    var openBrackets by remember { mutableStateOf(0) }
    val buttonSpacing = 8.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(16.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                if (calculation.isNotEmpty()) {
                    Text(
                        text = calculation,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.End,
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    )
                }

                Text(
                    text = input,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.End,
                    fontSize = 64.sp,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CalculatorButton("AC", Modifier.weight(1f), onClick = {
                    input = ""
                    calculation = ""
                })
                Spacer(modifier = Modifier.width(buttonSpacing))
                CalculatorButton("()", Modifier.weight(1f), onClick = {
                    if (openBrackets == 0) {
                        input += "("
                        openBrackets++
                    } else {
                        if (input.lastOrNull().isDigit() || input.lastOrNull() == ')') {
                            input += ")"
                            openBrackets--
                        } else {
                            input += "("
                            openBrackets++
                        }
                    }
                })
                Spacer(modifier = Modifier.width(buttonSpacing))
                CalculatorButton("^", Modifier.weight(1f), onClick = { input += "^" })
                Spacer(modifier = Modifier.width(buttonSpacing))
                CalculatorButton("/", Modifier.weight(1f), onClick = { input += "/" })
            }
            Spacer(modifier = Modifier.height(buttonSpacing))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CalculatorButton("7", Modifier.weight(1f), onClick = { input += "7" })
                Spacer(modifier = Modifier.width(buttonSpacing))
                CalculatorButton("8", Modifier.weight(1f), onClick = { input += "8" })
                Spacer(modifier = Modifier.width(buttonSpacing))
                CalculatorButton("9", Modifier.weight(1f), onClick = { input += "9" })
                Spacer(modifier = Modifier.width(buttonSpacing))
                CalculatorButton("*", Modifier.weight(1f), onClick = { input += "*" })
            }
            Spacer(modifier = Modifier.height(buttonSpacing))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CalculatorButton("4", Modifier.weight(1f), onClick = { input += "4" })
                Spacer(modifier = Modifier.width(buttonSpacing))
                CalculatorButton("5", Modifier.weight(1f), onClick = { input += "5" })
                Spacer(modifier = Modifier.width(buttonSpacing))
                CalculatorButton("6", Modifier.weight(1f), onClick = { input += "6" })
                Spacer(modifier = Modifier.width(buttonSpacing))
                CalculatorButton("-", Modifier.weight(1f), onClick = { input += "-" })
            }
            Spacer(modifier = Modifier.height(buttonSpacing))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CalculatorButton("1", Modifier.weight(1f), onClick = { input += "1" })
                Spacer(modifier = Modifier.width(buttonSpacing))
                CalculatorButton("2", Modifier.weight(1f), onClick = { input += "2" })
                Spacer(modifier = Modifier.width(buttonSpacing))
                CalculatorButton("3", Modifier.weight(1f), onClick = { input += "3" })
                Spacer(modifier = Modifier.width(buttonSpacing))
                CalculatorButton("+", Modifier.weight(1f), onClick = { input += "+" })
            }
            Spacer(modifier = Modifier.height(buttonSpacing))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CalculatorButton("0", Modifier.weight(1f), onClick = { input += "0" })
                Spacer(modifier = Modifier.width(buttonSpacing))
                CalculatorButton(",", Modifier.weight(1f), onClick = { input += "." })
                Spacer(modifier = Modifier.width(buttonSpacing))
                CalculatorButton("⌫", Modifier.weight(1f), onClick = {
                    if (input.isNotEmpty()) {
                        input = input.substring(0, input.length - 1)
                    }
                })
                Spacer(modifier = Modifier.width(buttonSpacing))
                CalculatorButton("=", Modifier.weight(1f), onClick = {
                    try {
                        calculation = input
                        val result = ExpressionBuilder(input)
                            .build()
                            .evaluate()

                        input = DecimalFormat("#.##").format(result)

                    } catch (e: Exception) {
                        input = "Error"
                    }
                },
                    buttonColor = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Composable
fun CalculatorButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    buttonColor: Color = MaterialTheme.colorScheme.tertiaryContainer
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(80.dp)
            .padding(4.dp),
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onTertiaryContainer
        )
    }
}

private fun Char?.isDigit(): Boolean {
    return this != null && this in '0'..'9'
}