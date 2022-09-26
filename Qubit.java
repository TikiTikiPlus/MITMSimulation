import java.io.*;

public class Qubit implements Serializable{
    int _value = 0;
    int _polarization = 0;
    int newValue = 0;

    //create a Qubit
    public Qubit() {
        if (Math.random()*100 < 50) _value = 1;
        if (Math.random()*100 < 50) _polarization = 1;
    }
    //check if polarizations match on the server. return value if
    //polarizations match then return the value. Otherwise, return a random new type
    public int measure(int polarization) {
        if(polarization == _polarization) return _value;
        if (Math.random()*100 < 50) newValue = 1;
        return newValue;
    }
}
