/* Mobile Development Project
   Bruna Heleno 3009733
 */
package com.example.brunaheleno_3009733_mdproject

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.brunaheleno_3009733_mdproject.database.FileRepository
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Switch
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.sp
import com.example.brunaheleno_3009733_mdproject.database.FileItem
import com.example.brunaheleno_3009733_mdproject.database.CommonFunctions
import com.example.brunaheleno_3009733_mdproject.ui.ButtonsApp
import com.example.brunaheleno_3009733_mdproject.ui.Menu

//this class represents the File screen: allows search, and shows files in a list with delete option
class Files : ComponentActivity() {

    private val home = R.string.home
    private val search = R.string.search
    private val camera = R.string.camera
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
        val repo = FileRepository(context) //connection with database

        var searchText by remember { mutableStateOf("") }
        var selectedCategory by remember { mutableStateOf("CATEGORIES") }
        var sortOrder by remember { mutableStateOf(true) }

        val categories = repo.getAllCategories() //get all categories from database

        //initial data
        val allFilesState = remember { mutableStateListOf<FileItem>() }
        LaunchedEffect(Unit) {
            allFilesState.clear()
            allFilesState.addAll(repo.getAll())
        }

        //filter according to title ignoring case; by category selected and order the list in uppercase according to switch
        val filtered = allFilesState
            .filter { it.title.contains(searchText, ignoreCase = true) }
            .filter { selectedCategory == "CATEGORIES" || it.category == selectedCategory }
            .let { list ->
                if(sortOrder){
                    list.sortedBy { it.title.uppercase() }
                }else{
                    list.sortedByDescending { it.title.uppercase() }
                }
            }

        Column {
            //Menu on top of the screen
            Menu(
                home,
                MainActivity::class.java,
                search,
                Search::class.java,
                camera,
                Camera::class.java
            )

            Spacer(modifier = Modifier.height(spaceBetween))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ){
                //search
                OutlinedTextField(
                    value = searchText,
                    onValueChange = {searchText = it},
                    label = {Text(stringResource(id= R.string.search))},

                    modifier = Modifier
                        .width(400.dp)
                        .background(colorResource(R.color.white), RoundedCornerShape(10.dp)),

                    shape = RoundedCornerShape(10.dp),
                )
            }

            Spacer(modifier = Modifier.height(spaceBetween))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
            ){
                DropDownCategory(selectedCategory, categories = categories, onChange = {selectedCategory = it}) //filter file by category selected
                SortSwitch(sortAscending = sortOrder, onToggle = {sortOrder = it}) //order files according to switch
            }

            LazyColumn {
                //showing files as a list: Title, category and delete button
                items(filtered){
                    item -> FileRow(item, repo, context, onRefresh = {}
                    )
                }
            }
        }
    }

    //this function is the dropdown menu for categories: it gets all categories in the database for user to select and filter files
    @Composable
    fun DropDownCategory(selected: String, categories: List<String>, onChange: (String) -> Unit) {
        var expanded by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = colorResource(R.color.dark_blue),
                    shape = RoundedCornerShape(10.dp)
                )
                .clip(RoundedCornerShape(10.dp))
                .clickable{expanded = true}
                .background(colorResource(R.color.white))
                .padding(horizontal = 5.dp, vertical = 5.dp)
        ) {
            Text(
                selected,
                Modifier
                    .clickable { expanded = true }
                    .padding(12.dp)
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                categories.forEach {
                    category -> DropdownMenuItem(
                        text = { Text(stringResource(R.string.category)) },
                        onClick = {
                            onChange(category)
                            expanded = false
                        }
                    )
                }
            }
        }
    }

    //switch for sort list by title from ascending (a->z) or descending (z->a)
    @Composable
    fun SortSwitch(sortAscending: Boolean, onToggle: (Boolean) -> Unit){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(8.dp)
        ){
            Text(
                text = "A -> Z",
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.width(spaceBetween)) //space between text and switch

            Switch(
                checked = sortAscending,
                onCheckedChange = onToggle,
                modifier = Modifier.scale(0.8f)
            )

            Spacer(modifier = Modifier.width(spaceBetween))

            Text(
                text = "Z -> A",
                fontSize = 16.sp
            )
        }
    }

    //showing files
    @Composable
    fun FileRow(item: FileItem, repo:FileRepository, context: Context, onRefresh: () -> Unit){
        var showDialog by remember { mutableStateOf(false) }
        var deleteFromDB by remember { mutableStateOf(true) }
        var deleteFromGallery by remember { mutableStateOf(false) }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable{CommonFunctions.openFile(context, item.uri)}
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column{
                Text(item.title, fontSize = 18.sp)
                Text(item.category, fontSize = 12.sp)
            }

            ButtonsApp(
                text = stringResource(R.string.delete),
                onClick = { showDialog = true }
            )
        }

        //when button delete is clicked open a dialog asking if want delete from database and/or gallery, allowing user to select the options
        if(showDialog){
            AlertDialog(
                onDismissRequest = {showDialog = false},
                title = {Text("DELETE file?")},
                text = {
                    Column{
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Checkbox(
                                checked = deleteFromDB,
                                onCheckedChange = {deleteFromDB = it}
                            )
                            Text("From DATABASE")
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Checkbox(
                                checked = deleteFromGallery,
                                onCheckedChange = {deleteFromGallery = it}
                            )
                            Text("From DEVICE")
                        }
                    }
                },
                dismissButton = {
                    ButtonsApp(
                        text = stringResource(R.string.cancel),
                        onClick = { showDialog = false },
                        backgroundColor = colorResource(R.color.light_purple)
                    )
                },
                confirmButton = {
                    ButtonsApp(
                        text = stringResource(R.string.confirm),
                        onClick = {
                            if (deleteFromDB) repo.delete(item.id)
                            if (deleteFromGallery) repo.deleteFromGallery(context, item.uri)
                            showDialog = false
                            onRefresh()
                        }
                    )
                }
            )
        }
    }
}