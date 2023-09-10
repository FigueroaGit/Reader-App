package com.figueroa.readerapp.screens.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.figueroa.readerapp.components.FABContent
import com.figueroa.readerapp.components.ListCard
import com.figueroa.readerapp.components.ReaderAppBar
import com.figueroa.readerapp.components.TitleSection
import com.figueroa.readerapp.model.MBook
import com.figueroa.readerapp.navigation.ReaderScreens
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Home(
    navController: NavController = NavController(LocalContext.current),
    viewModel: HomeScreenViewModel = hiltViewModel(),
) {
    Scaffold(topBar = {
        ReaderAppBar(title = "Reader", navController = navController)
    }, floatingActionButton = {
        FABContent {
            navController.navigate(ReaderScreens.SearchScreen.name)
        }
    }) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            Surface(modifier = Modifier.fillMaxSize()) {
                HomeContent(navController = navController, viewModel)
            }
        }
    }
}

@Composable
fun HomeContent(navController: NavController, viewModel: HomeScreenViewModel) {
    var listOfBooks = emptyList<MBook>()
    val currentUser = FirebaseAuth.getInstance().currentUser

    if (!viewModel.data.value.data.isNullOrEmpty()) {
        listOfBooks = viewModel.data.value?.data!!.toList()!!.filter { mBook ->
            mBook.userId == currentUser?.uid.toString()
        }
        Log.d("Books", "HomeContent: $listOfBooks")
    }

    // val listOfBooks =
    //     listOf(
    //         MBook(id = "1234", title = "Hello Again", authors = "All of us", notes = null),
    //         MBook(id = "1234", title = "Hello Again", authors = "All of us", notes = null),
    //         MBook(id = "1234", title = "Hello Again", authors = "All of us", notes = null),
    //         MBook(id = "1234", title = "Hello Again", authors = "All of us", notes = null),
    //         MBook(id = "1234", title = "Hello Again", authors = "All of us", notes = null),
    //     )
    val email = FirebaseAuth.getInstance().currentUser?.email
    val currentUserName =
        if (!email.isNullOrEmpty()) {
            FirebaseAuth.getInstance().currentUser?.email?.split("@")?.get(0)
        } else {
            "N/A"
        }
    Column(modifier = Modifier.padding(2.dp), verticalArrangement = Arrangement.Top) {
        Row(modifier = Modifier.align(alignment = Alignment.Start)) {
            TitleSection(label = "Your Reading\n" + "activity right now")
            Spacer(modifier = Modifier.fillMaxWidth(0.7F))
            Column() {
                Icon(
                    imageVector = Icons.Rounded.AccountCircle,
                    contentDescription = "Profile",
                    modifier = Modifier
                        .clickable { navController.navigate(ReaderScreens.ReaderStatsScreen.name) }
                        .size(45.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = currentUserName!!,
                    modifier = Modifier.padding(2.dp),
                    style = MaterialTheme.typography.labelLarge,
                    fontSize = 15.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                )
                Divider()
            }
        }
        ReadingRightNowArea(books = listOf(), navController = navController)
        TitleSection(label = "Reading List")
        BookListArea(listOfBooks = listOfBooks, navController = navController)
    }
}

@Composable
fun BookListArea(listOfBooks: List<MBook>, navController: NavController) {
    HorizontalScrollableComponent(listOfBooks) {
        navController.navigate(ReaderScreens.UpdateScreen.name + "/$it")
    }
}

@Composable
fun HorizontalScrollableComponent(listOfBooks: List<MBook>, onCardPressed: (String) -> Unit) {
    val scrollState = rememberScrollState()
    Row(modifier = Modifier.fillMaxWidth().heightIn(280.dp).horizontalScroll(scrollState)) {
        for (book in listOfBooks) {
            ListCard(book) {
                onCardPressed(book.googleBookId.toString())
            }
        }
    }
}

@Composable
fun ReadingRightNowArea(books: List<MBook>, navController: NavController) {
}
