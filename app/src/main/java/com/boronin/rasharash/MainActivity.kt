package com.boronin.rasharash

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    var sourceSongUrl: String? = null
    var songInfo: SongInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sourceSongUrl = intent?.clipData?.getItemAt(0)?.text?.toString()

        sourceSongUrl?.let {
            val task = HtmlLoader(it).getSongName()
            task.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ foundedSong ->
                    foundedSong?.let{
                        song_name.text = it.name
                    }
                    songInfo = foundedSong
                    progress_bar.visibility = INVISIBLE
                }, {
                    Toast.makeText(this, "Что-то пошло не так...", Toast.LENGTH_SHORT)
                        .show()
                    progress_bar.visibility = INVISIBLE
                })
        }

        initUI()
    }

    private fun initUI() {
        send_button.setOnClickListener(this)
        sourceSongUrl?.let {
            song_name.text = it
            progress_bar.visibility = VISIBLE
        }
    }

    private fun send() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, songInfo?.itunesUrl)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(sendIntent, resources.getText(R.string.send_to)))
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.send_button -> send()
        }
    }
}
