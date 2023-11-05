import java.util.Arrays;

public class MergeSort {
    public static void mergeSort(int[] vetor) {
        int n = vetor.length;
        if (n < 2) return; 

        // Dividir o array ao meio
        int meio = n / 2;
        int[] S1 = Arrays.copyOfRange(vetor, 0, meio); // Cópia da primeira metade
        int[] S2 = Arrays.copyOfRange(vetor, meio, n); // Cópia da segunda metade

        // Ordenar recursivamente as cópias
        mergeSort(S1);
        mergeSort(S2);

        // Mesclar os resultados ordenados
        merge(S1, S2, vetor);
    }

    private static void merge(int[] S1, int[] S2, int[] S) {
        int i = 0, j = 0, k = 0;
        int n1 = S1.length;
        int n2 = S2.length;

        while (i < n1 && j < n2) {
            if (S1[i] <= S2[j]) {
                S[k] = S1[i];
                i++;
            } else {
                S[k] = S2[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            S[k] = S1[i];
            i++;
            k++;
        }

        while (j < n2) {
            S[k] = S2[j];
            j++;
            k++;
        }
    }

    public static void main(String[] args) {
        int[] array = {12, 11, 13, 5, 6, 7};
        System.out.println("Array antes da ordenação: " + Arrays.toString(array));
        mergeSort(array);
        System.out.println("Array após a ordenação: " + Arrays.toString(array));
    }
}
