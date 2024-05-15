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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Make status bar and navigation bar transparent
        WindowCompat.setDecorFitsSystemWindows(window, false)
        // Set status bar color to transparent
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        setContent {
            val context = LocalContext.current
            val isSystemInDarkTheme = isSystemInDarkTheme()

            // Dynamically apply the appropriate color scheme
            val colorScheme = if (isSystemInDarkTheme) {
                dynamicDarkColorScheme(context)
            } else {
                dynamicLightColorScheme(context)
            }

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

// Function to check the system's dark mode setting
@Composable
fun isSystemInDarkTheme(): Boolean {
    val currentNightMode = LocalContext.current.resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK
    return currentNightMode == android.content.res.Configuration.UI_MODE_NIGHT_YES
}

@Composable
fun CalculatorScreen() {
    var input by remember { mutableStateOf("") }
    val buttonSpacing = 8.dp

    // Apply padding from Scaffold
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = input,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp)),
            textAlign = TextAlign.End,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // First row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CalculatorButton("AC", Modifier.weight(1f), onClick = { input = "" })
                Spacer(modifier = Modifier.width(buttonSpacing))
                CalculatorButton("()", Modifier.weight(1f), onClick = { /* TODO */ })
                Spacer(modifier = Modifier.width(buttonSpacing))
                CalculatorButton("^", Modifier.weight(1f), onClick = { input += "^" })
                Spacer(modifier = Modifier.width(buttonSpacing))
                CalculatorButton("/", Modifier.weight(1f), onClick = { input += "/" })
            }
            Spacer(modifier = Modifier.height(buttonSpacing))

            // Second row
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

            // Third row
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

            // Fourth row
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

            // Fifth row - 0 button is now the same size
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
                        val result = ExpressionBuilder(input)
                            .build()
                            .evaluate()
                        input = result.toString()
                    } catch (e: Exception) {
                        input = "Error"
                    }
                },
                    buttonColor = MaterialTheme.colorScheme.primary)
            }
        } // <-- Corrected position of closing curly brace
    }
}

// Now defined outside CalculatorScreen
@Composable
fun CalculatorButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    // Adjusted button color: tertiaryContainer
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
            // Set text color for better contrast
            color = MaterialTheme.colorScheme.onTertiaryContainer
        )
    }
}