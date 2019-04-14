package com.boronin.rasharash.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.TextView
import android.widget.Toast
import com.boronin.rasharash.R
import com.boronin.rasharash.models.vendor.ITunesMetaData
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.View, View.OnClickListener {

    private var presenter: MainPresenter = MainPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.attachView(this)
        presenter.viewIsReady()
    }

    override fun initUI() {
        send_button.setOnClickListener(this)
        search_button.setOnClickListener(this)

        val url = intent?.clipData?.getItemAt(0)?.text?.toString()
        presenter.onUpdateInput(url)
    }

    override fun showMainText(text: String) {
        song_name.setText(text, TextView.BufferType.EDITABLE)
    }

    override fun showFoundedUrl(text: String) {
        vendor_url.text = text
    }

    override fun enableLoading(isEnable: Boolean) {
        progress_bar.visibility = if (isEnable) VISIBLE else INVISIBLE
    }

    override fun enableShare(isEnable: Boolean) {
        send_button.isEnabled = isEnable
    }

    override fun shareLink(url: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, url)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(sendIntent, resources.getText(R.string.send_to)))
    }

    override fun showError(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.send_button -> presenter.onShare()
            R.id.search_button -> presenter.onSearchSongUrl(song_name.text.toString(), ITunesMetaData.INSTANCE)
        }
    }
}
