import java.io.*;
import java.net.*;
import java.util.logging.Logger;
import java.util.*;

public class RegistroServer {
    static Logger logger = Logger.getLogger("global");
    public static void main(String[] args) {
        HashMap<String, RecordRegistro> registro = new HashMap<String, RecordRegistro>();
        Socket socket = null;
        try {
            ServerSocket server = new ServerSocket(9000);
            while (true) {
                socket = server.accept();
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                RecordRegistro record = (RecordRegistro) in.readObject();
                if(record.getIndirizzo() != null){
                    registro.put(record.getNome(), record);
                } else {
                    RecordRegistro recordOut = registro.get(record.getNome());
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    out.writeObject(recordOut);
                    out.flush();

                    }
                    socket.close();
                }
        } catch (EOFException e) {
            logger.severe("EOFException: " + e.getMessage());
            e.printStackTrace();
        }
        catch (Throwable t) {
            logger.severe("Throwable: " + t.getMessage());
            t.printStackTrace();
        }
        finally {
            try {
                socket.close();
            } catch (IOException e) {
                logger.severe("IOException: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}