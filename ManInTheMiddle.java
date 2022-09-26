import java.net.*;
import java.io.*;

public class ManInTheMiddle {
    static String XORMessage = "";
    static String response = "";
    public static ManInTheMiddle main() throws IOException{
        ServerSocket ss = new ServerSocket(49999);
        while(true) {
            System.out.println("Mitm receiver");
            Socket clientSocket = ss.accept();

            // receives the qubits from the client
            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
            Qubit[] receivedQubits = null;
            try { receivedQubits = (Qubit[])
                    ois.readObject();
            }
            catch(Exception e) {
                clientSocket.close();
                ois.close();
                break;
            }
            // sends the qubits to the server
            Socket serverSocket = new Socket("localhost", 50000);
            ObjectOutputStream oos = new ObjectOutputStream(serverSocket.getOutputStream());
            oos.writeObject(receivedQubits);

            // receives the random polarization
            ObjectInputStream serverInputStream = new ObjectInputStream(serverSocket.getInputStream());
            int[] polarization = null;
            try {
                polarization = (int[]) serverInputStream.readObject();
            }
            catch(Exception e) { serverSocket.close();
                return null;
            }

            // sends the random polarizations to the client
            ObjectOutputStream clientOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            clientOutputStream.writeObject(polarization);

            // generates measured values and generates the secret key
            int[] measuredValues = Server.genValues(receivedQubits, polarization);
            String key = Server.genKey(measuredValues, polarization, receivedQubits);

            // converts the random polarizations and man in the middle values to strings for printing
            String polarizations = "";
            String values = "";
            for(int i = 0; i < receivedQubits.length; i++) {
                polarizations += polarization[i];
                values += measuredValues[i];
            }

            // prints out server polarizations, MitM measured values and key
            System.out.println("Polarization values = " + polarizations);
            System.out.println("measured values = " + values);
            System.out.println("key values = " + key);

            // receives the encrypted message from the client
            response = "";
            try { response = (String)ois.readObject(); }
            catch(Exception e){ ois.close(); ois.close(); break; }

            //turn back the message
            XORMessage = XOR.convertToMessage(XOR.XORconvert(response, key));

            // print both encrypted and decrypted message
            System.out.println("encrypted value = " + response);
            System.out.println("decrypted value = " + XORMessage+ "\n");
            
            // send the encrypted message to the receiver
            oos.writeObject(response);
            serverSocket.close();
        }
        ss.close();
        return null;
    }

}
