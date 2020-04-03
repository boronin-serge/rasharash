package com.boronin.rasharash.utils

import androidx.core.text.HtmlCompat
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class HtmlParser private constructor(){

    fun getJsonFromHtml(html: String): JSONObject {
        val output= HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY)
            .toString()
            .apply {
                substring(this.indexOf("{"),this.lastIndexOf("}") + 1)
            }
        return JSONObject(output)
    }

    fun getHtmlData(strJsonData: String): String {
        return jsonToHtml(JSONObject(strJsonData))
    }

    private fun jsonToHtml(obj: Any): String {
        val html = StringBuilder()

        try {
            when (obj) {
                is JSONObject -> {
                    val keys = obj.keys()

                    html.append("<div class=\"json_object\">")

                    for (key in keys) {
                        // print the key and open a DIV
                        html.append("<div><span class=\"json_key\">")
                            .append(key).append("</span> : ")

                        val `val` = obj.get(key)
                        // recursive call
                        html.append(jsonToHtml(`val`))
                        // close the div
                        html.append("</div>")
                    }

                    html.append("</div>")
                }
                is JSONArray -> for (i in 0 until obj.length()) {
                    // recursive call
                    html.append(jsonToHtml(obj.get(i)))
                }
                else -> // print the value
                    html.append(obj)
            }
        } catch (e: JSONException) {
            return e.localizedMessage
        }

        return html.toString()
    }

    companion object {
        val INSTANCE = HtmlParser()
    }
}