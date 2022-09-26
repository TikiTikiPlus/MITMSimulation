import java.net.*;
import java.io.*;

public class Server {
    static String XORmessage = "";
    static String polarizations = "";
    static String key = "";
    static String values = "";
    public static String _XORmessage()
    {
        return XORmessage;
    }
    public static String getPolarizations()
    {
        return polarizations;
    }
    public static String getKey()
    {
        return key;
    }
    public static String getValues()
    {
        return values;
    }
    public static Server main() throws IOException {
        ServerSocket ss = new ServerSocket(50000);
        while(true) {
            System.out.println("Waiting for client");
            Socket s = ss.accept();

            // receives the qubits from the transmitter
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            Qubit[] receivedQubits = null;
            try { receivedQubits = (Qubit[]) ois.readObject(); }
            catch(Exception e) { s.close(); ois.close(); break; }

            // generate random polarizations, measured values and the secret key
            int[] randomPolarizations = genPolarizations(receivedQubits.length);
            int[] measuredValues = genValues(receivedQubits, randomPolarizations);
            key = genKey(measuredValues, randomPolarizations, receivedQubits);

            // send the array of polarizations over the socket
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(randomPolarizations);

            // converts the random polarizations and measured values to strings for printing
            polarizations = "";
            values = "";
            for(int i = 0; i < receivedQubits.length; i++) {
                polarizations += randomPolarizations[i];
                values += measuredValues[i];
            }

            // print out random polarizations, measured values and secret key
            System.out.println("polarization values = " + polarizations);
            System.out.println("measured value = " + values);
            System.out.println("key value = " + key);

            // receives the encrypted message and prints out the decrypted output
            String decryptedValue = "";
            try {
                decryptedValue = (String)ois.readObject();
            }
            catch(Exception e)
            {
                s.close();
                ois.close();
                break;
            }
            XORmessage = XOR.convertToMessage(XOR.XORconvert(decryptedValue, key));
            System.out.println("Message received (decrypted) = " + XOR.convertToMessage(XOR.XORconvert(decryptedValue, key)) + "\n");
        }
        ss.close();
        return null;
    }

    //creates polarization values
    public static int[] genPolarizations(int length) {
        int[] arr = new int[length];
        for(int i = 0; i < length; i++) {
            if (Math.random() *100 < 50) arr[i] = 1;
            else arr[i] = 0;
        }
        return arr;
    }
    //generates array of values formed by matching polarizations and returns it
    public static int[] genValues(Qubit[] qubits, int[] polarization) {
        int[] arr = new int[qubits.length];
        for(int i = 0; i < qubits.length; i++) arr[i] = qubits[i].measure(polarization[i]);
        return arr;
    }
    //generates a key that relies on the measured value
    public static String genKey(int[] measuredValue, int[] polarization, Qubit[] qubits) {
        String key = "";
        for(int i = 0; i < measuredValue.length; i++) if(polarization[i] == qubits[i]._polarization) key += measuredValue[i];
        return key;
    }
}
