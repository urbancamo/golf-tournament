package uk.m0nom.golf.develop;

import java.io.File;    // for reading file data
import java.util.Map;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

public class JacksonMappingTest {

    @Test
    public void convert() {
        // create instance of the ObjectMapper class to map JSON data
        ObjectMapper mapper = new ObjectMapper();
        // create instance of the File class
        File cwd = new File(".");
        System.out.println(cwd.getAbsolutePath());
        File fileObj = new File("./src/test/json/tournamentDs1.json");

        // use try-catch block to convert JSON data into Map
        try {
            // read JSON data from file using fileObj and map it using ObjectMapper and TypeReference classes
            Map<String, Object> userData = mapper.readValue(
                    fileObj, new TypeReference<Map<String, Object>>() {
                    });
            // print all key-value pairs
            System.out.println("Name : " + userData.get("Name"));
            System.out.println("Mobile : " + userData.get("Mobile"));
            System.out.println("Designation : " + userData.get("Designation"));
            System.out.println("Pet : " + userData.get("Pet"));
            System.out.println("Address : " + userData.get("Address"));
        } catch (Exception e) {
            // show error message
            e.printStackTrace();
        }
    }
}