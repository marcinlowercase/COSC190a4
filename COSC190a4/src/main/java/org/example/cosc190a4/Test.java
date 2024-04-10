package org.example.cosc190a4;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.paint.Color;

public class Test {

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper mapper =  new ObjectMapper();
        User user1 = new User("Theo", "TT", "Color.RED", "theo@gmail.om");

        String jsonString = mapper.writeValueAsString(user1);
        System.out.println(jsonString);
    }

}
