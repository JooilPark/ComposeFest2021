package com.lululab.mybasicscodelab2

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lululab.mybasicscodelab2.ui.theme.MyBasicsCodelab2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContent {
            MyBasicsCodelab2Theme {
                MyApp()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyBasicsCodelab2Theme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {

    var shouldShowOnBoarding by rememberSaveable { mutableStateOf(true) }
    if (shouldShowOnBoarding) {
        onBoardScreen(onCouninueClicked = { shouldShowOnBoarding = false })
    } else {
        Greetings()
    }
}

@Composable
fun Greetings(names: List<String> = List(100) { "$it" }) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(items = names) { item ->
            Greeting(name = item)
        }
    }
}

@Composable
fun Greeting(name: String) {
    Card(backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardContent(name = name)
    }
}

@Composable
fun CardContent(name: String) {
    val expanded = rememberSaveable { mutableStateOf(false) }
    val extraPadding = animateDpAsState(
        targetValue = if (expanded.value) 40.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy
        )
    )

    Row(modifier = Modifier
        .padding(12.dp)
        .animateContentSize(animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow))) {
        Column(modifier = Modifier
            .weight(1f)
            .padding(12.dp)) {
            Text(text = "Hello.")
            Text(text = name, style = MaterialTheme.typography.h4.copy(
                fontWeight = FontWeight.ExtraBold
            ))
            if (expanded.value) {
                Text(
                    text = ("Composem ipsum color sit lazy, " +
                            "padding theme elit, sed do bouncy. ").repeat(4),
                )
            }
        }
        IconButton(onClick = { expanded.value = !expanded.value }) {
            Icon(imageVector = if (expanded.value) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = if (expanded.value) stringResource(id = R.string.show_less) else stringResource(
                    id = R.string.show_more)
            )
        }
    }


}


@Composable
fun onBoardScreen(onCouninueClicked: () -> Unit) {
    Surface {
        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "어서와라 기본 코드연구소")
            Button(modifier = Modifier.padding(vertical = 24.dp), onClick = onCouninueClicked) {
                Text(text = "계속 진행")
            }
        }
    }

}

@Preview(showBackground = true, widthDp = 320, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreviewDark() {
    MyBasicsCodelab2Theme {
        Greetings()
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    MyBasicsCodelab2Theme {
        Greetings()
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun onBoardingPreView() {
    MyBasicsCodelab2Theme {
        onBoardScreen(onCouninueClicked = {})
    }
}