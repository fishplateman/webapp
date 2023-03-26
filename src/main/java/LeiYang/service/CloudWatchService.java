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
        Dimension countDimension = new Dimension()
                .withName("Reqcount")
                .withValue("count");

        Dimension timerDimension = new Dimension()
                .withName("Reqtimer")
                .withValue("timer");

        MetricDatum requestCountDatum = new MetricDatum()
                .withMetricName(name+"-count")
                .withUnit(StandardUnit.Count.toString())
                .withValue(requestCount)
                .withDimensions(countDimension);

        MetricDatum responseTimeDatum = new MetricDatum()
                .withMetricName(name+"-timer")
                .withUnit(StandardUnit.Milliseconds.toString())
                .withValue(responseTime)
                .withDimensions(timerDimension);

        PutMetricDataRequest request = new PutMetricDataRequest()
                .withNamespace("CSYE6225")
                .withMetricData(requestCountDatum, responseTimeDatum);

        PutMetricDataResult response = cloudWatch.putMetricData(request);
    }
}
