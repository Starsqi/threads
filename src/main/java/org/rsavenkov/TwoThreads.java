package org.rsavenkov;

import java.io.*;
import java.util.Scanner;

public class TwoThreads {
    public static int integer;
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        while (!((integer = scanner.nextInt()) > 0 && integer % 2 == 0)) {
            System.out.println("Вводимое число должно быть четным и больше 0");
        }

        File file = new File("src/out.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(Integer.toString(0));
        writer.close();

        IncrementThread thread1 = new IncrementThread(file);
        IncrementThread thread2 = new IncrementThread(file);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();


        BufferedReader reader = new BufferedReader(new FileReader(file));
        System.out.println("-----------------------------");
        System.out.println("Содержимое файла out.txt: \n" + Integer.parseInt(reader.readLine()));
        reader.close();
    }
}