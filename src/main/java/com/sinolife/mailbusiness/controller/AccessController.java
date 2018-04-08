package com.sinolife.mailbusiness.controller;

import com.sinolife.mailbusiness.service.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AccessController {

    @Autowired
    private AccessService accessService;

//    @GetMapping(value = "/index")
//    public String index(Map<String, Object> map){
//        map.put("name", "HowieLi");
//        return "/index";
//    }
}
