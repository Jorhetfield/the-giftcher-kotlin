package es.vanadis.utg_estaxi_profesional.setup.utils.extensions

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity

fun intentShare(context: Context, text: String?) {
    Intent().apply {
        action = "android.intent.action.SEND"
        putExtra("android.intent.extra.TEXT", text)
        type = "text/plain"

        context.startActivity(this)
    }
}

fun openYoutubeVideo(context: Context, videoId: String?) {

    try {
        val intentApp = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$videoId"))
        context.startActivity(intentApp)
    } catch (ex: ActivityNotFoundException) {
        val intentBrowser = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=$videoId"))
        context.startActivity(intentBrowser)
    }
}

fun openIntentInstagram(context: Context, link: String?) {

    val uri = Uri.parse(link)
    val likeIng = Intent(Intent.ACTION_VIEW, uri)

    likeIng.setPackage("com.instagram.android")

    try {
        context.startActivity(likeIng)
    } catch (e: ActivityNotFoundException) {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                uri
            )
        )
    }
}

fun openIntentFacebook(context: Context, link: String?) {

    val uri = Uri.parse(link)
    val likeIng = Intent(Intent.ACTION_VIEW, uri)

    likeIng.setPackage("com.facebook.android")

    try {
        context.startActivity(likeIng)
    } catch (e: ActivityNotFoundException) {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                uri
            )
        )
    }
}

fun openIntentLinkedin(context: Context, link: String?) {

    val uri = Uri.parse(link)
    val likeIng = Intent(Intent.ACTION_VIEW, uri)

    likeIng.setPackage("com.linkedin.android")

    try {
        context.startActivity(likeIng)
    } catch (e: ActivityNotFoundException) {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                uri
            )
        )
    }
}

fun openIntentTwitter(context: Context, link: String?) {

    val uri = Uri.parse(link)
    val likeIng = Intent(Intent.ACTION_VIEW, uri)

    likeIng.setPackage("com.twitter.android")

    try {
        context.startActivity(likeIng)
    } catch (e: ActivityNotFoundException) {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                uri
            )
        )
    }
}

fun AppCompatActivity.openIntentWeb(url: String?) {
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
}

fun openIntentWeb(context: Context, url: String) {
    if (Patterns.WEB_URL.matcher(url).matches()) {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }
}