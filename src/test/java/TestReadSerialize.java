import java.io.*;

public class TestReadSerialize {
    public static void main(String[] args) {
        try {
            File file = new File("serialize.obj");
            InputStream in = new FileInputStream(file);
            byte buff[] = new byte[1024];
            int len = 0;
            while ((len = in.read(buff)) != -1) {
                for (int i = 0; i < len; i++) {
//                    System.out.printf("%02X ", buff[i]);
                    System.out.println(buff[i]);
                }
                System.out.println();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
