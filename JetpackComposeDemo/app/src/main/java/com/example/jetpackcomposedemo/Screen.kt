package com.example.jetpackcomposedemo

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PreviewItem() {
    LazyColumn(content = {
        items(getItems()) {
            Items(img = it.img, name = it.name, dev = it.dev)
        }
    })
}

@Composable
fun Items(img: Int, name: String, dev: String) {
    Row(modifier = Modifier.padding(10.dp)) {
        Image(
            painter = painterResource(id = img),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .border(2.dp, Color.LightGray, CircleShape)
                .weight(0.2f),
            contentDescription = ""
        )
        Column(
            modifier = Modifier
                .padding(8.dp)
                .weight(0.8f)
        ) {
            Text(text = name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(text = dev, fontSize = 18.sp, fontWeight = FontWeight.Thin)
        }

    }
}

data class Category(var img: Int, var name: String, var dev: String)

fun getItems(): MutableList<Category> {
    val list = mutableListOf<Category>()
    list.add(Category(R.drawable.image, "Jay", "Android"))
    list.add(Category(R.drawable.image, "Jimmy", "Web"))
    list.add(Category(R.drawable.image, "Jack", "Flutter"))
    list.add(Category(R.drawable.image, "John", "Software"))
    list.add(Category(R.drawable.image, "Jimmy", "Graphics"))
    list.add(Category(R.drawable.image, "Dev", "UI/UX"))
    list.add(Category(R.drawable.image, "Jay", "Android"))
    list.add(Category(R.drawable.image, "John", "Web"))
    list.add(Category(R.drawable.image, "Jack", "Flutter"))
    list.add(Category(R.drawable.image, "John", "Software"))
    list.add(Category(R.drawable.image, "Jimmy", "Graphics"))
    list.add(Category(R.drawable.image, "Dev", "UI/UX"))
    return list
}