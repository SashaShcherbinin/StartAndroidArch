package feature.user.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import base.compose.local.LocalNavigation
import base.compose.navigation.navigateTo
import base.compose.theme.PreviewAppTheme
import base.compose.theme.PreviewWithThemes
import base.compose.view.UploadingDialog
import coil.compose.rememberAsyncImagePainter
import feature.user.R
import feature.user.domain.entity.User
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@ExperimentalMaterial3Api
@Composable
fun UserScreen(
    userId: String,
    viewModel: UserViewModel = koinViewModel(
        parameters = {
            parametersOf(EXTRA_USER_ID to userId)
        },
    ),
) {
    val navController = LocalNavigation.current
    val state: UserState by viewModel.state.collectAsState()
    LaunchedEffect(key1 = viewModel) {
        viewModel.event.collect {
            when (it) {
                is UserEvent.Navigate -> navController.navigateTo(it.direction)
            }
        }
    }
    if (state.itUploading) {
        UploadingDialog()
    }
    Screen(state = state, processor = viewModel::process)
}

@Composable
private fun Screen(
    state: UserState,
    processor: (UserIntent) -> Unit = {},
) {
    Scaffold(
        topBar = {
            Toolbar()
        },
    ) { innerPadding ->
        Content(innerPadding = innerPadding, state = state, processor = processor)
    }
}

@Composable
private fun Content(
    innerPadding: PaddingValues,
    state: UserState,
    processor: (UserIntent) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(state.user.photoUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .padding(8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = state.fieldName,
                onValueChange = { processor(UserIntent.ChangeName(it)) },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                label = { Text("Name") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = state.fieldSurname,
                onValueChange = { processor(UserIntent.ChangeSurname(it)) },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                label = { Text("Surname") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = state.fieldEmail,
                onValueChange = { processor(UserIntent.ChangeEmail(it)) },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                label = { Text("Email") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { processor(UserIntent.Save) }) {
                Text(text = "Save")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { processor(UserIntent.Delete) }) {
                Text(text = "Delete")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Toolbar() {
    val navController = LocalNavigation.current
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(stringResource(R.string.dashboard_user_dashboard))
        },
        navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.user_back_icon),
                    contentDescription = stringResource(id = R.string.user_close_button)
                )
            }
        }
    )
}

@PreviewWithThemes
@Composable
private fun ScreenPreview() {
    PreviewAppTheme {
        Screen(
            state = UserState(
                user = User(
                    id = "1",
                    name = "John",
                    surname = "Doe",
                    email = "",
                    photoUrl = "https://example.com/photo.jpg",
                ),
            ),
        )
    }
}
