package com.acms.util;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;


public class JavaMail{
	
	private static Logger logger = Logger.getLogger(JavaMail.class);
	
	/**
	 * 
	 * @param smtpserver SMTP发送服务器
	 * @param sendto 收件人
	 * @param myfrom 发件人
	 * @param yourname 发件人昵称
	 * @param username 发件人邮箱用户名
	 * @param password 发件人邮箱密码
	 * @param subject 邮件主题
	 * @param content 邮件内容（支持HTML）
	 * @return 1 -- 发送成功 0 -- 发送失败
	 */
    public static int sendEmail(String smtpserver, String sendto, String myfrom, String yourname, String username, String password, String subject, String content)
    {
        // 发送email
        HtmlEmail semail = new HtmlEmail();
        try {
            // 这里是SMTP发送服务器的名字：163的如下："smtp.163.com"
            semail.setHostName(smtpserver);
            // 字符编码集的设置
            semail.setCharset("utf-8");
            // 收件人的邮箱
            semail.addTo(sendto);
            // 发送人的邮箱
            semail.setFrom(myfrom, yourname);
            // 如果需要认证信息的话，设置认证：用户名-密码。分别为发件人在邮件服务器上的注册名称和密码
            semail.setAuthentication(username, password);
            // 要发送的邮件主题
            semail.setSubject(subject);
            // 要发送的信息，由于使用了HtmlEmail，可以在邮件内容中使用HTML标签
            semail.setMsg(content);
            // 发送
            semail.send();
            logger.debug("邮件成功发给了："+sendto);
            return 1;
        } catch (EmailException e) {
        	logger.error(e.getMessage(), e);
            return 0;
        }
    }
}
