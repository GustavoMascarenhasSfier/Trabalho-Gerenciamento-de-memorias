#include <stdio.h>
#include <stdlib.h>

int main() {
    
    // Declaração do array_estatico e inserindo valor no array
    int array_estatico[5];

    for (int i = 0; i < 5; i++) {
        array_estatico[i] = i + 1; 
    }

    // Declaração do array_dinamico e inserindo valor no array
    int *array_dinamico = (int *)malloc(10 * sizeof(int));

    // Faz uma verificação para ver se foi alocado 
    if (array_dinamico == NULL) { 
        printf("Erro: falha na alocação de memória!\n");
        return 1;
    } 

    for (int i = 0; i < 10; i++) {   
        array_dinamico[i] = 10 + i;
    }

    // Os prints dos arrays 

    printf("--- Array Estatico ---\n");
    for (int i = 0; i < 5; i++) {
        printf("Valor: %2d | Endereco: %p\n", array_estatico[i], (void*)&array_estatico[i]);
    }

    printf("\n--- Array Dinamico ---\n");
    for (int i = 0; i < 10; i++) {
        printf("Valor: %2d | Endereco: %p\n", array_dinamico[i], (void*)&array_dinamico[i]);
    }

    // Faz o calculo da diferença dos endereços
    long diff = (long)array_dinamico - (long)array_estatico;
    printf("\nDiferenca entre os enderecos : %ld bytes\n", diff);

    // Libera a memoria
    free(array_dinamico);
    array_dinamico = NULL;

    return 0;
}
