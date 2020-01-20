package com.rahul.kotlin.spi

import java.net.URL
import java.net.URLClassLoader

/**
 *
 * @author in-rahul.khandelwal
 */
class CustomUrlClassLoader(urls: Array<URL>) : URLClassLoader (urls) {

    public override fun addURL(url: URL?) {
        super.addURL(url)
    }
}
