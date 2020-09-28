package com.harry.depromeet.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonInclude(JsonInclude.Include.NON_NULL)
class Place(
        val title: String?,
        val score: String?,
        val address: String?,
        val openingDays: String?,
        val openingTimes: String?
)