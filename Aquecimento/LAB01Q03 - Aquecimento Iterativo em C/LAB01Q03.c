#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

// ---------------------------------------------------------------------------------------------------------------------- //

bool isFim(char *string) { return !strcmp(string, "FIM"); }

int countMaiusculas(char *string)
{
    int count = 0;

    for(int x = 0; x != strlen(string); x++)
    {
        if(string[x] >= 'A' && string[x] <= 'Z') count++;
    }
    return count;
}

// ---------------------------------------------------------------------------------------------------------------------- //

int main()
{
    char string[1000][100];
    int numLinhas = 0;

    do scanf(" %[^\n]s", string[numLinhas]);
    while(!isFim(string[numLinhas++]));

    numLinhas--;

    for(int x = 0; x != numLinhas; x++) printf("%i\n", countMaiusculas(string[x]));
	return 0;
}