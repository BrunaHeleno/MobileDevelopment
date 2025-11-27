/* Mobile Development Project
   Bruna Heleno 3009733
 */

package com.example.brunaheleno_3009733_mdproject

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import com.example.brunaheleno_3009733_mdproject.ui.Menu
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource

class Camera : ComponentActivity() {
    private var home = R.string.home
    private var search = R.string.search
    private var files = R.string.files
    private var photoUri: Uri? = null
    private var videoUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Screen()
        }
    }

    @Composable
    fun Screen(){
        //val = immutable | var = mutable

        val context = LocalContext.current

        var capturedImageUri by remember { mutableStateOf<Uri?>(null) }
        var capturedVideoUri by remember { mutableStateOf<Uri?>(null) }
        var title by remember { mutableStateOf("")}
        var category by remember { mutableStateOf("")}

        val takePhotoLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()){
            success -> if(success){
                capturedVideoUri = null
                capturedImageUri = photoUri
            }
        }

        val recordVideoLauncher = rememberLauncherForActivityResult(ActivityResultContracts.CaptureVideo()) {
            success -> if(success) {
                capturedImageUri = null
                capturedVideoUri = videoUri
            }
        }

        Column {
            //Menu on top of the screen
            Menu(
                home,
                MainActivity::class.java,
                search,
                Search::class.java,
                files,
                Files::class.java
            )

            //Button to take photo
            Button(onClick = {
                photoUri = createMediaUri("IMG_", ".jpg")
                takePhotoLauncher.launch(photoUri!!)
            }) {
                Text("Take Photo")
            }

            //Button to take video
            Button(onClick = {
                videoUri = createMediaUri("VID_", ".mp4")
                recordVideoLauncher.launch(videoUri!!)
            }) {
                Text("Take Video")
            }

            capturedImageUri?.let{
                uri -> Image(
                    bitmap = loadBitmapFromUri(context, uri),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
            }

            capturedVideoUri?.let{
                uri -> Text("Video Recorded", fontSize = 20.sp)
            }

            //fields for database
            if(capturedImageUri != null || capturedVideoUri != null){
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it},
                    label = {Text("Title")}
                )

                OutlinedTextField(
                    value = title,
                    onValueChange = { category = it},
                    label = {Text("Category")}
                )

                //button to Save
                Button(onClick = {
                    val savedUri = capturedImageUri ?: capturedVideoUri

                    if(savedUri != null){
                        saveMedia(
                            uri = savedUri,
                            title = title,
                            category = category,
                            context = context
                        )

                        //TODO: make notification to say it saved

                        //resetting everything
                        capturedVideoUri = null
                        capturedImageUri = null
                        title = ""
                        category = ""
                    }
                }) {
                    Text(stringResource( id= R.string.save))
                }
            }
        }
    }
    private fun createMediaUri(prefix: String, extension: String): Uri{
        val contentValues = ContentValues().apply{
            put(
                MediaStore.MediaColumns.DISPLAY_NAME,
                prefix + System.currentTimeMillis() + extension
            )
            put(
                MediaStore.MediaColumns.MIME_TYPE,
                if (extension == ".mp4") "video/mp4" else "image/jpeg")
        }

        val uri = if(extension == ".mp4"){
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        }else{
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

        return contentResolver.insert(uri, contentValues) ?: throw IllegalArgumentException("Media URI failed")
    }

    fun loadBitmapFromUri(context: Context, uri: Uri): ImageBitmap{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            ImageDecoder.decodeBitmap(source).asImageBitmap()
        }else{
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri).asImageBitmap()
        }
    }

    fun saveMedia(uri: Uri, title: String, category: String, context: Context){
        //TODO: create database and function to save file
    }
}
