#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

// ---------------------------------------------------------------------------------------------------------------------- //

bool isFim(char *string) { return !strcmp(string, "FIM"); }

int countMaiusculas(char *string, int x)
{
    int count = 0;

    if(string[x] == '\0') return count;
    else if(string[x] >= 'A' && string[x] <= 'Z') count++;
    
    return count += countMaiusculas(string, ++x);
}

// ---------------------------------------------------------------------------------------------------------------------- //

int main()
{
    char string[1000][100];
    int numLinhas = 0;

    do scanf(" %[^\n]s", string[numLinhas]);
    while(!isFim(string[numLinhas++]));

    numLinhas--;

    for(int x = 0; x != numLinhas; x++) printf("%i\n", countMaiusculas(string[x], 0));
	return 0;
}