package com.vladislaviliev.birthday

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.vladislaviliev.birthday.createAppGraph
import com.vladislaviliev.birthday.ui.theme.BirthdayTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BirthdayTheme {
                val navController = rememberNavController()
                NavHost(navController, createAppGraph(navController))
            }
        }
    }
}