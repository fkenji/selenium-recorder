package br.com.seibzhen;

import com.google.gson.Gson;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GridNodeLocator {

    private String baseGridHost;
    private String baseGridPort;

    private Logger logger = LoggerFactory.getLogger(GridNodeLocator.class);

    private Pattern urlPattern = Pattern.compile("http://([^:/]+)");

    public GridNodeLocator(String baseGridHost, String baseGridPort) {
        this.baseGridHost = baseGridHost;
        this.baseGridPort = baseGridPort;
    }

    public String locate(RemoteWebDriver webdriver) throws IOException {

        SessionId sessionId = webdriver.getSessionId();

        if (sessionId == null) {
            throw new IllegalStateException("Webdriver does not have an allocated sessionId!");
        }

        String actualSessionId = sessionId.toString();

        logger.debug("webdriver {} is running at {}", webdriver, actualSessionId);

        String nodeURL = locateCurrentNode(actualSessionId);
        String hostAddress = getHostAddress(nodeURL);

        logger.debug("sessionId {} is running at {}", actualSessionId, hostAddress);

        return hostAddress;
    }

    private String getHostAddress(String currentSeleniumNode) {
        String nodeAddress = currentSeleniumNode;
        Matcher matcher = urlPattern.matcher(nodeAddress);
        String cleanAddress = "";
        while (matcher.find()) {
            cleanAddress = matcher.group(1);
        }
        return cleanAddress;
    }

    private String locateCurrentNode(String actualSessionId) throws IOException {
        String testSessionApiUrl = String.format("http://%s:%s/grid/api/testsession?session=%s", baseGridHost, baseGridPort, actualSessionId);


        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            HttpGet get = new HttpGet(testSessionApiUrl);
            response = httpClient.execute(get);
            String gridNode = EntityUtils.toString(response.getEntity());

            return (String) new Gson().fromJson(gridNode, Map.class).get("proxyId");
        } finally {
            httpClient.close();
        }
    }
}
