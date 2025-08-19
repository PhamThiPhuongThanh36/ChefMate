package com.example.chefmate

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.example.chefmate.ui.main.MainAct
import com.example.chefmate.ui.recipe.AddRecipeScreen
import com.example.chefmate.ui.theme.ChefMateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChefMateTheme {
                MainScreen(this)
            }
        }
    }
}

@Composable
fun MainScreen(activity: Activity) {
    val context = LocalContext.current
    val navController = rememberNavController()
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        NavHost(navController = navController, graph = NavGraph(activity, navController))
    }
}

fun NavGraph(activity: Activity, navController: NavController): NavGraph {
    return navController.createGraph(startDestination = "mainAct") {
        composable("mainAct") {
            MainAct(navController)
        }
        composable("addRecipe") {
            AddRecipeScreen(navController)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

}