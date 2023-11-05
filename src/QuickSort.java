import java.util.Arrays;

public class QuickSort {
    public static void quickSort(int[] vetor) {
        quickSort(vetor, 0, vetor.length - 1);
    }

    private static void quickSort(int[] vetor, int esquerda, int direita) {
        if (esquerda < direita) {
            // Escolha o pivô como o elemento do meio
            int meio = partition(vetor, esquerda, direita);

            // Ordena recursivamente os elementos menores e maiores que o pivô
            quickSort(vetor, esquerda, meio - 1);
            quickSort(vetor, meio + 1, direita);
        }
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

    public static void main(String[] args) {
        int[] array = {12, 11, 13, 5, 6, 7};
        System.out.println("Array antes da ordenação: " + Arrays.toString(array));
        quickSort(array);
        System.out.println("Array após a ordenação: " + Arrays.toString(array));
    }
}
