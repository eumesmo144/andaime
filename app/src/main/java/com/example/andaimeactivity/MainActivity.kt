package com.example.andaimeactivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.andaimeactivity.ui.theme.AndaimeActivityTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndaimeActivityTheme {
                AndaimeApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AndaimeApp() {
    var taskText by remember { mutableStateOf(TextFieldValue("")) }
    var taskList by remember { mutableStateOf(listOf<String>()) }

    Scaffold(

        topBar = {
            TopAppBar(
                title = { Text("Lista de Tarefas") },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (taskText.text.isNotBlank()) {
                    taskList = taskList + taskText.text
                    taskText = TextFieldValue("")
                }
            }) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar tarefa")
            }
        },
        bottomBar = {
            BottomAppBar {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "${taskList.size} tarefas",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,

            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Campo de entrada de texto para nova tarefa
            BasicTextField(
                value = taskText,
                onValueChange = { taskText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primary)
                    .padding(8.dp),
                textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.onBackground)
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(taskList) { task ->
                    TaskCard(task = task, onDelete = {
                        taskList = taskList - task
                    })
                }
            }
        }
    }
}

@Composable
fun TaskCard(task: String, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = task, style = MaterialTheme.typography.bodyLarge)

            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Remover tarefa")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTaskApp() {
    AndaimeActivityTheme {
        AndaimeApp()
    }
}