package com.rahul.kotlin.spi.tester.service

import com.rahul.kotlin.spi.TransformerServiceLoader
import org.json.JSONObject
import javax.enterprise.context.ApplicationScoped
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.Entity
import javax.ws.rs.core.MediaType


/**
 *
 * @author in-rahul.khandelwal
 */
@ApplicationScoped
class TransformerService {

    fun getQuotesForPartner(uiRequest: JSONObject, serviceProvider: String): String {
        val uri = "http://localhost:4547/insurance_provider/v1/quote"
        val serviceProviderInstance = TransformerServiceLoader.getProvider(serviceProvider)
        val response = JSONObject(ClientBuilder.newClient()!!.target(uri)
                .request()
                .post(Entity.entity(serviceProviderInstance!!.toPartnerRequest(uiRequest).toString(), MediaType.APPLICATION_JSON_TYPE))
                .readEntity(String::class.java))
        return serviceProviderInstance.toUiResponse(response).toString()
    }

    fun getPartnerSpecificPayload(uiRequest: JSONObject, serviceProvider: String): String {
        val serviceProviderInstance = TransformerServiceLoader.getProvider(serviceProvider)
        return serviceProviderInstance!!.toPartnerRequest(uiRequest).toString()
    }
    
    fun reload() {
        TransformerServiceLoader.reloadServices()
    }

    fun testWorking() : String {
        return "Working through Java EE Bean"
    }
}
