package br.com.bios.aws_project01.config.local;

import br.com.bios.aws_project01.service.ProductPublisher;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("local")
public class SnsCreate {

    private static final Logger LOG = LoggerFactory.getLogger(SnsCreate.class);

    private final String productEventsTopic;
    private final AmazonSNS snsClient;

    public SnsCreate() {
        this.snsClient = AmazonSNSClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:4566",
                        Regions.US_EAST_2.getName()))
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .build();

        CreateTopicRequest createTopicRequest =  new CreateTopicRequest("product-events");
        this.productEventsTopic = this.snsClient.createTopic(createTopicRequest).getTopicArn();

        LOG.info("SNS topic arn: " + this.productEventsTopic);
    }

    @Bean
    public AmazonSNS snsClient(){
        return this.snsClient;
    }

    @Bean(name = "productEventsTopic")
    public Topic snsProductEventsTopic() {
        return new Topic().withTopicArn(productEventsTopic);
    }

}
