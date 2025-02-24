package com.example.todolistapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHost
import com.example.todolistapp.ui.theme.TodoListAppTheme
import com.example.todolistapp.view.TodoListView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoListAppTheme {
            }
        }
    }
}

@Composable
fun Nav(context: Context) {
    val navController = NavController(context)

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        // NavHost for navigation between screens
        NavHost(
            navController = navController,
            startDestination = "todo_list_screen", // Starting screen
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("todo_list_screen") { TodoListView(navController) }
            composable("todo_detail_screen") { TodoDetailScreen(navController) }
        }
    }
}
