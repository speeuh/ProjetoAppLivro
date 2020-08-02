package br.com.fatec.ed.datamanipulation.enumerators;

import lombok.Getter;

@Getter
public enum TipoMeta {


    MENSAL("M", "MENSAL"),
    DIARIA("D", "DIARIA"),
    SEMANAL("S", "SEMANAL");

    private String codigo;
    private String descricao;

    TipoMeta(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }


    public static String parse(String codigo) {
        String descricao = "";

        if (codigo.equals(MENSAL.getCodigo())) {
            descricao = MENSAL.getDescricao();
        } else if (codigo.equals(DIARIA.getCodigo())) {
            descricao = DIARIA.getDescricao();
        } else {
            descricao = SEMANAL.getDescricao();
        }

        return descricao;
    }

}
