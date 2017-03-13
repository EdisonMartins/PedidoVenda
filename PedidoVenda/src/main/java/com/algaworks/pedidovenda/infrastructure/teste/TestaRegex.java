package com.algaworks.pedidovenda.infrastructure.teste;


public class TestaRegex {

	public static void main(String args[]) {

		// String to be scanned to find the pattern.
		String line = "SBBR F1000/10";
		String pattern = "(SB[CSBRE]{1}[TPRFGJ]{1}.[EDFBGZ]{1}\\d{4}/\\d{2})";

		if (line.matches(pattern)) {
			System.out.println("Linha corresponde ao padrao");

		} else {
			System.out.println("Linha nao corresponde ao padrao");	
		}
	}
}
