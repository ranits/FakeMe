package fake;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.socket.client.IO;
import io.socket.client.Socket;
import okhttp3.*;

import java.io.IOException;
import java.net.URISyntaxException;

public class Utils {
    public static final ObjectMapper mapper = new ObjectMapper();
    static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    static final OkHttpClient client = new OkHttpClient();

    public static <T> CallResponse<T> call(String url, Object obj) throws IOException {
        RequestBody body = RequestBody.create(JSON, mapper.writeValueAsString(obj));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return new CallResponse(response.code(), response.body().string());
        }
    }

    public static <T> T unwrap(String response, TypeReference<T> typeReference) throws IOException {
        return mapper.readValue(response, typeReference);
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
        }).on("event", args -> {
        })
                .on(Socket.EVENT_CONNECTING, args -> {
                })
                .on(Socket.EVENT_CONNECT_TIMEOUT, args -> {
                })
                .on(Socket.EVENT_DISCONNECT, args -> {
                });
        socket.connect();
    }
}
