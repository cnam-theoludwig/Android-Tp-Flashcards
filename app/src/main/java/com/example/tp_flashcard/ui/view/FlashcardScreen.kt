package com.example.tp_flashcard.ui.view

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tp_flashcard.model.FlashCard
import com.example.tp_flashcard.viewmodel.AppViewModelProvider
import com.example.tp_flashcard.viewmodel.FlashcardUiState
import com.example.tp_flashcard.viewmodel.FlashcardViewModel
import kotlinx.coroutines.launch

@Composable
fun FlashcardScreen(
    onNavigateBack: () -> Unit,
    flashcardViewModel: FlashcardViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by flashcardViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (uiState.isReviewFinished) {
            ReviewCompleteScreen(
                uiState = uiState,
                onRestart = flashcardViewModel::onRestart,
                onBackToHome = onNavigateBack
            )
        } else {
            ReviewScreen(
                uiState = uiState,
                onAnswer = flashcardViewModel::onAnswer
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ReviewScreen(
    uiState: FlashcardUiState,
    onAnswer: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = uiState.progressText,
                modifier = Modifier.align(Alignment.End),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                SegmentedProgressBar(
                    total = uiState.flashcards.size,
                    current = uiState.currentIndex,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                )
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            AnimatedContent(
                targetState = uiState.currentIndex,
                transitionSpec = {
                    fadeIn(animationSpec = tween(300)) togetherWith fadeOut(animationSpec = tween(300))
                },
                label = "cardAnimation"
            ) { targetIndex ->
                uiState.flashcards.getOrNull(targetIndex)?.let {
                    Flashcard(card = it)
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
        ) {
            Button(onClick = { onAnswer(false) }, shape = MaterialTheme.shapes.extraLarge) {
                Text("Je l'ignorai 👎")
            }
            Button(onClick = { onAnswer(true) }, shape = MaterialTheme.shapes.extraLarge) {
                Text("Je le savais ! 👍")
            }
        }
    }
}

@Composable
fun ReviewCompleteScreen(
    uiState: com.example.tp_flashcard.viewmodel.FlashcardUiState,
    onRestart: () -> Unit,
    onBackToHome: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Félicitations !",
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Bravo, vous avez terminé !",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Votre score : ${uiState.knownCardsCount} / ${uiState.flashcards.size}",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onBackToHome) {
            Text("Accueil")
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(onClick = onRestart) {
            Text("Recommencer")
        }
    }
}

@Composable
fun Flashcard(card: FlashCard) {
    var isFlipped by remember { mutableStateOf(false) }
    val rotation = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(card) {
        if (isFlipped) {
            scope.launch {
                rotation.snapTo(180f)
                rotation.animateTo(0f, animationSpec = tween(500))
                isFlipped = false
            }
        }
    }

    fun flipCard() {
        scope.launch {
            val targetRotation = if (isFlipped) 0f else 180f
            rotation.animateTo(
                targetValue = targetRotation,
                animationSpec = tween(durationMillis = 500)
            )
            isFlipped = !isFlipped
        }
    }

    Card(
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary),
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .graphicsLayer {
                rotationY = rotation.value
                cameraDistance = 8 * density
            }
            .clickable { flipCard() },
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (rotation.value <= 90f) {
                Text(
                    text = card.question,
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            } else {
                Text(
                    text = card.answer,
                    modifier = Modifier
                        .padding(16.dp)
                        .graphicsLayer { rotationY = 180f },
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
    }
}

@Composable
fun SegmentedProgressBar(total: Int, current: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0 until total) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(
                        color = if (i <= current) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(5.dp)
                    )
            )
            if (i < total - 1) {
                Spacer(modifier = Modifier.width(6.dp))
            }
        }
    }
}
