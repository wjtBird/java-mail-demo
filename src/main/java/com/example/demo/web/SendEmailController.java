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
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class SendEmailController extends BaseController {

    @Autowired
    private SendEmailsService sendEmailsService;

    private String account = "liyong.wb@sino-life.com";
    private String password = "LY29436hhz";

    /**
     * 跳转到收件页面
     * @return
     */
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(){
        return "/index";
    }

    /**
     * 跳转到发送页面
     * @return
     */
    @RequestMapping(value = "/sender",method = RequestMethod.GET)
    public String sender(){
        return "/sendMail";
    }


    @RequestMapping(value = "/mailbox-folder",method = RequestMethod.GET)
    public String mailboxolder(){
        return "/mailbox-folder";
    }

    @RequestMapping(value = "/mailbox-email",method = RequestMethod.GET)
    public String mailbox(@RequestParam("id") String id,Model model){
        model.addAttribute("id",id);
        return "/mailbox-email";
    }

    /**
     * 查看收件箱
     * @return
     */
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

    /**
     * 读取邮件详情
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "/read",method = RequestMethod.POST)
    @ResponseBody
    public TResult readEmail(@RequestParam("id") String id, HttpServletRequest request){
        try {
//            String realPath = request.getSession().getServletContext().getRealPath("");
            String realPath = request.getServletContext().getRealPath("/").replace("\\", "/");
            id = id.replace(" ","+");
            EmailEntity emailEntitiy= sendEmailsService.readEmail(this.account, this.password, id, realPath);
            return new TResult(SUCCESS,null,emailEntitiy);
        } catch (Exception e) {
            return new TResult(FAIL,e.getMessage(),null);
        }
    }

    /**
     * 发送邮件
     * @param emailEntity
     * @param request
     * @return
     */
    @RequestMapping(value = "/sendEmail",method = RequestMethod.POST)
    @ResponseBody
    public TResult sendEmail(EmailEntity emailEntity,MultipartFile[] files, HttpServletRequest request){

        FileOutputStream out = null;
        String fileName;
        String filePath;
        List<String> attachmentPaths = new ArrayList<>();
        for (MultipartFile file:files) {
            try {
//                String filePath = request.getSession().getServletContext().getRealPath("imgupload/");
//                String contentType = file.getContentType();
                if (null != file) {
                    fileName = file.getOriginalFilename();
                    filePath = "E:/workspace/java-mail-demo/files/";
                    File targetFile = new File(filePath);
                    if (!targetFile.exists()) {
                        targetFile.mkdirs();
                    }
                    out = new FileOutputStream(filePath + fileName);
                    out.write(file.getBytes());
                    FileUtil.uploadFile(file.getBytes(), filePath, fileName);
                    attachmentPaths.add(filePath+""+fileName);
                }
            } catch (Exception e) {
                LOGGER.info(e.getMessage());
            } finally {
                if (null != out) {
                    try {
                        out.close();
                        out.flush();
                    } catch (IOException e) {
                        LOGGER.info(e.getMessage());
                    }
                }
            }
        }
        try {
            sendEmailsService.sendEmail(this.account, this.password, emailEntity, attachmentPaths);
            return new TResult(SUCCESS, null, null);
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            return new TResult(FAIL, e.getMessage(), null);
        }
    }

    /**
     * 文件下载相关代码
     * @param fileName
     * @param request
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     */
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
                } catch (Exception e) {
                    e.printStackTrace();
                    LOGGER.info(e.getMessage());
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            LOGGER.info(e.getMessage());
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            LOGGER.info(e.getMessage());
                        }
                    }
                }
            }
        }
        return null;
    }
}
