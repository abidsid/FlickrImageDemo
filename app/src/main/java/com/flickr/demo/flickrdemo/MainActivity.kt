package com.flickr.demo.flickrdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.flickr.demo.flickrdemo.presentation.ui.DetailsScreen
import com.flickr.demo.flickrdemo.presentation.ui.ThumbnailGridScreen
import com.flickr.demo.flickrdemo.presentation.viewmodel.ThumbnailViewModel
import com.flickr.demo.flickrdemo.ui.theme.DemoAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            DemoAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AnimatedMainNavigation(navController = rememberNavController())
                }
            }

            //ThumbnailGridScreen()

        }
    }
}

@Composable
fun AnimatedMainNavigation(
    navController: NavHostController,
    viewModel: ThumbnailViewModel = viewModel()
) {


    NavHost(
        navController = navController,
        startDestination = "list",
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                tween(700)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                tween(700)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                tween(700)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                tween(700)
            )
        }
    ) {
        composable("list") {

            ThumbnailGridScreen(viewModel, navController)
        }
        composable(
            "details"
            //arguments = listOf(navArgument("thumbnailId") { type = NavType.StringType })
        ) { backStackEntry ->
            DetailsScreen(viewModel = viewModel, navController = navController)
        }
    }

//    AnimatedNavHost(
//        navController = navController,
//        startDestination = "list",
//       // enterTransition = { fadeIn(animationSpec = tween(700)) },
//        //exitTransition = { fadeOut(animationSpec = tween(700)) }
//    ) {
//        composable("list") {
//            ThumbnailGridScreen(viewModel, navController)
//        }
//        composable(
//            "details"
//            //arguments = listOf(navArgument("thumbnailId") { type = NavType.StringType })
//        ) { backStackEntry ->
//            val thumbnailId = backStackEntry.arguments?.getString("thumbnailId")
//            DetailsScreen(thumbnailId = thumbnailId!!, viewModel = viewModel, navController = navController)
//        }
//    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DemoAppTheme {
        Greeting("Android")
    }
}