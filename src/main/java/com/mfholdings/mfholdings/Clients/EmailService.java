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

    private final String fromEmail = "Enter Email Here";

    private final String toEmail = "Enter Email Here";

    EmailService(AWSConfig awsConfig){
        this.awsConfig = awsConfig;
    }

    public void sendEmail(String message){
        SendEmailRequest sendEmailRequest = new SendEmailRequest().
        withDestination(new Destination().withToAddresses(toEmail))
        .withMessage(new Message().withBody(new Body()
        .withHtml(new Content().withCharset("UTF-8")
        .withData(message))).withSubject(new Content().withCharset("UTF-8").withData("Stocks Changed")))
        .withSource(fromEmail);
        awsConfig.getAmazonSimpleEmailServiceClient().sendEmail(sendEmailRequest);
    }
}
 