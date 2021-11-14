package com.example.mylayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberImagePainter
import com.example.mylayout.ui.theme.MylayoutTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MylayoutTheme {
                // A surface container using the 'background' color from the theme
                LayoutCodeLab()
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MylayoutTheme {
        LayoutCodeLab()

    }
}

@Composable
fun LayoutCodeLab() {
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = "레이아웃 코드랩") }, actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Filled.Favorite, contentDescription = "설명 !")
                }
            }
        )
    }) { innerpadding ->
        Column {
            PhotoGrapherCard(Modifier
                .padding(innerpadding)
                .padding(8.dp))
            BodyContent(Modifier
                .padding(innerpadding)
                .padding(8.dp))
            listContent(Modifier
                .padding(innerpadding)
                .padding(10.dp))
        }

    }
}

@Composable
fun BodyContent(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(text = "여러분 안녕 !")
        Text(text = "레이아웃 코드랩을 해줘서 감사!")
    }

}

@Composable
@Preview
fun ChipPreView() {
    MylayoutTheme {
        BodyContentChip(Modifier)
    }
}

@Composable
fun BodyContentChip(modifier: Modifier = Modifier) {
    Row(modifier = modifier
        .background(color = Color.LightGray)
        .size(200.dp)
        .padding(16.dp)
        .horizontalScroll(rememberScrollState())) {
        StaggeredGrid(modifier = modifier) {
            for (topic in topics) {
                Chip(modifier = Modifier.padding(8.dp), text = topic)
            }
        }
    }

}

@Composable
fun StaggeredGrid(
    modifier: Modifier = Modifier,
    rows: Int = 3,
    content: @Composable () -> Unit,
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        val rowWidths = IntArray(rows) { 0 }
        val rowHeights = IntArray(rows) { 0 }
        val placeables = measurables.mapIndexed { index, measurable ->

            // Measure each child
            val placeable = measurable.measure(constraints)

            // Track the width and max height of each row
            val row = index % rows
            rowWidths[row] += placeable.width
            rowHeights[row] = Math.max(rowHeights[row], placeable.height)

            placeable
        }
        val width = rowWidths.maxOrNull()
            ?.coerceIn(constraints.minWidth.rangeTo(constraints.maxWidth)) ?: constraints.minWidth

        // Grid's height is the sum of the tallest element of each row
        // coerced to the height constraints
        val height = rowHeights.sumOf { it }
            .coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))

        // Y of each row, based on the height accumulation of previous rows
        val rowY = IntArray(rows) { 0 }
        for (i in 1 until rows) {
            rowY[i] = rowY[i - 1] + rowHeights[i - 1]
        }
        layout(width, height) {
            // x cord we have placed up to, per row
            val rowX = IntArray(rows) { 0 }

            placeables.forEachIndexed { index, placeable ->
                val row = index % rows
                placeable.placeRelative(
                    x = rowX[row],
                    y = rowY[row]
                )
                rowX[row] += placeable.width
            }
        }
    }
}


val topics = listOf(
    "Arts & Crafts", "Beauty", "Books", "Business", "Comics", "Culinary",
    "Design", "Fashion", "Film", "History", "Maths", "Music", "People", "Philosophy",
    "Religion", "Social sciences", "Technology", "TV", "Writing"
)

@Composable
fun Chip(modifier: Modifier = Modifier, text: String) {
    Card(modifier = modifier,
        border = BorderStroke(color = Color.Black, width = Dp.Hairline),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(16.dp, 16.dp)
                    .background(color = MaterialTheme.colors.secondary)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = text)
        }
    }
}

@Composable
fun listContent(modifier: Modifier = Modifier) {
    val scrollstatetext = rememberLazyListState()

    val listsize = 100
    val scrollstateImage = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()


    Column {
        Row(modifier = Modifier.padding(4.dp)) {
            Button(onClick = {
                coroutineScope.launch { scrollstateImage.animateScrollToItem(0) }
            }) {
                Text(text = "가장위로")
            }
            Button(onClick = {
                coroutineScope.launch { scrollstateImage.animateScrollToItem(listsize) }
            }) {
                Text(text = "가장아래로")
            }
        }
        Row {
            LazyColumn(modifier = modifier, state = scrollstatetext) {
                items(100) {
                    Text(text = "아이템 #$it")
                }
            }
            LazyColumn(modifier = modifier, state = scrollstateImage) {
                items(100) {
                    ImageListContent(it)
                }
            }
        }

    }
}

@Composable
fun ImageListContent(index: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(painter = rememberImagePainter(data = "https://developer.android.com/images/brand/Android_Robot.png"),
            contentDescription = "로봇",
            modifier = Modifier.size(50.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = "Item #$index", style = MaterialTheme.typography.subtitle1)
    }

}

@Composable
fun PhotoGrapherCard(modifier: Modifier = Modifier) {
    Row(modifier
        .padding(8.dp)
        .clip(RoundedCornerShape(4.dp))
        .background(MaterialTheme.colors.surface)
        .clickable(onClick = {})
        .padding(16.dp)
    ) {
        Surface(
            Modifier.size(50.dp),
            CircleShape,
            MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
        ) {

        }
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text("알프레드 시이스을리 ~", fontWeight = FontWeight.Bold)
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(text = "3분전에 왔다감", style = MaterialTheme.typography.body2)
            }
        }
    }
}

@Composable
fun ConstraintLayoutContent() {
    ConstraintLayout(modifier = Modifier.background(color = Color.White,
        shape = RoundedCornerShape(15.dp)).padding(16.dp)) {
        val (button1, button2, text) = createRefs()
        Button(onClick = { /*TODO*/ }, modifier = Modifier.constrainAs(button1) {
            top.linkTo(parent.top, margin = 16.dp)
        }) {
            Text(text = "Button1")
        }
        Text(text = "Text", Modifier.constrainAs(text) {
            top.linkTo(button1.bottom, margin = 16.dp)
            centerHorizontallyTo(parent)

        })
        val barrier = createEndBarrier(button1, text)
        Button(onClick = { /*TODO*/ }, modifier = Modifier.constrainAs(button2) {
            top.linkTo(parent.top, margin = 16.dp)
            start.linkTo(button1.end, margin = 16.dp)
            //start.linkTo(barrier)
        }) {
            Text(text = "Button2")
        }

        val text1 = createRef()
        val guideline = createGuidelineFromStart(fraction = 0.5f)
        Text(text = "이건 정말 매우매우매우매우매우매우매우매우 긴 텍스트이다", Modifier.constrainAs(text1) {
            linkTo(start = guideline, end = parent.end)
            top.linkTo(text.bottom, margin = 16.dp)
            width = Dimension.preferredWrapContent
        }
        )
        val rowinreinsics = createRef()
        TeoTexts(text1 = "하아이!" , text2 = "여기좀 보게나" , modifier = Modifier.constrainAs(rowinreinsics){
            top.linkTo(text1.bottom , margin = 16.dp)
        })

    }
}

@Composable
fun TeoTexts(modifier: Modifier = Modifier, text1: String, text2: String) {
    Row(modifier = modifier.height(IntrinsicSize.Min)) {
        Text(text = text1, modifier = Modifier
            .weight(1f)
            .padding(start = 4.dp)
            .wrapContentWidth(Alignment.Start))
        Divider(color = Color.Black, modifier = Modifier
            .fillMaxHeight()
            .width(1.dp))
        Text(text = text2, modifier = Modifier
            .padding(end = 4.dp)
            .weight(1f)
            .wrapContentWidth(
                Alignment.End))
    }

}


@Preview
@Composable
fun ConstraintLayoutPreView() {
    MylayoutTheme {
        ConstraintLayoutContent()
    }
}



