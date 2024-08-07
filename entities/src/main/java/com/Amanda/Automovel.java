package com.Amanda;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Automovel(@JsonAlias("Modelo") String modelo,
                        @JsonAlias("AnoModelo")String ano,
                        @JsonAlias("Marca")String marca,
                        @JsonAlias("Combustivel")String combustivel,
                        @JsonAlias("Valor")String valor,
                        @JsonAlias("MesReferencia") String mesReferncia) {
    @Override
    public String toString() {
        return "\nModelo: " + modelo +
                "\nAno: " + ano +
                "\nMarca: " + marca +
                "\nCombustível: " + combustivel +
                "\nValor: " + valor+
                "\nMês Referência: " + mesReferncia;
    }
}