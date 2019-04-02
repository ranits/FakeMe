package fake;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.socket.client.IO;
import io.socket.client.Socket;
import okhttp3.*;

import java.io.IOException;
import java.net.URISyntaxException;

public class Utils {
    static final ObjectMapper mapper = new ObjectMapper();
    static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    static final OkHttpClient client = new OkHttpClient();


    public static final String SCRAPE_BASE_URL = "http://localhost:8000/scrape/";

    /**************************************************************************************************************/
    /** Graph API: */
    /**     POST: [/graph/:graph_name/load_graph] **/
    /**     GET: [/graph/:graph_name/get_center_nodes/:n]  **/
    /**     GET: [/graph/<graph_name>/sample_details]  **/
    /**************************************************************************************************************/
    public static final String GRAPH_BASE_URL = "http://localhost:8010/graph/";

    public static final String IMAGE_BASE_URL = "http://localhost:8020/image/";

    public static final String NLP_BASE_URL = "http://localhost:8030/fields/";

    public static <T> CallResponse<T> call(String url, Object obj) throws IOException {
        RequestBody body = RequestBody.create(JSON, mapper.writeValueAsString(obj));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return new CallResponse(response.code(),response.body().string());
        }
    }

    public static class CallResponse<T> {
        public int code;
        public T body;

        public CallResponse(int code, T body) {
            this.code = code;
            this.body = body;
        }
    }

    public static void ws(String url) throws URISyntaxException {
        Socket socket = IO.socket(url);
        socket.on(Socket.EVENT_CONNECT, args -> {
            socket.emit("foo", "hi");
            socket.disconnect();
        }).on("event", args -> {})
                .on(Socket.EVENT_CONNECTING, args -> {})
                .on(Socket.EVENT_CONNECT_TIMEOUT, args -> {})
                .on(Socket.EVENT_DISCONNECT, args -> {});
        socket.connect();
    }
}
