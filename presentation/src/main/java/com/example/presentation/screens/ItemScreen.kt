package com.example.presentation.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.ImeOptions
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.data.repositories.ItemsRepository
import com.example.presentation.DataViewModel
import com.example.presentation.R

@Composable
fun ItemScreen(navController: NavController, state : String, title : String, mainText : String, itemId :Long) {

    val context = LocalContext.current.applicationContext
    val viewModel = DataViewModel(ItemsRepository(context))
    val isVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    val visible = remember {
        mutableStateOf(true)
    }
    LaunchedEffect(key1 = isVisible) {
        visible.value = !isVisible
    }

    val titleField = remember {
        mutableStateOf("")
    }
    val mainField = remember {
        mutableStateOf("")
    }

    LaunchedEffect(state) {
        if (state != "new") {
            titleField.value = title
            mainField.value = mainText
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Add", modifier = Modifier.absolutePadding(top = 10.dp), fontSize = 30.sp, textDecoration = TextDecoration.Underline, fontWeight = FontWeight.Bold)
        CardWithTags()
        Text(text = "Title",
            Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .absolutePadding(top = 10.dp))
        OutlinedTextField(value = titleField.value, onValueChange = { it ->
            titleField.value = it
        },
            Modifier
                .fillMaxWidth()
                .absolutePadding(left = 20.dp, right = 20.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )
        Column {
            Text(text = "Todo", Modifier.padding(20.dp))

            OutlinedTextField(value = mainField.value, onValueChange = { it ->
                mainField.value = it
            },
                Modifier
                    .fillMaxWidth()
                    .absolutePadding(left = 20.dp, right = 20.dp)
                    .height(150.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                colors = TextFieldDefaults.colors(focusedContainerColor = Color(0xFFF1F1F1), unfocusedContainerColor = Color(0xFFF1F1F1)
                )
            )
        }
    }
    if (visible.value) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FloatingActionButton(
                onClick = {
                    validation(
                        state,
                        viewModel,
                        mainField.value,
                        titleField.value,
                        context,
                        itemId,
                        navController
                    )
                },
                Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .absolutePadding(left = 20.dp, right = 20.dp),
                containerColor = Color.Blue,
                shape = CircleShape
            ) {
                Text(text = "Save", color = Color.White)
            }
        }
    }
}

fun validation(state : String, viewModel: DataViewModel, todoText : String, titleText : String, context: Context, itemId: Long, navController : NavController) {
    if (todoText != "" && titleText != ""){
        if (state != "new"){
            viewModel.updateCurrentItem(
                id = itemId,
                header = titleText,
                body = todoText
            )
            navController.navigate("home")
        } else {
            viewModel.insertItem(
                header = titleText,
                body = todoText
            )
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
            navController.navigate("home")
        }
    }
}

@Composable
fun CardWithTags(){
    Card (
        Modifier
            .fillMaxWidth()
            .absolutePadding(top = 20.dp, left = 20.dp, right = 20.dp),
        colors = CardColors(Color.Transparent, Color.Black, Color.Black, Color.Black),
        border = BorderStroke(width = 1.5.dp, brush = Brush.horizontalGradient(listOf(Color.Yellow, Color.Red, Color.Green)))
    ) {
        Row (
            Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { /*TODO*/ }, colors = ButtonColors(Color.Transparent, Color.Black, Color.Black, Color.Black)
            ) {
                Image(painter = painterResource(id = R.drawable.circle_yellow), contentDescription = "mb", modifier = Modifier
                    .size(10.dp)
                    .absolutePadding(right = 3.dp))
                Text(text = "Todo", fontSize = 12.sp)
            }
            Button(
                onClick = { /*TODO*/ }, colors = ButtonColors(Color.Transparent, Color.Black, Color.Black, Color.Black)
            ) {
                Image(painter = painterResource(id = R.drawable.circle_red), contentDescription = "mb", modifier = Modifier
                    .size(10.dp)
                    .absolutePadding(right = 3.dp))
                Text(text = "Routine", fontSize = 12.sp)
            }
            Button(
                onClick = { /*TODO*/ }, colors = ButtonColors(Color.Transparent, Color.Black, Color.Black, Color.Black)
            ) {
                Image(painter = painterResource(id = R.drawable.circle_green), contentDescription = "mb", modifier = Modifier
                    .size(10.dp)
                    .absolutePadding(right = 3.dp))
                Text(text = "Event" ,fontSize = 12.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Prew() {
    ItemScreen(navController = rememberNavController(), "0", "", "", -1)
}