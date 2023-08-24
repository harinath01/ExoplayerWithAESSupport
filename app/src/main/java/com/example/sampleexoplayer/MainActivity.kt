package com.example.sampleexoplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.ext.okhttp.OkHttpDataSource
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.ui.PlayerView
import okhttp3.OkHttpClient

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
        exoPlayer.setMediaSource(getMediaSourceFactory().createMediaSource(getMediaItem(AES_ENCRYPTED_VIDEO_URL)))
        exoPlayer.prepare()
    }

    private fun getMediaSourceFactory(): MediaSource.Factory {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                var request = chain.request()
                if (request.url.toString().contains("aes_key")) {
                    request = request.newBuilder()
                        .url("${request.url}?access_token=$access_token")
                        .build()
                }
                chain.proceed(request)
            }
            .build()
        val httpDataSourceFactory: OkHttpDataSource.Factory = OkHttpDataSource.Factory(okHttpClient)
        return DefaultMediaSourceFactory(this)
            .setDataSourceFactory(httpDataSourceFactory)
    }

    private fun getMediaItem(sourseUrl: String): MediaItem {
        return MediaItem.Builder()
            .setUri(sourseUrl).build()
    }
}