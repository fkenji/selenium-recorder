package br.com.seibzhen.grid;

import org.openqa.grid.common.RegistrationRequest;
import org.openqa.grid.internal.Registry;
import org.openqa.grid.internal.TestSession;
import org.openqa.grid.internal.listeners.TestSessionListener;
import org.openqa.grid.selenium.proxy.DefaultRemoteProxy;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VideoAwareRecorderProxy extends DefaultRemoteProxy implements TestSessionListener {

    private Pattern urlPattern = Pattern.compile("http://([^:/]+)");

    public VideoAwareRecorderProxy(RegistrationRequest request, Registry registry) {
        super(request, registry);
        System.out.println("Starting " + getClass().getSimpleName());
    }

    @Override
    public void beforeSession(TestSession session) {
        super.beforeSession(session);
        System.out.println(String.format("[vsky] Slot created at: %s", session.getSlot().getRemoteURL()));
        startRecording(session);
    }


    public void startRecording(TestSession session) {
        try {
            String nodeAddress = session.getSlot().getProxy().getRemoteHost().toString();
            Matcher matcher = urlPattern.matcher(nodeAddress);
            String cleanAddress = "";
            while (matcher.find()) {
                cleanAddress = matcher.group();
            }

            System.out.println(String.format("Going to start recording at %s", cleanAddress));
            URL url = new URL(cleanAddress + ":8080/selenium-recorder/start.groovy");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            System.out.println(inputStreamToString(conn.getInputStream()));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopRecording(TestSession session) {
        try {
            String nodeAddress = session.getSlot().getProxy().getRemoteHost().toString();
            Matcher matcher = urlPattern.matcher(nodeAddress);
            String cleanAddress = "";
            while (matcher.find()) {
                cleanAddress = matcher.group();
            }

            System.out.println(String.format("Going to stop recording at %s", cleanAddress));
            URL url = new URL(cleanAddress + ":8080/selenium-recorder/stop.groovy");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            System.out.println(inputStreamToString(conn.getInputStream()));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterSession(TestSession session) {
        super.afterSession(session);
        System.out.println(String.format("[vsky] Slot finished at: %s", session.getSlot().getRemoteURL()));
        stopRecording(session);
    }



    public static String inputStreamToString(InputStream is) throws IOException {
        StringBuilder result = new StringBuilder();
        int in;
        while ((in = is.read()) != -1) {
            result.append((char) in);
        }
        is.close();
        return result.toString();
    }

}
