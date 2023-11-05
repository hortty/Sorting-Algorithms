import java.util.Arrays;

public class BubbleSort {
    public static void bubbleSort(int[] vetor) {
        int n = vetor.length;
        boolean trocado;
        for (int i = 0; i < n - 1; i++) {
            trocado = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (vetor[j] > vetor[j + 1]) {
                    // Troca os elementos se estiverem fora de ordem
                    int temp = vetor[j];
                    vetor[j] = vetor[j + 1];
                    vetor[j + 1] = temp;
                    trocado = true;
                }
            }
            // Se nenhum elemento foi trocado na passagem interna, o array já está ordenado
            if (!trocado) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        int[] array = {12, 11, 13, 5, 6, 7};
        System.out.println("Array antes da ordenação: " + Arrays.toString(array));
        bubbleSort(array);
        System.out.println("Array após a ordenação: " + Arrays.toString(array));
    }
}
