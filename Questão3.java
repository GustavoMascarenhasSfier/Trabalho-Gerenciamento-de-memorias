import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class CodigoFifo {
    public static void main(String[] args) {
        
        // 1. Implementa try-with-resources (resolve o primeiro warning)
        try (Scanner scanner = new Scanner(System.in)) { 

            // 1. RECEBIMENTO DE ENTRADA: Número de frames
            int numFrames = 0;
            boolean entradaValida = false;
            while (!entradaValida) {
                try {
                    System.out.print("Digite o número de frames (quadros) disponíveis na memória: ");
                    numFrames = scanner.nextInt();
                    if (numFrames > 0) {
                        entradaValida = true;
                    } else {
                        System.out.println("O número de frames deve ser um valor positivo.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Entrada inválida. Por favor, digite um número inteiro.");
                    scanner.next(); // Limpa o buffer
                }
            }
            scanner.nextLine(); // Consome a linha pendente

            // 2. e 3. Inicializa referencias como array vazio (resolve os warnings de null pointer)
            int[] referencias = new int[0]; 
            entradaValida = false;
            while (!entradaValida) {
                try {
                    System.out.println("\nDigite a sequência de referências a páginas (separadas por vírgula ou espaço):");
                    System.out.print("Exemplo: 7, 0, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 2\nEntrada: ");
                    String linha = scanner.nextLine().replaceAll("[^0-9, ]", "").trim(); 
                    
                    String[] strReferencias = linha.split("[,\\s]+");
                    
                    if (strReferencias.length > 0 && !strReferencias[0].isEmpty()) {
                        referencias = new int[strReferencias.length];
                        for (int i = 0; i < strReferencias.length; i++) {
                            referencias[i] = Integer.parseInt(strReferencias[i].trim());
                        }
                        entradaValida = true;
                    } else {
                        System.out.println("A sequência de referências não pode estar vazia. Tente novamente.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Formato de página inválido. Certifique-se de usar apenas números inteiros.");
                }
            }
            
            // ====================================================================
            // INÍCIO DA SIMULAÇÃO FIFO
            // ====================================================================

            Queue<Integer> memoria = new LinkedList<>();
            int pageFaults = 0;

            System.out.println("\n===== Simulação FIFO Iniciada =====");
            System.out.println("Número de frames: " + numFrames);
            System.out.println("Sequência de referências: " + Arrays.toString(referencias) + "\n");
            
            // Simulação principal
            for (int pagina : referencias) { // Linha onde o warning 2/3 ocorria
                String acao;
                String substituida = "";

                if (memoria.contains(pagina)) {
                    acao = "Page Hit";
                } else {
                    pageFaults++;
                    if (memoria.size() == numFrames) {
                        int removida = memoria.poll(); // Remove a mais antiga (FIFO)
                        substituida = " (substituiu " + removida + ")";
                    }
                    memoria.add(pagina);
                    acao = "Page Fault" + substituida;
                }

                // Exibir status formatado
                System.out.printf("Referência: %-2d | Frames: %-20s | %-25s | Total Faults: %d\n",
                        pagina, memoria, acao, pageFaults);
            }

            // Estatísticas finais
            int totalRefs = referencias.length; // Linha onde o warning 2/3 ocorria
            double taxaFaltas = (double) pageFaults / totalRefs * 100;

            System.out.println("\n===== Resultados Finais =====");
            System.out.println("Total de referências: " + totalRefs);
            System.out.println("Total de faltas de página: " + pageFaults);
            System.out.printf("Taxa de faltas de página: %.2f%%\n", taxaFaltas);
            
        } // Scanner fechado automaticamente
        catch (Exception e) {
            System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
        }
    }
}