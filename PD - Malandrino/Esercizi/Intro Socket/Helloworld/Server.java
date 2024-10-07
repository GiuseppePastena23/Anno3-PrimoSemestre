import java.io.*;
import java.net.*;
import java.util.logging.Logger;

public class Server{
    static Logger logger = Logger.getLogger("global");
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(9000);
            Socket socket = server.accept();
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            String name = (String) in.readObject();
            out.writeObject("Hello, " + name);                
            socket.close();
        } catch (EOFException e) {
            logger.severe("EOFException: " + e.getMessage());
            e.printStackTrace();
        } catch (Throwable t) {
            logger.severe("Throwable: " + t.getMessage());
            t.printStackTrace();
        }
    }
}
