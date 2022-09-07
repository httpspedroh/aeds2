import java.io.*;
import java.net.*;
import java.util.Locale;

class TP01Q08
{
    // --------------------------------------------------------------------------------------------------------------- //
      
    public static boolean isFim(String s) { return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M'); }

    public static String getHtml(String endereco)
    {
        URL url;
        InputStream is = null;
        BufferedReader br;
        String resp = "", line;
  
        try 
        {
            url = new URL(endereco);
            is = url.openStream();
            br = new BufferedReader(new InputStreamReader(is));
  
            while ((line = br.readLine()) != null) resp += line + "\n";
        } 
        catch(MalformedURLException mue) 
        {
            mue.printStackTrace();
        } 
        catch(IOException ioe) 
        {
            ioe.printStackTrace();
        } 
  
        try { is.close(); }
        catch(IOException ioe) {}
        return resp;
    }

    public static int count_a(String str)
    {
        int count = 0;

        for(int x = 0; x != str.length(); x++)
        {
            if(str.charAt(x) == 'a') count++;
        }
        return count;
    }

    public static int count_e(String str)
    {
        int count = 0;
        
        for(int x = 0; x != str.length(); x++)
        {
            if(str.charAt(x) == 'e') count++;
        }
        return count;
    }

    public static int count_i(String str)
    {
        int count = 0;
        
        for(int x = 0; x != str.length(); x++)
        {
            if(str.charAt(x) == 'i') count++;
        }
        return count;
    }

    public static int count_o(String str)
    {
        int count = 0;
        
        for(int x = 0; x != str.length(); x++)
        {
            if(str.charAt(x) == 'o') count++;
        }
        return count;
    }

    public static int count_u(String str)
    {
        int count = 0;
        
        for(int x = 0; x != str.length(); x++)
        {
            if(str.charAt(x) == 'u') count++;
        }
        return count;
    }

    public static int count_ac_a(String str)
    {
        int count = 0;

        for(int x = 0; x != str.length(); x++)
        {
            if(str.charAt(x) == 'á') count++;
        }
        return count;
    }

    public static int count_ac_e(String str)
    {
        int count = 0;
        
        for(int x = 0; x != str.length(); x++)
        {
            if(str.charAt(x) == 'é') count++;
        }
        return count;
    }

    public static int count_ac_i(String str)
    {
        int count = 0;
        
        for(int x = 0; x != str.length(); x++)
        {
            if(str.charAt(x) == 'í') count++;
        }
        return count;
    }

    public static int count_ac_o(String str)
    {
        int count = 0;
        
        for(int x = 0; x != str.length(); x++)
        {
            if(str.charAt(x) == 'ó') count++;
        }
        return count;
    }

    public static int count_ac_u(String str)
    {
        int count = 0;
        
        for(int x = 0; x != str.length(); x++)
        {
            if(str.charAt(x) == 'ú') count++;
        }
        return count;
    }

    public static int count_revac_a(String str)
    {
        int count = 0;

        for(int x = 0; x != str.length(); x++)
        {
            if(str.charAt(x) == 'à') count++;
        }
        return count;
    }

    public static int count_revac_e(String str)
    {
        int count = 0;
        
        for(int x = 0; x != str.length(); x++)
        {
            if(str.charAt(x) == 'è') count++;
        }
        return count;
    }

    public static int count_revac_i(String str)
    {
        int count = 0;
        
        for(int x = 0; x != str.length(); x++)
        {
            if(str.charAt(x) == 'ì') count++;
        }
        return count;
    }

    public static int count_revac_o(String str)
    {
        int count = 0;
        
        for(int x = 0; x != str.length(); x++)
        {
            if(str.charAt(x) == 'ò') count++;
        }
        return count;
    }

    public static int count_revac_u(String str)
    {
        int count = 0;
        
        for(int x = 0; x != str.length(); x++)
        {
            if(str.charAt(x) == 'ù') count++;
        }
        return count;
    }

    public static int count_til_a(String str)
    {
        int count = 0;
        
        for(int x = 0; x != str.length(); x++)
        {
            if(str.charAt(x) == 'ã') count++;
        }
        return count;
    }

    public static int count_til_o(String str)
    {
        int count = 0;
        
        for(int x = 0; x != str.length(); x++)
        {
            if(str.charAt(x) == 'õ') count++;
        }
        return count;
    }

    public static int count_circ_a(String str)
    {
        int count = 0;

        for(int x = 0; x != str.length(); x++)
        {
            if(str.charAt(x) == 'â') count++;
        }
        return count;
    }

    public static int count_circ_e(String str)
    {
        int count = 0;
        
        for(int x = 0; x != str.length(); x++)
        {
            if(str.charAt(x) == 'ê') count++;
        }
        return count;
    }

    public static int count_circ_i(String str)
    {
        int count = 0;
        
        for(int x = 0; x != str.length(); x++)
        {
            if(str.charAt(x) == 'î') count++;
        }
        return count;
    }

    public static int count_circ_o(String str)
    {
        int count = 0;
        
        for(int x = 0; x != str.length(); x++)
        {
            if(str.charAt(x) == 'ô') count++;
        }
        return count;
    }

    public static int count_circ_u(String str)
    {
        int count = 0;
        
        for(int x = 0; x != str.length(); x++)
        {
            if(str.charAt(x) == 'û') count++;
        }
        return count;
    }
   
    public static int count_consoantes(String str)
    {
        int count = 0;

        for(int x = 0; x != str.length(); x++)
        {
            char c = str.charAt(x);

            if(c >= 'b' && c <= 'd' || c >= 'f' && c <= 'h' || c >= 'j' && c <= 'n' || c >= 'p' && c <= 't' || c >= 'v' && c <= 'z') count++;
        }
        return count;
    }

    static int count_palavras(String str, String palavra)
    {
        int count = 0;

        for(int x = 0; x != str.length();)
        {
            if(str.indexOf(palavra, x) != -1) 
            {
                count++;
                x = str.indexOf(palavra, x) + palavra.length();
            }
            else break;
        }
        return count;
    }
 
    // --------------------------------------------------------------------------------------------------------------- //
     
    public static void main(String[] args) 
    {
        Locale.setDefault(new Locale("en", "US"));
        MyIO.setCharset("utf-8");

        String[] linha = new String[1000];

		int numLinhas = 0;

		do linha[numLinhas] = MyIO.readLine();
		while(!isFim(linha[numLinhas++]));

		numLinhas--;

        String titulo = new String();

		for(int x = 0; x != numLinhas; x++) 
        {
            if(x % 2 == 0) titulo = linha[x];
            else
            {
                String html;

                html = getHtml(linha[x]);

                int c_consoantes = count_consoantes(html), c_br = count_palavras(html, "<br>"), c_table = count_palavras(html, "<table>");
                int c_a = count_a(html), c_e = count_e(html), c_i = count_i(html), c_o = count_o(html), c_u = count_u(html);
                int c_ac_a = count_ac_a(html), c_ac_e = count_ac_e(html), c_ac_i = count_ac_i(html), c_ac_o = count_ac_o(html), c_ac_u = count_ac_u(html);
                int c_revac_a = count_revac_a(html), c_revac_e = count_revac_e(html), c_revac_i = count_revac_i(html), c_revac_o = count_revac_o(html), c_revac_u = count_revac_u(html);
                int c_til_a = count_til_a(html), c_til_o = count_til_o(html);
                int c_circ_a = count_circ_a(html), c_circ_e = count_circ_e(html), c_circ_i = count_circ_i(html), c_circ_o = count_circ_o(html), c_circ_u = count_circ_u(html);

                for(int y = 0; y != c_br; y++) c_consoantes -= 2;
                for(int y = 0; y != c_table; y++) 
                {
                    c_consoantes -= 3;
                    c_a--;
                    c_e--;
                }

                MyIO.print("a(" + c_a + ") e(" + c_e + ") i(" + c_i + ") o(" + c_o + ") u(" + c_u + ") ");
                MyIO.print("á(" + c_ac_a + ") é(" + c_ac_e + ") í(" + c_ac_i + ") ó(" + c_ac_o + ") ú(" + c_ac_u + ") ");
                MyIO.print("à(" + c_revac_a + ") è(" + c_revac_e + ") ì(" + c_revac_i + ") ò(" + c_revac_o + ") ù(" + c_revac_u + ") ");
                MyIO.print("ã(" + c_til_a + ") õ(" + c_til_o + ") ");
                MyIO.print("â(" + c_circ_a + ") ê(" + c_circ_e + ") î(" + c_circ_i + ") ô(" + c_circ_o + ") û(" + c_circ_u + ") ");
                MyIO.print("consoante(" + c_consoantes + ") <br>(" + c_br + ") <table>(" + c_table + ") ");
                MyIO.print(titulo + "\n");
            }
        }
    }
}
