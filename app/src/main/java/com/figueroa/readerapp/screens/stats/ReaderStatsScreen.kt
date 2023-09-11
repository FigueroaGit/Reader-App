package com.figueroa.readerapp.screens.stats

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.ThumbUp
import androidx.compose.material.icons.sharp.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.figueroa.readerapp.components.ReaderAppBar
import com.figueroa.readerapp.model.MBook
import com.figueroa.readerapp.screens.home.HomeScreenViewModel
import com.figueroa.readerapp.utils.formatDate
import com.google.firebase.auth.FirebaseAuth
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderStatsScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel(),
) {
    var books: List<MBook>
    val currentUser = FirebaseAuth.getInstance().currentUser

    Scaffold(topBar = {
        ReaderAppBar(
            title = "Book Stats",
            icon = Icons.Rounded.ArrowBack,
            isHomeScreen = false,
            navController = navController,
        ) {
            navController.popBackStack()
        }
    }) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            Surface() {
                books = if (!viewModel.data.value.data.isNullOrEmpty()) {
                    viewModel.data.value.data!!.filter { mBook ->
                        (mBook.userId == currentUser?.uid)
                    }
                } else {
                    emptyList()
                }
                Column {
                    Row {
                        Box(modifier = Modifier.size(45.dp).padding(2.dp)) {
                            Icon(imageVector = Icons.Sharp.Person, contentDescription = "icon")
                        }
                        Text(
                            text = "Hi, ${
                                currentUser?.email.toString().split("@")[0].uppercase(
                                    Locale.getDefault(),
                                )
                            }",
                        )
                    }
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(4.dp),
                        shape = CircleShape,
                        elevation = CardDefaults.cardElevation(5.dp),
                    ) {
                        val readBookList: List<MBook> =
                            if (!viewModel.data.value.data.isNullOrEmpty()) {
                                books.filter { mBook -> (mBook.userId == currentUser?.uid) && (mBook.finishedReading != null) }
                            } else {
                                emptyList()
                            }

                        val readingBooks =
                            books.filter { mBook -> (mBook.startedReading != null) && (mBook.finishedReading == null) }

                        Column(
                            modifier = Modifier.padding(
                                start = 25.dp,
                                top = 4.dp,
                                bottom = 4.dp,
                            ),
                            horizontalAlignment = Alignment.Start,
                        ) {
                            Text(text = "Your Stats", style = MaterialTheme.typography.titleLarge)
                            Divider()
                            Text(text = "You're reading: ${readingBooks.size} books")
                            Text(text = "You've read: ${readBookList.size} books")
//
                        }
//
                    }
                    if (viewModel.data.value.loading == true) {
                        LinearProgressIndicator()
                    } else {
                        Divider()
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                            contentPadding = PaddingValues(16.dp),
                        ) {
                            val readBooks: List<MBook> =
                                if (!viewModel.data.value.data.isNullOrEmpty()) {
                                    viewModel.data.value.data!!.filter { mBook ->
                                        (mBook.userId == currentUser?.uid) && (mBook.finishedReading != null)
                                    }
                                } else {
                                    emptyList()
                                }
                            items(items = readBooks) { book ->
                                BookRowStats(book = book)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BookRowStats(book: MBook) {
    Card(
        modifier = Modifier.clickable {
            // navController.navigate(ReaderScreens.DetailsScreen.name + "/${book.id}")
        }.fillMaxWidth().height(100.dp).padding(3.dp),
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(7.dp),
        colors = CardDefaults.cardColors(Color.White),
    ) {
        Row(modifier = Modifier.padding(5.dp), verticalAlignment = Alignment.Top) {
            val imageURL = if (book.photoURL.toString().isEmpty()) {
                "http://bookcoverarchive.com/wp-content/uploads/2010/09/the_last_skin.large_.jpg"
            } else {
                book.photoURL.toString()
            }

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(imageURL).build(),
                contentDescription = "Book Image",
                modifier = Modifier.width(80.dp).fillMaxHeight().padding(end = 4.dp),
            )
            Column() {
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = book.title.toString(), overflow = TextOverflow.Ellipsis)
                    if (book.rating!! >= 4) {
                        Spacer(modifier = Modifier.fillMaxWidth(0.8F))
                        Icon(
                            imageVector = Icons.Rounded.ThumbUp,
                            contentDescription = "Thumbs up",
                            tint = Color.Green.copy(alpha = 0.5F),
                        )
                    } else {
                        Box {
                        }
                    }
                }

                Text(
                    text = "Author: ${book.authors}",
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.labelMedium,
                )
                Text(
                    text = "Started: ${formatDate(book.startedReading!!)}",
                    softWrap = true,
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.labelMedium,
                )
                Text(
                    text = "Finished: ${formatDate(book.finishedReading!!)}",
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.labelMedium,
                )
            }
        }
//
    }
}
