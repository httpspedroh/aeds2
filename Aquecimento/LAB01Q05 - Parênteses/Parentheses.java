class Parentheses {

    public static boolean isFim(String s) { return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M'); }

    // -------------------------------------------------------------------------------------- //

    public static void main(String[] args) {

        long t_begin = System.currentTimeMillis();

        String input;

        while(true) {

            // Read input
            input = MyIO.readLine();

            // Identify END
            if(isFim(input)) break;

            // ------------------------------------ //
            
            int abertos = 0;

            for(int i = 0; i < input.length(); i++) {

                if(input.charAt(i) == ')') {

                    if(abertos == 0) {

                        abertos = -1;
                        break;
                    }
                    else abertos--;
                }
                else if(input.charAt(i) == '(') abertos++;
            }

            MyIO.println((abertos < 0 || abertos > 0) ? "incorreto" : "correto");
        }

        MyIO.println("Tempo de execucao: " + (System.currentTimeMillis() - t_begin) + "ms");
    }
}