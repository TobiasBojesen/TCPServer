import javax.swing.tree.AbstractLayoutCache;
import java.io.*;
import java.net.*;
import java.util.*;

public class TCPServer1
{
    public static void main(String argv[]) throws Exception
    {
        System.out.println("starting Server main");
        String sentence;
        String userText;
        boolean go_on = true;

        ServerSocket welcomeSocket = new ServerSocket(777);
        System.out.println("we have a socket");
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("waiting for a connection");
        Socket connectionSocket = welcomeSocket.accept();
        System.out.println("Vi har en client connected");
        BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        //Scanner fromBroweser = new Scanner(connectionSocket.getInputStream());
        DataOutputStream  outToClient = new DataOutputStream(connectionSocket.getOutputStream());
        sentence = inFromClient.readLine();

        System.out.println("FROM CLIENT: " + sentence);

        outToClient.writeBytes("HTTP/1.0 200 JA Den spiller max" + '\n');
        outToClient.writeBytes("\n");


        String path= "/Users/tobias/Desktop/Skole/HTML/The_Daily_Paper-master/";
        StringTokenizer tokenizer = new StringTokenizer(sentence);
        String word;
        word = tokenizer.nextToken();
        word = tokenizer.nextToken();

        File file = new File(path + word);
        if(word.equals("/"))
        {
            file = new File(path + word + "The_Daily_Paper.html");
        }
        if(!file.exists())
        {
            file = new File(path + "facebook.PNG");


        }
        FileInputStream fromFile = new FileInputStream(file);

       boolean cont =true;
       int bla;
       byte bytearray[] = new byte[10];
       while(cont)
       {
           bla = fromFile.read(bytearray);
           if(bla==-1)
           {
               cont = false;
           }
           else
           {
               outToClient.write(bytearray);

           }
       }

        connectionSocket.close();
        welcomeSocket.close();
        System.exit(0);
        while(go_on)
        {
            System.out.print("Please type your text: ");
            userText = inFromUser.readLine();
            outToClient.writeBytes(userText + '\n');

            sentence = inFromClient.readLine();
            System.out.println("FROM CLIENT: " + sentence);
            if (sentence.equals("quit")) { go_on = false; }
        }

        System.out.println("quitting the loop and closing the socket");
        connectionSocket.close();
        welcomeSocket.close();
    }

}
