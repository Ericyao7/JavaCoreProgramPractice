/**
 * Created by eric on 6/17/16.
 */
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileFinder {

    public static void main(String[] args) {
        FileFinder finder = new FileFinder();

        List<String> filenameList = new ArrayList<String>();

        finder.findFiles(".java", "path", filenameList);

        for (String filename : filenameList) {
            System.out.println(filename);

        }
    }



    public void findFiles(String filenameSuffix, String currentDirUsed,
                          List<String> currentFilenameList) {
        File dir = new File(currentDirUsed);

        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }

        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                findFiles(filenameSuffix, file.getAbsolutePath(), currentFilenameList);
            } else {
                if (file.getAbsolutePath().endsWith(filenameSuffix)) {
                    currentFilenameList.add(file.getAbsolutePath());
                }
            }
        }

    }



}









}