package br.com.bios.aws_project01.config.local;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.Topic;
import com.amazonaws.services.sns.util.Topics;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("local")
public class SqsCreateSubscribe {

    public SqsCreateSubscribe(AmazonSNS snsClient, @Qualifier("productEventsTopic") Topic productEventsTopic) {
        AmazonSQS sqsClient = AmazonSQSClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:4566",
                        Regions.US_EAST_2.getName()))
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .build();

        String productEventsQueueUrl = sqsClient.createQueue(new CreateQueueRequest("product-events")).getQueueUrl();
        Topics.subscribeQueue(snsClient, sqsClient, productEventsTopic.getTopicArn(), productEventsQueueUrl);
    }
}
