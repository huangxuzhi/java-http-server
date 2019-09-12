package http.server;

import http.server.parser.HttpRequestParser;
import http.server.request.HttpRequest;
import http.server.request.RequestMethod;
import http.server.request.body.UrlencodedRequestBody;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.junit.*;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class TestHttpRequestParser {

    private ServerSocket ss;

    private HttpRequest request;

    private CloseableHttpClient client = HttpClients.createDefault();

    @Before
    public void startServer() throws Exception {
        ss = new ServerSocket(8080);
        Thread t = new Thread() {
            @Override
            public void run() {
                Socket s = null;
                try {
                    s = ss.accept();
                    HttpRequestParser p = new HttpRequestParser(s.getInputStream());
                    request = p.parse();
                    s.getOutputStream().write("Hello World".getBytes("UTF-8"));
                    s.getOutputStream().flush();
                    s.getOutputStream().close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        t.start();
    }

    @Test
    public void testGet()throws Exception {

        HttpGet get = new HttpGet();
        String uri = "http://localhost:8080";
        String subPath = "/companies/323/area/222/employees/111";
        Map<String,String> params = new HashMap<>();
        params.put("clientId","232323");
        params.put("method", "query");
        URIBuilder builder = new URIBuilder(uri + subPath);
        params.forEach((name, value) -> {
            builder.addParameter(name, value);
        });
        get.setURI(builder.build());
        String customHeader = "custom-header";
        String customHeaderVal = "custom-val";
        get.addHeader(customHeader, customHeaderVal);
        try {
            CloseableHttpResponse response = client.execute(get);
        } catch (ClientProtocolException ex) {

        }
        assertEquals(RequestMethod.GET.name(),request.getMethod());
        assertEquals(subPath, request.getUri());
        assertEquals(request.getHeader(customHeader), customHeaderVal);
        params.forEach((name, value) -> {
                    String parsedVal = request.getParameters().get(name);
                    if (parsedVal == null) {
                        fail("Expect " + name + " parameter, but not parsed out.");
                    } else {
                        assertEquals(value, parsedVal);
                    }
                }
        );
    }

    @Test
    public void testUrlEncodedPost() throws IOException, URISyntaxException {
        HttpPost post = new HttpPost();
        post.setURI(new URI("http://localhost:8080"));
        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("sn", "C02G8416DRJM"));
        urlParameters.add(new BasicNameValuePair("num", "12345"));
        post.setEntity(new UrlEncodedFormEntity(urlParameters));
        try {
            CloseableHttpResponse response = client.execute(post);
        } catch (ClientProtocolException ex) {

        }
        assertEquals(RequestMethod.POST.name(), request.getMethod());
        urlParameters.forEach(nameValuePair -> {
            Map<String,String> body = (Map)request.getBody().getContent();
            String parsedVal = body.get(nameValuePair.getName());
            if (parsedVal == null) {
                if (parsedVal == null) {
                    fail("Expect " + nameValuePair.getName() + " parameter, but not parsed out.");
                } else {
                    assertEquals(nameValuePair.getValue(), parsedVal);
                }
            }
        });
    }

    @Test
    public void testJsonPost() throws URISyntaxException {
        HttpPost post = new HttpPost();
        post.setURI(new URI("http://localhost:8080"));

    }

    @After
    public void stopServer() throws IOException {
        ss.close();
    }
}
