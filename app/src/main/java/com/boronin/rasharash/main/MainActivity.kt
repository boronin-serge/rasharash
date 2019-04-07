package com.boronin.rasharash.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import com.boronin.rasharash.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity :
    AppCompatActivity(),
    MainContract.View,
    View.OnClickListener {

    private var presenter: MainPresenter = MainPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.attachView(this)
        presenter.viewIsReady()
    }

    override fun initUI() {
        send_button.setOnClickListener(this)
    }

    override fun setMainText(text: String) {
        song_name.text = text
    }

    override fun readInputUrl() {
        val url = intent?.clipData?.getItemAt(0)?.text?.toString()
        presenter.onUpdateInput(url)
    }

    override fun enableLoading(isEnable: Boolean) {
        progress_bar.visibility = if (isEnable) VISIBLE else INVISIBLE
    }

    override fun shareLink(url: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, url)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(sendIntent, resources.getText(R.string.send_to)))
    }

    override fun showError() {
        Toast.makeText(this, "Что-то пошло не так...", Toast.LENGTH_SHORT).show()
    }


    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.send_button -> presenter.onShare()
        }
    }
}
