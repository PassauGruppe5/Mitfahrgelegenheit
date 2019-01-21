package com.PickmeUP.project.service;

import com.PickmeUP.project.model.User;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class GmailService {
    public static void sendWelcomeMail(User user) {
        String to = user.getEmail();
        String from = "pickmeup.uni.passau@gmail.com";
        final String username = "pickmeup.uni.passau@gmail.com";
        final String password = "lukpkucfltvvpxkf";
        String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        String welcomeHTML = "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\n" +
                "    <title>Willkommen bei PickmeUP!</title>\n" +
                "\n" +
                "    <style type=\"text/css\">\n" +
                "        /* Take care of image borders and formatting, client hacks */\n" +
                "        img { max-width: 600px; outline: none; text-decoration: none; -ms-interpolation-mode: bicubic;}\n" +
                "        a img { border: none; }\n" +
                "        table { border-collapse: collapse !important;}\n" +
                "        #outlook a { padding:0; }\n" +
                "        .ReadMsgBody { width: 100%; }\n" +
                "        .ExternalClass { width: 100%; }\n" +
                "        .backgroundTable { margin: 0 auto; padding: 0; width: 100% !important; }\n" +
                "        table td { border-collapse: collapse; }\n" +
                "        .ExternalClass * { line-height: 115%; }\n" +
                "        .container-for-gmail-android { min-width: 600px; }\n" +
                "\n" +
                "\n" +
                "        /* General styling */\n" +
                "        * {\n" +
                "            font-family: Helvetica, Arial, sans-serif;\n" +
                "        }\n" +
                "\n" +
                "        body {\n" +
                "            -webkit-font-smoothing: antialiased;\n" +
                "            -webkit-text-size-adjust: none;\n" +
                "            width: 100% !important;\n" +
                "            margin: 0 !important;\n" +
                "            height: 100%;\n" +
                "            color: #676767;\n" +
                "        }\n" +
                "\n" +
                "        td {\n" +
                "            font-family: Helvetica, Arial, sans-serif;\n" +
                "            font-size: 14px;\n" +
                "            color: #777777;\n" +
                "            text-align: center;\n" +
                "            line-height: 21px;\n" +
                "        }\n" +
                "\n" +
                "        a {\n" +
                "            color: #676767;\n" +
                "            text-decoration: none !important;\n" +
                "        }\n" +
                "\n" +
                "        .pull-left {\n" +
                "            text-align: left;\n" +
                "        }\n" +
                "\n" +
                "        .pull-right {\n" +
                "            text-align: right;\n" +
                "        }\n" +
                "\n" +
                "        .header-lg,\n" +
                "        .header-md,\n" +
                "        .header-sm {\n" +
                "            font-size: 32px;\n" +
                "            font-weight: 700;\n" +
                "            line-height: normal;\n" +
                "            padding: 35px 0 0;\n" +
                "            color: #4d4d4d;\n" +
                "        }\n" +
                "\n" +
                "        .header-md {\n" +
                "            font-size: 24px;\n" +
                "        }\n" +
                "\n" +
                "        .header-sm {\n" +
                "            padding: 5px 0;\n" +
                "            font-size: 18px;\n" +
                "            line-height: 1.3;\n" +
                "        }\n" +
                "\n" +
                "        .content-padding {\n" +
                "            padding: 20px 0 30px;\n" +
                "        }\n" +
                "\n" +
                "        .mobile-header-padding-right {\n" +
                "            width: 290px;\n" +
                "            text-align: right;\n" +
                "            padding-left: 10px;\n" +
                "        }\n" +
                "\n" +
                "        .mobile-header-padding-left {\n" +
                "            width: 290px;\n" +
                "            text-align: left;\n" +
                "            padding-left: 10px;\n" +
                "        }\n" +
                "\n" +
                "        .free-text {\n" +
                "            width: 100% !important;\n" +
                "            padding: 10px 60px 0px;\n" +
                "        }\n" +
                "\n" +
                "        .block-rounded {\n" +
                "            border-radius: 5px;\n" +
                "            border: 1px solid #e5e5e5;\n" +
                "            vertical-align: top;\n" +
                "        }\n" +
                "\n" +
                "        .button {\n" +
                "            padding: 30px 0;\n" +
                "        }\n" +
                "\n" +
                "        .info-block {\n" +
                "            padding: 0 20px;\n" +
                "            width: 260px;\n" +
                "        }\n" +
                "\n" +
                "        .block-rounded {\n" +
                "            width: 260px;\n" +
                "        }\n" +
                "\n" +
                "        .info-img {\n" +
                "            width: 258px;\n" +
                "            border-radius: 5px 5px 0 0;\n" +
                "        }\n" +
                "\n" +
                "        .force-width-gmail {\n" +
                "            min-width:600px;\n" +
                "            height: 0px !important;\n" +
                "            line-height: 1px !important;\n" +
                "            font-size: 1px !important;\n" +
                "        }\n" +
                "\n" +
                "        .button-width {\n" +
                "            width: 228px;\n" +
                "        }\n" +
                "\n" +
                "    </style>\n" +
                "\n" +
                "    <style type=\"text/css\" media=\"screen\">\n" +
                "        @import url(http://fonts.googleapis.com/css?family=Oxygen:400,700);\n" +
                "    </style>\n" +
                "\n" +
                "    <style type=\"text/css\" media=\"screen\">\n" +
                "        @media screen {\n" +
                "            /* Thanks Outlook 2013! */\n" +
                "            * {\n" +
                "                font-family: 'Oxygen', 'Helvetica Neue', 'Arial', 'sans-serif' !important;\n" +
                "            }\n" +
                "        }\n" +
                "    </style>\n" +
                "\n" +
                "    <style type=\"text/css\" media=\"only screen and (max-width: 480px)\">\n" +
                "        /* Mobile styles */\n" +
                "        @media only screen and (max-width: 480px) {\n" +
                "\n" +
                "            table[class*=\"container-for-gmail-android\"] {\n" +
                "                min-width: 290px !important;\n" +
                "                width: 100% !important;\n" +
                "            }\n" +
                "\n" +
                "            table[class=\"w320\"] {\n" +
                "                width: 320px !important;\n" +
                "            }\n" +
                "\n" +
                "            img[class=\"force-width-gmail\"] {\n" +
                "                display: none !important;\n" +
                "                width: 0 !important;\n" +
                "                height: 0 !important;\n" +
                "            }\n" +
                "\n" +
                "            a[class=\"button-width\"],\n" +
                "            a[class=\"button-mobile\"] {\n" +
                "                width: 248px !important;\n" +
                "            }\n" +
                "\n" +
                "            td[class*=\"mobile-header-padding-left\"] {\n" +
                "                width: 160px !important;\n" +
                "                padding-left: 0 !important;\n" +
                "            }\n" +
                "\n" +
                "            td[class*=\"mobile-header-padding-right\"] {\n" +
                "                width: 160px !important;\n" +
                "                padding-right: 0 !important;\n" +
                "            }\n" +
                "\n" +
                "            td[class=\"header-lg\"] {\n" +
                "                font-size: 24px !important;\n" +
                "                padding-bottom: 5px !important;\n" +
                "            }\n" +
                "\n" +
                "            td[class=\"header-md\"] {\n" +
                "                font-size: 18px !important;\n" +
                "                padding-bottom: 5px !important;\n" +
                "            }\n" +
                "\n" +
                "            td[class=\"content-padding\"] {\n" +
                "                padding: 5px 0 30px !important;\n" +
                "            }\n" +
                "\n" +
                "            td[class=\"button\"] {\n" +
                "                padding: 5px !important;\n" +
                "            }\n" +
                "\n" +
                "            td[class*=\"free-text\"] {\n" +
                "                padding: 10px 18px 30px !important;\n" +
                "            }\n" +
                "\n" +
                "            td[class=\"info-block\"] {\n" +
                "                display: block !important;\n" +
                "                width: 280px !important;\n" +
                "                padding-bottom: 40px !important;\n" +
                "            }\n" +
                "\n" +
                "            td[class=\"info-img\"],\n" +
                "            img[class=\"info-img\"] {\n" +
                "                width: 278px !important;\n" +
                "            }\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body bgcolor=\"#f7f7f7\">\n" +
                "<table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"container-for-gmail-android\" width=\"100%\">\n" +
                "    <tr>\n" +
                "        <td align=\"left\" valign=\"top\" width=\"100%\" style=\"background:repeat-x url(http://s3.amazonaws.com/swu-filepicker/4E687TRe69Ld95IDWyEg_bg_top_02.jpg) #ffffff;\">\n" +
                "            <center>\n" +
                "                <img src=\"http://s3.amazonaws.com/swu-filepicker/SBb2fQPrQ5ezxmqUTgCr_transparent.png\" class=\"force-width-gmail\">\n" +
                "                <table cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" bgcolor=\"#ffffff\" background=\"http://s3.amazonaws.com/swu-filepicker/4E687TRe69Ld95IDWyEg_bg_top_02.jpg\" style=\"background-color:transparent\">\n" +
                "                    <tr>\n" +
                "                        <td width=\"100%\" height=\"80\" valign=\"top\" style=\"text-align: center; vertical-align:middle;\">\n" +
                "                            <!--[if gte mso 9]>\n" +
                "                            <v:rect xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"true\" stroke=\"false\" style=\"mso-width-percent:1000;height:80px; v-text-anchor:middle;\">\n" +
                "                                <v:fill type=\"tile\" src=\"http://s3.amazonaws.com/swu-filepicker/4E687TRe69Ld95IDWyEg_bg_top_02.jpg\" color=\"#ffffff\" />\n" +
                "                                <v:textbox inset=\"0,0,0,0\">\n" +
                "                            <![endif]-->\n" +
                "                            <center>\n" +
                "                                <table cellpadding=\"0\" cellspacing=\"0\" width=\"600\" class=\"w320\">\n" +
                "                                    <tr>\n" +
                "                                        <td class=\"pull-left mobile-header-padding-left\" style=\"vertical-align: middle;\">\n" +
                "                                            <a href=\"https://www.pic-upload.de\" target=\"_blank\"\"><img width=\"137\" height=\"47\" src=\"https://www2.pic-upload.de/img/36322705/pick-me-up_logo.png\" alt=\"logo\"></a>\n" +
                "                                        </td>\n" +
                "                                        <td class=\"pull-right mobile-header-padding-right\" style=\"color: #4d4d4d;\">\n" +
                "                                            <a href=\"\"><img width=\"44\" height=\"47\" src=\"http://s3.amazonaws.com/swu-filepicker/k8D8A7SLRuetZspHxsJk_social_08.gif\" alt=\"twitter\" /></a>\n" +
                "                                            <a href=\"\"><img width=\"38\" height=\"47\" src=\"http://s3.amazonaws.com/swu-filepicker/LMPMj7JSRoCWypAvzaN3_social_09.gif\" alt=\"facebook\" /></a>\n" +
                "                                            <a href=\"\"><img width=\"40\" height=\"47\" src=\"http://s3.amazonaws.com/swu-filepicker/hR33ye5FQXuDDarXCGIW_social_10.gif\" alt=\"rss\" /></a>\n" +
                "                                        </td>\n" +
                "                                    </tr>\n" +
                "                                </table>\n" +
                "                            </center>\n" +
                "                            <!--[if gte mso 9]>\n" +
                "                            </v:textbox>\n" +
                "                            </v:rect>\n" +
                "                            <![endif]-->\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </table>\n" +
                "            </center>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td align=\"center\" valign=\"top\" width=\"100%\" style=\"background-color: #f7f7f7;\" class=\"content-padding\">\n" +
                "            <center>\n" +
                "                <table cellspacing=\"0\" cellpadding=\"0\" width=\"600\" class=\"w320\">\n" +
                "                    <tr>\n" +
                "                        <td class=\"header-lg\">\n" +
                "                            Willkommen bei PickmeUP!\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td class=\"free-text\">\n" +
                "                            Vielen Dank fuer deine Anmeldung! Plane jetzt deine erste eigene Fahrt:\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td class=\"button\">\n" +
                "                            <div><!--[if mso]>\n" +
                "                                <v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" href=\"http://\" style=\"height:45px;v-text-anchor:middle;width:155px;\" arcsize=\"15%\" strokecolor=\"#ffffff\" fillcolor=\"#ff6f6f\">\n" +
                "                                    <w:anchorlock/>\n" +
                "                                    <center style=\"color:#ffffff;font-family:Helvetica, Arial, sans-serif;font-size:14px;font-weight:regular;\">Pick Me Up!</center>\n" +
                "                                </v:roundrect>\n" +
                "                                <![endif]--><a class=\"button-mobile\" href=\"132.231.36.206/\"\n" +
                "                                               style=\"background-color:#ff6f6f;border-radius:5px;color:#ffffff;display:inline-block;font-family:'Cabin', Helvetica, Arial, sans-serif;font-size:14px;font-weight:regular;line-height:45px;text-align:center;text-decoration:none;width:155px;-webkit-text-size-adjust:none;mso-hide:all;\">Pick Me Up!</a></div>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </table>\n" +
                "            </center>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td align=\"center\" valign=\"top\" width=\"100%\" style=\"background-color: #f7f7f7; height: 100px;\">\n" +
                "            <center>\n" +
                "                <table cellspacing=\"0\" cellpadding=\"0\" width=\"600\" class=\"w320\">\n" +
                "                    <tr>\n" +
                "                        <td style=\"padding: 25px 0 25px\">\n" +
                "                            <strong>PickmeUP!</strong><br />\n" +
                "                            Uni Passau <br />\n" +
                "                            Teamorientierte Softwareentwicklung <br /><br />\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </table>\n" +
                "            </center>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "</table>\n" +
                "</body>\n" +
                "</html>";

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject("Deine Anmeldung bei PickmeUP");

            Multipart mp = new MimeMultipart();

            BodyPart pixPart = new MimeBodyPart();
            pixPart.setContent(welcomeHTML, "text/html");

            // Collect the Parts into the MultiPart
            mp.addBodyPart(pixPart);

            // Put the MultiPart into the Message
            message.setContent(mp);

            // Send message
            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendCreatedMail(User user) {
        String to = user.getEmail();
        String from = "pickmeup.uni.passau@gmail.com";
        final String username = "pickmeup.uni.passau@gmail.com";
        final String password = "lukpkucfltvvpxkf";
        String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        String createdHTML = "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\n" +
                "    <title>Willkommen bei PickmeUP!</title>\n" +
                "\n" +
                "    <style type=\"text/css\">\n" +
                "        /* Take care of image borders and formatting, client hacks */\n" +
                "        img { max-width: 600px; outline: none; text-decoration: none; -ms-interpolation-mode: bicubic;}\n" +
                "        a img { border: none; }\n" +
                "        table { border-collapse: collapse !important;}\n" +
                "        #outlook a { padding:0; }\n" +
                "        .ReadMsgBody { width: 100%; }\n" +
                "        .ExternalClass { width: 100%; }\n" +
                "        .backgroundTable { margin: 0 auto; padding: 0; width: 100% !important; }\n" +
                "        table td { border-collapse: collapse; }\n" +
                "        .ExternalClass * { line-height: 115%; }\n" +
                "        .container-for-gmail-android { min-width: 600px; }\n" +
                "\n" +
                "\n" +
                "        /* General styling */\n" +
                "        * {\n" +
                "            font-family: Helvetica, Arial, sans-serif;\n" +
                "        }\n" +
                "\n" +
                "        body {\n" +
                "            -webkit-font-smoothing: antialiased;\n" +
                "            -webkit-text-size-adjust: none;\n" +
                "            width: 100% !important;\n" +
                "            margin: 0 !important;\n" +
                "            height: 100%;\n" +
                "            color: #676767;\n" +
                "        }\n" +
                "\n" +
                "        td {\n" +
                "            font-family: Helvetica, Arial, sans-serif;\n" +
                "            font-size: 14px;\n" +
                "            color: #777777;\n" +
                "            text-align: center;\n" +
                "            line-height: 21px;\n" +
                "        }\n" +
                "\n" +
                "        a {\n" +
                "            color: #676767;\n" +
                "            text-decoration: none !important;\n" +
                "        }\n" +
                "\n" +
                "        .pull-left {\n" +
                "            text-align: left;\n" +
                "        }\n" +
                "\n" +
                "        .pull-right {\n" +
                "            text-align: right;\n" +
                "        }\n" +
                "\n" +
                "        .header-lg,\n" +
                "        .header-md,\n" +
                "        .header-sm {\n" +
                "            font-size: 32px;\n" +
                "            font-weight: 700;\n" +
                "            line-height: normal;\n" +
                "            padding: 35px 0 0;\n" +
                "            color: #4d4d4d;\n" +
                "        }\n" +
                "\n" +
                "        .header-md {\n" +
                "            font-size: 24px;\n" +
                "        }\n" +
                "\n" +
                "        .header-sm {\n" +
                "            padding: 5px 0;\n" +
                "            font-size: 18px;\n" +
                "            line-height: 1.3;\n" +
                "        }\n" +
                "\n" +
                "        .content-padding {\n" +
                "            padding: 20px 0 30px;\n" +
                "        }\n" +
                "\n" +
                "        .mobile-header-padding-right {\n" +
                "            width: 290px;\n" +
                "            text-align: right;\n" +
                "            padding-left: 10px;\n" +
                "        }\n" +
                "\n" +
                "        .mobile-header-padding-left {\n" +
                "            width: 290px;\n" +
                "            text-align: left;\n" +
                "            padding-left: 10px;\n" +
                "        }\n" +
                "\n" +
                "        .free-text {\n" +
                "            width: 100% !important;\n" +
                "            padding: 10px 60px 0px;\n" +
                "        }\n" +
                "\n" +
                "        .block-rounded {\n" +
                "            border-radius: 5px;\n" +
                "            border: 1px solid #e5e5e5;\n" +
                "            vertical-align: top;\n" +
                "        }\n" +
                "\n" +
                "        .button {\n" +
                "            padding: 30px 0;\n" +
                "        }\n" +
                "\n" +
                "        .info-block {\n" +
                "            padding: 0 20px;\n" +
                "            width: 260px;\n" +
                "        }\n" +
                "\n" +
                "        .block-rounded {\n" +
                "            width: 260px;\n" +
                "        }\n" +
                "\n" +
                "        .info-img {\n" +
                "            width: 258px;\n" +
                "            border-radius: 5px 5px 0 0;\n" +
                "        }\n" +
                "\n" +
                "        .force-width-gmail {\n" +
                "            min-width:600px;\n" +
                "            height: 0px !important;\n" +
                "            line-height: 1px !important;\n" +
                "            font-size: 1px !important;\n" +
                "        }\n" +
                "\n" +
                "        .button-width {\n" +
                "            width: 228px;\n" +
                "        }\n" +
                "\n" +
                "    </style>\n" +
                "\n" +
                "    <style type=\"text/css\" media=\"screen\">\n" +
                "        @import url(http://fonts.googleapis.com/css?family=Oxygen:400,700);\n" +
                "    </style>\n" +
                "\n" +
                "    <style type=\"text/css\" media=\"screen\">\n" +
                "        @media screen {\n" +
                "            /* Thanks Outlook 2013! */\n" +
                "            * {\n" +
                "                font-family: 'Oxygen', 'Helvetica Neue', 'Arial', 'sans-serif' !important;\n" +
                "            }\n" +
                "        }\n" +
                "    </style>\n" +
                "\n" +
                "    <style type=\"text/css\" media=\"only screen and (max-width: 480px)\">\n" +
                "        /* Mobile styles */\n" +
                "        @media only screen and (max-width: 480px) {\n" +
                "\n" +
                "            table[class*=\"container-for-gmail-android\"] {\n" +
                "                min-width: 290px !important;\n" +
                "                width: 100% !important;\n" +
                "            }\n" +
                "\n" +
                "            table[class=\"w320\"] {\n" +
                "                width: 320px !important;\n" +
                "            }\n" +
                "\n" +
                "            img[class=\"force-width-gmail\"] {\n" +
                "                display: none !important;\n" +
                "                width: 0 !important;\n" +
                "                height: 0 !important;\n" +
                "            }\n" +
                "\n" +
                "            a[class=\"button-width\"],\n" +
                "            a[class=\"button-mobile\"] {\n" +
                "                width: 248px !important;\n" +
                "            }\n" +
                "\n" +
                "            td[class*=\"mobile-header-padding-left\"] {\n" +
                "                width: 160px !important;\n" +
                "                padding-left: 0 !important;\n" +
                "            }\n" +
                "\n" +
                "            td[class*=\"mobile-header-padding-right\"] {\n" +
                "                width: 160px !important;\n" +
                "                padding-right: 0 !important;\n" +
                "            }\n" +
                "\n" +
                "            td[class=\"header-lg\"] {\n" +
                "                font-size: 24px !important;\n" +
                "                padding-bottom: 5px !important;\n" +
                "            }\n" +
                "\n" +
                "            td[class=\"header-md\"] {\n" +
                "                font-size: 18px !important;\n" +
                "                padding-bottom: 5px !important;\n" +
                "            }\n" +
                "\n" +
                "            td[class=\"content-padding\"] {\n" +
                "                padding: 5px 0 30px !important;\n" +
                "            }\n" +
                "\n" +
                "            td[class=\"button\"] {\n" +
                "                padding: 5px !important;\n" +
                "            }\n" +
                "\n" +
                "            td[class*=\"free-text\"] {\n" +
                "                padding: 10px 18px 30px !important;\n" +
                "            }\n" +
                "\n" +
                "            td[class=\"info-block\"] {\n" +
                "                display: block !important;\n" +
                "                width: 280px !important;\n" +
                "                padding-bottom: 40px !important;\n" +
                "            }\n" +
                "\n" +
                "            td[class=\"info-img\"],\n" +
                "            img[class=\"info-img\"] {\n" +
                "                width: 278px !important;\n" +
                "            }\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body bgcolor=\"#f7f7f7\">\n" +
                "<table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"container-for-gmail-android\" width=\"100%\">\n" +
                "    <tr>\n" +
                "        <td align=\"left\" valign=\"top\" width=\"100%\" style=\"background:repeat-x url(http://s3.amazonaws.com/swu-filepicker/4E687TRe69Ld95IDWyEg_bg_top_02.jpg) #ffffff;\">\n" +
                "            <center>\n" +
                "                <img src=\"http://s3.amazonaws.com/swu-filepicker/SBb2fQPrQ5ezxmqUTgCr_transparent.png\" class=\"force-width-gmail\">\n" +
                "                <table cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" bgcolor=\"#ffffff\" background=\"http://s3.amazonaws.com/swu-filepicker/4E687TRe69Ld95IDWyEg_bg_top_02.jpg\" style=\"background-color:transparent\">\n" +
                "                    <tr>\n" +
                "                        <td width=\"100%\" height=\"80\" valign=\"top\" style=\"text-align: center; vertical-align:middle;\">\n" +
                "                            <!--[if gte mso 9]>\n" +
                "                            <v:rect xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"true\" stroke=\"false\" style=\"mso-width-percent:1000;height:80px; v-text-anchor:middle;\">\n" +
                "                                <v:fill type=\"tile\" src=\"http://s3.amazonaws.com/swu-filepicker/4E687TRe69Ld95IDWyEg_bg_top_02.jpg\" color=\"#ffffff\" />\n" +
                "                                <v:textbox inset=\"0,0,0,0\">\n" +
                "                            <![endif]-->\n" +
                "                            <center>\n" +
                "                                <table cellpadding=\"0\" cellspacing=\"0\" width=\"600\" class=\"w320\">\n" +
                "                                    <tr>\n" +
                "                                        <td class=\"pull-left mobile-header-padding-left\" style=\"vertical-align: middle;\">\n" +
                "                                            <a href=\"https://www.pic-upload.de\" target=\"_blank\"\"><img width=\"137\" height=\"47\" src=\"https://www2.pic-upload.de/img/36322705/pick-me-up_logo.png\" alt=\"logo\"></a>\n" +
                "                                        </td>\n" +
                "                                        <td class=\"pull-right mobile-header-padding-right\" style=\"color: #4d4d4d;\">\n" +
                "                                            <a href=\"\"><img width=\"44\" height=\"47\" src=\"http://s3.amazonaws.com/swu-filepicker/k8D8A7SLRuetZspHxsJk_social_08.gif\" alt=\"twitter\" /></a>\n" +
                "                                            <a href=\"\"><img width=\"38\" height=\"47\" src=\"http://s3.amazonaws.com/swu-filepicker/LMPMj7JSRoCWypAvzaN3_social_09.gif\" alt=\"facebook\" /></a>\n" +
                "                                            <a href=\"\"><img width=\"40\" height=\"47\" src=\"http://s3.amazonaws.com/swu-filepicker/hR33ye5FQXuDDarXCGIW_social_10.gif\" alt=\"rss\" /></a>\n" +
                "                                        </td>\n" +
                "                                    </tr>\n" +
                "                                </table>\n" +
                "                            </center>\n" +
                "                            <!--[if gte mso 9]>\n" +
                "                            </v:textbox>\n" +
                "                            </v:rect>\n" +
                "                            <![endif]-->\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </table>\n" +
                "            </center>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td align=\"center\" valign=\"top\" width=\"100%\" style=\"background-color: #f7f7f7;\" class=\"content-padding\">\n" +
                "            <center>\n" +
                "                <table cellspacing=\"0\" cellpadding=\"0\" width=\"600\" class=\"w320\">\n" +
                "                    <tr>\n" +
                "                        <td class=\"header-lg\">\n" +
                "                            Can you pick me up?\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td class=\"free-text\">\n" +
                "                            Du hast eine neue Fahrt erstellt! Suche jetzt nach neuen Mitfahrern:\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td class=\"button\">\n" +
                "                            <div><!--[if mso]>\n" +
                "                                <v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" href=\"http://\" style=\"height:45px;v-text-anchor:middle;width:155px;\" arcsize=\"15%\" strokecolor=\"#ffffff\" fillcolor=\"#ff6f6f\">\n" +
                "                                    <w:anchorlock/>\n" +
                "                                    <center style=\"color:#ffffff;font-family:Helvetica, Arial, sans-serif;font-size:14px;font-weight:regular;\">Pick Me Up!</center>\n" +
                "                                </v:roundrect>\n" +
                "                                <![endif]--><a class=\"button-mobile\" href=\"132.231.36.206/\"\n" +
                "                                               style=\"background-color:#ff6f6f;border-radius:5px;color:#ffffff;display:inline-block;font-family:'Cabin', Helvetica, Arial, sans-serif;font-size:14px;font-weight:regular;line-height:45px;text-align:center;text-decoration:none;width:155px;-webkit-text-size-adjust:none;mso-hide:all;\">Pick Me Up!</a></div>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </table>\n" +
                "            </center>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td align=\"center\" valign=\"top\" width=\"100%\" style=\"background-color: #f7f7f7; height: 100px;\">\n" +
                "            <center>\n" +
                "                <table cellspacing=\"0\" cellpadding=\"0\" width=\"600\" class=\"w320\">\n" +
                "                    <tr>\n" +
                "                        <td style=\"padding: 25px 0 25px\">\n" +
                "                            <strong>PickmeUP!</strong><br />\n" +
                "                            Uni Passau <br />\n" +
                "                            Teamorientierte Softwareentwicklung <br /><br />\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </table>\n" +
                "            </center>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "</table>\n" +
                "</body>\n" +
                "</html>";

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject("Deine erstellte Fahrt bei PickmeUP");

            Multipart mp = new MimeMultipart();

            BodyPart pixPart = new MimeBodyPart();
            pixPart.setContent(createdHTML, "text/html");

            // Collect the Parts into the MultiPart
            mp.addBodyPart(pixPart);

            // Put the MultiPart into the Message
            message.setContent(mp);

            // Send message
            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendBookingMail(User user) {
        String to = user.getEmail();
        String from = "pickmeup.uni.passau@gmail.com";
        final String username = "pickmeup.uni.passau@gmail.com";
        final String password = "lukpkucfltvvpxkf";
        String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        String bookingHTML = "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\n" +
                "    <title>I can pick you up!!</title>\n" +
                "\n" +
                "    <style type=\"text/css\">\n" +
                "        /* Take care of image borders and formatting, client hacks */\n" +
                "        img { max-width: 600px; outline: none; text-decoration: none; -ms-interpolation-mode: bicubic;}\n" +
                "        a img { border: none; }\n" +
                "        table { border-collapse: collapse !important;}\n" +
                "        #outlook a { padding:0; }\n" +
                "        .ReadMsgBody { width: 100%; }\n" +
                "        .ExternalClass { width: 100%; }\n" +
                "        .backgroundTable { margin: 0 auto; padding: 0; width: 100% !important; }\n" +
                "        table td { border-collapse: collapse; }\n" +
                "        .ExternalClass * { line-height: 115%; }\n" +
                "        .container-for-gmail-android { min-width: 600px; }\n" +
                "\n" +
                "\n" +
                "        /* General styling */\n" +
                "        * {\n" +
                "            font-family: Helvetica, Arial, sans-serif;\n" +
                "        }\n" +
                "\n" +
                "        body {\n" +
                "            -webkit-font-smoothing: antialiased;\n" +
                "            -webkit-text-size-adjust: none;\n" +
                "            width: 100% !important;\n" +
                "            margin: 0 !important;\n" +
                "            height: 100%;\n" +
                "            color: #676767;\n" +
                "        }\n" +
                "\n" +
                "        td {\n" +
                "            font-family: Helvetica, Arial, sans-serif;\n" +
                "            font-size: 14px;\n" +
                "            color: #777777;\n" +
                "            text-align: center;\n" +
                "            line-height: 21px;\n" +
                "        }\n" +
                "\n" +
                "        a {\n" +
                "            color: #676767;\n" +
                "            text-decoration: none !important;\n" +
                "        }\n" +
                "\n" +
                "        .pull-left {\n" +
                "            text-align: left;\n" +
                "        }\n" +
                "\n" +
                "        .pull-right {\n" +
                "            text-align: right;\n" +
                "        }\n" +
                "\n" +
                "        .header-lg,\n" +
                "        .header-md,\n" +
                "        .header-sm {\n" +
                "            font-size: 32px;\n" +
                "            font-weight: 700;\n" +
                "            line-height: normal;\n" +
                "            padding: 35px 0 0;\n" +
                "            color: #4d4d4d;\n" +
                "        }\n" +
                "\n" +
                "        .header-md {\n" +
                "            font-size: 24px;\n" +
                "        }\n" +
                "\n" +
                "        .header-sm {\n" +
                "            padding: 5px 0;\n" +
                "            font-size: 18px;\n" +
                "            line-height: 1.3;\n" +
                "        }\n" +
                "\n" +
                "        .content-padding {\n" +
                "            padding: 20px 0 30px;\n" +
                "        }\n" +
                "\n" +
                "        .mobile-header-padding-right {\n" +
                "            width: 290px;\n" +
                "            text-align: right;\n" +
                "            padding-left: 10px;\n" +
                "        }\n" +
                "\n" +
                "        .mobile-header-padding-left {\n" +
                "            width: 290px;\n" +
                "            text-align: left;\n" +
                "            padding-left: 10px;\n" +
                "        }\n" +
                "\n" +
                "        .free-text {\n" +
                "            width: 100% !important;\n" +
                "            padding: 10px 60px 0px;\n" +
                "        }\n" +
                "\n" +
                "        .block-rounded {\n" +
                "            border-radius: 5px;\n" +
                "            border: 1px solid #e5e5e5;\n" +
                "            vertical-align: top;\n" +
                "        }\n" +
                "\n" +
                "        .button {\n" +
                "            padding: 30px 0;\n" +
                "        }\n" +
                "\n" +
                "        .info-block {\n" +
                "            padding: 0 20px;\n" +
                "            width: 260px;\n" +
                "        }\n" +
                "\n" +
                "        .block-rounded {\n" +
                "            width: 260px;\n" +
                "        }\n" +
                "\n" +
                "        .info-img {\n" +
                "            width: 258px;\n" +
                "            border-radius: 5px 5px 0 0;\n" +
                "        }\n" +
                "\n" +
                "        .force-width-gmail {\n" +
                "            min-width:600px;\n" +
                "            height: 0px !important;\n" +
                "            line-height: 1px !important;\n" +
                "            font-size: 1px !important;\n" +
                "        }\n" +
                "\n" +
                "        .button-width {\n" +
                "            width: 228px;\n" +
                "        }\n" +
                "\n" +
                "    </style>\n" +
                "\n" +
                "    <style type=\"text/css\" media=\"screen\">\n" +
                "        @import url(http://fonts.googleapis.com/css?family=Oxygen:400,700);\n" +
                "    </style>\n" +
                "\n" +
                "    <style type=\"text/css\" media=\"screen\">\n" +
                "        @media screen {\n" +
                "            /* Thanks Outlook 2013! */\n" +
                "            * {\n" +
                "                font-family: 'Oxygen', 'Helvetica Neue', 'Arial', 'sans-serif' !important;\n" +
                "            }\n" +
                "        }\n" +
                "    </style>\n" +
                "\n" +
                "    <style type=\"text/css\" media=\"only screen and (max-width: 480px)\">\n" +
                "        /* Mobile styles */\n" +
                "        @media only screen and (max-width: 480px) {\n" +
                "\n" +
                "            table[class*=\"container-for-gmail-android\"] {\n" +
                "                min-width: 290px !important;\n" +
                "                width: 100% !important;\n" +
                "            }\n" +
                "\n" +
                "            table[class=\"w320\"] {\n" +
                "                width: 320px !important;\n" +
                "            }\n" +
                "\n" +
                "            img[class=\"force-width-gmail\"] {\n" +
                "                display: none !important;\n" +
                "                width: 0 !important;\n" +
                "                height: 0 !important;\n" +
                "            }\n" +
                "\n" +
                "            a[class=\"button-width\"],\n" +
                "            a[class=\"button-mobile\"] {\n" +
                "                width: 248px !important;\n" +
                "            }\n" +
                "\n" +
                "            td[class*=\"mobile-header-padding-left\"] {\n" +
                "                width: 160px !important;\n" +
                "                padding-left: 0 !important;\n" +
                "            }\n" +
                "\n" +
                "            td[class*=\"mobile-header-padding-right\"] {\n" +
                "                width: 160px !important;\n" +
                "                padding-right: 0 !important;\n" +
                "            }\n" +
                "\n" +
                "            td[class=\"header-lg\"] {\n" +
                "                font-size: 24px !important;\n" +
                "                padding-bottom: 5px !important;\n" +
                "            }\n" +
                "\n" +
                "            td[class=\"header-md\"] {\n" +
                "                font-size: 18px !important;\n" +
                "                padding-bottom: 5px !important;\n" +
                "            }\n" +
                "\n" +
                "            td[class=\"content-padding\"] {\n" +
                "                padding: 5px 0 30px !important;\n" +
                "            }\n" +
                "\n" +
                "            td[class=\"button\"] {\n" +
                "                padding: 5px !important;\n" +
                "            }\n" +
                "\n" +
                "            td[class*=\"free-text\"] {\n" +
                "                padding: 10px 18px 30px !important;\n" +
                "            }\n" +
                "\n" +
                "            td[class=\"info-block\"] {\n" +
                "                display: block !important;\n" +
                "                width: 280px !important;\n" +
                "                padding-bottom: 40px !important;\n" +
                "            }\n" +
                "\n" +
                "            td[class=\"info-img\"],\n" +
                "            img[class=\"info-img\"] {\n" +
                "                width: 278px !important;\n" +
                "            }\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body bgcolor=\"#f7f7f7\">\n" +
                "<table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"container-for-gmail-android\" width=\"100%\">\n" +
                "    <tr>\n" +
                "        <td align=\"left\" valign=\"top\" width=\"100%\" style=\"background:repeat-x url(http://s3.amazonaws.com/swu-filepicker/4E687TRe69Ld95IDWyEg_bg_top_02.jpg) #ffffff;\">\n" +
                "            <center>\n" +
                "                <img src=\"http://s3.amazonaws.com/swu-filepicker/SBb2fQPrQ5ezxmqUTgCr_transparent.png\" class=\"force-width-gmail\">\n" +
                "                <table cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" bgcolor=\"#ffffff\" background=\"http://s3.amazonaws.com/swu-filepicker/4E687TRe69Ld95IDWyEg_bg_top_02.jpg\" style=\"background-color:transparent\">\n" +
                "                    <tr>\n" +
                "                        <td width=\"100%\" height=\"80\" valign=\"top\" style=\"text-align: center; vertical-align:middle;\">\n" +
                "                            <!--[if gte mso 9]>\n" +
                "                            <v:rect xmlns:v=\"urn:schemas-microsoft-com:vml\" fill=\"true\" stroke=\"false\" style=\"mso-width-percent:1000;height:80px; v-text-anchor:middle;\">\n" +
                "                                <v:fill type=\"tile\" src=\"http://s3.amazonaws.com/swu-filepicker/4E687TRe69Ld95IDWyEg_bg_top_02.jpg\" color=\"#ffffff\" />\n" +
                "                                <v:textbox inset=\"0,0,0,0\">\n" +
                "                            <![endif]-->\n" +
                "                            <center>\n" +
                "                                <table cellpadding=\"0\" cellspacing=\"0\" width=\"600\" class=\"w320\">\n" +
                "                                    <tr>\n" +
                "                                        <td class=\"pull-left mobile-header-padding-left\" style=\"vertical-align: middle;\">\n" +
                "                                            <a href=\"https://www.pic-upload.de\" target=\"_blank\"\"><img width=\"137\" height=\"47\" src=\"https://www2.pic-upload.de/img/36322705/pick-me-up_logo.png\" alt=\"logo\"></a>\n" +
                "                                        </td>\n" +
                "                                        <td class=\"pull-right mobile-header-padding-right\" style=\"color: #4d4d4d;\">\n" +
                "                                            <a href=\"\"><img width=\"44\" height=\"47\" src=\"http://s3.amazonaws.com/swu-filepicker/k8D8A7SLRuetZspHxsJk_social_08.gif\" alt=\"twitter\" /></a>\n" +
                "                                            <a href=\"\"><img width=\"38\" height=\"47\" src=\"http://s3.amazonaws.com/swu-filepicker/LMPMj7JSRoCWypAvzaN3_social_09.gif\" alt=\"facebook\" /></a>\n" +
                "                                            <a href=\"\"><img width=\"40\" height=\"47\" src=\"http://s3.amazonaws.com/swu-filepicker/hR33ye5FQXuDDarXCGIW_social_10.gif\" alt=\"rss\" /></a>\n" +
                "                                        </td>\n" +
                "                                    </tr>\n" +
                "                                </table>\n" +
                "                            </center>\n" +
                "                            <!--[if gte mso 9]>\n" +
                "                            </v:textbox>\n" +
                "                            </v:rect>\n" +
                "                            <![endif]-->\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </table>\n" +
                "            </center>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td align=\"center\" valign=\"top\" width=\"100%\" style=\"background-color: #f7f7f7;\" class=\"content-padding\">\n" +
                "            <center>\n" +
                "                <table cellspacing=\"0\" cellpadding=\"0\" width=\"600\" class=\"w320\">\n" +
                "                    <tr>\n" +
                "                        <td class=\"header-lg\">\n" +
                "                            Willkommen bei PickmeUP!\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td class=\"free-text\">\n" +
                "                            Du hast erfolgreich eine Fahrt gebucht! Finde jetzt weitere Fahrten:\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td class=\"button\">\n" +
                "                            <div><!--[if mso]>\n" +
                "                                <v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:w=\"urn:schemas-microsoft-com:office:word\" href=\"http://\" style=\"height:45px;v-text-anchor:middle;width:155px;\" arcsize=\"15%\" strokecolor=\"#ffffff\" fillcolor=\"#ff6f6f\">\n" +
                "                                    <w:anchorlock/>\n" +
                "                                    <center style=\"color:#ffffff;font-family:Helvetica, Arial, sans-serif;font-size:14px;font-weight:regular;\">Pick Me Up!</center>\n" +
                "                                </v:roundrect>\n" +
                "                                <![endif]--><a class=\"button-mobile\" href=\"132.231.36.206/\"\n" +
                "                                               style=\"background-color:#ff6f6f;border-radius:5px;color:#ffffff;display:inline-block;font-family:'Cabin', Helvetica, Arial, sans-serif;font-size:14px;font-weight:regular;line-height:45px;text-align:center;text-decoration:none;width:155px;-webkit-text-size-adjust:none;mso-hide:all;\">Pick Me Up!</a></div>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </table>\n" +
                "            </center>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "        <td align=\"center\" valign=\"top\" width=\"100%\" style=\"background-color: #f7f7f7; height: 100px;\">\n" +
                "            <center>\n" +
                "                <table cellspacing=\"0\" cellpadding=\"0\" width=\"600\" class=\"w320\">\n" +
                "                    <tr>\n" +
                "                        <td style=\"padding: 25px 0 25px\">\n" +
                "                            <strong>PickmeUP!</strong><br />\n" +
                "                            Uni Passau <br />\n" +
                "                            Teamorientierte Softwareentwicklung <br /><br />\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </table>\n" +
                "            </center>\n" +
                "        </td>\n" +
                "    </tr>\n" +
                "</table>\n" +
                "</body>\n" +
                "</html>";

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject("Deine gebuchte Fahrt bei PickmeUP");

            Multipart mp = new MimeMultipart();

            BodyPart pixPart = new MimeBodyPart();
            pixPart.setContent(bookingHTML, "text/html");

            // Collect the Parts into the MultiPart
            mp.addBodyPart(pixPart);

            // Put the MultiPart into the Message
            message.setContent(mp);

            // Send message
            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}