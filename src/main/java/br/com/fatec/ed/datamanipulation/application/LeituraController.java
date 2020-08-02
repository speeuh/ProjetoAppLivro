package br.com.fatec.ed.datamanipulation.application;

import br.com.fatec.ed.datamanipulation.entidades.Leitura;
import br.com.fatec.ed.datamanipulation.enumerators.StatusLeitura;
import br.com.fatec.ed.datamanipulation.enumerators.TipoMeta;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.*;

public class LeituraController {

    private static int id = 1;
    private static Logger LOGGER = Logger.getLogger(LeituraController.class);
    static String caminho = "C:\\Users\\Lenovo\\Documents\\vitoria_temp\\leitura.txt";
    static Queue<Leitura> leituras = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        LOGGER.info("**********INICIANDO APLICACAO*************");

        criarArquivo("C:\\Users\\Lenovo\\Documents\\vitoria_temp", "leitura");
        personalizarLeitura(1, "LE");
        excluirLeitura();
    }


    public static void criarArquivo(String path, String arq) throws IOException {

        File dir = new File(path);
        File arquivo = new File(path, arq + ".txt");
        if (dir.exists()) {
            boolean arquivoExiste = false;
            if (arquivo.exists()) {
                arquivoExiste = true;
            }
            String conteudo = gerarLeitura();
            FileWriter fw = new FileWriter(arquivo, arquivoExiste);
            PrintWriter pw = new PrintWriter(fw);
            pw.write(conteudo);
            pw.flush();
            pw.close();
            fw.close();
        } else {
            throw new IOException("Diretório Inválido");
        }

        LOGGER.info("LEITURA REGISTRADA COM SUCESSO");
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
            linha2 = linha;


            buffer.close();
            leitor.close();
            fluxo.close();
        } else {
            throw new IOException("Arquivo inválido");
        }
        return linha2;
    }

    public static String gerarLeitura(String leitor, String livro, String status, String meta, int paginas) {
        Leitura leitura = new Leitura();
        leitura.setId(id++);
        leitura.setLeitor(leitor);
        leitura.setLivro(livro);
        leitura.setMeta(TipoMeta.parse(meta));
        leitura.setStatus(StatusLeitura.parse(status));
        leitura.setQuantidadeDePaginas(paginas);
        leituras.add(leitura);


        String resposta = leitura.toString();

        return resposta;
    }

    public static String gerarLeitura() {
        StringBuffer buffer = new StringBuffer();
        String linha = "";

        Scanner sc = new Scanner(System.in);

        for (int i = 0; i <= 1; i++) {

            System.out.println("NOME DO LEITOR: ");
            String leitor = sc.next();
            sc.nextLine();

            System.out.println("INFORME O LIVRO QUE ESTA SENDO LIDO?: ");
            String livro = sc.next();
            sc.nextLine();

            System.out.println("QUAL E STATUS DA LEITURA? ");
            String status = sc.next();

            System.out.println("QUAL E O TIPO DE META DESSA LEITURA? ");
            String meta = sc.next();

            System.out.println("QUANTAS PAGINAS VOCE QUER LER? ");
            int paginas = sc.nextInt();

            linha = gerarLeitura(leitor, livro, status, meta, paginas);
            buffer.append(linha + "\r\n");

        }

        sc.close();
        return buffer.toString();
    }

    public static void personalizarLeitura(int id, String alteracao) throws IOException {

        try {
            String oldLine = lerArquivo(caminho, id);

            for (int i = 0; i < leituras.size(); i++) {
                if (leituras.peek().getId() == id) {
                    leituras.peek().setStatus(StatusLeitura.parse(alteracao));
                    String newLine = leituras.peek().toString();
                    OverwriteLine(oldLine, newLine);

                    LOGGER.info("ID DA META PERSONALIZADA " + leituras.peek().getId());
                }
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
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

    public static String recuperarRegistro(String absolutePath) throws IOException {
        File arquivo = new File(absolutePath);

        String registroExcluido = "";
        if (arquivo.exists()) {

            LOGGER.info("INFORMACOES ENCONTRADAS NO ARQUIVO");

            FileInputStream fluxo = new FileInputStream(arquivo);
            InputStreamReader leitor = new InputStreamReader(fluxo);
            BufferedReader buffer = new BufferedReader(leitor);
            String linha = buffer.readLine();


            while (linha != null) {
                registroExcluido = linha;
                linha = buffer.readLine();
            }


            buffer.close();
            leitor.close();
            fluxo.close();
        } else {
            throw new IOException("Arquivo não existe");
        }

        return registroExcluido;
    }

    private static boolean excluirLeitura() {
        try {
            String linhaExcluir = recuperarRegistro(caminho);
            LOGGER.info("REGISTRO EXCLUIDO: " + linhaExcluir);
            leituras.remove();
            excluirLinha(linhaExcluir);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }
}
