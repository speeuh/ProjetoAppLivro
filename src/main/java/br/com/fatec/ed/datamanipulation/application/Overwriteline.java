package br.com.fatec.ed.datamanipulation.application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Overwriteline {
    public static void main(String args[]) throws IOException {
        String filePath = "C:\\Users\\Lenovo\\Documents\\vitoria_temp\\leitor.txt";
        Scanner sc = new Scanner(new File(filePath));
        StringBuffer buffer = new StringBuffer();

        while (sc.hasNextLine()) {
            buffer.append(sc.nextLine() + System.lineSeparator());
        }
        String fileContents = buffer.toString();
        System.out.println("antes de alterar: " + fileContents);

        sc.close();

        String oldLine = "[genero: masculino ,  nome: eduardo ,  idade: 19 id: 2]";
        String newLine = "[genero: masculino ,  nome: eduardo ,  idade: 20 id: 2]";

        fileContents = fileContents.replace(oldLine, newLine);

        FileWriter writer = new FileWriter(filePath);
        System.out.println("");
        System.out.println("depois de alterar: " + fileContents);

        writer.append(fileContents);
        writer.flush();
        writer.close();
    }
}