package br.com.fatec.ed.datamanipulation.entidades;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Meta {

    private int quantidadePaginas;
    private String tipo;
    private String livro;
    private int id;

    @Override
    public String toString() {
        return "paginas: " + this.quantidadePaginas + ", " + " meta: " + this.tipo + ", " + " livro: " + this.livro + ", " +
                " id: " + this.id;
    }
}
