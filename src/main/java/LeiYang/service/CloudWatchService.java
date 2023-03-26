package LeiYang.service;

import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CloudWatchService {
    @Autowired
    private AmazonCloudWatch cloudWatch;

    public void sendCustomMetric(String name, double requestCount, double responseTime) {
        Dimension dimension = new Dimension()
                .withName("CSYE6225")
                .withValue("webapp");

        MetricDatum requestCountDatum = new MetricDatum()
                .withMetricName(name+"-count")
                .withUnit(StandardUnit.Count.toString())
                .withValue(requestCount)
                .withDimensions(dimension);

        MetricDatum responseTimeDatum = new MetricDatum()
                .withMetricName(name+"-timer")
                .withUnit(StandardUnit.Milliseconds.toString())
                .withValue(responseTime)
                .withDimensions(dimension);

        PutMetricDataRequest request = new PutMetricDataRequest()
                .withNamespace("webapp")
                .withMetricData(requestCountDatum, responseTimeDatum);

        PutMetricDataResult response = cloudWatch.putMetricData(request);
    }
}
