package LeiYang.config;

import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudWatchConfig {
    @Value("${aws.region}")
    private String awsRegion;

    @Bean
    public AmazonCloudWatch amazonCloudWatch() {
        return AmazonCloudWatchClientBuilder.standard()
                .withRegion(awsRegion)
                .build();
    }
}
