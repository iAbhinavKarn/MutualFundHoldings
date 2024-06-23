package com.mfholdings.mfholdings.Clients;

import org.springframework.stereotype.Service;

import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

@Service
public class EmailService {

    public final AWSConfig awsConfig;

    EmailService(AWSConfig awsConfig){
        this.awsConfig = awsConfig;
    }

    public void sendEmail(String from, String to, String message){
        SendEmailRequest sendEmailRequest = new SendEmailRequest().withDestination(new Destination().withToAddresses(to)).withMessage(message).withSource(from);
        awsConfig.getAmazonSimpleEmailServiceClient().sendEmail(sendEmailRequest);
    }
}
