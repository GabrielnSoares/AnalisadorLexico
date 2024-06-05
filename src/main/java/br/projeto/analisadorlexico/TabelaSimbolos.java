package br.projeto.analisadorlexico;
import java.util.*;

public class TabelaSimbolos {
    private List<EntradaTabelaSimbolos> simbolos = new ArrayList<>();

    public void adicionar(String identificador, String tipo, String descricao) {
        simbolos.add(new EntradaTabelaSimbolos(identificador, tipo, descricao));
    }

    public void imprimir() {
        System.out.println("\nTabela de símbolos:");
        System.out.printf("%-20s %-20s %-40s%n", "Símbolo", "Tipo", "Descrição");
        System.out.println("----------------------------------------------------------------------");
        Set<String> impressos = new HashSet<>();
        for (EntradaTabelaSimbolos entrada : simbolos) {
            if (!impressos.contains(entrada.getIdentificador())) {
                System.out.printf("%-20s %-20s %-40s%n", entrada.getIdentificador(), entrada.getTipo(), entrada.getDescricao());
                impressos.add(entrada.getIdentificador());
            }
        }
    }

    public void imprimirAgrupada() {
        Map<String, List<EntradaTabelaSimbolos>> agrupados = new LinkedHashMap<>();
        agrupados.put("Palavras Reservadas", new ArrayList<>());
        agrupados.put("Operadores", new ArrayList<>());
        agrupados.put("Pontuação", new ArrayList<>());
        agrupados.put("Delimitadores", new ArrayList<>());
        agrupados.put("Atribuição", new ArrayList<>());
        agrupados.put("Identificadores", new ArrayList<>());
        agrupados.put("Numerais", new ArrayList<>());

        for (EntradaTabelaSimbolos entrada : simbolos) {
            if (entrada.getTipo().equals("Palavra Reservada")) {
                agrupados.get("Palavras Reservadas").add(entrada);
            } else if (entrada.getTipo().equals("Operador")) {
                agrupados.get("Operadores").add(entrada);
            } else if (entrada.getTipo().equals("Pontuação")) {
                agrupados.get("Pontuação").add(entrada);
            } else if (entrada.getTipo().equals("Delimitador")) {
                agrupados.get("Delimitadores").add(entrada);
            } else if (entrada.getTipo().equals("Atribuição")) {
                agrupados.get("Atribuição").add(entrada);
            } else if (entrada.getTipo().equals("Identificador")) {
                agrupados.get("Identificadores").add(entrada);
            } else if (entrada.getTipo().equals("Numeral")) {
                agrupados.get("Numerais").add(entrada);
            }
        }

        for (Map.Entry<String, List<EntradaTabelaSimbolos>> grupo : agrupados.entrySet()) {
            System.out.println("\n" + grupo.getKey() + ":");
            for (EntradaTabelaSimbolos entrada : grupo.getValue()) {
                System.out.printf("%-20s %-20s %-40s%n", entrada.getIdentificador(), entrada.getTipo(), entrada.getDescricao());
            }
        }
    }
}