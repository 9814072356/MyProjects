// package
package Netzwerk;

// imports --------------------
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
// --------------------

/**
 * Server that is realised through Threads for simultanious use with game
 * The Server handles every DATA from Player To Player and the Chat
 */
public class Server implements Runnable
{
    // Server Variables
    private static ServerSocket server;
    private ArrayList<PrintWriter> list_clientWriter;
    private PrintWriter host;
    private PrintWriter guest;

    // temporary HostData until the Client Connects
    private static boolean readytosend = false;

    private String hostname;
    private String hostship;

    /**
     * Calls the Main Methods of the Server
     */
    public void run()
    {
        Server s = new Server();
        if (s.runServer())
        {
            s.listenToClients();
        }
    }

    /**
     * ClientHandler Class handles messages to Players
     */
    public class ClientHandler implements Runnable
    {
        Socket client;
        BufferedReader reader;

        public ClientHandler(Socket client)
        {
            try
            {
                this.client = client;
                reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        public void run()
        {
            String message;
            String messagetag;
            String playertag;

            try
            {
                while((message = reader.readLine()) != null)
                {
                    // Takes out the Message's Tags (e.g. message/gamelog, h/g)
                    messagetag = message.substring(0,7);
                    playertag = message.substring(7,8);
                    // differentiate between messages and gamelogic
                    if(messagetag.compareTo("message") == 0)
                    {
                        sendToAllClients(message);
                    }
                    else if (messagetag.compareTo("gamelog") == 0)
                    {
                        // wait until a guest a connected to the Server
                        if(readytosend)
                        {
                            // differentiate between messages for the guest and host
                            if (playertag.compareTo("h") == 0)
                            {
                                sendToPlayer(message, guest);
                            }
                            else if (playertag.compareTo("g") == 0)
                            {
                                sendToPlayer(message, host);
                            }
                        }
                        // if no guest is connected, save data in Server for later use
                        else
                        {
                            if(playertag.compareTo("h") == 0)
                            {
                                // differentiates between PlayerName and placed Ships
                                if(message.substring(8,12).compareTo("name") == 0)
                                {
                                    hostname = message;
                                }
                                else if(message.substring(8,12).compareTo("ship") == 0)
                                {
                                    hostship = message;
                                }
                            }
                        }
                    }
                }
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Starts the Server
     * @return true if the Server is started succesfully
     */
    public boolean runServer()
    {
        try
        {
            server = new ServerSocket(9999);
            System.out.println("Server started!");

            list_clientWriter = new ArrayList<PrintWriter>();
            return true;
        } catch (IOException e)
        {
            System.out.println("Server couldn't be started");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Accepts clients that connect to the Server
     * limited to 2 clients
     *
     * sends signal if a second (guest) Player logs on
     * -> Sends saved DATA to Guest
     */
    public void listenToClients()
    {
        for(int nofclients = 0; nofclients < 2; nofclients ++)
        {
            try
            {
                Socket client = server.accept();

                PrintWriter writer = new PrintWriter(client.getOutputStream());
                list_clientWriter.add(writer);

                Thread clientThread = new Thread(new Thread(new ClientHandler(client)));
                clientThread.start();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        // Assigns A Player to the ClientWriters
        host = list_clientWriter.get(0);
        guest = list_clientWriter.get(1);
        // Triggers GameData to be allowed to be sent to Guest
        readytosend = true;
        sendCachedData();
    }

    /**
     * Sends a single message to a single player
     * @param message message to be send
     * @param player player the message is to be send to
     */
    private void sendToPlayer(String message, PrintWriter player)
    {
        player.println(message);
        player.flush();
    }

    private void sendToAllClients(String message)
    {
        // iterator goes through the different clients (overkill for 2 players)
        Iterator it = list_clientWriter.iterator();

        while(it.hasNext())
        {
            PrintWriter writer = (PrintWriter) it.next();
            writer.println(message);
            writer.flush();
        }
    }

    /**
     * sends Data to guest
     * is triggered when 2nd Client (guest) connects to Server
     */
    private void sendCachedData()
    {
        if(hostname != null)
            sendToPlayer(hostname, guest);

        if(hostship != null)
            sendToPlayer(hostship, guest);
    }
}
