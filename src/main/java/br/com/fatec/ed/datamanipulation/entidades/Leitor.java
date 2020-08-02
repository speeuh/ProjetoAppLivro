package br.com.fatec.ed.datamanipulation.entidades;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Leitor {

    private int id;
    private String nome;
    private int idade;
    private String genero;


    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "[genero: " + this.genero + " , " + " nome: " + this.nome + " , " + " idade: " + this.idade + " , " + " id: " + this.id + "]";
    }
}
