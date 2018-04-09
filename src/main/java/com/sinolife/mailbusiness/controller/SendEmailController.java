package com.sinolife.mailbusiness.controller;

import com.example.demo.core.web.BaseController;
import com.sinolife.base.factories.ExchangeServiceFactory;
import com.sinolife.mailbusiness.domain.EmailEntity;
import com.sinolife.mailbusiness.service.SendEmailsService;
import com.sinolife.mailbusiness.utils.TResult;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class SendEmailController extends BaseController {

    @Autowired
    private SendEmailsService sendEmailsService;
    @Autowired
    private ExchangeServiceFactory serviceFactory;

    private String account = "liyong.wb@sino-life.com";
    private String password = "LY29436hhz";

    public static final String FILE_PATH = "/Users/wjt/Downloads/";


    /**
     * 跳转到收件页面
     *
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {

        return "/index";
    }

    /**
     * 跳转到发送页面
     *
     * @return
     */
    @RequestMapping(value = "/sender", method = RequestMethod.GET)
    public String sender() {
        return "/sendMail";
    }


    @RequestMapping(value = "/mailbox-folder", method = RequestMethod.GET)
    public String mailboxolder() {
        serviceFactory.syncRegistrationService(this.account, this.password);
        return "mailbox-folder";
    }

    @RequestMapping(value = "/mailbox-email", method = RequestMethod.GET)
    public String mailbox(@RequestParam("id") String id, Model model) {
        model.addAttribute("id", id);
        return "/mailbox-email";
    }

    /**
     * 查看收件箱
     *
     * @return
     */
    @RequestMapping(value = "/show", method = RequestMethod.GET)
    @ResponseBody
    public TResult show() {
        try {
            List<EmailEntity> emailEntities = sendEmailsService.listItems(this.account, this.password);
            return new TResult(SUCCESS, null, emailEntities);
        } catch (Exception e) {
            return new TResult(FAIL, e.getMessage(), null);
        }
    }

    /**
     * 读取邮件详情
     *
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(value = "/read", method = RequestMethod.POST)
    @ResponseBody
    public TResult readEmail(@RequestParam("id") String id, HttpServletRequest request) {
        try {
//            String realPath = request.getSession().getServletContext().getRealPath("");
            String realPath = request.getServletContext().getRealPath("/").replace("\\", "/");
            id = id.replace(" ", "+");
            EmailEntity emailEntitiy = sendEmailsService.readEmail(this.account, this.password, id, realPath);
            return new TResult(SUCCESS, null, emailEntitiy);
        } catch (Exception e) {
            return new TResult(FAIL, e.getMessage(), null);
        }
    }

    /**
     * 发送邮件
     *
     * @param emailEntity
     * @param request
     * @return
     */
    @RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
    @ResponseBody
    public TResult sendEmail(EmailEntity emailEntity, MultipartFile[] files, HttpServletRequest request) throws IOException {

        FileOutputStream out = null;
        List<String> attachmentPaths = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                if (null != file) {
                    org.apache.commons.io.FileUtils.forceMkdir(new File(FILE_PATH));
                    String  fileName = FILE_PATH+ UUID.randomUUID().toString();
                    File targetFile = new File(fileName);
                    FileUtils.copyInputStreamToFile(file.getInputStream(), targetFile);
                    attachmentPaths.add(fileName);
                }
            } catch (Exception e) {
                LOGGER.info(e.getMessage());
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
     *
     * @param fileName
     * @param request
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    @ResponseBody
    public void downloadFile(@RequestParam("fileName") String fileName,
                             HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (fileName != null) {
            //设置文件路径
            File file = new File(FILE_PATH, fileName);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition", "attachment;fileName=" + new String(fileName.getBytes(), "ISO8859-1"));// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(file);
                    FileUtils.copyFile(file, response.getOutputStream());
                } catch (Exception e) {
                    LOGGER.info(e.getMessage());
                } finally {
                    if (fis != null) {
                        fis.close();
                    }
                }
            }
        }
    }
}
