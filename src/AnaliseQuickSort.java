import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import com.opencsv.CSVWriter;
import java.util.Random;
import java.util.Arrays;

public class AnaliseQuickSort {
    public static void main(String[] args) {
        int[] tamanhos = {50, 500, 1000, 5000, 10000};
        int numExecucoes = 5;

        try {
            String nomeClasse = AnaliseQuickSort.class.getSimpleName();
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
                    int[] resultados = quickSort(Arrays.copyOf(vetor, vetor.length)); // Crie uma cópia do vetor para manter o original inalterado
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

    public static int[] quickSort(int[] vetor) {
        int n = vetor.length;
        int iteracoes = quickSortHelper(vetor, 0, n - 1);
        return new int[]{iteracoes};
    }

    private static int quickSortHelper(int[] vetor, int esquerda, int direita) {
        int iteracoes = 0;
        if (esquerda < direita) {
            // Escolha o pivô como o elemento do meio
            int meio = partition(vetor, esquerda, direita);

            // Ordena recursivamente os elementos menores e maiores que o pivô
            int iteracoesEsquerda = quickSortHelper(vetor, esquerda, meio - 1);
            int iteracoesDireita = quickSortHelper(vetor, meio + 1, direita);
            iteracoes = iteracoesEsquerda + iteracoesDireita + 1;
        }
        return iteracoes;
    }

    private static int partition(int[] vetor, int esquerda, int direita) {
        int pivot = vetor[direita];
        int i = esquerda - 1;

        for (int j = esquerda; j < direita; j++) {
            if (vetor[j] <= pivot) {
                i++;
                int temp = vetor[i];
                vetor[i] = vetor[j];
                vetor[j] = temp;
            }
        }

        int temp = vetor[i + 1];
        vetor[i + 1] = vetor[direita];
        vetor[direita] = temp;

        return i + 1;
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
