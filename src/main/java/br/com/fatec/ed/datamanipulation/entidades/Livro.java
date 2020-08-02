package br.com.fatec.ed.datamanipulation.entidades;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Livro {
	private int ISBN;
    private String titulo;
    private String autor;
    private String genero;
    private int paginas;


}
