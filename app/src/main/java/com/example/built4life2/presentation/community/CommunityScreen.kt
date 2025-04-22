package com.example.built4life2.presentation.community

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat

@Composable
fun CommunityScreen() {
        val htmlContent = """
<p>Join our Discord Community <a href="https://discord.gg/Hjqkgs4Kan">here</a></p>
""".trimIndent()
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            HtmlText(
                html = htmlContent,
                linkColor = MaterialTheme.colorScheme.primary,
                textColor = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp,
                fontWeight = FontWeight.W400
            )
    }
}


@Composable
fun HtmlText(
    modifier: Modifier = Modifier,
    html: String,
    linkColor: Color, // Default link color
    textColor: Color,
    fontSize: TextUnit = 11.sp,
    fontWeight: FontWeight = FontWeight.Normal
) {
    val uriHandler = LocalUriHandler.current
    val annotatedText = remember(html) {
        val spanned = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)
        val text = spanned.toString()
        buildAnnotatedString {
            append(text)
            val urlSpans =
                spanned.getSpans(0, spanned.length, android.text.style.URLSpan::class.java)
            urlSpans.forEach { urlSpan ->
                val start = spanned.getSpanStart(urlSpan)
                val end = spanned.getSpanEnd(urlSpan)
                val url = urlSpan.url
                addStyle(
                    style = SpanStyle(
                        color = linkColor,
                        fontSize = fontSize,
                        fontWeight = FontWeight.SemiBold,
                        textDecoration = TextDecoration.Underline
                    ), start = start, end = end
                )
                addLink(
                    url = LinkAnnotation.Url(
                        url = url,
                        linkInteractionListener = {
                            uriHandler.openUri(url)
                        }
                    ),
                    start = start,
                    end = end
                )
            }
        }
    }


    Text(
        text = annotatedText,
        style = TextStyle(
            color = textColor,
            fontSize = fontSize,
            fontWeight = fontWeight,
            textAlign = TextAlign.Center
        )
    )
}