package com.harry.depromeet.controller

import com.harry.depromeet.model.Place
import com.harry.depromeet.service.PlaceService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class PlaceController(
        val placeService: PlaceService
) {
    @ApiOperation(value = "Kakao Mapi API Parsing API")
    @GetMapping("/place/{place_id}")
    fun getPlace(@PathVariable(value = "place_id") placeId: Long): Place {
        return placeService.request(placeId)
    }
}