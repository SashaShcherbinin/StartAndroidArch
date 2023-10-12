package feature.dashboard.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import base.compose.local.LocalNavigation
import base.compose.navigation.Back
import base.compose.navigation.navigateTo
import base.compose.theme.PreviewAppTheme
import base.compose.theme.PreviewWithThemes
import coil.compose.rememberAsyncImagePainter
import feature.dashboard.R
import feature.user.domain.entity.User
import feature.user.domain.navigation.SCREEN_USER
import feature.user.domain.navigation.navigateToUser
import org.koin.androidx.compose.koinViewModel

@Composable
fun DashboardScreen(viewModel: DashboardViewModel = koinViewModel()) {
    val state: DashboardState by viewModel.state.collectAsState()
    Screen(state)
}

@Composable
private fun Screen(
    state: DashboardState
) {
    Scaffold(
        topBar = { Toolbar() },
    ) { innerPadding ->
        Body(innerPadding, state)
    }
}

@Composable
private fun Body(
    innerPadding: PaddingValues,
    state: DashboardState,
) {
    Box(
        modifier = Modifier.padding(innerPadding),
        contentAlignment = Alignment.Center
    ) {
        when (state.contentState) {
            is ContentState.Loading -> {
                CreateLoading()
            }

            is ContentState.Content -> {
                CreateContent(state = state)
            }

            is ContentState.Error -> {
                CreateError(state = state)
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun Toolbar() {
    val navController = LocalNavigation.current
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text("Dashboard")
        },
        navigationIcon = {
            IconButton(onClick = {
                navController.navigateTo(Back)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.dashboard_ic_close),
                    contentDescription = stringResource(id = R.string.dashboard_close_button)
                )
            }
        }
    )
}

@Composable
private fun CreateContent(
    state: DashboardState,
) {
    val navController = LocalNavigation.current
    LazyColumn {
        items(state.users) { user ->
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .wrapContentHeight()
                    .fillParentMaxWidth()
                    .clickable(
                        onClick = {
                            navController.navigate(navigateToUser(user.id))
                        },
                    )
            ) {
                Image(
                    painter = rememberAsyncImagePainter(user.photoUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                        .padding(8.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    modifier = Modifier.padding(8.dp), text = user.name + " " + user.surname
                )
            }
            HorizontalDivider()
        }
    }
}

@Composable
private fun CreateError(
    state: DashboardState,
) {
    val navController = LocalNavigation.current
    Column {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(state.errorResId),
                Modifier.clickable(onClick = { navController.navigate(SCREEN_USER) })
            )
        }
    }
}

@Composable
private fun CreateLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            trackColor = MaterialTheme.colorScheme.secondary,
        )
    }
}

@PreviewWithThemes
@Composable
fun PreviewScreen() {
    PreviewAppTheme {
        Screen(
            DashboardState(
                users = listOf(
                    User(
                        id = "1",
                        name = "John",
                        surname = "Doe",
                        email = "",
                        photoUrl = "https://example.com/photo.jpg"
                    )
                ),
                contentState = ContentState.Content
            )
        )
    }
}
