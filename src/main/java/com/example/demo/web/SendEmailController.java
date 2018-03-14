package com.example.demo.web;

import com.example.demo.entity.EmailEntity;
import com.example.demo.service.SendEmailsService;
import com.example.utils.TResult;
import microsoft.exchange.webservices.data.core.service.item.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class SendEmailController {

    @Autowired
    private SendEmailsService sendEmailsService;

    private String account = "liyong.wb@sino-life.com";
    private String password = "LY29436hhz";

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(Model model){
        try {
            List<EmailEntity> emailEntities = sendEmailsService.listItems(this.account, this.password);
            model.addAttribute("list", emailEntities);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/index";
    }

    @RequestMapping(value = "/read",method = RequestMethod.GET)
    @ResponseBody
    public TResult readEmail(String id){
        try {
            EmailEntity emailEntitiy= sendEmailsService.readEmail(this.account,this.password,id);
            return new TResult(200,null,emailEntitiy);
        } catch (Exception e) {
            e.printStackTrace();
            return new TResult(500,e.getMessage(),null);
        }
    }
}
