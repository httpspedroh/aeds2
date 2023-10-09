class TP01Q15
{
    // --------------------------------------------------------------------------------------------------------------- //
      
    public static boolean isFim(String s) { return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M'); }

    public static char toUpper(char c) { return (c >= 'a' && c <= 'z') ? (char)(c - 32) : c; }
    public static boolean isVogal(char c)
    {
        c = toUpper(c);
        return (c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U');
    }
    public static boolean isConsoante(char c)
    {
        c = toUpper(c);
        return (c >= 'B' && c <= 'D' || c >= 'F' && c <= 'H' || c >= 'J' && c <= 'N' || c >= 'P' && c <= 'T' || c >= 'V' && c <= 'Z');
    }

    // --------------------------------------------------------------------------------------------------------------- //
      
    public static boolean isSomenteVogais(String str, int pos)
    {
        if(pos == str.length()) return true;
        else if(!isVogal(str.charAt(pos))) return false;
        return isSomenteVogais(str, ++pos);
    }

    public static boolean isSomenteVogais(String str) { return isSomenteVogais(str, 0); }

    public static boolean isSomenteConsoantes(String str, int pos)
    {
        if(pos == str.length()) return true;
        else if(!isConsoante(str.charAt(pos))) return false;
        return isSomenteConsoantes(str, ++pos);
    }

    public static boolean isSomenteConsoantes(String str) { return isSomenteConsoantes(str, 0); }

    public static boolean isInteger(String str, int pos) 
    {
        int length = str.length();

        if(pos == str.length()) return true;
        else if(length == 0 || str == null) return false;
        else if(str.charAt(0) == '-' && length == 1) return false;
        else if(str.charAt(pos) < '0' || str.charAt(pos) > '9') return false;
        return isInteger(str, ++pos);
    }

    public static boolean isInteger(String str) { return isInteger(str, 0); }

    public static boolean isReal(String str, int pos, int count_dots) 
    {
        int length = str.length();

        if(pos == str.length()) return true;
        else if(length == 0 || str == null) return false;
        else if(count_dots > 1) return false;
        else if(str.charAt(0) == '-' && length == 1) return false;
        else
        {
            char c = str.charAt(pos);

            if(c != ',' && c != '.' && (c < '0' || c > '9')) return false;
            if(c == ',' || c == '.') count_dots++;
        }
        return isReal(str, ++pos, count_dots);
    }

    public static boolean isReal(String str) { return isReal(str, 0, 0); }

    // --------------------------------------------------------------------------------------------------------------- //
     
    public static void main(String[] args) 
    {
        String[] linha = new String[1000];

		int numLinhas = 0;

		do linha[numLinhas] = MyIO.readLine();
		while(!isFim(linha[numLinhas++]));

		numLinhas--;

		for(int x = 0; x != numLinhas; x++) 
        {
            MyIO.print((isSomenteVogais(linha[x]) ? "SIM" : "NAO") + " ");
            MyIO.print((isSomenteConsoantes(linha[x]) ? "SIM" : "NAO") + " ");
            MyIO.print((isInteger(linha[x]) ? "SIM" : "NAO") + " ");
            MyIO.print((isReal(linha[x]) ? "SIM" : "NAO") + "\n");
        }
    }
}
