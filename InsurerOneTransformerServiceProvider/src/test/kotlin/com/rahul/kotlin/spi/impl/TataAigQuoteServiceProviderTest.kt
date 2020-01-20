package com.rahul.spi.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author in-rahul.khandelwal
 */
class TataAigQuoteServiceProviderTest {

    @Test
    fun benchmark() {
        val input = JSONObject();
        input.put("name", "John Doe");
        input.put("gender", "Male");
        input.put("dob", Date.from(LocalDate.of(1988, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        input.put("phone", "1234566541");
        input.put("isSmoker", false);
        input.put("salary", 600000);
        input.put("products", JSONArray(arrayOf("LI", "GI")));

        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy");
        
        val renderedValues = ArrayList<String>();
        val startTime = System.currentTimeMillis();

        for (i in 0..100000) {
            renderedValues.add(this.sampleBenchmarkTask(input, simpleDateFormat));
        }

        var endTime = System.currentTimeMillis();
        System.out.println("Milliseconds taken to run the test -> " +  (endTime - startTime));
        Assertions.assertTrue(renderedValues.size >= 100000);
    }

    private fun sampleBenchmarkTask(input: JSONObject, dateFormat: DateFormat) : String {
        val output = JSONObject();

        output.put("name", input.get("name"));
        output.put("ph", input.get("phone"));
        output.put("dob", dateFormat.format(input.get("dob")));
        output.put("gender", input.get("gender"));
        output.put("isSmoker", input.get("isSmoker"));
        output.put("yearlyIncome", input.get("salary"));
        output.put("products", input.get("products"));

        return output.toString();
    }

}
