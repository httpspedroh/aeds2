import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.util.Locale;

class TP01Q09
{
    // --------------------------------------------------------------------------------------------------------------- //
      
    public static boolean isFim(String s) { return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M'); }

    // --------------------------------------------------------------------------------------------------------------- //
     
    public static void main(String[] args) throws Exception {
    {
        Locale.setDefault(new Locale("en", "US"));

        // ---------------------- //

        int numLinhas = MyIO.readInt();

        Arq.openWrite("input.txt");

        for(int x = 0; x != numLinhas; x++) 
        {
            if(x != numLinhas - 1) Arq.println(MyIO.readLine());
            else Arq.print(MyIO.readLine());
        }

        Arq.close();

        // ---------- //

        RandomAccessFile raf = new RandomAccessFile("input.txt", "r");

        long pos = raf.length();
        
        raf.seek(pos);

        for(; pos > 0; pos--)
        {
            float f;

            if(raf.read() == 10) // "\n"
            {
                raf.seek(pos + 2);

                f = Float.parseFloat(raf.readLine()); 

                MyIO.println(new DecimalFormat().format(f));
            }
            else if(pos == 1)
            {
                raf.seek(pos - 1);

                f = Float.parseFloat(raf.readLine()); 

                MyIO.println(new DecimalFormat().format(f));
            }

            raf.seek(pos);
        }
    }
}
}
