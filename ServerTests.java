import static org.junit.jupiter.api.Assertions.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.lang.reflect.Array;

public class ServerTests
{
    String shortMessage = "Hello World";
    String[] args = new String[3];
    @Before
    public void startServerTest(){
        Server server = new Server();
        Thread serverThread = new Thread(() -> {
            server.;
        });
        serverThread.start();
        while (!server.isRunning() {
            Thread.sleep(1000);
        }
    }
    ManInTheMiddle mitm;
    {
        try {

            mitm = ManInTheMiddle.main();
            Thread serverThread = new Thread(() -> {
                mitm.init();
            });
            serverThread.start();
            while (!mitm.isRunning() {
                Thread.sleep(1000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    Server server;
    {
        try {
            server = Server.main();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void ServerResults() throws IOException {
        args[0] = "49999";
        args[1] = "16";
        args[2] = shortMessage;
        Client client = Client.main(args);
    }
}