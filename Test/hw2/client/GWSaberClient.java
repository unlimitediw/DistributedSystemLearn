import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.BufferedReader;
import java.util.HashMap;

class GWClientThread implements Runnable {
    Socket sock;

    GWClientThread(Socket sock) {
        this.sock = sock;
    }

    public void run() {
        // receive message
        try {
            PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            System.out.println(in.readLine());

            // clean things up
            out.close();
            in.close();
            sock.close();
            System.out.println("Done. \n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

public class GWSaberClient {
    public static void main(String[] args) {
        String host = null;
        String operation = null;
        int portnum = 5555;
        // arg[0]-hostname, arg[1]-operationMode, if arg[2]-key, if arg[3]-value
        if (args.length > 1 && args.length < 5) {
            host = args[0];
            operation = args[1];
            switch (operation) {
                case "SET":
                    if (args.length < 4) {
                        System.out.println("Invalid operation.");
                    } else {
                        String key = args[2];
                        String value = args[3];
                        if (key.length() > 64 || value.length() > 1024) {
                            System.out.println("Invalid operation.");
                        } else {
                            connectAndSend(host, portnum, args[1] + '\n' + args[2] + '\n' + args[3]);
                        }
                    }
                    break;
                case "GET":
                    if (args.length < 3) {
                        System.out.println("Invalid operation.");
                    } else {
                        String key = args[2];
                        if (key.length() > 64) {
                            System.out.println("Invalid operation.");
                        } else {
                            connectAndSend(host, portnum, args[1] + '\n' + args[2]);
                        }
                    }
                    break;
                case "STATS":
                    connectAndSend(host, portnum, args[1]);
                    break;
                default:
                    System.out.println("Invalid operation.");
            }
        } else {
            System.out.println("Local error: Invalid operation.");
        }

    }

    public static void connectAndSend(String host, int portnum, String cmd) {
        try {
            Socket sock = new Socket(host, portnum);
            PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
            out.println(cmd);
            System.out.println("MsgSendOut.");
            // Wait feedback
            GWClientThread job = new GWClientThread(sock);
            Thread t = new Thread(job);
            t.start();
        } catch (UnknownHostException e) {
            System.err.println("UnknownHostException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }

    }
}
