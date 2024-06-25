package com.mfholdings.mfholdings.Clients;

import org.springframework.stereotype.Service;

import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

@Service
public class EmailService {

    public final AWSConfig awsConfig;

    EmailService(AWSConfig awsConfig){
        this.awsConfig = awsConfig;
    }

    public void sendEmail(String from, String to, String message){
        SendEmailRequest sendEmailRequest = new SendEmailRequest().
        withDestination(new Destination().withToAddresses(to))
        .withMessage(new Message().withBody(new Body()
        .withHtml(new Content().withCharset("UTF-8")
        .withData(message))).withSubject(new Content().withCharset("UTF-8").withData(message)))
        .withSource(from);
        awsConfig.getAmazonSimpleEmailServiceClient().sendEmail(sendEmailRequest);
    }
}
 