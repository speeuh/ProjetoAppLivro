package br.com.fatec.ed.datamanipulation.application;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.fatec.ed.datamanipulation.entidades.Leitor;
import org.apache.log4j.Logger;

public class LeitorController {

    // TODO: ALTERAR O CAMINHO!!!
    static int id = 1;
    static String caminho = "C:\\Users\\sarah\\Desktop\\estrutura-dados\\leitor.txt";
    static List<Leitor> leitores = new ArrayList<>();
    private static Logger LOGGER = Logger.getLogger(LeitorController.class);

    public static void main(String[] args) throws IOException {

        criarArquivo("C:\\Users\\sarah\\Desktop\\estrutura-dados", "leitor");
        lerArquivo(caminho);
        atualizarLeitor(2, 2, "Teste");
        excluirLeitor(2);

    }

    public static void criarArquivo(String path, String arq) throws IOException {

        File dir = new File(path);
        File arquivo = new File(path, arq + ".txt");
        if (dir.exists()) {
            boolean arquivoExiste = false;
            if (arquivo.exists()) {
                arquivoExiste = true;
            }
            String conteudo = gerarArquivoLeitor();
            FileWriter fw = new FileWriter(arquivo, arquivoExiste);
            PrintWriter pw = new PrintWriter(fw);
            pw.write(conteudo);
            pw.flush();
            pw.close();
            fw.close();
        } else {
            throw new IOException("Diretório Inválido");
        }

        LOGGER.info("LEITOR REGISTRADO COM SUCESSO");
    }

    public static void lerArquivo(String absolutePath) throws IOException {
        File arquivo = new File(absolutePath);

        if (arquivo.exists()) {

            LOGGER.info("INFORMACOES ENCONTRADAS NO ARQUIVO");

            FileInputStream fluxo = new FileInputStream(arquivo);
            InputStreamReader leitor = new InputStreamReader(fluxo);
            BufferedReader buffer = new BufferedReader(leitor);
            String linha = buffer.readLine();

            while (linha != null) {
                System.out.println(linha);
                linha = buffer.readLine();
            }

            buffer.close();
            leitor.close();
            fluxo.close();
        } else {
            throw new IOException("Arquivo não existe");
        }
    }

    public static String lerArquivo(String absolutePath, int id) throws IOException {
        File arquivo = new File(absolutePath);

        String linha2 = "";
        if (arquivo.exists()) {

            FileInputStream fluxo = new FileInputStream(arquivo);
            InputStreamReader leitor = new InputStreamReader(fluxo);
            BufferedReader buffer = new BufferedReader(leitor);
            String linha = buffer.readLine();

            while (linha != null) {
                if (linha.contains(",  id: " + id)) {
                    System.out.println(linha);
                    linha2 = linha;
                }
                linha = buffer.readLine();

            }

            buffer.close();
            leitor.close();
            fluxo.close();
        } else {
            throw new IOException("Arquivo inválido");
        }
        return linha2;
    }

    public static String gerarArquivoLeitor() {
        StringBuffer buffer = new StringBuffer();
        String linha = "";

        Scanner sc = new Scanner(System.in);

        for (int i = 0; i <= 3; i++) {

            System.out.println("INSIRA O NOME DO LEITOR: ");
            String nome = sc.next();

            System.out.println("INSIRA O GENERO: ");
            String genero = sc.next();

            System.out.println("INSIRA A IDADE: ");
            int idade = sc.nextInt();

            linha = gerarLeitor(nome, genero, idade);
            buffer.append(linha + "\r\n");

        }

        sc.close();
        return buffer.toString();
    }

    public static String gerarLeitor(String nome, String genero, int idade) {

        Leitor leitor = new Leitor();
        leitor.setId(id++);
        leitor.setGenero(genero);
        leitor.setIdade(idade);
        leitor.setNome(nome);
        leitores.add(leitor);

        String resposta = leitor.toString();

        return resposta;
    }

    public static void atualizarLeitor(int id, int opcao, String alteracao) {

        try {

            String oldLine = lerArquivo(caminho, id);

            for (int i = 0; i < leitores.size(); i++) {

                if (leitores.get(i).getId() == id) {
                    switch (opcao) {
                        case 1:
                            leitores.get(i).setGenero(alteracao);

                            String newLine = leitores.get(i).toString();
                            OverwriteLine(oldLine, newLine);
                            break;

                        case 2:
                            leitores.get(i).setNome(alteracao);

                            newLine = leitores.get(i).toString();
                            OverwriteLine(oldLine, newLine);
                            break;

                        case 3:
                            leitores.get(i).setIdade(Integer.parseInt(alteracao));
                            newLine = leitores.get(i).toString();
                            OverwriteLine(oldLine, newLine);
                            break;

                        default:
                            break;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("ERROR");
        }
    }

    public static void OverwriteLine(String oldLine, String newLine) throws IOException {
        String filePath = caminho;
        Scanner sc = new Scanner(new File(filePath));
        StringBuffer buffer = new StringBuffer();
        while (sc.hasNextLine()) {
            buffer.append(sc.nextLine() + System.lineSeparator());
        }
        String fileContents = buffer.toString();
        LOGGER.info("ANTES DE ALTERAR: " + "[ " + fileContents + " ]");

        sc.close();

        fileContents = fileContents.replace(oldLine, newLine);

        FileWriter writer = new FileWriter(filePath);
        LOGGER.info("DEPOIS DE ALTERAR: " + "[ " + fileContents + " ]");

        writer.append(fileContents);
        writer.flush();
        writer.close();
    }

    public static void excluirLinha(String lineToRemove) {

        try {

            File inFile = new File(caminho);

            if (!inFile.isFile()) {
                System.out.println("Arquivo informado não existe");
                return;
            }

            // Construct the new file that will later be renamed to the original filename.
            File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

            BufferedReader br = new BufferedReader(new FileReader(caminho));
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

            String line = null;

            // Read from the original file and write to the new
            // unless content matches data to be removed.
            while ((line = br.readLine()) != null) {

                if (!line.trim().equals(lineToRemove)) {

                    pw.println(line);
                    pw.flush();
                }
            }
            pw.close();
            br.close();

            // Delete the original file
            if (!inFile.delete()) {
                System.out.println("Não foi possível deletar o arquivo");
                return;
            }

            // Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(inFile))
                System.out.println("Não foi possível renomear o arquivo");

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    private static void excluirLeitor(int id) {
        try {
            String linhaExcluir = lerArquivo(caminho, id);
            for (int i = 0; i < leitores.size(); i++) {

                if (leitores.get(i).getId() == id) {
                    leitores.remove(i);
                    System.out.println(leitores.toString());
                    excluirLinha(linhaExcluir);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
