package com.rahul.kotlin.spi

import org.json.JSONArray
import org.json.JSONObject

/**
 *
 * @author in-rahul.khandelwal
 */
interface TransformerServiceProvider {
    
    fun serviceName() : String
    
    fun toPartnerRequest(uiRequest: JSONObject) : JSONObject
    
    fun toUiResponse(partnerResponse: JSONObject) : JSONArray
}
