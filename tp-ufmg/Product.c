// -------------------------------------------------------------------------------- //

// Includes
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <err.h>

// -------------------------------------------------------------------------------- //

// Definitions
#define MAX_PRODUCT_NAME            50
#define MAX_PRODUCT_SITUATION       5
#define MAX_LINE_SIZE               100

// -------------------------------------------------------------------------------- //

// Structs
typedef struct {

    char name[MAX_PRODUCT_NAME],
        situation[MAX_PRODUCT_SITUATION];

    int code, amount;
    float price;
} Product;

// -------------------------------------------------------------------------------- //

// Class product functions
void product_start(Product *product) {

    strcpy(product -> name, "");
    strcpy(product -> situation, "");

    product -> code = -1;
    product -> amount = -1;
    product -> price = -1;
}

// -------------------------------------------------------------------------------- //

// Flexible cell
typedef struct Cell {

	Product element;
	struct Cell* next;
} Cell;

Cell* newCell(Product element) {

   	Cell* new = (Cell*)malloc(sizeof(Cell));

   	new -> element = element;
   	new -> next = NULL;
   	return new;
}

Cell* first;
Cell* last;

// -------------------------------------------------------------------------------- //

// Flexible queue
void queue_start() {

	Product invalid;

   	first = newCell(invalid);
   	last = first;
}

void queue_insert(Product x) {

   	last -> next = newCell(x);
   	last = last -> next;
}

void swap(Cell* i, Cell* j) {

    Product temp = i -> element;
    i -> element = j -> element;
    j -> element = temp;
}

int queue_size() {

   	int size = 0;

   	Cell* i;
	for(i = first; i != last; i = i -> next, size++);
	return size;
}

void quicksort(Cell** array, int low, int high) {

    int i = low, j = high;

    Product pivot = array[(low + high) / 2] -> element;

    while(i <= j) {

        while(strcmp(array[i] -> element.name, pivot.name) < 0) i++;
        while(strcmp(array[j] -> element.name, pivot.name) > 0) j--;
        
        if(i <= j) swap(array[i++], array[j--]);
    }

    if(low < j) quicksort(array, low, j);
    if(i < high) quicksort(array, i, high);
}

void queue_sort() { 
    
    int size = queue_size(), j = 0;

    Cell* array[size];

    for(Cell* i = first -> next; i != NULL; i = i -> next, j++) array[j] = i;

    quicksort(array, 0, size - 1); 
}

void queue_print(char* fileOutName) {

	FILE *fileOut = fopen(fileOutName, "w+");
	Cell* i;

	for(i = first -> next; i != NULL; i = i -> next) {
		
		fprintf(fileOut, "%i\n%s\n%i\n%.2f\n%s%s", i -> element.code, i -> element.name, i -> element.amount, i -> element.price, i -> element.situation, i -> next != NULL ? "\n" : "");
	}

	fclose(fileOut);
}

void queue_printProduct(Product element) {

	printf("%i\n%s\n%i\n%.2f\n%s", element.code, element.name, element.amount, element.price, element.situation);
}

void queue_printWithCode(int code) {

	Cell* i;
	int j = 0;

	for(i = first -> next; i != NULL; i = i -> next) {
		
		if(i -> element.code == code) {
			
			printf("%s%i\n%s\n%i\n%.2f\n%s", j++ > 0 ? "\n" : "", i -> element.code, i -> element.name, i -> element.amount, i -> element.price, i -> element.situation);
		}
	}
}

void queue_printWithSituation(char* situation) {

	Cell* i;
	int j = 0;

	for(i = first -> next; i != NULL; i = i -> next) {
		
		if(!strcmp(i -> element.situation, situation)) {

			printf("%s%i\n%s\n%i\n%.2f\n%s", j++ > 0 ? "\n" : "", i -> element.code, i -> element.name, i -> element.amount, i -> element.price, i -> element.situation);
		}
	}
}

void queue_printLesserAmount(char* situation) {

	Cell* i;
	Cell* lesser = first;

	for(i = first -> next; i != NULL; i = i -> next) {
		
		if(i -> element.amount < lesser -> element.amount) {
			
			if(situation == NULL) lesser = i;
			else if(!strcmp(i -> element.situation, situation)) lesser = i;
		}
	}

	queue_printProduct(lesser -> element);
}

int queue_getTotalProducts() {

	Cell* i;
	int count = 0;

	for(i = first -> next; i != NULL; i = i -> next) count += i -> element.amount;
	return count;
}

// -------------------------------------------------------------------------------- //

int main(int argc, char **argv) {

	queue_start();

	// ---------------------------------------------------------------------------------------------- //

	if(argv[1] == NULL) return printf("\nERRO: O parâmetro 1 é obrigatório.\n\n");
    
	FILE *fileInput;
	fileInput = fopen(argv[1], "r");

	if(fileInput == NULL) return printf("\nERRO: O arquivo de entrada não foi encontrado.\n\n");
	else {

		char line[MAX_LINE_SIZE];
		fgets(line, sizeof(line), fileInput);

		int totalProducts = atoi(line);

		for(int i = 0; i < totalProducts; i++) {

			Product product;
    		product_start(&product);

			fgets(line, sizeof(line), fileInput);
			product.code = atoi(line);

			fgets(line, sizeof(line), fileInput);
			strcpy(product.name, line);

			product.name[strlen(product.name) - 1] = '\0';

			fgets(line, sizeof(line), fileInput);
			product.amount = atoi(line);

			fgets(line, sizeof(line), fileInput);
			product.price = atof(line);

			fgets(line, sizeof(line), fileInput);
			strcpy(product.situation, line);

			if(i != totalProducts - 1) product.situation[strlen(product.situation) - 2] = '\0';

			queue_insert(product);
		}

		fclose(fileInput);
	}

	// ---------------------------------------------------------------------------------------------- //
	
	if(argv[2] == NULL) return printf("\nERRO: O parâmetro 2 é obrigatório.\n\n");
    
	int operation = atoi(argv[2]);

	switch(operation) {

		case 1: {

			if(argv[3] == NULL) return printf("\nERRO: O parâmetro 3 é obrigatório.\n\n");
    
			queue_sort();
			queue_print(argv[3]);
			break;
		}

		case 2: {

			if(argv[3] == NULL) return printf("\nERRO: O parâmetro 3 é obrigatório.\n\n");
    
			queue_printWithCode(atoi(argv[3]));
			break;
		}

		case 3: {

			queue_printLesserAmount(NULL);
			break;
		}

		case 4: {

			if(argv[3] == NULL) return printf("\nERRO: O parâmetro 3 é obrigatório.\n\n");
    
			queue_sort();
			queue_printWithSituation(argv[3]);
			break;
		}

		case 5: {

			if(argv[3] == NULL) return printf("\nERRO: O parâmetro 3 é obrigatório.\n\n");
    
			queue_printLesserAmount(argv[3]);
			break;
		}

		case 6: {

			printf("%i", queue_getTotalProducts());
			break;
		}
	}

	// ---------------------------------------------------------------------------------------------- //
	
    return EXIT_SUCCESS;
}