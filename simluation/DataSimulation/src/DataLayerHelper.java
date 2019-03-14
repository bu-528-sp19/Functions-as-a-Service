public class Controller {

    //all candidate chars that would be using in generating
    public static final String[] CANDIDATE_CHARS =  new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };

    public static String generateID(char type) {
        StringBuilder sb = new StringBuilder();
        sb.append(type);
        for (int i = 0; i < 15; i++) {
            sb.append(CANDIDATE_CHARS[(int)(Math.random()*(CANDIDATE_CHARS.length-1))]);
        }
        return sb.toString();
    }



}
