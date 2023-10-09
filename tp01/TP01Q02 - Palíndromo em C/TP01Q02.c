#include <stdio.h>
#include <stdbool.h>
#include <string.h>

// --------------------------------------------------------------------------------------------------- //

bool isFim(char *s) { return !strcmp(s, "FIM"); }
bool isPalindromo(char *s) {
    
    for(int x = 0; x != (strlen(s) / 2) + 1; x++) if(s[x] != s[strlen(s) - x - 1]) return false;
    return true;
}

// --------------------------------------------------------------------------------------------------- //

int main() {

    char entrada[1000];

    scanf(" %[^\n]s", entrada);

    while(!isFim(entrada)) {

        printf("%s\n", isPalindromo(entrada) ? "SIM" : "NAO");
        
        scanf(" %[^\n]s", entrada);
    }
}