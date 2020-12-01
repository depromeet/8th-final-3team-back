package com.harry.depromeet.service

import com.fasterxml.jackson.databind.JsonNode
import com.harry.depromeet.model.Place
import com.harry.depromeet.utils.JsonUtils
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
import java.lang.NullPointerException
import java.lang.RuntimeException

@Service
class PlaceService(val restTemplate: RestTemplate) {

    fun getPlace(placeId: Long): Place {
        val headers = HttpHeaders()
        headers.contentType = MediaType.valueOf("text/plain;charset=utf-8");
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)" +
                " AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        val entity = HttpEntity<String>("", headers)

        val url = "https://place.map.kakao.com/main/v/$placeId"
        val response = restTemplate.exchange(url, HttpMethod.GET, entity, String::class.java)
        response.body?.let {
            return toPlace(it)
        } ?: throw NullPointerException()
    }

    fun toPlace(body: String): Place {
        val jsonNode = JsonUtils.toJsonNode(body)

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