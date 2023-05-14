
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public class HTTPServer {

    // For this main method we create and host a server with the hostname localhost and set it to running on port 8001.
    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8001), 0);
            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

            server.createContext("/", new Handler());//Here we create the context for the URLs that are used in the HTTP data transfer
            server.setExecutor(threadPoolExecutor);

            server.start(); //This is the heart of the system, where we assign the server-object a thread-pool, to keep it running

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

