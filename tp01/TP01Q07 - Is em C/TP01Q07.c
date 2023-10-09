#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

// --------------------------------------------------------------------------------------------------------------- //

bool isFim(char *string) { return !strcmp(string, "FIM"); }
char toUpper(char c) { return (c >= 'a' && c <= 'z') ? (char)(c - 32) : c; }

bool isVogal(char c)
{
    c = toUpper(c);
    return (c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U');
}

bool isConsoante(char c)
{
    c = toUpper(c);
    return (c >= 'B' && c <= 'D' || c >= 'F' && c <= 'H' || c >= 'J' && c <= 'N' || c >= 'P' && c <= 'T' || c >= 'V' && c <= 'Z');
}

// --------------------------------------------------------------------------------------------------------------- //

bool isSomenteVogais(char *str)
{
    for(int x = 0; x != strlen(str); x++) if(!isVogal(str[x])) return false;
    return true;
}

bool isSomenteConsoantes(char *str)
{
    for(int x = 0; x != strlen(str); x++) if(!isConsoante(str[x])) return false;
    return true;
}

bool isInteger(char *str) 
{
    int length = strlen(str);

    if(!length) return false;
    
    int tmp = 0;

    if(str[0] == '-') 
    {
        if(length == 1) return false;
        tmp = 1;
    }

    for(int i = tmp; i < length; i++) 
    {
        if(str[i] < '0' || str[i] > '9') return false;
    }
    return true;
}

bool isReal(char *str) 
{
    int length = strlen(str);

    if(!length) return false;
    
    int tmp = 0;

    if(str[0] == '-') 
    {
        if(length == 1) return false;
        tmp = 1;
    }

    int count_dots = 0;

    for(int i = tmp; i < length; i++) 
    {
        char c = str[i];

        if(c != ',' && c != '.' && (c < '0' || c > '9')) return false;
        if(c == ',' || c == '.') count_dots++;
        if(count_dots > 1) return false;
    }
    return true;
}

// --------------------------------------------------------------------------------------------------------------- //

int main()
{
    char entrada[1000];

    scanf(" %[^\n]s", entrada);

    while(!isFim(entrada))
    {
        printf("%s %s %s %s\n", isSomenteVogais(entrada) ? "SIM" : "NAO", isSomenteConsoantes(entrada) ? "SIM" : "NAO", isInteger(entrada) ? "SIM" : "NAO", isReal(entrada) ? "SIM" : "NAO");

        scanf(" %[^\n]s", entrada);
    }
}