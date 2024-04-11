package org.example.cosc190a4;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.IOException;

public class Test {

    public static void main(String[] args) throws IOException {
        ObjectMapper mapper =  new ObjectMapper();
//        String jsonString = "{ \"username\": \"Hello\", \"handle\": \"fadf\", \"red\": \"100\", \"green\": \"120\", \"blue\": \"140\", \"email\": \"ladfjalkdf\" }";

//        User user = mapper.readValue(jsonString, new TypeReference<User>() {});
//        System.out.println(user);
//        User user1 = new User("theo", "TT", "255", "0", "100", "theodore@gmail.com");
//        mapper.writeValue(new File("data_files/info.json"), user1);

          String test = "{\"username\":\"theo\",\"handle\":\"TT\",\"red\":255,\"green\":0,\"blue\":100,\"email\":\"theodore@gmail.com\"}";
//        String test2 = "{\"username\":\"theo\",\"handle\":\"TT\",\"textColor\":{\"red\":1.0,\"green\":0.0,\"blue\":0.3921568691730499,\"opacity\":1.0,\"opaque\":true,\"saturation\":1.0,\"brightness\":1.0,\"hue\":336.470587849617},\"email\":\"theodore@gmail.com\"}";
        User userTHeo = mapper.readValue(test, new TypeReference<User>() {});
//
        System.out.println(userTHeo);


    }

}
