import java.util.Random;

public class Test {

    public static Random r = new Random();

    public static String getRandom(){
        long num = Math.abs(r.nextLong() % 10000000000L);
        String s = String.valueOf(num);
        for(int i = 0; i < 10-s.length(); i++){
            s = "0" + s;
        }

        return s;
    }

    public static void main(String[] args) {
        for(int i = 0; i < 10; i++){
            System.out.println(getRandom());
        }
    }
}
