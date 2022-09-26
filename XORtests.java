import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class XORtests {
    @ParameterizedTest
    @ValueSource(strings = {"Ya yeet", "amogus", "my name jeff"})

    //test if I can convert properly from string to binary and vice versa
    public void testConversion(String s) {
        String binaryValue = XOR.convertStringToBinary(s);
        String stringValue = XOR.convertToMessage(binaryValue);
        if(!s.equals(stringValue)) fail("Conversion methods do not work together.");
    }
    @ParameterizedTest
    @ValueSource(strings = {"Ya yeet"})
    //check if XOR is applied properly
    public void checkConvertXOR(String message)
    {
        String binary = "0101011010101010";
        String s1 = XOR.convertStringToBinary(message);
        String s2 = XOR.XORconvert(s1,binary);
        System.out.print(s2);
        if(!s2.equals("11110000001101001000100100101100110011000011000011011101")) fail("conversion not working properly");
    }

    @ParameterizedTest
    @ValueSource(strings = {"Ya yeet", "amogus", "my name jeff"})
    // test if converting message with XORs will work
    //and back
    public void testConvertXOR(String message) {
        String binary = "0101011010101010";
        String string1 = XOR.convertStringToBinary(message);
        String string2 = XOR.XORconvert(string1, binary);
        String string3 = XOR.XORconvert(string2, binary);
        if(!string3.equals(string1)) fail("XOR operation not working correctly.");
    }

}
