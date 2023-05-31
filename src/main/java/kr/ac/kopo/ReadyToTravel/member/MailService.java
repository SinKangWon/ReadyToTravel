package kr.ac.kopo.ReadyToTravel.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
@Slf4j
public class MailService {

    private final JavaMailSender javaMailSender;
    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    private MimeMessage validMail(String email, String code) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        mimeMessage.setFrom(new InternetAddress("readytotravel2304@naver.com"));
        mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
        mimeMessage.setSubject("ReadyToTravel 메일 인증 안내");
        String msgg="";
        msgg+= "<div style='margin:20px;'>";
        msgg+= "<h1> Ready To Travel </h1>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:red;'>인증 만료 시간 (5분)</h3>";
        msgg+= "<br>";
        msgg+= "<p>아래는 이메일 인증 보안코드 입니다</p>";
        msgg+= "<p>" + code + "</p>";
        msgg+= "<br>";
        msgg+= "</div>";
        mimeMessage.setText(msgg,"utf-8","html");

        return mimeMessage;
    }

    private MimeMessage regeneratedPasswordMessage(String email, String password) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        mimeMessage.setFrom(new InternetAddress("readytotravel2304@naver.com"));
        mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
        mimeMessage.setSubject("ReadyToTravel 비밀번호 변경 안내");

        String msgg = "";
        msgg+= "<div style='margin:20px;'>";
        msgg+= "<h1> Ready To Travel </h1>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:red;'>인증 만료 시간 (5분)</h3>";
        msgg+= "<br>";
        msgg+= "<p>아래는 초기화된 비밀번호 입니다</p>";
        msgg+= "<p>" + password + "</p>";
        msgg+= "<br/>";
        msgg+= "</div>";
        msgg+= "</div>";
        mimeMessage.setText(msgg,"utf-8","html");

        return mimeMessage;
    }

    public void sendMailForEmail(String email,String code) {
        try {
            javaMailSender.send(validMail(email,code));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMailForPass(String email, String password) {
        try {
            javaMailSender.send(regeneratedPasswordMessage(email, password));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}