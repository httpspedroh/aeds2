class TP01Q14
{
    // --------------------------------------------------------------------------------------------------------------- //
      
    public static int firstDigit(String str, int start) 
    { 
        for(int x = start; x != str.length(); x++) if(str.charAt(x) >= '0' && str.charAt(x) <= '9') return x;
        return -1;
    }

    public static int firstDigit(String str) { return firstDigit(str, 0); }

    public static int lastExpression(String str)
    {
        int and = str.lastIndexOf("and"), or = str.lastIndexOf("or"), not = str.lastIndexOf("not");

        if(and > or && and > not) return and;
        else if(or > and && or > not) return or;
        else if(not > or && not > and) return not;
        return -1;
    }

    // --------------------------------------------------------------------------------------------------------------- //
     
    public static void main(String[] args) 
    {
        while(true)
        {
            int qtd_letras = MyIO.readInt();

            if(qtd_letras == 0) break;

            int[] letras = new int[qtd_letras];

            for(int x = 0; x != qtd_letras; x++) letras[x] = MyIO.readInt();

            // ------------------------------ //
            
            String linha = MyIO.readLine();

            if(linha.charAt(linha.length() - 1) == ' ') linha = linha.substring(0, linha.length() - 1);

            for(int x = 0; x != qtd_letras; x++)
            {
                if(x == 0) 
                {
                    linha = linha.replace("not(A)", letras[0] == 0 ? "1" : "0");
                    linha = linha.replace("A", letras[0] == 0 ? "0" : "1");
                }
                else if(x == 1)
                {
                    linha = linha.replace("not(B)", letras[1] == 0 ? "1" : "0");
                    linha = linha.replace("B", letras[1] == 0 ? "0" : "1");
                }
                else if(x == 2) 
                {
                    linha = linha.replace("not(C)", letras[2] == 0 ? "1" : "0");
                    linha = linha.replace("C", letras[2] == 0 ? "0" : "1");
                }
            }

            // ---------------------------------- //

            while(linha.length() > 1)
            {
                int lastExp = lastExpression(linha);
    
                String expressao = linha.substring(lastExp, linha.indexOf(")", lastExp) + 1);

                // ---------------------------------- //

                if(expressao.charAt(0) != 'n') // Se não for not
                {
                    int countParam = 1;

                    for(int x = 0; x != expressao.length(); x++) if(expressao.charAt(x) == ',') countParam++;

                    int[] params = new int[countParam];
                    int posatual = 0;

                    for(int x = 0; x != countParam; x++) // Armazenar os parâmetros
                    {
                        posatual = firstDigit(expressao, posatual);
                        
                        String string_num = expressao.substring(posatual, ++posatual);

                        params[x] = Integer.parseInt(string_num);
                    }

                    // ------- //

                    if(expressao.charAt(0) == 'a') // and
                    {
                        String string_resp = "1";

                        if(countParam == 1) string_resp = String.format("%i", params[0]);
                        else
                        {
                            for(int x = 0; x != countParam; x++) 
                            {
                                if(params[x] == 0) // Se achar algum 0, é 0
                                {
                                    string_resp = "0";
                                    break;
                                }
                            }
                        }

                        linha = linha.replace(expressao, string_resp);
                    }
                    else if(expressao.charAt(0) == 'o') // or
                    {
                        String string_resp = "0";

                        if(countParam == 1) string_resp = String.format("%i", params[0]);
                        else
                        {
                            for(int x = 0; x != countParam; x++) 
                            {
                                if(params[x] == 1) // Se achar algum 1, é 1
                                {
                                    string_resp = "1";
                                    break;
                                }
                            }
                        }

                        linha = linha.replace(expressao, string_resp);
                    }
                }
                else
                {
                    if(expressao.equals("not(0)")) linha = linha.replace("not(0)", "1");
                    else if(expressao.equals("not(1)")) linha = linha.replace("not(1)", "0");
                }
            }

            MyIO.println(linha);
        }
    }
}
