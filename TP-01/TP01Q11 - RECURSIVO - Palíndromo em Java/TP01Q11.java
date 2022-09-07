class TP01Q11
{
    // --------------------------------------------------------------------------------------------------------------- //
      
    public static boolean isFim(String s) { return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M'); }

    public static boolean isPalindromo(String str, int pos)
    {
        if(pos == str.length()) return true;
        else if(str.charAt(pos) != str.charAt(str.length() - pos - 1)) return false;
        return isPalindromo(str, pos + 1);
    }

    public static boolean isPalindromo(String str) { return isPalindromo(str, 0); };

    // --------------------------------------------------------------------------------------------------------------- //
        
    public static void main(String[] args) 
    {
        String[] linha = new String[1000];

		int numLinhas = 0;

		do linha[numLinhas] = MyIO.readLine();
		while(!isFim(linha[numLinhas++]));

		numLinhas--;

		for(int x = 0; x != numLinhas; x++) MyIO.println(isPalindromo(linha[x]) ? "SIM" : "NAO");
    }
}