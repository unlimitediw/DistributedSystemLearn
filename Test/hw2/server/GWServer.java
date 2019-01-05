import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Scanner;

class GWServerThread implements Runnable {
    // Core KV

    private Socket sock;
    private HashMap<String, String> KVgw;

    public GWServerThread(Socket s, HashMap<String, String> KVgw) {
        this.sock = s;
        this.KVgw = KVgw;
    }

    public void run() {
        // receive message
        try {
            PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            System.out.println("Got connection from " + sock.getInetAddress());
            // read in the  name and message
            String operation = "";
            String key = "";
            String value = "";
            String line;
            int count = 0;
            System.out.println("check2");
            while(in.ready() && count < 3){
                if (count == 0){
                    operation = in.readLine();
                }
                else if (count == 1){
                    key = in.readLine();
                }
                else{
                    value = in.readLine();
                }
                count ++;
            }
            System.out.println("check3");
            String feedback = operate(operation, key, value);
            out.println(feedback);

            // clean things up
            out.close();
            in.close();
            sock.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String operate(String operation, String key, String value) {
        System.out.println("Operation name: " + operation);
        String feedback;
        if (operation.equals("SET")) {
            if (KVgw.containsKey(key)) {
                feedback = "Key is already in KVgw.";
            } else {
                KVgw.put(key, value);
                feedback = "Key and Value have been set.";
            }
        } else if (operation.equals("GET")) {
            if (KVgw.containsKey(key)) {
                feedback = "The value of key " + key + " is: " + KVgw.get(key) + ".";
            } else {
                feedback = "Key is not found.";
            }
        } else if (operation.equals("STATS")) {
            int count = KVgw.size();
            String rep = count > 1 ? "are " : "is ";
            String rep2 = count > 1 ? "mappings " : "mapping ";
            feedback = "There " + rep + count + " key-value " + rep2 + "in KVgw.";
        } else {
            feedback = "Invalid operation!";
        }
        System.out.println(feedback);
        return feedback;
    }
}

class InputThread implements Runnable{

    public void run() {
        try{
            Scanner sc = new Scanner(System.in);
            if(sc.hasNext()){
                System.exit(0);
            }
        }
        catch (Exception e) {

        }
    }
}

public class GWServer {
    public GWServer() {
        // terminal check exit thread
        InputThread check = new InputThread();
        Thread k = new Thread(check);
        k.start();

        // KVgw HashMap
        HashMap<String, String> KVgw = new HashMap<>();

        // Socket
        ServerSocket serverSocket = null;
        boolean listening = true;

        try {
            InetAddress addr = InetAddress.getLocalHost();

            // Get IP Address
            byte[] ipAddr = addr.getAddress();

            // Get hostname
            String hostname = addr.getHostAddress();
            // System.out.println("Server IP = " + hostname);
        } catch (UnknownHostException e) {

        }

        try {
            serverSocket = new ServerSocket(5555);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 5555.");
            System.exit(-1);
        }

        System.out.println("Waiting for connections on port 5555...");

        while (listening) {
            // scanner.hasNext check whether there is a input in terminal
            try {
                // wait for a connection
                GWServerThread job = new GWServerThread(serverSocket.accept(), KVgw);
                // start a new thread to handle the connection
                Thread t = new Thread(job);
                t.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new GWServer();
    }
}
