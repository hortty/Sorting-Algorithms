import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import com.opencsv.CSVWriter;
import java.util.Random;
import java.util.Arrays;

public class AnaliseHeapSort {
    public static void main(String[] args) {
        int[] tamanhos = {50, 500, 1000, 5000, 10000};
        int numExecucoes = 5;

        try {
            String nomeClasse = AnaliseHeapSort.class.getSimpleName();
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
                    ResultadoExecucao resultado = HeapSort(vetor, tamanho);
                    long fim = System.nanoTime();
                    long tempoExecucao = fim - inicio;
                    tempoTotal += tempoExecucao;

                    long iteracoes = resultado.NumIteracoes;
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

    public static ResultadoExecucao HeapSort(int vetor[], int tamanho) {
        ResultadoExecucao resultadoExecucao = new ResultadoExecucao();

        long startTime = System.nanoTime();

        for (int i = tamanho / 2 - 1; i >= 0; i--) {
            OrdenarVetor(vetor, tamanho, i, resultadoExecucao);
        }

        for (int i = tamanho - 1; i >= 0; i--) {
            int temp = vetor[0];
            vetor[0] = vetor[i];
            vetor[i] = temp;
            resultadoExecucao.NumTrocas++;

            OrdenarVetor(vetor, i, 0, resultadoExecucao);
        }

        long endTime = System.nanoTime();
        resultadoExecucao.TempExecucao = endTime - startTime;

        return resultadoExecucao;
    }

    static int OrdenarVetor(int vetor[], int n, int i, ResultadoExecucao resultadoExecucao) {
        resultadoExecucao.NumIteracoes++;

        int maiorValor = i;
        int esquerdaValor = 2 * i + 1;
        int direitaValor = 2 * i + 2;

        if (esquerdaValor < n && vetor[esquerdaValor] > vetor[maiorValor]) {
            maiorValor = esquerdaValor;
        }

        if (direitaValor < n && vetor[direitaValor] > vetor[maiorValor]) {
            maiorValor = direitaValor;
        }

        if (maiorValor != i) {
            int temp = vetor[i];
            vetor[i] = vetor[maiorValor];
            vetor[maiorValor] = temp;

            resultadoExecucao.NumTrocas++;

            OrdenarVetor(vetor, n, maiorValor, resultadoExecucao);
        }

        return (int) resultadoExecucao.NumTrocas;
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

class ResultadoExecucao {
    public long TempExecucao;
    public long NumTrocas;
    public long NumIteracoes;
}
