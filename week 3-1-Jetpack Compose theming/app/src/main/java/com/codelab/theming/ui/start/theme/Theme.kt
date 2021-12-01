package com.codelab.theming.ui.start.theme

import MyJetnewsShapes
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.codelab.theming.ui.finish.theme.Red700
import com.codelab.theming.ui.finish.theme.Red800
import com.codelab.theming.ui.finish.theme.Red900

@Composable
fun MyJetNewsTheme( darkTheme : Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit ) {
    MaterialTheme(shapes = MyJetnewsShapes, content = content,
        colors = if(darkTheme) MyDarkColors else LightColors, typography = MyJetnewsTypography

    )
}

private val LightColors = lightColors(
    primary = MyRed700,
    primaryVariant = MyRed900,
    onPrimary = Color.White,
    secondary = MyRed700,
    secondaryVariant = MyRed900,
    onSecondary = Color.White,
    error = MyRed800
)
private val MyDarkColors = darkColors(
    primary = MyRed300,
    primaryVariant = Red700,
    onPrimary = Color.Black,
    secondary = MyRed300,
    onSecondary = Color.Black,
    error = MyRed200
)