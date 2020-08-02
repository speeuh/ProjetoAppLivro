package br.com.fatec.ed.datamanipulation.enumerators;

import lombok.Getter;

@Getter
public enum StatusLeitura {

    LIDO("L", "LIDO"),

    QUERO_LER("Q", "QUERO LER"),

    LENDO("LE", "LENDO");

    private String codigo;
    private String descricao;

    StatusLeitura(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public static String parse(String codigo) {
        String descricao = "";

        if (codigo.equals(LIDO.getCodigo())) {
            descricao = LIDO.getDescricao();
        } else if (codigo.equals(QUERO_LER.getCodigo())) {
            descricao = QUERO_LER.getDescricao();
        } else {
            descricao = LENDO.getDescricao();
        }

        return descricao;
    }
}
