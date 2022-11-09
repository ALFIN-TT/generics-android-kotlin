package com.example.generics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import com.example.generics.data.HtmlData
import com.example.generics.ui.theme.GenericsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GenericsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Generics()
                }
            }
        }
    }
}

@Composable
fun Generics() {
    Column {
        Text(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 0.dp),
            text = HtmlCompat.fromHtml(
                HtmlData.FIRST_TEXT,
                HtmlCompat.FROM_HTML_MODE_LEGACY
            ).toString(),
        )
        Text(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 0.dp),
            text = HtmlCompat.fromHtml(
                HtmlData.CREATE_CAGE_FOR_SPECIFIC_ANIMAL,
                HtmlCompat.FROM_HTML_MODE_LEGACY
            ).toString(),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GenericsTheme {
        Generics()
    }
}