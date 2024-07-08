package com.example.presentation.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.presentation.DataViewModel
import java.io.ByteArrayOutputStream

object BitmapConverter {
    fun converterBitmapToString(bitmap: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos)
        val byteArray = baos.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    fun converterStringToBitmap(encodedString: String): Bitmap? {
        return try {
            val encodeByte = Base64.decode(encodedString, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        } catch (e:Exception) {
            e.printStackTrace()
            null
        }
    }
}

@Composable
fun DeleteAlerter(shouldShowDialog: MutableState<Boolean>, viewModel: DataViewModel, id : MutableState<Long>) {
    AlertDialog(onDismissRequest = {  },
        title = { Text(text = "Delete?") },
        text = { Text(text = "Relly?") },
        dismissButton = {
            Button(onClick = {
                shouldShowDialog.value = false })
            {
                Text(text = "NO")
            }
        },
        confirmButton = {
            Button(onClick = {
                viewModel.deleteItem(id.value)
                shouldShowDialog.value = false })
            {
                Text(text = "DA")
            }
        })
}