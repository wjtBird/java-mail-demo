package com.example.demo.web;

import com.example.demo.service.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

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
