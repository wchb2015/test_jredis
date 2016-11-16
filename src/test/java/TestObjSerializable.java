import java.io.*;

public class TestObjSerializable implements Serializable {
    private static final long serialVersionUID = -3448858835843951872L;

    public int num = 1390;

    public void serialized() {
        try {
            FileOutputStream fos = new FileOutputStream("serialize.obj");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            TestObjSerializable serialize = new TestObjSerializable();
            oos.writeObject(serialize);
            oos.flush();
            oos.close();
            fos.close();  //只是为了方便没有写到finally里面
            System.out.println("序列化结束!!!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deSerialized() {
        TestObjSerializable serialize = null;
        try {
            FileInputStream fis = new FileInputStream("serialize.obj");
            ObjectInputStream ois = new ObjectInputStream(fis);
            serialize = (TestObjSerializable) ois.readObject();
            ois.close();
            fis.close();
            System.out.println("反序列化结束!!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(serialize.num);
    }

    public static void main(String[] args) {
        TestObjSerializable serialize = new TestObjSerializable();
        serialize.serialized();
        serialize.deSerialized();
    }
}
