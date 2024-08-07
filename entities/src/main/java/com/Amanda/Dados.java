package com.Amanda;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Dados(String codigo, String nome) {
    @Override
    public String toString() {
        return "Codigo: " + codigo +
                "\nNome: " + nome + "\n";
    }
}