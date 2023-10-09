class TP01Q06
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
      
    public static boolean isSomenteVogais(String str)
    {
        for(int x = 0; x != str.length(); x++) if(!isVogal(str.charAt(x))) return false;
        return true;
    }

    public static boolean isSomenteConsoantes(String str)
    {
        for(int x = 0; x != str.length(); x++) if(!isConsoante(str.charAt(x))) return false;
        return true;
    }

    public static boolean isInteger(String str) 
    {
        int length = str.length();

        if(length == 0 || str == null) return false;
        
        int tmp = 0;

        if(str.charAt(0) == '-') 
        {
            if(length == 1) return false;
            tmp = 1;
        }

        for(int i = tmp; i < length; i++) 
        {
            if(str.charAt(i) < '0' || str.charAt(i) > '9') return false;
        }
        return true;
    }

    public static boolean isReal(String str) 
    {
        int length = str.length();

        if(length == 0 || str == null) return false;
        
        int tmp = 0;

        if(str.charAt(0) == '-') 
        {
            if(length == 1) return false;
            tmp = 1;
        }

        int count_dots = 0;

        for(int i = tmp; i < length; i++) 
        {
            char c = str.charAt(i);

            if(c != ',' && c != '.' && (c < '0' || c > '9')) return false;
            if(c == ',' || c == '.') count_dots++;
            if(count_dots > 1) return false;
        }
        return true;
    }

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
