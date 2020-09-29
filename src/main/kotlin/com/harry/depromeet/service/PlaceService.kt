package com.harry.depromeet.service

import com.fasterxml.jackson.databind.JsonNode
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Body
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.core.ResponseResultOf
import com.github.kittinunf.fuel.core.isSuccessful
import com.harry.depromeet.model.Place
import com.harry.depromeet.utils.JsonUtils
import org.springframework.stereotype.Service
import java.lang.RuntimeException

@Service
class PlaceService {
    fun request(placeId: Long): Place {
        val url = "https://place.map.kakao.com/main/v/$placeId"
        return Fuel.get(url)
                .response()
                .second
                .isSuccess()
                .data
                .toPlace()
    }

    fun Response.isSuccess(): Response {
        if (this.isSuccessful) {
            return this
        }

        when (this.statusCode) {
            400 -> throw RuntimeException("Bad Request")
            else -> throw RuntimeException("Unexpected Exception")
        }
    }

    fun ByteArray.toPlace(): Place {
        val jsonNode = JsonUtils.toJsonNode(String(this))

        val basicInfo: JsonNode? = jsonNode["basicInfo"]

        val placeId = basicInfo?.get("cid")?.asLong()
        val title = basicInfo?.get("placenamefull")?.asText()
        val photo = basicInfo?.get("mainphotourl")?.asText()

        val address = basicInfo?.get("address")
        val region = address?.get("region")?.get("newaddrfullname")?.asText() ?: ""
        val newAddress = address?.get("newaddr")?.get("newaddrfull")?.asText() ?: ""
        val addressDetail = address?.get("addrdetail")?.asText() ?: ""
        val zipCode = address?.get("newaddr")?.get("bsizonno")?.asText()

        val scoreSum = basicInfo?.get("feedback")?.get("scoresum")?.asDouble()
        val scoreCnt = basicInfo?.get("feedback")?.get("scorecnt")?.asDouble()

        val point = if (scoreSum == null || scoreCnt == null) {
            "0"
        } else {
            String.format("%.1f", scoreSum / scoreCnt)
        }

        return Place(
                placeId = placeId,
                title = title,
                photo = photo,
                address = "$region $newAddress $addressDetail",
                zipCode = zipCode,
                point = point,
        )
    }
}