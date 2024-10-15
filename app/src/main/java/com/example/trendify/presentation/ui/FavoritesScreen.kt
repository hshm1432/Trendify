package com.example.trendify.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import coil.compose.AsyncImage
import com.example.trendify.data.model.DataXXXX
import com.example.trendify.presentation.viewmodel.FavoritesViewModel

class FavoriteScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel: FavoritesViewModel = hiltViewModel()
        val favoriteResponse = viewModel.favoritesResponse.collectAsState()

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("Your Favorites") },
                    navigationIcon = {
                        IconButton(onClick = { /* Handle back navigation */ }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFFFFA500)) // Orange color
                )
            }
        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {

                // LazyColumn for Favorite Products
                favoriteResponse.value?.data?.data?.let { favoritesItems ->
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(favoritesItems) { favoriteItem -> // Use favoritesItems here
                            FavoriteProductCard(product = favoriteItem)
                        }
                    }
                } ?: run {
                    // Handle empty or loading state if necessary
                    emptyFavoriteMessage()
                }
            }
        }
    }

    @Composable
    fun FavoriteProductCard(product: DataXXXX) { // Correct the typo here
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = product.product.image ?: "",
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .padding(end = 16.dp)
                )

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = product.product.name ?: "No Title",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "Price: ${product.product.price}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF4CAF50) // Green color for price
                    )
                    Text(
                        text = "Old Price: ${product.product.old_price}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }

                // Remove from Favorites Icon Button
                IconButton(onClick = { /* Handle remove from favorites */ }) {
                    Icon(Icons.Default.Delete, contentDescription = "Remove from favorites")
                }
            }
        }
    }

    @Composable
    fun emptyFavoriteMessage() {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "You have no favorite products", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Start adding products to your favorites!")
            }
        }
    }
}
