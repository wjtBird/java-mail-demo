package com.example.demo.web;

import com.example.demo.core.web.BaseController;
import com.example.demo.entity.EmailEntity;
import com.example.demo.service.SendEmailsService;
import com.example.utils.TResult;
import microsoft.exchange.webservices.data.core.service.item.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class SendEmailController extends BaseController {

    @Autowired
    private SendEmailsService sendEmailsService;

    private String account = "liyong.wb@sino-life.com";
    private String password = "LY29436hhz";

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(){
        return "/index";
    }
    @RequestMapping(value = "/sender",method = RequestMethod.GET)
    public String sender(){
        return "/sendMail";
    }

    @RequestMapping(value = "/show",method = RequestMethod.GET)
    @ResponseBody
    public TResult show(){
        try {
            List<EmailEntity> emailEntities = sendEmailsService.listItems(this.account, this.password);
            return new TResult(SUCCESS,null,emailEntities);
        } catch (Exception e) {
            return new TResult(FAIL,e.getMessage(),null);
        }
    }

    @RequestMapping(value = "/read",method = RequestMethod.POST)
    @ResponseBody
    public TResult readEmail(@RequestParam("id") String id){
        try {
            EmailEntity emailEntitiy= sendEmailsService.readEmail(this.account,this.password,id);
            return new TResult(SUCCESS,null,emailEntitiy);
        } catch (Exception e) {
            return new TResult(FAIL,e.getMessage(),null);
        }
    }

    @RequestMapping(value = "/sendEmail",method = RequestMethod.POST)
    @ResponseBody
    public TResult sendEmail(EmailEntity emailEntity){
        try {
            sendEmailsService.sendEmail(this.account,this.password,emailEntity);
            return new TResult(SUCCESS,"发送成功",null);
        } catch (Exception e) {
            return new TResult(FAIL,e.getMessage(),null);
        }
    }
}
