package com.support.utils;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Properties;

/**
 * @ClassName MailUtil
 * @Author 吴俊淇
 * @Date 2020/5/8 17:48
 * @Version 1.0
 **/
public class MailUtil {
    public static void SendMail(String email, String theme, String body) throws GeneralSecurityException, MessagingException, UnsupportedEncodingException {
        // 设置发送邮件的配置信息
        Properties props = new Properties();
        // 开启debug调试
//        props.setProperty("mail.debug", "true");
        // 发送服务器需要身份验证
        props.setProperty("mail.smtp.auth", "true");
        // 设置邮件服务器主机名
        props.setProperty("mail.host", "smtp.qq.com");
        // 发送邮件协议名称
        props.setProperty("mail.transport.protocol", "smtp");
        //开启了 SSL 加密
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.socketFactory", sf);

        Session session = Session.getInstance(props);

        //邮件内容部分
        Message msg = new MimeMessage(session);
        msg.setSubject(theme);
        msg.setText(body);
        //邮件发送者
        msg.setFrom(new InternetAddress("136065670@qq.com", "暖到", "UTF-8"));

        //发送邮件
        Transport transport = session.getTransport();
        //在邮箱的设置中开启POP3/SMTP服务得到授权码
        transport.connect("smtp.qq.com", "136065670@qq.com", "pnhfksyvjezwbiha");

        transport.sendMessage(msg, new Address[]{new InternetAddress(email)});
        transport.close();
    }
}
