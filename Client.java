import java.net.*;
import java.io.*;

public class Client {
    static int streamLength = 0;
    static String polarizations = "";
    static String values = "";
    static String genkey = "";
    static String stringToSend = "";
    public static Client main(String[] args) throws IOException {

        // checks if the qubit stream length argument is valid
        try {
            streamLength = Integer.parseInt(args[1]);
        }
        catch(Exception e)
        {
            System.err.println("Error, invalid value for qubit stream length.");
            return null;
        }

        // creates the random stream of qubits
        Qubit qubitStream[] = generateQubits(streamLength);

        // creates a socket to communicate with receiver and sends the stream of qubits
        Socket s = new Socket("localhost", Integer.parseInt(args[0]));
        ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
        oos.writeObject(qubitStream);

        // receives the random polarizations sent from the receiver
        ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
        int[] receivedPolarization = null;
        try {
            receivedPolarization = (int[]) ois.readObject();
        }
        catch(Exception e) { s.close();
            return null;
        }

        // generates secret key, then encrypts message with secret key then sends it
        genkey = genKey(receivedPolarization, qubitStream);
        stringToSend = XOR.XORconvert(XOR.convertStringToBinary(args[2]), genkey);
        oos.writeObject(stringToSend);

        // converts the qubit polarization and values to strings for printing
        for(int i = 0; i < streamLength; i++) {
            polarizations += qubitStream[i]._polarization;
            values += qubitStream[i]._value;
        }
        
        // prints polarizations, values, secret key and the encrypted message
        System.out.println("polarization = " + polarizations);
        System.out.println("values = " + values);
        System.out.println("key = " + genkey);
        System.out.println("encrypted message = " + stringToSend);
        s.close();
        return null;
    }
    //generate key from qubit values where received polarization and receivers polarization is the same
    public static String genKey(int[] polarization, Qubit[] qubit) {
        String key = "";
        for(int i = 0; i < polarization.length; i++) {
            if (polarization[i] == qubit[i]._polarization) {
                key += qubit[i]._value;
            }
        }
        return key;
    }
    //generate random qubits
    public static Qubit[] generateQubits(int length) {
        Qubit qubitStream[] = new Qubit[length];
        for(int i = 0; i < length; i++) { qubitStream[i] = new Qubit();}
        return qubitStream;
    }
}
