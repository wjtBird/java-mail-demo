package com.example.demo.web;

import com.example.demo.core.web.BaseController;
import com.example.demo.entity.EmailEntity;
import com.example.demo.entity.EnclosureEntity;
import com.example.demo.service.SendEmailsService;
import com.example.utils.FileUtil;
import com.example.utils.TResult;
import microsoft.exchange.webservices.data.core.service.item.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
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
    public TResult readEmail(@RequestParam("id") String id, HttpServletRequest request){
        try {
//            String realPath = request.getSession().getServletContext().getRealPath("");
            String realPath = request.getServletContext().getRealPath("/").replace("\\", "/");
            EmailEntity emailEntitiy= sendEmailsService.readEmail(this.account, this.password, id, realPath);
            return new TResult(SUCCESS,null,emailEntitiy);
        } catch (Exception e) {
            return new TResult(FAIL,e.getMessage(),null);
        }
    }

    @RequestMapping(value = "/sendEmail",method = RequestMethod.POST)
    @ResponseBody
    public TResult sendEmail(EmailEntity emailEntity,HttpServletRequest request){
        MultipartFile file = emailEntity.getFile();
        String fileName = "";
        String filePath = "";
        try {
//            String filePath = request.getSession().getServletContext().getRealPath("imgupload/");
            if (null != file){
//        String contentType = file.getContentType();
                fileName = file.getOriginalFilename();
                filePath = "E:/workspace/java-mail-demo/files/";
                FileUtil.uploadFile(file.getBytes(), filePath, fileName);
            }
            sendEmailsService.sendEmail(this.account,this.password,emailEntity,filePath+""+fileName);
            return new TResult(SUCCESS,null,"1");
        } catch (Exception e) {
            return new TResult(FAIL,e.getMessage(),null);
        }
    }

    //文件下载相关代码
    @RequestMapping(value = "/download",method = RequestMethod.GET)
    @ResponseBody
    public String downloadFile(@RequestParam("fileName") String fileName,
                               HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        if (fileName != null) {
            //设置文件路径
            File file = new File("E://workspace//java-mail-demo//files" , fileName);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开

                response.addHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes(),"ISO8859-1"));// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("success");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }
}
