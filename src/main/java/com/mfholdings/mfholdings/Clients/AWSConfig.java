package com.mfholdings.mfholdings.Clients;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;

@Configuration
public class AWSConfig {

    @Bean
    AmazonSimpleEmailService getAmazonSimpleEmailServiceClient(){
        return AmazonSimpleEmailServiceClientBuilder.standard().withCredentials(getAWAwsCredentialsProvider()).withRegion("ap-south-1").build();
    }

    private AWSCredentialsProvider getAWAwsCredentialsProvider(){
        AWSCredentials awsCredentials = new BasicAWSCredentials(System.getenv("access-key"), System.getenv("secret-key"));
        return new AWSStaticCredentialsProvider(awsCredentials);
    }

}
