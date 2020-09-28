package com.harry.depromeet.service

import com.gargoylesoftware.htmlunit.WebClient
import com.gargoylesoftware.htmlunit.html.DomElement
import com.gargoylesoftware.htmlunit.html.HtmlDivision
import com.gargoylesoftware.htmlunit.html.HtmlPage
import com.harry.depromeet.model.Place
import org.springframework.stereotype.Service
import java.lang.Exception
import java.lang.NullPointerException

@Service
class ParseService {
    fun parseHTML(url: String): Place {
        WebClient().use {
            it.options.isCssEnabled = false
            it.options.isJavaScriptEnabled = true
            val page: HtmlPage = it.getPage(url)

            it.waitForBackgroundJavaScript(1000)

            val title: DomElement? = page.getFirstByXPath<DomElement>("//h2[@class='tit_location']")
            val point: DomElement? = page.getFirstByXPath<DomElement>("//span[@class='color_b']")
            val address: DomElement? = page.getFirstByXPath<DomElement>("//span[@class='txt_address']")
            val openDays: DomElement? = page.getFirstByXPath<DomElement>("//span[@class='txt_operation']")
            val openTime: DomElement? = page.getFirstByXPath<DomElement>("//span[@class='time_operation']")

            return Place(title?.textContent, point?.textContent, address?.textContent, openDays?.textContent?.trim(), openTime?.textContent?.trim())
        }
    }
}