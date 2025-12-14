package com.example.brunaheleno_3009733_mdproject.database

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.io.File

//this object contain common function used in Camera, Search and File Screen
object CommonFunctions {
    //image preview
    fun loadBitmapFromUri(context: Context, uri: Uri): ImageBitmap{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            ImageDecoder.decodeBitmap(source).asImageBitmap()
        }else{
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri).asImageBitmap()
        }
    }

    //open files
    fun openFile(context: Context, uri: String){
        val intent = Intent(Intent.ACTION_VIEW).apply{
            setDataAndType(Uri.parse(uri), "*/*")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(intent)
    }

    //thumbnail - photo for Search Screen
    fun loadThumbnail(context: Context, uri: Uri): Bitmap?{
        //API 29+
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            return try{
                val source = ImageDecoder.createSource(context.contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            }catch(e:Exception){
                e.printStackTrace()
                null
            }
        }

        //API 28-
        return try{
            context.contentResolver.openInputStream(uri)?.use {
                input -> BitmapFactory.decodeStream(input)
            }
        }catch(e: Exception){
            e.printStackTrace()
            null
        }
    }

    //thumbnail - video for Search Screen
    fun loadVideoThumbnail(uri: Uri): Bitmap? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            try {
                val file = File(uri.path!!)
                ThumbnailUtils.createVideoThumbnail(
                    file,
                    android.util.Size(300, 300),
                    null
                )
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        } else {
            try {
                ThumbnailUtils.createVideoThumbnail(
                    uri.path!!,
                    MediaStore.Images.Thumbnails.MINI_KIND
                )
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}