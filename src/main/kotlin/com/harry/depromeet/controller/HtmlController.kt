package com.harry.depromeet.controller

import com.harry.depromeet.model.Place
import com.harry.depromeet.service.ParseService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class HtmlController(
        val parseService: ParseService
) {
    @GetMapping("/place")
    fun getPlace(@RequestParam url: String): Place {
        return parseService.parseHTML(url)
    }
}