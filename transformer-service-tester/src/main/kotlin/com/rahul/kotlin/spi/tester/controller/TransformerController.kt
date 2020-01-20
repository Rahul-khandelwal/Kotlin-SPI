package com.rahul.kotlin.spi.tester.controller

import com.rahul.kotlin.spi.tester.service.TransformerService
import org.json.JSONObject
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

/**
 *
 * @author in-rahul.khandelwal
 */
@Path("/quotes")
class TransformerController {

    @Inject
    lateinit var service: TransformerService

    @POST
    @Path("/{partner}")
    @Produces(MediaType.APPLICATION_JSON)
    fun returnQuotes(@PathParam("partner") partner: String , request: String) : String {
        return service.getQuotesForPartner(JSONObject(request), partner)
    }

    @POST
    @Path("/payload/{partner}")
    @Produces(MediaType.APPLICATION_JSON)
    fun returnTransformedPartnerPayload(@PathParam("partner") partner: String , request: String) : String {
        return service.getPartnerSpecificPayload(JSONObject(request), partner)
    }
    
    @POST
    @Path("/reload")
    @Produces(MediaType.APPLICATION_JSON)
    fun reloadQuoteServices(): String {
        service.reload()
        return "{\"status\" : \"success\"}"
    }

    @GET
    fun working(): String {
        return service.testWorking()
    }
}
