package com.vrukshavalli.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

private val LeafGreen = Color(0xFF2E7D32)
private val LightGreen = Color(0xFFE8F5E9)
private val Background = Color(0xFFF7FBF6)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VrukshavalliTheme {
                VrukshavalliApp()
            }
        }
    }
}

@Composable
fun VrukshavalliTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = LeafGreen,
            secondary = Color(0xFF689F38),
            background = Background,
            surface = Color.White
        ),
        content = content
    )
}

@Composable
fun VrukshavalliApp() {
    var selectedTab by remember { mutableIntStateOf(0) }
    var showDoctor by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = Background,
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = {
                        selectedTab = 0
                        showDoctor = false
                    },
                    icon = { Icon(Icons.Default.Home, null) },
                    label = { Text("Home") }
                )

                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = {
                        selectedTab = 1
                        showDoctor = false
                    },
                    icon = { Icon(Icons.Default.LocalFlorist, null) },
                    label = { Text("My Plants") }
                )

                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = {
                        selectedTab = 2
                        showDoctor = false
                    },
                    icon = { Icon(Icons.Default.Person, null) },
                    label = { Text("Profile") }
                )
            }
        }
    ) { padding ->

        if (showDoctor) {
            PlantDoctor(
                modifier = Modifier.padding(padding),
                onBack = { showDoctor = false }
            )
        } else {
            when (selectedTab) {
                0 -> HomeScreen(
                    modifier = Modifier.padding(padding),
                    onDoctor = { showDoctor = true }
                )

                1 -> MyPlantsScreen(
                    modifier = Modifier.padding(padding),
                    onDoctor = { showDoctor = true }
                )

                2 -> ProfileScreen(
                    modifier = Modifier.padding(padding)
                )
            }
        }
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onDoctor: () -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Vrukshavalli 🌿",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = LeafGreen
            )

            Text(
                text = "Your plants. Your garden. One intelligent companion.",
                color = Color.Gray
            )
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDoctor() },
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(
                    containerColor = LightGreen
                )
            ) {
                Column(
                    modifier = Modifier.padding(22.dp)
                ) {
                    Text(
                        text = "🩺 Plant Doctor",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        "Something wrong with your plant?"
                    )

                    Spacer(Modifier.height(16.dp))

                    Button(
                        onClick = onDoctor,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.AddAPhoto, null)
                        Spacer(Modifier.width(8.dp))
                        Text("Check My Plant")
                    }
                }
            }
        }

        item {
            Text(
                "Today's care",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            CareCard(
                emoji = "💧",
                title = "Water check",
                subtitle = "No watering scheduled today"
            )
        }

        item {
            CareCard(
                emoji = "☀️",
                title = "Light",
                subtitle = "Your plants prefer bright indirect light"
            )
        }

        item {
            Text(
                "Your garden",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            PlantCard(
                name = "My First Plant",
                species = "Add your first plant",
                health = "Ready for a health scan"
            )
        }
    }
}

@Composable
fun CareCard(
    emoji: String,
    title: String,
    subtitle: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                emoji,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(Modifier.width(14.dp))

            Column {
                Text(title, fontWeight = FontWeight.Bold)
                Text(subtitle, color = Color.Gray)
            }
        }
    }
}

@Composable
fun PlantCard(
    name: String,
    species: String,
    health: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "🌱",
                style = MaterialTheme.typography.displaySmall
            )

            Spacer(Modifier.width(14.dp))

            Column {
                Text(name, fontWeight = FontWeight.Bold)
                Text(species, color = Color.Gray)
                Spacer(Modifier.height(4.dp))
                Text(
                    "● $health",
                    color = LeafGreen
                )
            }
        }
    }
}

@Composable
fun MyPlantsScreen(
    modifier: Modifier = Modifier,
    onDoctor: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            "My Plants 🌿",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = onDoctor,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.AddAPhoto, null)
            Spacer(Modifier.width(8.dp))
            Text("Identify / Check a Plant")
        }

        Spacer(Modifier.height(20.dp))

        PlantCard(
            name = "No plants yet",
            species = "Add a plant using the camera",
            health = "Waiting for your first scan"
        )
    }
}

@Composable
fun PlantDoctor(
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {
    var soil by remember { mutableStateOf("") }
    var water by remember { mutableStateOf("") }
    var light by remember { mutableStateOf("") }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                "🩺 Plant Doctor",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                "Let's understand what your plant needs."
            )
        }

        item {
            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.AddAPhoto, null)
                Spacer(Modifier.width(8.dp))
                Text("Take Plant Photo")
            }
        }

        item {
            QuestionCard(
                title = "How does the soil feel?",
                options = listOf("Wet", "Moist", "Dry"),
                selected = soil,
                onSelect = { soil = it }
            )
        }

        item {
            QuestionCard(
                title = "When did you last water?",
                options = listOf(
                    "Today",
                    "2–3 days ago",
                    "1 week+",
                    "Don't know"
                ),
                selected = water,
                onSelect = { water = it }
            )
        }

        item {
            QuestionCard(
                title = "Where is the plant?",
                options = listOf(
                    "Direct sunlight",
                    "Bright indirect light",
                    "Low light"
                ),
                selected = light,
                onSelect = { light = it }
            )
        }

        if (soil.isNotEmpty() &&
            water.isNotEmpty() &&
            light.isNotEmpty()
        ) {
            item {
                DiagnosisCard(
                    soil = soil,
                    water = water,
                    light = light
                )
            }
        }

        item {
            TextButton(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back to Home")
            }
        }
    }
}

@Composable
fun QuestionCard(
    title: String,
    options: List<String>,
    selected: String,
    onSelect: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(Modifier.padding(18.dp)) {
            Text(
                title,
                fontWeight = FontWeight.Bold
            )

            options.forEach { option ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelect(option) }
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selected == option,
                        onClick = { onSelect(option) }
                    )

                    Text(option)
                }
            }
        }
    }
}

@Composable
fun DiagnosisCard(
    soil: String,
    water: String,
    light: String
) {
    val diagnosis =
        if (soil == "Wet" &&
            (water == "Today" || water == "2–3 days ago")
        ) {
            "Possible overwatering"
        } else if (
            soil == "Dry" &&
            water == "1 week+"
        ) {
            "Possible underwatering"
        } else if (light == "Low light") {
            "Possible insufficient light"
        } else {
            "No obvious problem from these answers"
        }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = LightGreen
        ),
        shape = RoundedCornerShape(22.dp)
    ) {
        Column(Modifier.padding(20.dp)) {
            Text(
                "🌿 Plant assessment",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(10.dp))

            Text(
                diagnosis,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(8.dp))

            Text(
                "This is preliminary guidance. A photo-based AI analysis will be added in the next version."
            )
        }
    }
}

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            "Profile",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(20.dp))

        Text("🌿 Plant lover")

        Spacer(Modifier.height(10.dp))

        Text(
            "Vrukshavalli will eventually learn your plants, environment and care habits.",
            color = Color.Gray
        )
    }
}
