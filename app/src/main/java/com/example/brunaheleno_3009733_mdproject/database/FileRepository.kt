/* Mobile Development Project
   Bruna Heleno 3009733
 */
package com.example.brunaheleno_3009733_mdproject.database

import android.content.ContentValues
import android.content.Context
import android.net.Uri

//this class is a link between database and the screens (middleware), it centralized CRUD operations
class FileRepository (context: Context) {
    private val dbHelper = Database(context)

    //add file on database
    fun insert(title: String, category: String, uri: String, timestamp: Long){
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(Database.TITLE, title)
            put(Database.CATEGORY, category)
            put(Database.URI, uri)
            put(Database.TIMESTAMP, timestamp)
        }

        db.insert(Database.TABLE_NAME, null, values)
        db.close()
    }

    //retrieve all files in alphabetical order
    fun getAll(): List<FileItem>{
        val list = mutableListOf<FileItem>()
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM ${Database.TABLE_NAME} ORDER BY ${Database.TITLE} ASC", null)

        if(cursor.moveToFirst()){
            do{
                list.add(
                    FileItem(
                        id = cursor.getInt(cursor.getColumnIndexOrThrow(Database.ID)),
                        title = cursor.getString(cursor.getColumnIndexOrThrow(Database.TITLE)),
                        category = cursor.getString(cursor.getColumnIndexOrThrow(Database.CATEGORY)),
                        uri = cursor.getString(cursor.getColumnIndexOrThrow(Database.URI)),
                        timestamp = cursor.getLong(cursor.getColumnIndexOrThrow(Database.TIMESTAMP))
                    )
                )
            }while(cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return list
    }

    //get all categories
    fun getAllCategories(): List<String>{
        val db = dbHelper.readableDatabase
        val list = mutableListOf<String>()

        val cursor = db.rawQuery(
            "SELECT DISTINCT ${Database.CATEGORY} FROM ${Database.TABLE_NAME}",null
        )

        if(cursor.moveToFirst()){
            do{
                list.add(cursor.getString(0))
            }while(cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return list
    }

    //delete file from database
    fun delete(id:Int){
        val db = dbHelper.writableDatabase
        db.delete(Database.TABLE_NAME, "${Database.ID} = ?", arrayOf(id.toString()))
        db.close()
    }

    //delete from gallery
    fun deleteFromGallery(context: Context, uri:String){
        context.contentResolver.delete(Uri.parse(uri), null, null)
    }
}