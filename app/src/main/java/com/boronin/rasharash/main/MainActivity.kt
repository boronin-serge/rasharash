package com.boronin.rasharash.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.KeyEvent
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import com.boronin.rasharash.R
import com.boronin.rasharash.models.SearchResult
import com.boronin.rasharash.models.SongInfo
import com.boronin.rasharash.models.vendor.ITunesMetaData
import com.boronin.rasharash.models.vendor.VendorMetaData
import com.boronin.rasharash.utils.SystemHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.View, View.OnClickListener,
    TextView.OnEditorActionListener, VendorsAdapter.ItemClickListener {
    private var presenter: MainPresenter = MainPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.attachView(this)
        presenter.viewIsReady()
    }

    override fun initUI() {
        song_name.setOnEditorActionListener(this)
        search_button.setOnClickListener(this)
        clear_button.setOnClickListener(this)

        vendors.layoutManager = LinearLayoutManager(this)
        vendors.adapter = VendorsAdapter(ArrayList(), this)

        val url = intent?.clipData?.getItemAt(0)?.text?.toString()
        presenter.onUpdateInput(url)
    }

    override fun showMainText(text: String) {
        song_name.setText(text, TextView.BufferType.EDITABLE)
    }

    override fun showFoundedSong(searchResult: SearchResult) {
        val list = ArrayList<SearchResult>()
        list.add(searchResult)
        (vendors.adapter as VendorsAdapter).update(list)
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

    override fun showError(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.search_button -> presenter.onSearchSongUrl(song_name.text.toString(), ITunesMetaData.INSTANCE)
            R.id.clear_button -> song_name.text.clear()
        }
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            presenter.onSearchSongUrl(song_name.text.toString())
            SystemHelper.INSTANCE.hideKeyboard(this, song_name)
            return true
        }
        return false
    }

    override fun resultItemClicked(outputUrl: String) {
        shareLink(outputUrl)
    }
}
