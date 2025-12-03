/* Mobile Development Project
   Bruna Heleno 3009733
 */

package com.example.brunaheleno_3009733_mdproject

import android.content.ContentValues
import android.content.Context
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.VideoView
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView


class Camera : ComponentActivity() {
    private var home = R.string.home
    private var search = R.string.search
    private var files = R.string.files
    private var photoUri: Uri? = null
    private var videoUri: Uri? = null
    private var spaceBetween = 15.dp // space between items in the Column layout - Spacer
    private var heightBtn = 70.dp //height for button
    private var widthBtn = 300.dp //width for button
    private var textSizeBtn = 30.sp //text size inside button

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
        //Menu on top of the screen
        Menu(
            home,
            MainActivity::class.java,
            search,
            Search::class.java,
            files,
            Files::class.java
        )

        Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ){

            Spacer(modifier = Modifier.height(spaceBetween))

            //Button to take photo
            ButtonsApp(
                text = stringResource(id = R.string.photo),
                onClick = {
                    photoUri = createMediaUri("IMG_", ".jpg")
                    takePhotoLauncher.launch(photoUri!!)
                },
                height = heightBtn,
                width = widthBtn,
                textSize = textSizeBtn
            )

            Spacer(modifier = Modifier.height(spaceBetween))

            //Button to take video
            ButtonsApp(
                text = stringResource(id = R.string.video),
                onClick = {
                    videoUri = createMediaUri("VID_", ".mp4")
                    recordVideoLauncher.launch(videoUri!!)
                },
                height = heightBtn,
                width = widthBtn,
                textSize = textSizeBtn
            )

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
                    uri -> VideoPreview(uri)
            }

            //fields for database
            if(capturedImageUri != null || capturedVideoUri != null){
                Spacer(modifier = Modifier.height(spaceBetween))

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it},
                    label = {Text("Title")}
                )

                Spacer(modifier = Modifier.height(spaceBetween))

                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it},
                    label = {Text("Category")}
                )

                Spacer(modifier = Modifier.height(spaceBetween))

                //button to Save
                ButtonsApp(
                    text= stringResource( id= R.string.save),
                    onClick = {
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
                    }
                )

                Spacer(modifier = Modifier.height(spaceBetween))

                //button to Cancel
                ButtonsApp(
                    text = stringResource( id= R.string.cancel),
                    onClick = {
                        capturedVideoUri = null
                        capturedImageUri = null
                        title = ""
                        category = ""

                    }
                )
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

    //shows the first frame of the video
    @Composable
    fun VideoPreview(uri: Uri){
        AndroidView(
            factory= {
                    context -> VideoView(context).apply{
                setVideoURI(uri)
            }
            },
            update = {
                    view -> view.setVideoURI(uri)
                view.start()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
    }

    fun saveMedia(uri: Uri, title: String, category: String, context: Context){
        //TODO: create database and function to save file
    }
}