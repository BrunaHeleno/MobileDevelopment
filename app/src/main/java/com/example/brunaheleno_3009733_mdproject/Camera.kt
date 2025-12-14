/* Mobile Development Project
   Bruna Heleno 3009733
 */

package com.example.brunaheleno_3009733_mdproject

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.MediaController
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.brunaheleno_3009733_mdproject.database.FileRepository
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.res.colorResource
import com.example.brunaheleno_3009733_mdproject.database.CommonFunctions
import com.example.brunaheleno_3009733_mdproject.ui.ButtonsApp
import com.example.brunaheleno_3009733_mdproject.ui.ButtonsAppBig
import com.example.brunaheleno_3009733_mdproject.ui.Menu

//Camera: responsible for open camera: take photo and video, get database input from user and save file
class Camera : ComponentActivity() {
    private val home = R.string.home
    private val search = R.string.search
    private val files = R.string.files
    private var photoUri: Uri? = null
    private var videoUri: Uri? = null
    private val spaceBetween = 15.dp // space between items in the Column layout - Spacer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Screen()
        }
    }

    @Composable
    fun Screen(){
        val context = LocalContext.current
        val repo = remember { FileRepository(context) } //connection with database
        var capturedImageUri by remember { mutableStateOf<Uri?>(null) }
        var capturedVideoUri by remember { mutableStateOf<Uri?>(null) }
        var title by remember { mutableStateOf("")}
        var category by remember { mutableStateOf("")}

        //launcher for photos and videos - handle lifecycle, restore pending result, don't lose callback
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

            //buttons appear just when there's no photo or video
            if(capturedImageUri == null && capturedVideoUri == null){
                //Button to take photo
                ButtonsAppBig(
                    text = stringResource(R.string.take_photo),
                    onClick = {
                        photoUri = createMediaUri("IMG_", ".jpg")
                        takePhotoLauncher.launch(photoUri!!)
                    },
                )

                Spacer(modifier = Modifier.height(spaceBetween))

                //Button to take video
                ButtonsAppBig(
                    text = stringResource(R.string.record_video),
                    onClick = {
                        videoUri = createMediaUri("VID_", ".mp4")
                        recordVideoLauncher.launch(videoUri!!)
                    },
                )
            }

            //show photo that was taken by user
            capturedImageUri?.let{
                uri -> Image(
                    bitmap = CommonFunctions.loadBitmapFromUri(context, uri),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
            }

            //show first frame of video taken
            capturedVideoUri?.let{
                    uri -> VideoPreview(uri)
            }

            //fields for database
            if(capturedImageUri != null || capturedVideoUri != null){
                Spacer(modifier = Modifier.height(spaceBetween))

                //title
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it},
                    label = {Text(stringResource(R.string.title_field))}
                )

                Spacer(modifier = Modifier.height(spaceBetween))

                //category
                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it},
                    label = {Text(stringResource(R.string.category))}
                )

                Spacer(modifier = Modifier.height(spaceBetween))

                Row(
                    modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    ){

                    //button to Cancel
                    ButtonsApp(
                        text = stringResource(id = R.string.cancel),
                        onClick = {
                            capturedVideoUri = null
                            capturedImageUri = null
                            title = ""
                            category = ""
                        },
                        backgroundColor = colorResource(id = R.color.light_purple)
                    )

                    //button to Save
                    ButtonsApp(
                        text = stringResource(id = R.string.save),
                        onClick = {
                            val savedUri = capturedImageUri ?: capturedVideoUri

                            if (savedUri != null) {
                                repo.insert(
                                    title = title,
                                    category = category,
                                    uri = savedUri.toString(),
                                    timestamp = System.currentTimeMillis()
                                )

                                //resetting everything
                                capturedVideoUri = null
                                capturedImageUri = null
                                title = ""
                                category = ""
                            }
                        }
                    )
                }
            }
        }
    }

    //save on gallery
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

    //video preview with controllers
    @Composable
    fun VideoPreview(uri: Uri){
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            factory= {
                context -> VideoView(context).apply{
                    setVideoURI(uri)

                    //controls for video
                    val controller = MediaController(context)
                    controller.setAnchorView(this)
                    setMediaController(controller)

                    //shows the first frame of the video
                    setOnPreparedListener {
                        mp -> mp.isLooping = false
                        seekTo(1)
                    }
                }
            },
            update = {
                view -> view.setVideoURI(uri)
            }
        )
    }
}