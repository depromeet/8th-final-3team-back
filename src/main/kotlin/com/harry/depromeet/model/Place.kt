package com.harry.depromeet.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
class Place(
        val placeId: Long?,
        val title: String?,
        val photo: String?,
        val address: String?,
        val zipCode: String?,
        val point: String?
)