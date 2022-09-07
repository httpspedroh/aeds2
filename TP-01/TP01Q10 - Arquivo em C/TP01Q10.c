#include <stdio.h>
#include <string.h>
#include <stdbool.h>

// --------------------------------------------------------------------------------------------------------------- //
      
int main() 
{
    int numLinhas;
    scanf("%i", &numLinhas);

    FILE *fp;

    fp = fopen("input.txt", "w+");

    for(int x = 0; x != numLinhas; x++) 
    {
        float flt;
        scanf("%f", &flt);

        if(x != numLinhas - 1) fprintf(fp, "%g\n", flt);
        else fprintf(fp, "%g", flt);
    }

    // ------------------------- //

    fseek(fp, 0, SEEK_END);

    int pos = ftell(fp);

    for(; pos > 0; pos--)
    {
        float flt;

        if(fgetc(fp) == '\n')
        {
            fseek(fp, pos + 2, SEEK_SET);

            fscanf(fp, "%f", &flt);
            printf("%g\n", flt);
        }
        else if(pos == 1) 
        {
            fseek(fp, pos - 1, SEEK_SET);

            fscanf(fp, "%f", &flt);
            printf("%g\n", flt);
        }

        fseek(fp, pos, SEEK_SET);
    }

    fclose(fp);
    return 0;
}