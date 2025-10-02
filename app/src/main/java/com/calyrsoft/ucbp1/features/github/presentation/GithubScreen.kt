package com.calyrsoft.ucbp1.features.github.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment


@Composable
fun GithubScreen(
    modifier: Modifier = Modifier,
    vm: GithubViewModel = koinViewModel()
) {
    var nickname by remember { mutableStateOf("") }
    val state by vm.state.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = nickname,
                onValueChange = { nickname = it },
                label = { Text("Nickname") }
            )

            OutlinedButton(onClick = { vm.fetchAlias(nickname) }) {
                Text("Buscar")
            }

            when (val st = state) {
                is GithubViewModel.GithubStateUI.Error -> {
                    Text("Error: ${st.message}")
                }

                GithubViewModel.GithubStateUI.Init -> {
                    Text("Ingrese un nickname para buscar.")
                }

                GithubViewModel.GithubStateUI.Loading -> {
                    Text("Cargando...")
                }

                is GithubViewModel.GithubStateUI.Success -> {
                    Text("Nickname: ${st.github.nickname}")
                    Spacer(modifier = Modifier.height(8.dp))
                    AsyncImage(
                        model = st.github.pathUrl.value,
                        contentDescription = null,
                        modifier = Modifier.size(100.dp),
                        contentScale = ContentScale.Crop,
                    )
                    Text("URL: ${st.github.pathUrl.value}")
                }
            }
        }
    }
}
