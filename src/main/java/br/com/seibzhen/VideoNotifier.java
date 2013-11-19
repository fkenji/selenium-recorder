package br.com.seibzhen;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class VideoNotifier {

    private Logger logger = LoggerFactory.getLogger(VideoNotifier.class);

    public VideoNotifier() {
    }

    public void notifyStart(String hostAddress) throws IOException {
        if (hostAddress == null) {
            return;
        }


        String notifyUrl = String.format("http://%s:8080/selenium-recorder/start.groovy", hostAddress);

        logger.info("Headed to {}", notifyUrl);


        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            HttpGet get = new HttpGet(notifyUrl);
            response = httpClient.execute(get);
            String status = EntityUtils.toString(response.getEntity());

            return;
        } finally {
            httpClient.close();
        }
    }

    public void notifyStop(String hostAddress) throws IOException {
        if (hostAddress == null) {
            return;
        }
        String notifyUrl = "http://localhost:8080/selenium-recorder/stop.groovy";

        logger.info("Headed to {}", notifyUrl);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            HttpGet get = new HttpGet(notifyUrl);
            response = httpClient.execute(get);
            String status = EntityUtils.toString(response.getEntity());

            return;
        } finally {
            httpClient.close();
        }
    }


    public void notifyDestroy(String hostAddress) throws IOException {
        if (hostAddress == null) {
            return;
        }
        String notifyUrl = "http://localhost:8080/selenium-recorder/destroy.groovy";

        logger.info("Headed to {}", notifyUrl);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            HttpGet get = new HttpGet(notifyUrl);
            response = httpClient.execute(get);
            String status = EntityUtils.toString(response.getEntity());

            return;
        } finally {
            httpClient.close();
        }
    }
}
