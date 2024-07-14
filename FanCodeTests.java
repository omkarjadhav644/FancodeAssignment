package com.Omkar.SDETAssignmentFancode;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.ArrayList;

public class FanCodeTests extends FanCodeBase{

    @Test
    public void verifyAllFancodeTaskCompletionAbove50Percent() {
        //Get the response of the users api
        RestAssured.baseURI = baseUrl;
        Response rs = getResponse("/users");

        //Get the list of Fancode cities
        ArrayList<Integer> id = listOfIDsFancodeCity();

        //Print all the id's of Fancode city
        System.out.println("List of FanCode ID's from users : "+id);

        //Assertion to check all the Facode users completed more than 50% tasks.
        Assert.assertTrue(verifyTaskCompletion(id,50),"All fancode users have more than half of their todos task completed.");

    }






}