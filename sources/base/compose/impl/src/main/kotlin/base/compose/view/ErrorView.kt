package base.compose.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import base.compose.theme.AppTheme

@Composable
fun ErrorView(
    message: String,
    retryButtonName: String,
    onRetryClicked: () -> Unit,
) {
    Column {
        Box(
            Modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = message,
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedButton(modifier = Modifier.fillMaxWidth(), onClick = {
                    onRetryClicked()
                }) {
                    Text(retryButtonName)
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewDark() = AppTheme(useDarkTheme = true) {
    ErrorView(
        message = "Internet connection Error, Internet connection Error," +
            " Internet connection Error, Internet connection Error",
        retryButtonName = "Retry",
    ) {}
}
