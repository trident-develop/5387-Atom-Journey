package com.vfg.silkroad.goo.ui.components

import java.net.URLEncoder

class ScoreBuilder(
    private val baseUrl: String
) {

    fun build(data: Map<String, String>): String {

        val referrer = data["referrer"].orEmpty()
        val gadid = data["gadid"].orEmpty()
        val device = data["device"].orEmpty()
        val probe = data["probe"].orEmpty()
        val firstInst = data["package"].orEmpty()
        val firebaseId = data["firebase_id"].orEmpty()

        val url = buildString {
            append(baseUrl)
            append("hmcx3t")
            append("?sg2bs48j7=").append(referrer.encode())
            append("&cthhv33=").append(gadid.encode())
            append("&xr14fqdg=").append(device.encode())
            append("&jb6m6gzy5=").append(probe.encode())
            append("&f8szr=").append(firstInst.encode())
            append("&gypj2=").append(firebaseId.encode())
        }

//        log("LinkBuilder: final url = $url")

        return url
    }

    private fun String.encode(): String {
        return URLEncoder.encode(this, "UTF-8")
    }
}