import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static long totalLines = 0;

    public static void main(String[] args) {

        File folder = new File("/Users/qrx53/Documents/GitHub/Q");

        ArrayList<File> list = iterate(folder);

        for (File f : list) {
            try {
                totalLines += countLines(f);
            } catch (IOException e) {
            }
        }

        System.err.println("Grand Total: " + totalLines);

    }

    public static ArrayList<File> iterate(File folder) {

        ArrayList<File> list = new ArrayList<>();

        File[] dirlist = folder.listFiles();

        for (File f : dirlist) {
            if (f.isDirectory()) {
                list.addAll(iterate(f));
            } else {
                list.add(f);
            }
        }

        return list;
    }

    public static long countLines(File f) throws IOException {
        List<String> list = Files.readAllLines(f.toPath());
        
        return list.size();
    }

}
