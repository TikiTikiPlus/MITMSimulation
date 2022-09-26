public class XOR {
    //apply XOR to key
    public static String XORconvert(String message, String k) {
        char[] _message = message.toCharArray();
        String _key = k;
        String _newMessage = "";
        
        // checks if key is valid
        for(int index = 0; index < k.length(); index++) {
            if(k.charAt(index) != '1' && k.charAt(index) != '0') {
                System.err.println("Invalid Key");
                System.exit(0);
            }
        }
        //creates a key that matches the length of message
        //and loops through it while its not finished
        int i = 0;
        while(_key.length() < message.length()) {
            _key += k.charAt(i % k.length());
            i++;
        }
        for(int messageIndex = 0; messageIndex < _message.length; messageIndex++) {
            //if the characters do not match, return 0. Otherwise, return 1
            if(_message[messageIndex] != _key.charAt(messageIndex)) _newMessage+='0';
            else
                _newMessage+='1';
            if(_message[messageIndex] != '1' && _message[messageIndex] != '0') {
                System.err.println("Incorrect Input");
                System.exit(0);
            }
        }
        return _newMessage;
    }
    //converts the message sent to binary
    public static String convertStringToBinary(String input) {

        StringBuilder result = new StringBuilder();
        char[] chars = input.toCharArray();
        for (char aChar : chars) {
            result.append(
                    String.format("%8s", Integer.toBinaryString(aChar))   // char -> int, auto-cast
                            .replaceAll(" ", "0")                         // zero pads
            );
        }
        return result.toString();
    }
    //converts binary to string
    public static String convertToMessage(String message) {
        StringBuilder stringBuilder = new StringBuilder();
        int charCode;
        for (int i = 0; i < message.length(); i += 8) {
            charCode = Integer.parseInt(message.substring(i, i + 8), 2);
            String returnChar = Character.toString((char) charCode);
            stringBuilder.append(returnChar);
        }
        return stringBuilder.toString();

    }
}
