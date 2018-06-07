package pk;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

import static java.lang.System.nanoTime;
import static pk.Tools.*;

public class Main {


    public static void main(String[] args) {
        String encryptedFile = "myPDF.pdf";
        String password = "CCC";
        int passwordSize = 3;
        String dictionary = "ABC";
        int threads = 8;

        createDocument(encryptedFile, password);
        System.out.println("Opening file [" + encryptedFile + "] encrypted with password [" + password + "] using [" + threads + "] threads.");

        File file = new File(encryptedFile);
        ExecutorService executorService = new ThreadPoolExecutor(threads, threads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        List list = possibleStrings(passwordSize, dictionary.toCharArray(), "");

        Iterator<String> iterator = list.iterator();
        final long startTime = nanoTime();
        while (iterator.hasNext()) {
            executorService.submit(() -> openDocument(file, iterator.next(), startTime));
        }


    }

}
