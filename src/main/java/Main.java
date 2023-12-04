import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class Main {

    /**
     * Total lines, a long variable which holds the total number of lines for the given project.
     */
    public static long totalLines = 0;

    /**
     * Total chars, simply holds the total number of characters in a folder.
     */
    public static long totalChars = 0;

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        /*
          Main folder to be iterated through.
         */
        File homeFolder = new File("/Users/qrx53/Q");

        ArrayList<File> list = iterate(homeFolder);

        for (File f : list) {
            try {
                totalLines += countLines(f);
            } catch (IOException e) {
                LOGGER.error(e.getLocalizedMessage());
            }
        }

        System.err.println("Grand Total of Lines: " + NumberFormat.getIntegerInstance().format(totalLines));
        System.err.println("Grand Total of Chars: " + NumberFormat.getIntegerInstance().format(totalChars));
    }

    /**
     * Basic function to iterate through a given Folder variable, and return every file in every subdirectory as an ArrayList
     *
     * @param folder The Folder to iterate through.
     * @return ArrayList    The method returns an ArrayList of files.
     */
    public static ArrayList<File> iterate(File folder) {

        ArrayList<File> list = new ArrayList<>();

        File[] dirlist = folder.listFiles();

        if (dirlist != null) {
            for (File f : dirlist) {
                if (f.isDirectory()) {
                    list.addAll(iterate(f));
                } else {
                    list.add(f);
                    LOGGER.info("Counted lines of '" + f + "'");
                }
            }
        } else {
            LOGGER.error("Couldn't locate given folder: " + folder + ".");
            return new ArrayList<>();
        }

        return list;
    }

    /**
     * This method will count the total lines of a file. Used in the iterating method above, and returns a long
     * with the total line count of each file. Variable is a `long` in case it's a very big file.
     * This method also counts the number of characters in each file (skipping whitespace).
     *
     * @param f The file to read lines and chars from.
     * @return long             The long returned here is the total line count of the file. Added to the 'totalLines' variable.
     * @throws IOException Throws an IOException in ase the file cannot be found, or cannot be read.
     */
    public static long countLines(File f) throws IOException {
        List<String> list = Files.readAllLines(f.toPath());

        for (String s : list) {
            char[] chars = s.toCharArray();
            for (char c : chars) {
                if (c == ' ' || c == '\n' || c == '\t' || c == '\r') {
                    // Skip all whitespace.
                    continue;
                } else {
                    totalChars += 1;
                }
            }
        }

        return list.size();
    }


}
