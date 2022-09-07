class TP01Q13
{
    // --------------------------------------------------------------------------------------------------------------- //
      
    public static boolean isFim(String s) { return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M'); }

    public static String codCesar(String str, int x) 
    {
        char c = str.charAt(0);

        StringBuffer output = new StringBuffer();
        
        c += 3;
        output.append(c);
    
        if(str.length() > 1) output.append(codCesar(str.substring(1), ++x));
    
        return output.toString();
    }

    public static String codCesar(String str) { return codCesar(str, 0); }

    // --------------------------------------------------------------------------------------------------------------- //
     
    public static void main(String[] args) 
    {
        String[] linha = new String[1000];

		int numLinhas = 0;

		do linha[numLinhas] = MyIO.readLine();
		while(!isFim(linha[numLinhas++]));

		numLinhas--;

		for(int x = 0; x != numLinhas; x++) MyIO.println(codCesar(linha[x]));
    }
}
