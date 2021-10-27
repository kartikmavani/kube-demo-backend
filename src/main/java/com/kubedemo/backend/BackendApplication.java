package com.kubedemo.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(BackendApplication.class);

    public static void main(String[] args) {
        LOG.info("STARTING THE APPLICATION");
        SpringApplication.run(BackendApplication.class, args);
        LOG.info("APPLICATION FINISHED");
    }

    @Override
    public void run(String... args) throws InterruptedException, IOException {
        LOG.info("EXECUTING : command line runner");
		String containerName = System.getenv("containerName") == null ? "tempContainer" : System.getenv("containerName");
		while (true) {
			try {
				URL url = new URL("http://localhost:8090/addToList?name="+containerName);
				HttpURLConnection http = (HttpURLConnection)url.openConnection();
				http.setRequestMethod("POST");
				http.setDoOutput(true);
				String data = "{\n  \"test\": 12345\n}";
				byte[] out = data.getBytes(StandardCharsets.UTF_8);

				OutputStream stream = http.getOutputStream();
				stream.write(out);

				LOG.info(http.getResponseCode() + " " + http.getResponseMessage());
				http.disconnect();
			}
			catch (IOException e) {
				LOG.info("Cannot connect to the frontend");
			}

			Thread.sleep(1000L);
		}

    }
}