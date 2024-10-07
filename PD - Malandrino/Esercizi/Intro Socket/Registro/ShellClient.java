import java.io.*;
import java.net.*;
import java.util.logging.Logger;

public class ShellClient {
    static Logger logger = Logger.getLogger("global"); 
    Static BufferedReader in = null;
    public static void main(String args[]) {
        
        String host = args[0];
        String cmd;
        in = new BufferedReader (new InputStreamReader(System.in)); 
        try{
            while (! (cmd = ask (">>>")).equals("quit")) {
                if (cmd.equals ("inserisci")) {
                    System.out.println ("Inserire i dati."); String nome = ask ("Nome: ");
                    String indirizzo = ask ("Indirizzo: ");
                    RecordRegistro r = new RecordRegistro (nome, indirizzo);
                    Socket socket = new Socket (host, 7000);
                    ObjectOutputStream sock_out = new ObjectOutputStream(socket.getOutputStream()); sock_out.writeObject(r);
                    sock_out.flush();
                    socket.close();
        }   
        else if (cmd.equals ("cerca")) {
            System.out.println("Inserire il nome da cercare.");
            String nome =   ask ("Nome: ");
            RecordRegistro r = new RecordRegistro (nome, null);
            Socket socket = new Socket (host, 7000);
            ObjectOutputStream sock_out = new ObjectOutputStream(socket.getOutputStream());
            sock_out.writeObject(r);
            sock_out.flush();
            ObjectInputStream sock_in = new ObjectInputStream(socket.getInputStream());
            RecordRegistro result = (RecordRegistro) sock_in.readObject();
            if(result != null) {
                System.out.println("Indirizzo: " + result.getIndirizzo());
            } else {
                System.out.println("Nome non trovato.");
                socket.close();
            } 
        }
            else {
                System.out.println("Comando non riconosciuto.");
        }
            
        } catch (EOFException e) {
            logger.severe("EOFException: " + e.getMessage());
            e.printStackTrace();
        } catch (Throwable t) { 
            logger.severe("Throwable: " + t.getMessage());
            t.printStackTrace();
        }
    }
}


        