package com.zoom.restapi.controller;

import com.zoom.restapi.chat.Authorization;
import org.springframework.web.bind.annotation.*;

@RestController
public class ZoomOAuthController {

    @RequestMapping(path = "/zoomOAuth", method = RequestMethod.GET)
    public String getCode(@RequestParam(name = "code") String code) throws Exception {
        return "This is the authCode: " + code + "/nThis is the accessToken: " + Authorization.authorizeAuthCode(code);
    }

}