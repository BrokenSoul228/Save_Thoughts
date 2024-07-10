package com.example.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.data.entities.Items
import com.example.data.repositories.ItemsRepository
import com.example.presentation.DataViewModel
import com.example.presentation.R
import com.example.presentation.util.DeleteAlerter

@Composable
fun MainListScreen(navController : NavController) {
    val context = LocalContext.current.applicationContext
    val viewModel = DataViewModel(ItemsRepository(context))
    val tags = remember { mutableStateOf("") }
    val list by viewModel.returnListItems(tags.value).observeAsState(mutableListOf())
    val shouldShowDialog = remember { mutableStateOf(false) }
    val itemID = remember { mutableLongStateOf(-1) }
    if (shouldShowDialog.value) {
        DeleteAlerter(shouldShowDialog = shouldShowDialog, viewModel, itemID)
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = AbsoluteAlignment.Right
    ) {

        HeaderOfScreen(tags)

        LazyColumn {
            items(list.size) {
                val openContextMenu = remember { mutableStateOf(false) }
                val colorCustomMenu = remember { mutableStateOf(false) }
                print(list.joinToString(" "))
                Card(
                    border = BorderStroke(1.dp, Color.Blue),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(170.dp)
                        .absolutePadding(left = 10.dp, right = 10.dp, top = 10.dp),
                    colors = CardColors(Color(0xFFEDFDFF), contentColor = Color.Black, disabledContentColor = Color.Black, disabledContainerColor = Color.Black),
                    onClick = {
                        val new = "old"
                        navController.navigate("item_screen/$new/${list[it].header}/${list[it].mainText}/${list[it].id}/${list[it].tags}")
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .absolutePadding(top = 10.dp, left = 7.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = list[it].header, fontSize = 27.sp)
                        Box {
                            IconButton(onClick = { openContextMenu.value = true }) {
                                Icon(Icons.Default.MoreVert, contentDescription = "Показать меню")
                            }
                            DropdownMenu(
                                expanded = openContextMenu.value,
                                onDismissRequest = { openContextMenu.value = false },
                                modifier = Modifier
                                    .background(Color.White)
                                    .border(0.9.dp, Color.Black)
                            ) {
//                                HorizontalDivider()
                                Text("Delete", fontSize=18.sp, modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxWidth()
                                    .clickable {
                                        itemID.longValue = list[it].id
                                        shouldShowDialog.value = true
                                        openContextMenu.value = false
                                    }, color = Color.Red)
                            }
                        }
                    }
                    Column (
                        Modifier
                            .absolutePadding(left = 7.dp, top = 3.dp)
                            .fillMaxHeight(0.6F)
                    ) {
                        Text(text = list[it].mainText, fontSize = 20.sp)
                    }
                    Row (
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Outlined.DateRange, contentDescription = "delete",
                            Modifier
                                .size(20.dp), tint = Color.Black)
                        Text(text = list[it].date)
                        Box(
                            Modifier.fillMaxSize(),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            if (colorCustomMenu.value) {
                                ColorCustomMenu(colorCustomMenu, list, it, viewModel)
                            } else {
                                Image(
                                    painter = painterResource(id = returnTagColor(list[it].tags)),
                                    contentDescription = "",
                                    Modifier
                                        .absolutePadding(right = 10.dp)
                                        .size(20.dp)
                                        .border(1.dp, Color.Black, shape = CircleShape)
                                        .clickable {
                                            colorCustomMenu.value = !colorCustomMenu.value
                                        })
                            }
                        }
                    }
                }
            }
        }
    }
    ComposeFloatingButton(navController = navController)
}

@Composable
fun HeaderOfScreen(tags: MutableState<String>){
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val listOfTags = listOf("white" to R.drawable.group_white, "red" to R.drawable.group_red,"yellow" to R.drawable.group_yellow,"green" to R.drawable.group_green)

        listOfTags.forEach { (color, image) ->
            Image(painter = painterResource(id = image), contentDescription = "mb", modifier = Modifier
                .size(50.dp, 60.dp)
                .clickable {
                    tags.value = color
                })
        }
    }
}

@Composable
fun ComposeFloatingButton(navController : NavController){
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
                navController.navigate("item_screen/$new/$new/$new/$item/white")
            }, containerColor = Color(0xFFC1EDF3),
        ) {
            Icon(Icons.Filled.Add, "Floating action button.", modifier = Modifier.size(30.dp,30.dp))
        }
    }
}

@Composable
fun ColorCustomMenu(isVisible: MutableState<Boolean>, listItems : MutableList<Items>,itemPos : Int, viewModel: DataViewModel) {

    val listOfColors = listOf("white" to R.drawable.circle_white, "red" to R.drawable.circle_red,
        "yellow" to R.drawable.circle_yellow,"green" to R.drawable.circle_green)

        Row(
            Modifier
                .wrapContentWidth()
                .border(
                    width = 1.5.dp,
                    Color.LightGray,
                    shape = RoundedCornerShape(30)
                )
                .size(160.dp, 40.dp)
                .background(
                    Color(0xFFEDFDFF),
                    shape = RoundedCornerShape(30)
                )
                .clickable {

                },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            listOfColors.forEach { (color, image) ->
                Image(painter = painterResource(id = image), contentDescription = "mb", modifier = Modifier
                    .size(20.dp, 20.dp)
                    .border(1.dp, Color.Black, shape = CircleShape)
                    .clickable {
                        viewModel.updateTagColors(listItems[itemPos].id, color, itemPos)
                        isVisible.value = false
                    })
                Spacer(modifier = Modifier.padding(5.dp))
            }
        }
}

fun returnTagColor(tagString: String) : Int{
    return when(tagString){
        "white" -> R.drawable.circle_white
        "red" -> R.drawable.circle_red
        "green" -> R.drawable.circle_green
        "yellow" -> R.drawable.circle_yellow
        else -> { R.drawable.circle_white }
    }
}

fun returnListGroupedOrNot(
    currentTag: MutableState<String>,
    listItems: MutableList<Items>,
    groupedListItems: MutableList<Items>
): MutableList<Items> {
    if (currentTag.value != "")
        return listItems
    else {
        println("WWWWWWWWWWWWWW ${groupedListItems.joinToString(" ")}")
        return groupedListItems}
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MainListScreen(navController = rememberNavController())
}