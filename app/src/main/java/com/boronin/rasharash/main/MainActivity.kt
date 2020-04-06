package com.boronin.rasharash.main

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.boronin.rasharash.R
import com.boronin.rasharash.models.SearchResult
import com.boronin.rasharash.utils.SystemHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.View, View.OnClickListener,
    TextView.OnEditorActionListener, VendorsAdapter.ItemClickListener {
    private var presenter: MainPresenter = MainPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.attachView(this)

        initListeners()
        initList()
        parseInputIntent()
    }

    // region MainContract.View

    override fun preFillInput(text: String) {
        etTrackName.setText(text, TextView.BufferType.EDITABLE)
    }

    override fun showSearchResult(searchResult: SearchResult) {
        (rvVendorList.adapter as VendorsAdapter).update(listOf(searchResult))
    }

    override fun enableLoading(isEnable: Boolean) {
        progressBar.visibility = if (isEnable) VISIBLE else INVISIBLE
    }

    override fun showError(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    override fun shareLink(url: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, url)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(sendIntent, resources.getText(R.string.send_to)))
    }

    // endregion


    // region Listeners

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btnClear -> etTrackName.text?.clear()
        }
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            presenter.onSearchSongByName(etTrackName.text.toString())
            SystemHelper.hideKeyboard(this, etTrackName)
            return true
        }
        return false
    }

    override fun resultItemClicked(outputUrl: String) {
        shareLink(outputUrl)
    }

    // endregion


    // region private

    private fun initList() {
        rvVendorList.layoutManager =
            LinearLayoutManager(this)
        rvVendorList.adapter = VendorsAdapter(this)
    }

    private fun parseInputIntent() {
        val url = intent?.clipData?.getItemAt(0)?.text?.toString()
        presenter.searchSongByUrl(url)
    }

    private fun initListeners() {
        etTrackName.setOnEditorActionListener(this)
        btnClear.setOnClickListener(this)
    }

    // endregion
}
