package com.example.projet_carte;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Properties;

@Service
@AllArgsConstructor
public class EmailSenderService {

    private JavaMailSender mailSender;

    public void sendSimpleEmail(String toEmail,
                                String body,
                                String subject){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("fayeomzolive@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);
        System.out.println("Mail Send...");
    }

    public void sendEmailInlineImage(String subject, String message, String to, File fileatach)
    {
        String senderEmail = "fayeomzolive@gmail.com"; // your gmail email id
        String senderPassword = "dnwicurnxneqqofi"; // your gmail id password

        //Properties class enables us to connect to the host SMTP server
        Properties properties = new Properties();

        // Setting necessary information for object property

        // Setup host and mail server
        properties.put("mail.smtp.auth", "true"); //enable authentication
        properties.put("mail.smtp.starttls.enable", "true"); // enable TLS-protected connection
        properties.put("mail.smtp.host", "smtp.gmail.com"); // Mention the SMTP server address. Here Gmail's SMTP server is being used to send email
        properties.put("mail.smtp.port", "587"); // 587 is TLS port number

        // get the session object and pass username and password
        Session session = Session.getDefaultInstance(properties, new Authenticator()
        {
            protected PasswordAuthentication getPasswordAuthentication(){

                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {

            MimeMessage msg = new MimeMessage(session); //compose the message (multi media, text )

            MimeMessageHelper helper = new MimeMessageHelper(msg, true); // create MimeMessageHelper class

            helper.setFrom(new InternetAddress(senderEmail)); //adding sender email id to helper object

            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); //adding recipient to msg object

            helper.setSubject(subject); // adding subject to helper object

            MimeMultipart mimeMultipart = new MimeMultipart(); // create MimeMultipart object

            MimeBodyPart textMime = new MimeBodyPart(); // create first MimeBodyPart object textMime for containing the message

            MimeBodyPart messageBodyPart = new MimeBodyPart(); // create second MimeBodyPart object messageBodyPart for containing the html format message

            MimeBodyPart fileMime = new MimeBodyPart(); // create third MimeBodyPart object fileMime for containing the file

            textMime.setText(message); // sets message to the textMime object

            // create message within html format tag and assign to the content variable
            String content = "<br><br><img src='cid:carte_access'/><br><b>Votre carte d'access</b>";

            // sets html format content to the messageBodyPart object
            messageBodyPart.setContent(content,"text/html; charset=utf-8");

            //File file = new File(path); //Initialize the File and Move Path variable
            fileMime.attachFile(fileatach); //attach file to fileMime object

            helper.addInline("carte_access", fileatach); //inline image attach using addInline() method

            //The mimeMultipart adds textMime, messageBodypart and fileMime to the
            mimeMultipart.addBodyPart(textMime);
            mimeMultipart.addBodyPart(messageBodyPart);
            mimeMultipart.addBodyPart(fileMime);

            msg.setContent(mimeMultipart); // Set the mimeMultipart the contents of the msg

            Transport.send(msg); // Transport class send the message using send() method
        }catch(Exception e){

            System.out.println("EmailService File Error"+ e);
        }

    }
}
