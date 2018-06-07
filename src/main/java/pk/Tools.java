package pk;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.lang.System.nanoTime;

public class Tools {

    public static PDDocument createDocument(String path, String password) {
        File file = new File("sample.pdf");
        try {
            PDDocument document = PDDocument.load(file);
            document.addPage(new PDPage());
            AccessPermission ap = new AccessPermission();
            StandardProtectionPolicy spp = new StandardProtectionPolicy(password, password, ap);
            spp.setEncryptionKeyLength(128);
            spp.setPermissions(ap);
            document.protect(spp);
            document.save(path);
            document.close();
            return document;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void openDocument(File file, String password, long startTime) {
        if (decrypt(file, password)) {
            long duration = nanoTime() - startTime;
            System.out.println("[Thread-" + Thread.currentThread().getName()
                    + "] decrypted using [" + password + "]: " + decrypt(file, password)
                    + ", in time: " + TimeUnit.NANOSECONDS.toMillis(duration) + " ms");
        }
    }

    public static boolean decrypt(File file, String password) {
        try {
            PDDocument document = PDDocument.load(file, password);
            document.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    static List list = new ArrayList<String>();

    public static List possibleStrings(int maxLength, char[] alphabet, String curr) {
        if (curr.length() == maxLength) {
            list.add(curr);
        } else {
            for (int i = 0; i < alphabet.length; i++) {
                String oldCurr = curr;
                curr += alphabet[i];
                possibleStrings(maxLength, alphabet, curr);
                curr = oldCurr;
            }
        }
        return list;
    }

}
