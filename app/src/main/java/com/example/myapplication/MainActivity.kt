package com.example.myapplication

import android.os.Bundle
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                EditText()
                CalculatorScreen()

            }
        }
    }
}

@Preview
@Composable
fun CalcApp() {
    CalculatorScreen()
    EditText()

}


@Composable
fun EditText(){
    var text by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Введите имя")
            }
        )
    }
}


@Composable
fun CalculatorScreen() {
    var number1 by remember { mutableStateOf("") }
    var number2 by remember { mutableStateOf("") }
    var selectedOperation by remember { mutableStateOf(Operation.PLUS) }
    var result by remember { mutableStateOf<Double?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = number1,
            onValueChange = { number1 = it },
            label = { Text("Первое число") },
            modifier = Modifier
        )

        Spacer(modifier = Modifier.height(16.dp))


        OutlinedTextField(
            value = number2,
            onValueChange = { number2 = it },
            label = { Text("Второе число") },
            modifier = Modifier
        )

        Spacer(modifier = Modifier.height(16.dp))


        Text("Выберите операцию:")
        Operation.entries.forEach { operation ->
            Row(
                modifier = Modifier
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (operation == selectedOperation),
                    onClick = { selectedOperation = operation }
                )
                Text(
                    text = operation.symbol,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        Button(
            onClick = {
                result = calculate(number1, number2, selectedOperation)
            },
        ) {
            Text("Вычислить")
        }

        Spacer(modifier = Modifier.height(16.dp))


        if (result != null) {
            Text(
                text = "Результат: $result",
            )
        }
    }
}

enum class Operation(val symbol: String) {
    PLUS("+"),
    MINUS("-"),
    MULTIPLY("×"),
    DIVIDE("÷")
}

fun calculate(number1Str: String, number2Str: String, operation: Operation): Double? {
    val num1 = number1Str.toDouble()
    val num2 = number2Str.toDouble()

    return when (operation) {
        Operation.PLUS -> num1 + num2
        Operation.MINUS -> num1 - num2
        Operation.MULTIPLY -> num1 * num2
        Operation.DIVIDE -> if (num2 != 0.0) num1 / num2 else null
    }
}
