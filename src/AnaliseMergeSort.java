import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import com.opencsv.CSVWriter;
import java.util.Random;
import java.util.Arrays;

public class AnaliseMergeSort {
    public static void main(String[] args) {
        int[] tamanhos = {50, 500, 1000, 5000, 10000};
        int numExecucoes = 5;

        try {
            String nomeClasse = AnaliseMergeSort.class.getSimpleName();
            String nomeArquivo = nomeClasse + "_resultados.csv";

            // Use BufferedWriter e OutputStreamWriter para definir a codificação UTF-8
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(nomeArquivo), "UTF-8"));
            CSVWriter csvWriter = new CSVWriter(bufferedWriter);

            // Defina o BOM (Byte Order Mark) para indicar a codificação UTF-8
            bufferedWriter.write("\uFEFF");

            String[] header = {"Tamanho do Vetor", "Tempo Médio (nanos)", "Iterações Médias"};
            csvWriter.writeNext(header);

            for (int tamanho : tamanhos) {
                System.out.println("Tamanho do vetor: " + tamanho);

                long tempoTotal = 0;
                long totalIteracoes = 0;

                for (int execucao = 0; execucao < numExecucoes; execucao++) {
                    int[] vetor = gerarVetorAleatorio(tamanho);

                    long inicio = System.nanoTime();
                    int[] resultados = mergeSort(Arrays.copyOf(vetor, vetor.length)); // Crie uma cópia do vetor para manter o original inalterado
                    long fim = System.nanoTime();
                    long tempoExecucao = fim - inicio;
                    tempoTotal += tempoExecucao;

                    int iteracoes = resultados[0];
                    totalIteracoes += iteracoes;

                    System.out.println("Execução " + (execucao + 1));
                    System.out.println("Tempo de execução (nanos): " + tempoExecucao);
                    System.out.println("Número de iterações: " + iteracoes);
                }

                double tempoMedio = tempoTotal / (double) numExecucoes;
                double iteracoesMedias = totalIteracoes / (double) numExecucoes;

                System.out.println("Média após " + numExecucoes + " execuções:");
                System.out.println("Tempo de execução médio (nanos): " + tempoMedio);
                System.out.println("Número médio de iterações: " + iteracoesMedias);
                System.out.println();

                String[] data = {String.valueOf(tamanho), String.valueOf(tempoMedio), String.valueOf(iteracoesMedias)};
                csvWriter.writeNext(data);
            }

            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int[] mergeSort(int[] vetor) {
        int n = vetor.length;
        int iteracoes = mergeSortHelper(vetor, 0, n - 1);
        return new int[]{iteracoes};
    }

    private static int mergeSortHelper(int[] vetor, int inicio, int fim) {
        if (inicio < fim) {
            int meio = inicio + (fim - inicio) / 2;
            int iteracoesEsquerda = mergeSortHelper(vetor, inicio, meio);
            int iteracoesDireita = mergeSortHelper(vetor, meio + 1, fim);
            int iteracoesMerge = merge(vetor, inicio, meio, fim);
            return iteracoesEsquerda + iteracoesDireita + iteracoesMerge;
        }
        return 0;
    }

    private static int merge(int[] vetor, int inicio, int meio, int fim) {
        int iteracoes = 0;
        int n1 = meio - inicio + 1;
        int n2 = fim - meio;

        int[] L = new int[n1];
        int[] R = new int[n2];

        for (int i = 0; i < n1; i++) {
            L[i] = vetor[inicio + i];
        }

        for (int j = 0; j < n2; j++) {
            R[j] = vetor[meio + 1 + j];
        }

        int i = 0, j = 0, k = inicio;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                vetor[k] = L[i];
                i++;
            } else {
                vetor[k] = R[j];
                j++;
            }
            k++;
            iteracoes++;
        }

        while (i < n1) {
            vetor[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            vetor[k] = R[j];
            j++;
            k++;
        }

        return iteracoes;
    }

    public static int[] gerarVetorAleatorio(int tamanho) {
        int[] vetor = new int[tamanho];
        Random aleatorio = new Random(123); // Semente para reprodutibilidade
        for (int i = 0; i < tamanho; i++) {
            vetor[i] = aleatorio.nextInt(1000); // Geração de inteiros aleatórios de 0 a 999
        }
        return vetor;
    }
}
