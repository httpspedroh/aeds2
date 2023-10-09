#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

// --------------------------------------------------------------------------------------------------------------- //

bool isFim(char *string) { return !strcmp(string, "FIM"); }

bool isPalindromo(char *str, int pos)
{
    if(pos == strlen(str)) return true;
    else if(str[pos] != str[strlen(str) - pos - 1]) return false;
    return isPalindromo(str, pos + 1);
}

// --------------------------------------------------------------------------------------------------------------- //
      
int main()
{
    char entrada[1000];

    scanf(" %[^\n]s", entrada);

    while(!isFim(entrada))
    {
        printf("%s\n", isPalindromo(entrada, 0) ? "SIM" : "NAO");
        
        scanf(" %[^\n]s", entrada);
    }
}