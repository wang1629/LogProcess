
import java.io.*;

public class Write {
    public static void main(String[] args) {
        write("data.txt", "hello");
    }
    public static void write(String path, String content) {
        try {
            File f = new File(path);
            if (f.exists()) {
                //System.out.println("文件存在");
            } else {
                //System.out.println("文件不存在，正在创建...");
                if (f.createNewFile()) {
                    //System.out.println("文件创建成功！");
                } else {
                    //System.out.println("文件创建失败！");
                }
            }
            BufferedWriter output = new BufferedWriter(new FileWriter(f, true));
            output.write(content + "\n");
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
