package com.algaworks.pedidovenda.infrastructure.teste;


public class TestaRegex2 {
	
	/*
	 * - A denominação deverá conter até 100 caracteres alfanumérico 
	 * [A-Z] [0-9] e os seguintes caracteres especiais 
	 * {ç, ( ), [ ], -, ª, º, _, &, “.”, “,”, “:” } e os seguintes acentos: (^, ´, `, ~).
	 * 
	 * */
	
	// [A-Z] -> 1 Caractere UpperCase

	public static void main(String args[]) {

		// String to be scanned to find the pattern.
		String line = "SSD0(SD2[Ç-ª_~`.";
		String pattern = "[A-Z0-9Ç\\(\\)\\[\\]\\-ªº\\_\\&\\.\\,\\:\\^´`~]*";

		if (line.matches(pattern)) {
			System.out.println("Linha corresponde ao padrao");

		} else {
			System.out.println("NÃO corresponde ao padrao");	
		}
	}
}
