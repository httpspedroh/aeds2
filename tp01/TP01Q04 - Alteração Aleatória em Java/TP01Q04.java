import java.util.Random;

class TP01Q04
{
    // --------------------------------------------------------------------------------------------------------------- //
      
    public static boolean isFim(String s) { return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M'); }

    public static String alteracaoAleatoria(String origString, char letraOrig, char letraSubs)
    {
        char[] chars = origString.toCharArray();
        for(int x = 0; x != origString.length(); x++) if(chars[x] == letraOrig) chars[x] = letraSubs;
        return String.valueOf(chars);
    }

    // --------------------------------------------------------------------------------------------------------------- //
     
    public static void main(String[] args) 
    {
        String[] linha = new String[1000];

		int numLinhas = 0;

		do linha[numLinhas] = MyIO.readLine();
		while(!isFim(linha[numLinhas++]));

		numLinhas--;

        Random gerador = new Random();
        gerador.setSeed(4);

        for(int x = 0; x != numLinhas; x++)
        {
            char st_letra = (char)('a' + Math.abs(gerador.nextInt()) % 26);
            char nd_letra = (char)('a' + Math.abs(gerador.nextInt()) % 26);

            MyIO.println(alteracaoAleatoria(linha[x], st_letra, nd_letra));
        }
    }
}
