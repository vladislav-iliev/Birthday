package com.vladislaviliev.birthday.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun Container(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController, createAppGraph(navController), modifier)
}