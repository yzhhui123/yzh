package com.qf.springboot_mail;

import com.qf.entity.Email;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootMailApplicationTests {
    @Autowired
    JavaMailSender javaMailSender;
    @Test
    public void contextLoads() {
    }
    @Test
    public void sendEmail() throws Exception {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        //创建一个Spring提供的邮件帮助对象
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

        //设置发送方
        messageHelper.setFrom("yzh960813@sina.com");

        //设置接收方
        //to - 普通接收方
        //cc - 抄送方
        //bcc - 密送方
        messageHelper.addTo("zj490057768@sina.com", "金牌会员");

        //设置标题
        messageHelper.setSubject("111！");

        //设置内容
        messageHelper.setText("请点击<a href='http://www.baidu.com'>这里</a>找回密码~", true);

        //设置时间
        messageHelper.setSentDate(new Date());

        //发送附件
        // messageHelper.addAttachment("a.jpg", new File("C:\\Users\\ken\\Pictures\\Saved Pictures\\奥格瑞玛.jpg"));

        //发送邮件
        javaMailSender.send(mimeMessage);
    }
}
