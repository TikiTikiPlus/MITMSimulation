import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.*;

public class QubitTests {
    @Test
    //check if you can create 2 different qubits
    public void differentValueTesting() {
        Qubit[] qubit = generateQubits(5);
        Qubit[] qubit2 = generateQubits(5);

        for (int i = 0; i < qubit.length; i++) {
            if(qubit[i]._polarization != qubit2[i]._polarization) return;
            else if(qubit[i]._value != qubit2[i]._value) return;
        }
        fail("Fail, both lists of qubits are the same.");
    }

    @Test
    // tests if qubit measure method works everytime there is a match.
    public void qubitMatching() {
        Qubit[] qubits = generateQubits(5);
        for(int i = 0; i < qubits.length; i++) {
            int a = qubits[i].measure(qubits[i]._polarization);
            if(a != qubits[i]._value) {
                fail("Fail, the qubit measure didn't match");
            }
        }
    }

    @Test
    // tests if qubit measure method returns a random value when there is no match.
    public void NonMatchingQubits() {
        for(int i = 0; i < 100; i++) {
            Qubit q = new Qubit();
            int polarization = 1 - q._polarization;
            int j = q.measure(polarization);
            if(q._value != j) return;
        }
        fail("Fail. The method to check if they are not the same is broken");
    }

    //generateQubits
    public Qubit[] generateQubits(int qubitLength) {
        Qubit[] qubit = new Qubit[qubitLength];
        for (int i = 0; i < qubit.length; i++)
        {
            qubit[i] = new Qubit();
        }
        return qubit;
    }
}
