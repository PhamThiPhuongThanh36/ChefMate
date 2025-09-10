package com.example.chefmate

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import androidx.navigation.navArgument
import com.example.chefmate.ui.main.MainAct
import com.example.chefmate.ui.main.SplashScreen
import com.example.chefmate.ui.recipe.AddEditRecipeScreen
import com.example.chefmate.ui.recipe.RecipeScreen
import com.example.chefmate.ui.shopping.AddShoppingScreen
import com.example.chefmate.ui.shopping.ShoppingScreen
import com.example.chefmate.ui.theme.ChefMateTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
    val navController = rememberNavController()
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        NavHost(navController = navController, graph = NavGraph(activity, navController))
    }
}

fun NavGraph(activity: Activity, navController: NavController): NavGraph {
        return navController.createGraph(startDestination = "splash") {
            composable("splash") {
                SplashScreen(navController)
            }
        composable("mainAct") {
            MainAct(navController)
        }
        composable(
            route = "addRecipe/{recipeId}",
            arguments = listOf(
                navArgument("recipeId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getInt("recipeId") ?: -1
            AddEditRecipeScreen(recipeId = recipeId, navController)
        }
        composable(
            route = "recipeDetail/{recipeId}",
            arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getInt("recipeId") ?: 0
            RecipeScreen(navController, recipeViewModel = hiltViewModel(), recipeId)
        }
        composable("shopping/{shoppingId}", arguments = listOf(navArgument("shoppingId") { type = NavType.IntType })) { backStackEntry ->
            val shoppingId = backStackEntry.arguments?.getInt("shoppingId") ?: -1 // Default -1 nếu null
            ShoppingScreen(shoppingId = shoppingId,shoppingViewModel = hiltViewModel(), navController = navController)
        }
        composable("addShopping") {
            AddShoppingScreen(navController = navController, recipeViewModel = hiltViewModel(), shoppingViewModel = hiltViewModel())
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

}