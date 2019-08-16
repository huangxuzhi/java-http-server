package http.server.request.body;

import http.server.common.Constants;
import http.server.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class UrlencodedRequestBody extends AbstractHttpRequestBody {

    private Map<String,String> encodedParams;

    public UrlencodedRequestBody(InputStream is) {
        super(RequestBodyType.X_WWW_FORM_URLENCODED, is);
    }

    @Override
    public Map<String,String> getContent() {
        return encodedParams;
    }

    @Override
    public void initContent() throws IOException {
        byte[] b = new byte[is.available()];
        is.read(b);
        String s = new String(b);
        s = URLDecoder.decode(s, Constants.UTF_8);
        String[] arr = s.split(Constants.AND_STR);
        if (arr.length > 0) {
            if (encodedParams == null) {
                encodedParams = new HashMap<>();
            }
            for (int i = 0; i < arr.length; i++) {
                if (StringUtils.hasText(arr[i])) {
                    String[] arr1 = arr[i].split(Constants.EQ_STR);
                    if (arr1.length > 1 && StringUtils.hasText(arr1[0]) && StringUtils.hasText(arr1[1])) {
                        encodedParams.put(arr1[0], arr1[1]);
                    }
                }
            }
        }
    }
}
