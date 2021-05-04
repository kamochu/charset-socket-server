package tech.meliora.natujenge.charset;

import tech.meliora.natujenge.charset.util.ByteToHexPrinter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.time.LocalDateTime;

/**
 * A character set server, that receives a byte stream,
 * prints the data and sends back the same data over the network...
 * <p>
 * It is an echo server (sends the same thing it received)
 */
public class CharsetServer {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void start(int port) {

        try {

            //start the server - listens on the port shared (TCP port)
            serverSocket = new ServerSocket(port);

            System.out.println(LocalDateTime.now() + "|started the server on port " + port);

        } catch (IOException ex) {

            System.out.println(LocalDateTime.now() + "|error starting the server on port " + port);
            ex.printStackTrace(System.err);

            //if there is an error starting the server, return - stop the service
            return;
        }

        while (true) {
            try {

                System.out.println(LocalDateTime.now() + "|waiting for client connection on " + port);
                //blocking call that waits to receive connections from client
                clientSocket = serverSocket.accept();


                System.out.println("**************************************************");


                System.out.println(LocalDateTime.now() + "|received a client connection on " + port
                        + "|client_ip: " + clientSocket.getInetAddress().getHostAddress()
                        + "|client_port: " + clientSocket.getPort() + "|about to the read the data");


                out = new PrintWriter(clientSocket.getOutputStream(), true);
                InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
                in = new BufferedReader(isr);



                //read first line only -- for the sake of demo... you can read line in a while loop...
                //read more here.... https://www.baeldung.com/a-guide-to-java-sockets
                byte[] receivedBytes = in.readLine().getBytes();


                System.out.println(LocalDateTime.now()
                        + "|client_ip: " + clientSocket.getInetAddress().getHostAddress()
                        + "|client_port: " + clientSocket.getPort() + "|byte stream");

                //print the bytes in dec format
                System.out.print("### Bytes in Dec [ "); //begin
                for (int i = 0; i < receivedBytes.length; i++) {
                    System.out.print(receivedBytes[i] + " ");
                }
                System.out.println("]"); // end

                System.out.println("### Bytes in Hex ["+ ByteToHexPrinter.bytesToHex(receivedBytes) +" ]");

                //string in default java charset
                System.out.println("### Received string in default charset ("+ isr.getEncoding() +") ["+ new String(receivedBytes) +" ]");


                //add some logs after decoding the message is the correct format...

                //write a response to the client
                out.println("Good bye"); //echo the same message received - first line

                System.out.println("**************************************************");


            } catch (IOException e) {
                e.printStackTrace(System.err);

            } finally {
                try {
                    in.close();
                    out.close();
                    clientSocket.close();

                    System.out.println(LocalDateTime.now() + "|received a client connection on " + port
                            + "|client_ip: " + clientSocket.getInetAddress().getHostAddress()
                            + "|client_port: " + clientSocket.getPort() + "|closed the client connection");

                } catch (Exception ex) {

                }

            }
        }

    }




}
