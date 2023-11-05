import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import com.opencsv.CSVWriter;
import java.util.Random;
import java.util.Arrays;


public class AnaliseShellSort {
    public static void main(String[] args) {
        int[] tamanhos = {50, 500, 1000, 5000, 10000};
        int numExecucoes = 5;

        try {
            String nomeClasse = AnaliseShellSort.class.getSimpleName();
            String nomeArquivo = nomeClasse + "_resultados.csv";

            // Use BufferedWriter e OutputStreamWriter para definir a codificação UTF-8
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(nomeArquivo), "UTF-8"));
            CSVWriter csvWriter = new CSVWriter(bufferedWriter);

            // Defina o BOM (Byte Order Mark) para indicar a codificação UTF-8
            bufferedWriter.write("\uFEFF");

            String[] header = {"Tamanho do Vetor", "Tempo Médio (nanos)", "Trocas Médias", "Iterações Médias"};
            csvWriter.writeNext(header);

            for (int tamanho : tamanhos) {
                System.out.println("Tamanho do vetor: " + tamanho);

                long tempoTotal = 0;
                long totalTrocas = 0;
                long totalIteracoes = 0;

                for (int execucao = 0; execucao < numExecucoes; execucao++) {
                    int[] vetor = gerarVetorAleatorio(tamanho);

                    long inicio = System.nanoTime();
                    int[] resultados = shellSort(Arrays.copyOf(vetor, vetor.length)); // Crie uma cópia do vetor para manter o original inalterado
                    long fim = System.nanoTime();
                    long tempoExecucao = fim - inicio;
                    tempoTotal += tempoExecucao;

                    int trocas = resultados[0];
                    int iteracoes = resultados[1];
                    totalTrocas += trocas;
                    totalIteracoes += iteracoes;

                    System.out.println("Execução " + (execucao + 1));
                    System.out.println("Tempo de execução (nanos): " + tempoExecucao);
                    System.out.println("Número de trocas: " + trocas);
                    System.out.println("Número de iterações: " + iteracoes);
                }

                double tempoMedio = tempoTotal / (double) numExecucoes;
                double trocasMedias = totalTrocas / (double) numExecucoes;
                double iteracoesMedias = totalIteracoes / (double) numExecucoes;

                System.out.println("Média após " + numExecucoes + " execuções:");
                System.out.println("Tempo de execução médio (nanos): " + tempoMedio);
                System.out.println("Número médio de trocas: " + trocasMedias);
                System.out.println("Número médio de iterações: " + iteracoesMedias);
                System.out.println();

                String[] data = {String.valueOf(tamanho), String.valueOf(tempoMedio), String.valueOf(trocasMedias), String.valueOf(iteracoesMedias)};
                csvWriter.writeNext(data);
            }

            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int[] shellSort(int[] vetores) {
        int tamanho = vetores.length;
        int diferencaGap = tamanho / 2;
        int NumTrocas = 0;
        int NumIteracoes = 0;

        while (diferencaGap > 0) {
            for (int i = diferencaGap; i < tamanho; i++) {
                int temp = vetores[i];
                int j;
                for (j = i; j >= diferencaGap && vetores[j - diferencaGap] > temp; j -= diferencaGap) {
                    vetores[j] = vetores[j - diferencaGap];
                    NumTrocas++;
                    NumIteracoes++;
                }
                vetores[j] = temp;
                NumTrocas++;
                NumIteracoes++;
            }
            diferencaGap = diferencaGap / 2;
        }
        return new int[]{NumTrocas, NumIteracoes};
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
