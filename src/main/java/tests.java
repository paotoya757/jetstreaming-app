
import Servlets.UploadSevlet;
import java.io.File;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author paotoyav
 */
public class tests {

    private static final Logger LOG = Logger.getLogger(tests.class.getName());

    public static void main(String... args) {
        File[] files = new File( UploadSevlet.UPLOAD_DIRECTORY ).listFiles();
        showFiles(files);
    }

    public static void showFiles(File[] files) {
        for (File file : files) {
           System.out.println("File: " + file.getName());
        }
    }

}
