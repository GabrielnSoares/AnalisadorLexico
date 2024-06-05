package br.projeto.analisadorlexico;
import java.util.*;
import java.util.regex.*;

public class AnalisadorLexico {
    // Tipos de tokens
    private static final Map<String, String> PALAVRAS_RESERVADAS = Map.of(
            "if", "Palavra-chave reservada pela linguagem",
            "else", "Palavra-chave reservada pela linguagem",
            "while", "Palavra-chave reservada pela linguagem",
            "switch", "Palavra-chave reservada pela linguagem",
            "int", "Palavra-chave reservada pela linguagem",
            "double", "Palavra-chave reservada pela linguagem"
    );

    private static final Map<String, String> OPERADORES = Map.of(
            "+", "Adição",
            "-", "subtração",
            "*", "Multiplicação",
            "/", "Divisão",
            "<", "Menor que",
            ">", "Maior que",
            "<=", "Menor ou igual a",
            ">=", "Maior ou igual a",
            "!=", "Diferente de"
    );

    private static final Map<String, String> SIMBOLOS = Map.of(
            ",", "Vírgula",
            ";", "Ponto e vírgula",
            ":", "Dois pontos",
            ".", "Ponto"
    );

    private static final Map<String, String> DELIMITADORES = Map.of(
            "(", "Parêntese",
            ")", "Parêntese",
            "[", "Colchete",
            "]", "Colchete",
            "{", "Chave",
            "}", "Chave"
    );

    private static final String ATRIBUICAO = "=";
    private static final Pattern IDENTIFICADOR = Pattern.compile("[a-zA-Z_]\\w{0,29}");
    private static final Pattern NUMERAL = Pattern.compile("\\d+");

    // colorir a saída com erro
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";

    private TabelaSimbolos tabelaSimbolosInicial = new TabelaSimbolos(); //ajustar depois
    private TabelaSimbolos tabelaSimbolosDinamica = new TabelaSimbolos();

    public AnalisadorLexico() {
        inicializarTabelaSimbolos();
    }

    public void analisar(String codigo) {
        System.out.println("\nCódigo fonte fornecido:\n" + codigo);
        System.out.println("Iniciando análise léxica...\n");
        codigo = removerComentarios(codigo);
        String[] linhas = codigo.split("\\R");

        for (String linha : linhas) {
            List<String> tokens = separarTokens(linha);
            for (String token : tokens) {
                classificarToken(token);
            }
        }

        tabelaSimbolosDinamica.imprimir();
    }

    public void resetarAnalise() {
        tabelaSimbolosDinamica = new TabelaSimbolos();
    }

    private List<String> separarTokens(String linha) {
        List<String> tokens = new ArrayList<>();
        Matcher matcher = Pattern.compile("\\d+|[a-zA-Z_]\\w*|[=<>!]=|\\S").matcher(linha);
        while (matcher.find()) {
            tokens.add(matcher.group());
        }
        return tokens;
    }

    private void classificarToken(String token) {
        if (token.isEmpty()) return;

        if (PALAVRAS_RESERVADAS.containsKey(token)) {
            System.out.println("Token: " + token + " | Tipo: Palavra Reservada");
            tabelaSimbolosDinamica.adicionar(token, "Palavra Reservada", PALAVRAS_RESERVADAS.get(token));
        } else if (OPERADORES.containsKey(token)) {
            System.out.println("Token: " + token + " | Tipo: Operador");
            tabelaSimbolosDinamica.adicionar(token, "Operador", OPERADORES.get(token));
        } else if (SIMBOLOS.containsKey(token)) {
            System.out.println("Token: " + token + " | Tipo: Pontuação");
            tabelaSimbolosDinamica.adicionar(token, "Pontuação", SIMBOLOS.get(token));
        } else if (DELIMITADORES.containsKey(token)) {
            System.out.println("Token: " + token + " | Tipo: Delimitador");
            tabelaSimbolosDinamica.adicionar(token, "Delimitador", DELIMITADORES.get(token));
        } else if (token.equals(ATRIBUICAO)) {
            System.out.println("Token: " + token + " | Tipo: Atribuição");
            tabelaSimbolosDinamica.adicionar(token, "Atribuição", "Usado para atribuir valor a uma variável");
        } else if (NUMERAL.matcher(token).matches()) {
            System.out.println("Token: " + token + " | Tipo: Numeral");
            tabelaSimbolosDinamica.adicionar(token, "Numeral", "Número");
        } else if (IDENTIFICADOR.matcher(token).matches()) {
            System.out.println("Token: " + token + " | Tipo: Identificador");
            tabelaSimbolosDinamica.adicionar(token, "Identificador", "Nome de variável ou função");
        } else {
            System.out.println(ANSI_RED + "Erro: Token desconhecido: " + token + ANSI_RESET);
        }
    }

    private String removerComentarios(String codigo) {
        // Remover comentários de linha
        codigo = codigo.replaceAll("//.*", "");
        // Remover comentários de bloco
        codigo = codigo.replaceAll("/\\*.*?\\*/", "");
        return codigo;
    }

    public void mostrarTabelaSimbolos() {
        tabelaSimbolosInicial.imprimirAgrupada();
    }

    private void inicializarTabelaSimbolos() {
        for (Map.Entry<String, String> entrada : PALAVRAS_RESERVADAS.entrySet()) {
            tabelaSimbolosInicial.adicionar(entrada.getKey(), "Palavra Reservada", entrada.getValue());
        }
        for (Map.Entry<String, String> entrada : OPERADORES.entrySet()) {
            tabelaSimbolosInicial.adicionar(entrada.getKey(), "Operador", entrada.getValue());
        }
        for (Map.Entry<String, String> entrada : SIMBOLOS.entrySet()) {
            tabelaSimbolosInicial.adicionar(entrada.getKey(), "Pontuação", entrada.getValue());
        }
        for (Map.Entry<String, String> entrada : DELIMITADORES.entrySet()) {
            tabelaSimbolosInicial.adicionar(entrada.getKey(), "Delimitador", entrada.getValue());
        }
        tabelaSimbolosInicial.adicionar(ATRIBUICAO, "Atribuição", "Usado para atribuir valor a uma variável");

        // Adiciona exemplos de identificadores e números
        tabelaSimbolosInicial.adicionar("A-Z, a-z, _", "Identificador", "Letras maiúsculas, minúsculas ou sublinhado, usado como nome de variável ou função");
        tabelaSimbolosInicial.adicionar("0-9", "Numeral", "Número");
    }
}