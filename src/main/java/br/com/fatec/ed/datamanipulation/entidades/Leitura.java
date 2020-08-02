package br.com.fatec.ed.datamanipulation.entidades;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Leitura {

    private int id;
    private String leitor;
    private String livro;
    private String status;
    private String meta;
    private int quantidadeDePaginas;


    @Override
    public String toString() {
        return "leitor: " + this.leitor + ", " + " livro: " + this.livro + ", " + " status: " + this.status + ", " +
                " meta: " + this.meta + ", " + "paginas: " + this.quantidadeDePaginas + ", " + " id: " + this.id;
    }
}
