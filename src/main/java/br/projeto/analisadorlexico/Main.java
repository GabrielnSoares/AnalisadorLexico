package br.projeto.analisadorlexico;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AnalisadorLexico analisador = new AnalisadorLexico();

        // Mensagem de boas-vindas
        System.out.println("=====================================");
        System.out.println(" Bem-vindo ao Analisador Léxico em Java");
        System.out.println("=====================================");

        // Mostrar tabela de símbolos cadastrados no início
        System.out.println("\nTabela de símbolos disponíveis no sistema:");
        analisador.mostrarTabelaSimbolos();

        String escolha;
        do {
            StringBuilder codigo = new StringBuilder();
            String linha;

            System.out.println("\nDigite o código fonte (digite 'FIM' em uma nova linha para finalizar):");

            while (!(linha = scanner.nextLine()).equals("FIM")) {
                codigo.append(linha).append("\n");
            }

            analisador.analisar(codigo.toString());

            System.out.println("\nDeseja continuar usando o programa? (s/n)");
            escolha = scanner.nextLine().trim().toLowerCase();
            analisador.resetarAnalise(); // Resetar a análise para a próxima execução
        } while (escolha.equals("s"));

        System.out.println("Obrigado por usar o Analisador Léxico em Java. Até a próxima!");
        scanner.close();
    }
}
