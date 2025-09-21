import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.jetbrains.kmpapp.AppSimple

fun main() {
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "KMP App Template"
        ) {
            AppSimple()
        }
    }
}