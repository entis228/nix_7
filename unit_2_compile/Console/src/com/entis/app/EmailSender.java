package com.entis;

import org.apache.commons.mail.*;
import javax.mail.*;

public class EmailSender{
    public static void SendHello(String emailto) throws EmailException {
        SimpleEmail email = new SimpleEmail();
        email.setHostName("smtp.googlemail.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator("lutyjvolk53@gmail.com", "Lv234567"));
        email.setSSLOnConnect(true);
        email.setFrom("Vol4ara@entiscorp");
        email.setSubject("Greetings");
        email.setMsg("Hello, from Entis Corp");
        email.addTo(emailto);
        email.send();
    }
}