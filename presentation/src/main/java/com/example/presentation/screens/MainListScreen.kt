package com.example.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.data.DataBase
import com.example.data.repositories.ItemsRepository
import com.example.presentation.DataViewModel
import com.example.presentation.R
import com.example.presentation.util.DeleteAlerter

@Composable
fun MainListScreen(navController : NavController) {
    val context = LocalContext.current.applicationContext
    val viewModel = DataViewModel(ItemsRepository(context))
    val scope = rememberCoroutineScope()
    viewModel.getAllItemsFromDB()
    DataBase.getDatabase(context)
    val list by viewModel.listOfAllItems.observeAsState(emptyList())
    val shouldShowDialog = remember {
        mutableStateOf(false)
    }
    val itemID = remember {
        mutableLongStateOf(-1)
    }
    println(list.joinToString(" "))
    if (shouldShowDialog.value) {
        DeleteAlerter(shouldShowDialog = shouldShowDialog, viewModel, itemID)
    }
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Row (
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Save your thoughts",
                Modifier
                    .wrapContentSize()
                    .absolutePadding(left = 15.dp, top = 15.dp, bottom = 5.dp),
                fontStyle = FontStyle.Italic, fontFamily = FontFamily.Serif, fontSize = 26.sp, color = Color(
                    0xFF79421C
                )
            )
            Row(
                Modifier
                    .width(80.dp)
                    .absolutePadding(right = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Image(painter = painterResource(id = R.drawable.circle_red), contentDescription = "mb", modifier = Modifier.size(20.dp,20.dp))
                Image(painter = painterResource(id = R.drawable.circle_yellow), contentDescription = "mb", modifier = Modifier.size(20.dp,20.dp))
                Image(painter = painterResource(id = R.drawable.circle_green), contentDescription = "mb", modifier = Modifier.size(20.dp,20.dp))
            }
        }


        LazyColumn(
        ) {
            items(list.size) {
                Card(
                    border = BorderStroke(1.3.dp, Color(0xFF79421C)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(170.dp)
                        .absolutePadding(left = 10.dp, right = 10.dp, top = 10.dp),
                    colors = CardColors(Color(0xFFE6B99A), contentColor = Color.Black, disabledContentColor = Color.Black, disabledContainerColor = Color.Black)
                    , onClick = {
                        val new = "old"
                        navController.navigate("item_screen/$new/${list[it].header}/${list[it].mainText}/${list[it].id}")
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .absolutePadding(top = 10.dp, left = 7.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "${list[it].header}", fontSize = 30.sp)
                        IconButton(onClick = {
                            itemID.longValue = list[it].id
                            shouldShowDialog.value = true
                        }) {
                            Icon(Icons.Outlined.Delete, contentDescription = "delete",
                                Modifier
                                    .size(30.dp), tint = Color.White)
                        }
                    }
                    Column (
                        Modifier
                            .absolutePadding(left = 7.dp, top = 3.dp)
                            .fillMaxHeight(0.7F)
                    ) {
                        Text(text = "${list[it].mainText}", fontSize = 20.sp)
                    }
                    Row (
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(5.dp),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(text = "${list[it].date}")
                    }
                }
            }
        }
    }
    Row(
        Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Bottom
    ) {
        FloatingActionButton(
            modifier = Modifier
                .absolutePadding(right = 10.dp, bottom = 30.dp),
            onClick = {
                val new = "new"
                val item = -1L
                navController.navigate("item_screen/$new/$new/$new/$item")
            }, containerColor = Color(0xffb48768),
        ) {
            Icon(Icons.Filled.Add, "Floating action button.", modifier = Modifier.size(30.dp,30.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MainListScreen(navController = rememberNavController())
}