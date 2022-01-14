package com.example.restapi.restapi.controller;

//import com.example.restapi.restapi.chat.Authorization;
import org.springframework.web.bind.annotation.*;

@RestController
public class ZoomOAuthController {

    @RequestMapping(path = "/zoomOAuth", method = RequestMethod.GET)
    public String getCode(@RequestParam(name = "code") String code) throws Exception {
        return "this is the code: " + code;
        //return Authorization.authorizeAuthCode(code);
    }
}
