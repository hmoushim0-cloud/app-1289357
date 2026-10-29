package sa.salat.furniture.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import sa.salat.furniture.data.InMemoryFurnitureItemRepository
import sa.salat.furniture.model.TableProduct

val BespokeColorScheme = darkColorScheme(
    primary = Color(0xFF0F766E),
    secondary = Color(0xFFF59E0B),
    tertiary = Color(0xFFF59E0B),
    background = Color(0xFF0F172A),
    surface = Color(0xFF1E293B),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color(0xFFF8FAFC),
    onSurface = Color(0xFFF8FAFC)
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = InMemoryFurnitureItemRepository()
        val viewModel = FurnitureItemViewModel(repository)

        // Mathematical Lock 2: Support start_screen and screen intent extras
        val startScreen = intent?.getStringExtra("start_screen")
            ?: intent?.getStringExtra("screen")
            ?: "home"

        setContent {
            MaterialTheme(colorScheme = BespokeColorScheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var currentScreen by remember { mutableStateOf(startScreen) }
                    var selectedItem by remember { mutableStateOf<TableProduct?>(null) }

                    when (currentScreen) {
                        "details" -> {
                            FurnitureItemDetailsScreen(
                                item = selectedItem,
                                onBack = { currentScreen = "home" },
                                onProceedCheckout = { currentScreen = "checkout" }
                            )
                        }
                        "checkout", "tracking" -> {
                            FurnitureItemCheckoutScreen(
                                onBack = { currentScreen = "details" },
                                onComplete = { currentScreen = "home" }
                            )
                        }
                        else -> {
                            FurnitureItemHomeScreen(
                                viewModel = viewModel,
                                onSelectItem = { item ->
                                    selectedItem = item
                                    currentScreen = "details"
                                },
                                onQuickCheckout = {
                                    currentScreen = "checkout"
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
