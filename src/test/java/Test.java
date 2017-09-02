import java.util.Random;

public class Test {

    @org.junit.Test
    public void test01() {
        int i = 16;
        Runnable r = () -> {
            int i1 = 0; // error:variable i is already define in the scope
            System.out.println("cnt is: " + i1);
        };

        new Thread(r).start();
    }

    public static Random r = new Random();

    public static String getRandom() {
        long num = Math.abs(r.nextLong() % 10000000000L);
        String s = String.valueOf(num);
        for (int i = 0; i < 10 - s.length(); i++) {
            s = "0" + s;
        }

        return s;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(getRandom());
        }
    }
}
