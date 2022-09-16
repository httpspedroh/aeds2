import java.util.Scanner;

class TP01Q1 {

	public static void main(String[] args) {

		Scanner scr = new Scanner (System.in);
		boolean[] check = new boolean[1000];
		
		int a = 0;
		int i = 0;
		
		do {
		String str1 = "", str2 = ""; //str1 = digitada / str2 = reversa
		
		str1 = scr.nextLine(); //lê a palavra/frase digitada

		if (str1.equals("FIM")) {
			a = 1;
		}
		
		int strLength = str1.length();

		for (int y = (strLength - 1); y>=0; --y) {
			str2 = str2 + str1.charAt(y);
		} //coloca a palavra/frase reversa na str2
		

		if (str1.equals(str2) && a != 1) {   //compara
			check[i] = true;
			i++;
		} else if (a != 1) {
			check[i] = false;
			i++;
		}
		} while (a != 1);

		for (int x = 0; x < i; x++) {
			if (check[x] == true) {
				System.out.println("SIM");
			} else {
				System.out.println("NÃO");
			}
		}

		scr.close();
	}
}