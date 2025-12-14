/* Mobile Development Project
   Bruna Heleno 3009733
 */
package com.example.brunaheleno_3009733_mdproject.database

//this class represent a row in the database, instead of retrieve variables, retrieve an object
data class FileItem(
    val id: Int,
    val title: String,
    val category: String,
    val uri: String,
    val timestamp: Long
)