package com.Amanda;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    private static Scanner leitura = new Scanner(System.in);
    private static final String URL = "https://parallelum.com.br/fipe/api/v1/";
    private static String endereco;

    public static void main(String[] args) {
        ConsumirApi consumirApi = new ConsumirApi();
        ConverterDados converterDados = new ConverterDados();

        var menu = """
                Busque o preço de Automóveis pela tabela Fipe
                Carros 1
                Motos 2
                Caminhões 3
                Escolha uma das opções digitando o número correspondente:""";
        System.out.println(menu);
        while (true) {
            var opcao = leitura.nextInt();
            switch (opcao) {
                case 1:
                    endereco = URL + "carros/marcas";
                    break;
                case 2:
                    endereco = URL + "motos/marcas";
                    break;
                case 3:
                    endereco = URL + "caminhoes/marcas";
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente:");
            }
            break;
        }

        var json = consumirApi.obterDados(endereco);
        System.out.println("\nMarcas:\n");
        var dadosMarcaList = converterDados.conversorLista(json, Dados.class);
        dadosMarcaList.stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("Digite o Código da marca para consultar: ");
        var codMarca = leitura.nextInt();
        endereco = endereco + "/" + codMarca + "/modelos";

        json = consumirApi.obterDados(endereco);
        System.out.println("\nModelos:\n");
        var modelosList = converterDados.conversorObjetos(json, Modelos.class);
        modelosList.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        if (modelosList.modelos().size() > 7) {
            while (true) {
                System.out.println("Digite um trecho do nome do modelo desejado:");
                var buscaAutomovel = leitura.next();

                if (buscaAutomovel.isEmpty()) {
                    System.out.println("Digite um trecho do nome do modelo desejado:");
                    continue;
                }

                List<Dados> modelosFiltrado = modelosList.modelos().stream()
                        .filter(m -> m.nome().toLowerCase().contains(buscaAutomovel.toLowerCase()))
                        .sorted(Comparator.comparing(Dados::codigo))
                        .collect(Collectors.toList());

                if (modelosFiltrado.isEmpty()) {
                    System.out.println("\nNenhum modelo encontrado. Tente novamente.");
                    continue;
                } else if (modelosFiltrado.size() > 0) {
                    System.out.println("\nModelos Filtrados: \n ");
                    modelosFiltrado.forEach(System.out::println);
                    break;
                }
            }
        }

        System.out.println("\nPara ver os Anos disponíveis com Avaliação \nDigite o Código do Automóvel Escolhido:");
        var codAutomovel = leitura.next();
        endereco = endereco + "/" + codAutomovel + "/anos";

        json = consumirApi.obterDados(endereco);
        List<Dados> anoList = converterDados.conversorLista(json, Dados.class);

        List<Automovel> automovelList = new ArrayList<>();

        for (Dados ano : anoList) {
            var enderecoBase = endereco + "/" + ano.codigo();
            json = consumirApi.obterDados(enderecoBase);
            Automovel automovel = converterDados.conversorObjetos(json, Automovel.class);
            automovelList.add(automovel);
        }

        System.out.println("\nTotal de automóveis encontrados: " + automovelList.size());
        automovelList.forEach(System.out::println);

        leitura.close();
    }
}