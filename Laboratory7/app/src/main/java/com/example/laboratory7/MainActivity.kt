package com.example.laboratory7

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    // Initialize ViewModel
    private val viewModel: StudentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                StudentApp(viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentApp(viewModel: StudentViewModel) {
    val students by viewModel.students.collectAsState()

    // State for the form inputs
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }

    // State for the Snackbar
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Students") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6200EA),
                    titleContentColor = Color.White
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            // Form
            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text("First Name") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = "First Name Icon") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Last Name") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Last Name Icon") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (firstName.isNotBlank() && lastName.isNotBlank()) {
                        viewModel.addStudent(firstName, lastName)

                        // Show Snackbar
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("Student added")
                        }

                        // Clear form
                        firstName = ""
                        lastName = ""
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EA))
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Icon")
                Spacer(modifier = Modifier.width(8.dp))
                Text("ADD STUDENT")
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("Student list:", color = Color.Gray, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))

            // List
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(students, key = { it.id }) { student ->
                    StudentItem(
                        student = student,
                        onDeleteClick = { viewModel.removeStudent(student) }
                    )
                    HorizontalDivider(color = Color.LightGray, thickness = 0.5.dp)
                }
            }
        }
    }
}

@Composable
fun StudentItem(student: Student, onDeleteClick: () -> Unit) {
    // Create User Avatar (initials)
    val initials = "${student.firstName.firstOrNull() ?: ""}${student.lastName.firstOrNull() ?: ""}".uppercase()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Avatar Circle
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color(0xFF6200EA), shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = initials,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Student Info
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = student.firstName,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Text(
                text = student.lastName,
                color = Color.Gray,
                fontSize = 14.sp
            )
        }

        // Delete Icon Button
        IconButton(onClick = onDeleteClick) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete Student",
                tint = Color.Gray
            )
        }
    }
}