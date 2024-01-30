package ir.erfansn.lifecycleawarenavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ir.erfansn.lifecycleawarenavigation.ui.theme.SafePopBackstackTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SafePopBackstackTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "home"
                    ) {
                        composable("home") {
                            Screen(
                                title = "Home Screen",
                                verticalArrangement = Arrangement.Top
                            ) {
                                Button(
                                    onClick = {
                                        navController.runWithLifecycleAware {
                                            navigate("settings")
                                        }
                                    }
                                ) {
                                    Text(text = "Go to Settings")
                                }
                            }
                        }

                        composable("settings") {
                            Screen(
                                title = "Settings Screen",
                                verticalArrangement = Arrangement.Center
                            ) {
                                Button(
                                    onClick = {
                                        navController.runWithLifecycleAware {
                                            popBackStack()
                                        }
                                    }
                                ) {
                                    Text(text = "Return to back")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Screen(
    title: String,
    verticalArrangement: Arrangement.Vertical,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = verticalArrangement,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(title)
        Spacer(modifier = Modifier.height(32.dp))
        content()
    }
}

fun NavController.runWithLifecycleAware(block: NavController.() -> Unit) {
    if (currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        block()
    }
}
