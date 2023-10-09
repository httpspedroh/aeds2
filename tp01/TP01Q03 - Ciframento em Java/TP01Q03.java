class TP01Q03 
{
    // --------------------------------------------------------------------------------------------------------------- //
      
    public static boolean isFim(String s) { return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M'); }

    public static String codCesar(String origString) {
        
        char[] chars = origString.toCharArray();

        for(int x = 0; x != origString.length(); x++) chars[x] += 3;
        return String.valueOf(chars);
    }

    // --------------------------------------------------------------------------------------------------------------- //
     
    public static void main(String[] args) {

        String[] linha = new String[1000];

		int numLinhas = 0;

		do linha[numLinhas] = MyIO.readLine();
		while(!isFim(linha[numLinhas++]));

		numLinhas--;

		for(int x = 0; x != numLinhas; x++) MyIO.println(codCesar(linha[x]));
    }
}
