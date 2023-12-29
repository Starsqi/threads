package org.rsavenkov;

import java.io.*;
import java.util.concurrent.ThreadLocalRandom;


public class IncrementThread extends Thread {
    public static int startValue = 0;
    public static int maxValue = TwoThreads.integer;
    private int id;
    private final File file;

    IncrementThread(File file) {
        this.file = file;
        id = ThreadLocalRandom.current().nextInt(100);
    }

    @Override
    public void run() {
        try {
            while (true) {
                synchronized (file) {
                    if (startValue == maxValue) {
                        break;
                    }
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    int oldValue = Integer.parseInt(reader.readLine());
                    reader.close();

                    int newValue = oldValue+1;
                    startValue = newValue;

                    System.out.println("oldValue: " + oldValue + ", newValue: " + newValue + ", ThreadId: " + id);

                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                    writer.write(Integer.toString(newValue));
                    writer.close();
                }
                sleep(10);//чтобы другой поток мог захватить мьютекс объекта файла
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
