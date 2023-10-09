// -------------------------------------------------------------------------------- //

// Includes
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <time.h>

// -------------------------------------------------------------------------------- //

// Definitions
#define MAX_MOVIES          100
#define MAX_FIELD_SIZE      100
#define MAX_KEYWORDS        50
#define MAX_LINE_SIZE       250
#define FDR_PREFIX          "/tmp/filmes/"

// -------------------------------------------------------------------------------- //

// Structs
typedef struct {

    int year,
    month,
    day;
} Date; 

typedef struct {

    char name[MAX_FIELD_SIZE],
        original_title[MAX_FIELD_SIZE],
        genre[MAX_FIELD_SIZE], 
        original_language[MAX_FIELD_SIZE], 
        situation[MAX_FIELD_SIZE],
        keywords[MAX_KEYWORDS][MAX_FIELD_SIZE];

    Date release_date;
    int duration, count_keywords;
    float budget;
} Movie;

// -------------------------------------------------------------------------------- //

// Global variables
Movie movies[MAX_MOVIES];
int n = 0;

// -------------------------------------------------------------------------------- //

// Functions
bool isFim(char *str) { return str[0] == 'F' && str[1] == 'I' && str[2] == 'M'; }

char *remove_line_break(char *line) {

    while (*line != '\r' && *line != '\n') line++;
    *line = '\0';
    return line;
}

char *freadline(char *line, int max_size, FILE *file) { return remove_line_break(fgets(line, max_size, file)); }
char *readline(char *line, int max_size) { return freadline(line, max_size, stdin); }

long int indexOf(char *str, char *search) {

    long int pos = strcspn(str, search);
    return pos == strlen(str) ? -1 : pos;
}

char *substring(char *string, int position, int length) {

    char *p;
    int c;
    
    p = malloc(length+1);
    
    if(p == NULL) {

        printf("Unable to allocate memory.\n");
        exit(1);
    }
    
    for(c = 0; c < length; c++) {

        *(p+c) = *(string+position-1);      
        string++;  
    }
    
    *(p+c) = '\0';
    return p;
}

void str_replace(char *target, const char *needle, const char *replacement) {

    char buffer[1024] = {0};
    char *insert_point = &buffer[0];

    const char *tmp = target;

    size_t needle_len = strlen(needle);
    size_t repl_len = strlen(replacement);

    while(1) {

        const char *p = strstr(tmp, needle);

        if(p == NULL) {

            strcpy(insert_point, tmp);
            break;
        }

        memcpy(insert_point, tmp, p - tmp);
        insert_point += p - tmp;

        memcpy(insert_point, replacement, repl_len);
        insert_point += repl_len;

        tmp = p + needle_len;
    }

    strcpy(target, buffer);
}

int firstDigit(const char *str, int start) {
        
    for(int i = start; i != strlen(str); i++) if(str[i] >= '0' && str[i] <= '9') return i;
    return -1;
}

char *extractOnlyText(char *html, char *text) 
{
    char *start = text;
    int contagem = 0;

    while(*html != '\0') {

        if(*html == '<') {

            if(
                (*(html + 1) == 'p') ||
                (*(html + 1) == 'b' && *(html + 2) == 'r') ||
                (*(html + 1) == '/' && *(html + 2) == 'h' && *(html + 3) == '1') ||
                (*(html + 1) == '/' && *(html + 2) == 't' && *(html + 3) == 'h') ||
                (*(html + 1) == '/' && *(html + 2) == 't' && *(html + 3) == 'd')
            ) break;
            else contagem++;
        }
        else if(*html == '>') contagem--;
        else if(contagem == 0 && *html != '"') {

            if(*html == '&') html = strchr(html, ';');
            else if(*html != '\r' && *html != '\n') *text++ = *html;
        }
        html++;
    }

    *text = '\0';
    return *start == ' ' ? start + 1 : start;
}

void swap(int i, int j) {

    Movie temp = movies[i];
    movies[i] = movies[j];
    movies[j] = temp;
}

// -------------------------------------------------------------------------------- //

// Class movie functions
void movie_start(Movie *movie) {

    strcpy(movie -> name, "");
    strcpy(movie -> original_title, "");
    strcpy(movie -> genre, "");
    strcpy(movie -> original_language, "");
    strcpy(movie -> situation, "");

    movie -> release_date.day = 0;
    movie -> release_date.month = 0;
    movie -> release_date.year = 0;
    movie -> duration = 0;
    movie -> count_keywords = 0;
    movie -> budget = 0;

    for(int i = 0; i < MAX_KEYWORDS; i++) strcpy(movie -> keywords[i], "");
}

void movie_print(Movie *movie) {
    
    printf("%s %s %02i/%02i/%04i %i %s %s %s %g [",
    movie -> name,
    movie -> original_title,
    movie -> release_date.day, movie -> release_date.month, movie -> release_date.year,
    movie -> duration,
    movie -> genre,
    movie -> original_language,
    movie -> situation,
    movie -> budget);

    for(int i = 0; i < movie -> count_keywords; i++) {
        
        if(i == movie -> count_keywords - 1) printf("%s]\n", movie -> keywords[i]);
        else printf("%s, ", movie -> keywords[i]);
    }

    if(movie -> count_keywords == 0) printf("]\n");
}

Movie movie_clone(Movie *movie) {
    
    Movie cloned;

    strcpy(cloned.name, movie -> name);
    strcpy(cloned.original_title, movie -> original_title);
    strcpy(cloned.genre, movie -> genre);
    strcpy(cloned.original_language, movie -> original_language);
    strcpy(cloned.situation, movie -> situation);

    cloned.release_date.day = movie -> release_date.day;
    cloned.release_date.month = movie -> release_date.month;
    cloned.release_date.year = movie -> release_date.year;
    cloned.duration = movie -> duration;
    cloned.budget = movie -> budget;
    cloned.count_keywords = movie -> count_keywords;

    for(int i = 0; i < movie -> count_keywords; i++) strcpy(cloned.keywords[i], movie -> keywords[i]);
    return cloned;
}

char *movie_getOriginalTitle(Movie *movie) { return movie -> original_title; }

void movie_readHtml(Movie *movie, char *filename) {

    FILE *html_file;
    char *line_html = NULL;
    char fullpath[MAX_LINE_SIZE];
    size_t len = 0;
    ssize_t read;

    strcpy(fullpath, FDR_PREFIX);
    strcat(fullpath, filename);

    html_file = fopen(fullpath, "r");

    if(html_file == NULL) exit(EXIT_FAILURE);

    // ------------------------------------ //

    // Creating movie variables
    char *name = NULL, 
    *original_title = NULL,
    *genre = NULL,
    *original_language = NULL,
    *situation = NULL,
    *keywords = NULL;

    Date release_date;

    release_date.day = 0;

    int duration = -1;
    float budget = -1;

    // ------------------------------------ //
    
    // Read HTML line by line
    while((read = getline(&line_html, &len, html_file)) != -1) {

        // --------------------------- //

        // Find movie name
        if(name == NULL) {

            if(strstr(line_html, "<title>") != NULL) {
                    
                name = strstr(line_html, "<title>") + 7;

                strcpy(movie -> name, name);
                str_replace(movie -> name, "&#8212;", "—");

                movie -> name[strlen(movie -> name) - 46] = '\0';
            }
        }
       
        // --------------------------- //

        // Find movie original title
        if(original_title == NULL) {
            
            if(strstr(line_html, "<p class=\"wrap\">") != NULL) {
                
                original_title = strstr(line_html, "</strong> ") + 10;
                original_title[strlen(original_title) - 5] = '\0';

                strcpy(movie -> original_title, original_title);
            }
        }

        // --------------------------- //

        // Find movie release date
        if(release_date.day == 0) {
            
            if(strstr(line_html, "<span class=\"release\">") != NULL) {
                
                // Skip one line
                read = getline(&line_html, &len, html_file);

                char *day, *month, *year;

                day = substring(line_html, 9, 2);
                month = substring(line_html, 12, 2);
                year = substring(line_html, 15, 4);

                movie -> release_date.day = atoi(day);
                movie -> release_date.month = atoi(month);
                movie -> release_date.year = atoi(year);
            }
        }

        // --------------------------- //

        // Find movie duration
        if(duration == -1) {
            
            if(strstr(line_html, "<span class=\"runtime\">") != NULL) {
                
                // Skip two lines
                read = getline(&line_html, &len, html_file);
                read = getline(&line_html, &len, html_file);

                int h_pos = indexOf(line_html, "h"),
                    hours = 0,
                    minutes = 0;

                if(h_pos != -1) hours = atoi(substring(line_html, firstDigit(line_html, 0), h_pos));
                
                minutes = atoi(substring(line_html, firstDigit(line_html, h_pos == -1 ? 0 : h_pos), strlen(line_html) - 1));
                duration = (hours * 60) + minutes;

                movie -> duration = duration;
            }
        }

        // --------------------------- //

        // Find movie genres
        if(genre == NULL) {

            if(strstr(line_html, "<span class=\"genres\">") != NULL) {
                
                // Skip two lines
                read = getline(&line_html, &len, html_file);
                read = getline(&line_html, &len, html_file);
 
                extractOnlyText(line_html, movie -> genre);

                genre = substring(movie -> genre, 7, strlen(movie -> genre));

                strcpy(movie -> genre, genre);
            }
        }

        // --------------------------- //

        // Find movie original language
        if(original_language == NULL) {

            if(strstr(line_html, "<bdi>Idioma original</bdi>") != NULL) {
                
                strcpy(movie -> original_language, line_html);

                original_language = substring(movie -> original_language, 50, strlen(line_html) - 54);

                strcpy(movie -> original_language, original_language);
            }
        }

        // --------------------------- //

        // Find movie situation
        if(situation == NULL) {

            if(strstr(line_html, "<bdi>Situação</bdi>") != NULL) {
                
                strcpy(movie -> situation, line_html);
                
                situation = substring(movie -> situation, 44, strlen(line_html) - 44);

                strcpy(movie -> situation, situation);
            }
        }

        // --------------------------- //

        // Find movie budget
        if(budget == -1) {

            if(strstr(line_html, "<bdi>Orçamento</bdi>") != NULL) {
                
                char *p_budget, e_budget[strlen(line_html)];

                strcpy(e_budget, line_html);

                p_budget = substring(e_budget, 45, strlen(line_html) - 49);

                if(!strcmp(p_budget, "-")) movie -> budget = 0;
                else
                {
                    strcpy(e_budget, p_budget);

                    str_replace(e_budget, "$", "");
                    str_replace(e_budget, ",", "");
 
                    movie -> budget = atof(e_budget);
                }
            }
        }

        // --------------------------- //

        // Find movie keywords
        if(keywords == NULL) {

            if(strstr(line_html, "<h4><bdi>Palavras-chave</bdi></h4>") != NULL) {
                
                // Skip two lines until keywords starts
                for(int i = 0; i < 2; i++) read = getline(&line_html, &len, html_file);

                char tmp_line[strlen(line_html)];

                strcpy(tmp_line, line_html);
                
                keywords = substring(tmp_line, 5, strlen(line_html) - 5);

                if(strcmp(keywords, "<p><bdi>Nenhuma palavra-chave foi adicionada.</bdi></p>")) {

                    // Skip more two lines until keywords starts
                    for(int x = 0; x < 2; x++) read = getline(&line_html, &len, html_file);

                    while(true) {
                        
                        if(strstr(line_html, "</ul>") != NULL) break;

                        if(strstr(line_html, "<li>") != NULL) {
                            
                            extractOnlyText(line_html, tmp_line);

                            keywords = substring(tmp_line, 9, strlen(line_html) - 8);

                            strcpy(movie -> keywords[movie -> count_keywords++], keywords);
                        }

                        read = getline(&line_html, &len, html_file);
                    }
                }
            }
        }

        // ------------------------------------ //
            
        // Verify variables still "null"
        if(original_title == NULL) strcpy(movie -> original_title, movie -> name);
    }

    // ------------------------------------ //
    
    fclose(html_file);

    if(line_html) free(line_html);
}

// -------------------------------------------------------------------------------- //

// Functions - List
void list_insertEnd(Movie x) {

    if(n >= MAX_MOVIES) {

        printf("Insert error: MAX_MOVIES reached!");
        exit(1);
    } 

    movies[n++] = x;
}

void list_print() {

    for(int i = 0; i < n; i++) movie_print(&movies[i]);
}

// Initialize bench log
int c_compares = 0;
    
int list_searchSmaller(int j) {

    int sm = -1;

    if(j == n - 1) sm = j;
    else {
        
        sm = list_searchSmaller(j + 1);

        c_compares++;
        
        if(strcmp(movie_getOriginalTitle(&movies[sm]), movie_getOriginalTitle(&movies[j])) > 0) sm = j;
    }
    return sm;
}

void list_sort() {

    clock_t t_begin = clock();

    for(int i = 0; i < n; i++) {

        int sm = list_searchSmaller(i);

        swap(i, sm);
    }

    FILE *fptr;

    fptr = fopen("753045_selecaoRecursiva.txt", "w+");

    fprintf(fptr, "753045\t%lims\t%i", (clock() - t_begin), c_compares);
    fclose(fptr);
}

// -------------------------------------------------------------------------------- //

int main() {

    char line[MAX_LINE_SIZE];

    readline(line, MAX_LINE_SIZE);

    while(!isFim(line)) {

        Movie c_movie;

        movie_start(&c_movie);
        movie_readHtml(&c_movie, line);
        list_insertEnd(c_movie);

        // ----------- //

        readline(line, MAX_LINE_SIZE);
    }

    // ------------------------------------------------------------- //

    list_sort();
    list_print();

    return EXIT_SUCCESS;
}