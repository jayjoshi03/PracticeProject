package com.example.jetpackcomposedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ManageBlock()
        }
    }

    @Composable
    fun ManageBlock(){
        val state = rememberSaveable { mutableStateOf(0) }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize(1f)
        ) {
            CountNumber(state.value) { state.value++ }
            MassageScreen(state.value)
        }
    }

    @Composable
    fun MassageScreen(count:Int){
        Text(text = "This above Count is: $count")
    }

    @Composable
    fun CountNumber(count: Int, increment: ()-> Unit) {
        Column(verticalArrangement = Arrangement.Center) {
            Text(text = "Count: $count")
            Button(onClick = { increment()}) {
                Text(text = "Count Increase")
            }
        }
    }
}
