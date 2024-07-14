package com.Omkar.SDETAssignmentFancode;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class FanCodeBase {
    String baseUrl = "https://jsonplaceholder.typicode.com/";


    /**
     * Fetch the response for GET request with provided url
     *
     * @param url
     * @return
     */
    public Response getResponse(String url) {
        RequestSpecification rp = RestAssured.given();
        Response response = rp.get(url);
        if (response.statusCode() == 200) {
            return response;
        } else
            return null;
    }

    /**
     * Calculte list of Fancode cities
     * @return
     */
    public ArrayList listOfIDsFancodeCity() {
        Response rs = getResponse("/users");
        JSONArray users= new JSONArray(rs.asString());
        ArrayList<Integer> id = new ArrayList<>();
        String lat_value, lng_value;

        for (int i = 0; i < users.length(); i++) {
            JSONObject jo = (JSONObject) users.get(i);
            lat_value = jo.getJSONObject("address").getJSONObject("geo").getString("lat");
            lng_value = jo.getJSONObject("address").getJSONObject("geo").getString("lng");

            if (cityFancode(lat_value, lng_value)) {
                id.add((Integer) jo.get("id"));
            }
        }
        return id;
    }

    /**
     * Calculate lat and lng to determine whether city is Fancode or not
     *
     * @param lat_value
     * @param lng_value
     * @return
     */
    public boolean cityFancode(String lat_value, String lng_value) {
        float lat = Float.parseFloat(lat_value);
        float lng = Float.parseFloat(lng_value);

        //lat between ( -40 to 5) and long between ( 5 to 100)
        return (lat >= -40 && lat <= 5) && (lng >= 5 && lng <= 100);
    }

    /**
     * Calculate
     *
     * @param id
     * @return
     */
    public boolean verifyTaskCompletion(List<Integer> id,int percentage) {

        Response rs1 = getResponse("/todos");
        JSONArray todos = new JSONArray(rs1.asString());

        for (int i = 0; i < id.size(); i++) {
            int count = 0;
            int size = 0;
            for (int j = 0; j < todos.length(); j++) {
                JSONObject task = todos.getJSONObject(j);
                int userId = task.getInt("userId");
                boolean completed = task.getBoolean("completed");

                if (id.get(i).equals(userId)) {
                    size++;
                }
                if (id.get(i).equals(userId) && completed) {
                    count++;
                }
            }
            System.out.println("User ID: " + id.get(i) + " have completed todo task of percentage - " + percent(count, size));
            Assert.assertFalse(percent(count, size) < percentage, "Fancode UserId : " + id.get(i) + " does not have more than half of their todos task completed.");
        }

        return true;
    }

    /**
     * Calculate percentage for users
     *
     * @param count
     * @param userId
     * @return
     */
    public double percent(double count, double userId) {
        double percentage;
        percentage = (count / userId) * 100;
        return percentage;
    }
}
