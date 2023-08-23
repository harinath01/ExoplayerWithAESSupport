package com.example.sampleexoplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.media3.common.AudioAttributes
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.okhttp.OkHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.ui.PlayerView
import okhttp3.OkHttpClient

@UnstableApi
class MainActivity : AppCompatActivity() {

    val AES_ENCRYPTED_VIDEO_URL = "https://d384padtbeqfgy.cloudfront.net/transcoded_private/B66mmRm2TPF/video.m3u8?Expires=1692809424&Signature=wJ6JZVQHbBEwyhTFy4uYOjK1DhDE9-MyzSO7AD2KnZ1emQxhbYBjSX9tyThG9ZqdMPzec1BKK5lj7LjTp5q9UrYu~VrtyhGDdLwHrQrWUHGE2VUzfokhJ73L8decpDFF3pS0gxHhfgnG~js3WHXORmFfmrSghmr50AOeSHQwyqTyK1eSrV~ufXe5sRQXXLltjYtXyXDWmE02fi1RsWiTvKmXkGVF66hAxFPFR3rGtuqYTC9TA-NYHJacL9HVqIgHLUKp67yXOyjqdSpYIZJaeCh3rk9U2liss7wv-U6pQ4fxCDf3TGOR3k-DG5smU1DEFjHhkCq-U~2wGADT0zGoJQ__&Key-Pair-Id=K2XWKDWM065EGO"
    val access_token = "79ef7b66-bcac-4129-bfe1-34ca3006e799"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        prepareExoplayer()
    }

    private fun prepareExoplayer() {
        val exoPlayer = ExoPlayer.Builder(this)
            .build()
            .also { exoPlayer ->
                exoPlayer.setAudioAttributes(AudioAttributes.DEFAULT, true)
                findViewById<PlayerView>(R.id.player_view).player = exoPlayer
            }
        exoPlayer.addMediaItem(getMediaItem(AES_ENCRYPTED_VIDEO_URL))
        exoPlayer.prepare()
    }

    private fun getMediaItem(sourseUrl: String): MediaItem {
        return MediaItem.Builder()
            .setUri(sourseUrl).build()
    }
}