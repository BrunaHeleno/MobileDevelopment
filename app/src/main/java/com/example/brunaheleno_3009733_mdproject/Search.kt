/* Mobile Development Project
   Bruna Heleno 3009733
 */

package com.example.brunaheleno_3009733_mdproject

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.brunaheleno_3009733_mdproject.database.FileItem
import com.example.brunaheleno_3009733_mdproject.database.FileRepository
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import com.example.brunaheleno_3009733_mdproject.database.CommonFunctions
import com.example.brunaheleno_3009733_mdproject.ui.Menu

//this class represents the Search screen, it allows user to search the files and shows previews as thumbnails
class Search : ComponentActivity() {
    private val home = R.string.home
    private val camera = R.string.camera
    private val files = R.string.files
    private val spaceBetween = 15.dp // space between items in the Column layout - Spacer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Screen()
        }
    }

    @Composable
    fun Screen() {
        val context = LocalContext.current
        val repo = FileRepository(context) //connection with database

        var searchText by remember { mutableStateOf("") }
        var allFiles by remember { mutableStateOf(repo.getAll()) } //all files

        //filter files by title and category ignoring case
        val filteredFiles = allFiles.filter {
            it.title.contains(searchText, ignoreCase = true) ||
            it.category.contains(searchText, ignoreCase = true)
        }

        Column {
            //Menu on top of the screen
            Menu(
                home,
                MainActivity::class.java,
                camera,
                Camera::class.java,
                files,
                Files::class.java
            )

            Spacer(modifier = Modifier.height(spaceBetween))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                //search field
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    label = { Text(stringResource(id = R.string.search)) },

                    modifier = Modifier
                        .width(400.dp)
                        .background(colorResource(R.color.white), RoundedCornerShape(10.dp)),

                    shape = RoundedCornerShape(10.dp),
                )
            }

            Spacer(modifier = Modifier.height(spaceBetween))

            //if there's no result it shows a message "No Results" on screen
            if (filteredFiles.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No Results",
                        fontSize = 18.sp,
                        color = colorResource(R.color.dark_blue)
                    )
                }
            } else {
                //showing file as thumbnails
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(filteredFiles) { file ->
                        FilePreview(file, context) {
                            CommonFunctions.openFile(context, file.uri)
                        }

                    }
                }

            }
        }
    }

    //this function is to show preview of the files as thumbnails
    @Composable
    fun FilePreview(file: FileItem, context: Context, onClick: () -> Unit) {

        val bitmap = remember(file.uri) {

            val uri = Uri.parse(file.uri)

            if (file.uri.endsWith(".mp4", ignoreCase = true)) {
                CommonFunctions.loadVideoThumbnail(Uri.parse(file.uri)) //calling function to show video
            } else {
                CommonFunctions.loadThumbnail(context, uri) //calling function to show photo
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
        ) {
            //showing the image of the files
            if (bitmap != null) {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = file.title,
                    modifier = Modifier
                        .height(150.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                //in case it couldn't load preview shows a message "No preview" inside of the box
                Box(
                    modifier = Modifier
                        .height(150.dp)
                        .fillMaxWidth()
                        .background(colorResource(R.color.white), RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No preview", color = colorResource(R.color.black))
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(file.title, fontSize = 18.sp, maxLines = 1) //title of the file
            Text(file.category, fontSize = 12.sp, maxLines = 1) //category of the file
        }
    }
}


