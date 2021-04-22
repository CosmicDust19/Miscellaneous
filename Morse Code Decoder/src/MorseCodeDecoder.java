public class MorseCodeDecoder {
    private static String getMorse(String str) {
        // declare arrays that hold alphabetical equivalent of each morse code
        String[] alpha = {"A", "B", "C", "D", "E", "F",
                "G", "H", "I", "J", "K", "L",
                "M", "N", "O", "P", "Q", "R",
                "S", "T", "U", "V", "W", "X",
                "Y", "Z", "1", "2", "3", "4",
                "5", "6", "7", "8", "9", "0",
                "!", ",", "?", ".", "'", "SOS"};
        String[] morse = {".-", "-...", "-.-.", "-..", ".", "..-.",
                "--.", "....", "..", ".---", "-.-", ".-..",
                "--", "-.", "---", ".--.", "--.-", ".-.",
                "...", "-", "..-", "...-", ".--", "-..-",
                "-.--", "--..", ".----", "..---", "...--", "....-",
                ".....", "-....", "--...", "---..", "----.", "-----",
                "-.-.--", "--..--", "..--..", ".-.-.-", ".----.", "...---..."};

        // return alphabetical equivalent of the morse code
        for (int i = 0; i < morse.length; i++) if (str.equals(morse[i])) return alpha[i];
        return null;
    }

    public static String decodeMorse(String morseCode) {
        StringBuilder result = new StringBuilder();
        for (String word : morseCode.trim().split("\s".repeat(3))) {
            for (String letter : word.split("\s")) {
                // append words to result
                result.append(getMorse(letter));
            }
            result.append(" ");
        }
        return result.toString().trim();
    }

    public static String decodeBits(String bits) {
        StringBuilder result = new StringBuilder();
        StringBuilder sb = new StringBuilder(bits);

        //delete right-hand and left-hand side redundant zeros
        int num = 0;
        while (sb.length() != 0 && sb.charAt(num) == '0') sb.deleteCharAt(num);
        num = sb.length() - 1;
        while (sb.length() != 0 && sb.charAt(num) == '0') sb.deleteCharAt(num--);
        String distilled = sb.toString();

        //detect bitrate
        int bitRate = detectBitRate(distilled);

        //declare separators that will use in the split method in for loops
        String wordSeparator = "0".repeat(7).repeat(bitRate);
        String letterSeparator = "0".repeat(3).repeat(bitRate);
        String morseUnitSeparator = "0".repeat(bitRate);

        //create morse code
        String dot = "1".repeat(bitRate);
        for (String word : distilled.split(wordSeparator)) {
            for (String letter : word.split(letterSeparator)) {
                for (String ch : letter.split(morseUnitSeparator)) {
                    if (ch.equals(dot)) result.append(".");
                    else result.append("-");
                }
                result.append("\s");
            }
            result.append("\s".repeat(2));
        }
        return result.toString().trim();
    }

    public static int detectBitRate(String bits) {
        //skip redundant zeros (if it contains any)
        int start = 0, end = bits.length() - 1;
        while (start < bits.length() && bits.charAt(start) == '0') start++;
        while (end >= 0 && bits.charAt(end) == '0') end--;

        if (bits.length() <= 1) return 1;
        int minOnesLength = Integer.MAX_VALUE;
        int minZerosLength = Integer.MAX_VALUE;

        //scan string to determine minimum length of the ONES
        int count;
        for (int i = start; i <= end; i++) {
            if (i > 0 && bits.charAt(i) == '1' && bits.charAt(i - 1) == '0') {
                count = 0;
                while (i <= end && bits.charAt(i++) == '1') count++;
                if (count < minOnesLength) minOnesLength = count;
            }
        }

        //scan string to determine minimum length of the ZEROS
        for (int i = start; i <= end; i++) {
            if (i > 0 && bits.charAt(i) == '0' && bits.charAt(i - 1) == '1') {
                count = 0;
                while (i <= end && bits.charAt(i++) == '0') count++;
                if (count < minZerosLength) minZerosLength = count;
            }
        }

        //check if they are changed
        if (minOnesLength == Integer.MAX_VALUE && minZerosLength == Integer.MAX_VALUE) return bits.length();

        return Math.min(minOnesLength, minZerosLength);
    }
}