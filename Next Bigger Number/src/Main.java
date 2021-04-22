public class Main {
    public static void main(String[] args) {
        System.out.println(nextBiggerNumber(1234));
    }

    public static long nextBiggerNumber(long n) {

        StringBuilder strN = new StringBuilder(String.valueOf(n));
        char temp;
        int index;

        // find the 1st number's index(index-1) that will be changed
        for (index = strN.length() - 1; index > 0; index--)
            if ((int) strN.charAt(index) > (int) strN.charAt(index - 1)) break;

        //whether index went to 0
        if (index == 0) return -1L;

        // find the 2nd number's index(num) that will be changed
        int num = index;
        for (int i = index; i < strN.length(); i++)
            if ((int) strN.charAt(i) > (int) strN.charAt(index - 1) && (int) strN.charAt(i) <= strN.charAt(num))
                num = i;

        //change(characters at num and index-1)
        temp = strN.charAt(num);
        strN.setCharAt(num, strN.charAt(index - 1));
        strN.setCharAt(index - 1, temp);

        //sort numbers that right-hand side of the 1st number (smaller to bigger)
        for (int i = strN.length() - 1; i > index - 1; i--) {
            for (int j = i - 1; j > index - 1; j--) {
                if ((int) strN.charAt(j) > (int) strN.charAt(i)) {
                    temp = strN.charAt(i);
                    strN.setCharAt(i, strN.charAt(j));
                    strN.setCharAt(j, temp);
                }
            }
        }

        return Long.parseLong(strN.toString());

    }
}
