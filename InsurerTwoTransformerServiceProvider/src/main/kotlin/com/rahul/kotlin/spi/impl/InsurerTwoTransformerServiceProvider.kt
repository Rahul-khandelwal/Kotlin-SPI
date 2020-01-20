package com.rahul.kotlin.spi.impl

import com.rahul.kotlin.dsl.json
import com.rahul.kotlin.dsl.jsonArray
import com.rahul.kotlin.spi.TransformerServiceProvider
import com.rahul.kotlin.utils.*
import org.json.JSONArray
import org.json.JSONObject

/**
 *
 * @author in-rahul.khandelwal
 */
class InsurerTwoTransformerServiceProvider: TransformerServiceProvider {

    override fun serviceName() : String = "provider-two"
    
    override fun toPartnerRequest(uiRequest: JSONObject): JSONObject {
        val policySdate = getFormattedCurrentDate("yyyyMMdd")
        val policyEnddate = getOneYearLaterDate("yyyyMMdd")
        var memberDobs = uiRequest.getJSONArray("members")

        return json {
            "functionality" To "groupmedicarequotation"
            "emp_code" To "3954557"
            "quote_type" To "calc"
            "quotation_no" To uiRequest["quotation_id"]
            "sol_id" To "BANK003"
            "branch" To "MUMBAIBANDRAWEST"
            "lead_id" To "1001"
            "partbrrefid" To "125311RTSS"
            "partner_name" To "BANK"
            "intermediary_name" To "SANDEEP"
            "intermediary_code" To "0030240001"
            "group_custid" To "GRP123"
            "mobile_no" To uiRequest.get("mobile")
            "telphone_no" To ""
            "email_id" To uiRequest.get("email")
            "product_code" To uiRequest.get("product_code")
            "policy_tenure" To "1"
            "product_name" To "Group MediCare"
            "btype_name" To "New"
            "policy_sdate" To policySdate
            "policy_enddate" To policyEnddate
            "policy_type" To "Individual"
            "plan_type" To "Individual"
            "masterpolno" To "0235032828"
            "plan_code" To uiRequest.get("plan_code")
            "cust_pincode" To uiRequest.get("pin_code")
            "gstin" To ""
            "floater_si" To uiRequest.get("floater_sum_insured").toString()
            "partnerappno" To "1000157732"
            "partnername" To "BANK"
            "autodebitflag" To "N"
            "ab_cust_id" To ""
            "ab_emp_id" To ""
            "cust_name" To ""
            "grp_name" To "BANK"
            "source" To "BANK"
            "insuredpersons" To memberDobs.length()

            "members" To jsonArray {
                for (i in 1..memberDobs.length()) {
                    this + json {
                        "m$i" To json {
                            "dob" To reformatDate(memberDobs.getJSONObject(i - 1).getString("birth_date"), "yyyy-MM-dd", "yyyyMMdd")
                            "age" To calculateAge(memberDobs.getJSONObject(i - 1).getString("birth_date"), "yyyy-MM-dd")
                            "suminsured" To memberDobs.getJSONObject(i - 1).get("suminsured")
                            "relation" To memberDobs.getJSONObject(i - 1).get("relation")
                            "medQs1" To memberDobs.getJSONObject(i - 1).get("smokerStatus")
                            "medQs2" To memberDobs.getJSONObject(i - 1).get("diebatic")
                            "medQs3" To memberDobs.getJSONObject(i - 1).get("hereditry")
                        }
                    }
                }
            }
        }
    }

    override fun toUiResponse(partnerResponse: JSONObject) : JSONArray {
        val status = if (partnerResponse.getString("status").equals("1"))  "accepted" else "rejected"
        val data = partnerResponse.getJSONObject("data")

        return jsonArray {
            this + json {
                "status" To status
                "partner_code" To "provider-two"
                "product_code" To data.get("product_code")
                "plan_code" To data.get("plan_code")
                "quotation_id" To data.get("quotation_no")
                "premium" To jsonArray {
                    this + json {
                        "total" To data.getDouble("totalpremium")
                        "net" To data.getDouble("netpremium")

                        "taxes" To jsonArray {
                            this + json {
                                "type" To "igst"
                                "value" To data.getDouble("igst")
                            }
                            this + json {
                                "type" To "sgst"
                                "value" To data.getDouble("sgst")
                            }
                            this + json {
                                "type" To "cess"
                                "value" To data.getDouble("other_cess")
                            }
                        }

                        "frequency" To json {
                            "unit" To "year"
                            "value" To data.getInt("policy_tenure")
                        }
                    }
                }
            }
        }
    }

}
