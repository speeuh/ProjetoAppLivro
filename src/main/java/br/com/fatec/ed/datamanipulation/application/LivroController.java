package br.com.fatec.ed.datamanipulation.application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.fatec.ed.datamanipulation.entidades.Livro;

public class LivroController {
	private static Logger LOGGER = Logger.getLogger(LivroController.class);
	static List<Livro> livros = new ArrayList<>();

	public static void main(String[] args) throws IOException {
		// mudar caminho
		lerArquivoLivro("C:\\Users\\sarah\\Downloads\\livro.txt");
		for (int i = 0; i < livros.size(); i++) {
			System.out.println(livros.get(i).getTitulo());
		}

	}

	public static void lerArquivoLivro(String absolutePath) throws IOException {
		File arquivo = new File(absolutePath);

		if (arquivo.exists()) {

			FileInputStream fluxo = new FileInputStream(arquivo);
			InputStreamReader livro = new InputStreamReader(fluxo);
			BufferedReader buffer = new BufferedReader(livro);
			String linha = buffer.readLine();

			while (linha != null) {
				System.out.println(linha);
				gerarLivro(linha);
				linha = buffer.readLine();

			}

			buffer.close();
			livro.close();
			fluxo.close();
		} else {
			LOGGER.error("ARQUIVO NÃO ENCONTRADO");
			throw new IOException("Arquivo não existe");
		}

		LOGGER.info("LIVROS COLHIDOS COM SUCESSO");
	}

	private static void gerarLivro(String linha) {
		// : .*?,

		if (linha != null) {
			Livro livro = new Livro();
			String[] line = linha.split("(?=,).*?(?<=: )");

			for (int i = 0; i < line.length; i++) {
				System.out.println(line[i] + "     " + i);
				if (i == 1)
					livro.setTitulo(line[i].toString());
				if (i == 2)
					livro.setAutor(line[i].toString());
				if (i == 3)
					livro.setGenero(line[i].toString());
				if (i == 4) 
						livro.setPaginas(Integer.parseInt(line[i].toString().trim()));
			
				if (i == 5)
					livro.setISBN(Integer.parseInt(line[i].toString().trim()));
			}
			livros.add(livro);

		}

	}
}
