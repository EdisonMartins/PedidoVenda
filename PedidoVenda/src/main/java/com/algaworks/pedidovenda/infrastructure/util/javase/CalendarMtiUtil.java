package com.algaworks.pedidovenda.infrastructure.util.javase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Classe responsavel por fornecer facilidade de manipulação com o tipo
 * Calendar.
 * 
 * @author Edison Gonçalves Martins Júnior | martinsegmj
 * @version 1.0 - 01/08/2015
 * @since 01/08/2013
 */

public class CalendarMtiUtil {
	private static final SimpleDateFormat SDF = new SimpleDateFormat(
			"dd/MM/yyyy");
	private static final SimpleDateFormat DATA_HORA_MINUTO_FORMAT = new SimpleDateFormat(
			"dd/MM/yyyy HH:mm");
	
	
	private static final SimpleDateFormat ANO_MES_DATA_HORA_MINUTO_FORMAT = new SimpleDateFormat(
			"dd MMM yy HHmm");
	
	
	private static final SimpleDateFormat ANO_MES_DIA_HORA_MINUTO_FORMAT = new SimpleDateFormat(
			"yyMMddHHmm");

	private static final SimpleDateFormat DIA_MES_ANO_HORA_MINUTO_SEGUNDO_FORMAT = new SimpleDateFormat(
			"dd/MM/yy HH:mm:ss");


	/**
	 * Pega o ano vigente em String.
	 * 
	 * @return ano
	 */
	public static String getAnoAtualString() {
		Calendar anoCalendar = Calendar.getInstance();
		int ano = anoCalendar.get(Calendar.YEAR);
		String anoAtual = "" + ano;
		return anoAtual;
	}

	/**
	 * Pega data atual no formato dd/MM/yyyy
	 * 
	 * @return data atual
	 */
	public static String getDataAtualString() {
		Date data = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String dataAtual = sdf.format(data);
		return dataAtual;
	}

	/**
	 * Pega data atual no formato dd/MM/yyyy HH:mm
	 * 
	 * @return data atual
	 */
	public static String getDataAtualComDataEtHora() {
		Date data = new Date(System.currentTimeMillis());
		return DATA_HORA_MINUTO_FORMAT.format(data.getTime());
	}

	/**
	 * Pega a data atual do tipo Calendar
	 * 
	 * @return data atual
	 */
	public static Calendar getDataAtualCalendar() {
		Date data = new Date(System.currentTimeMillis());
		Calendar dataCalendar = Calendar.getInstance();
		dataCalendar.setTime(data);
		return dataCalendar;
	}

	/**
	 * Pega uma data a tantos dias atrás da data atual. Ex.: Se a data atual for
	 * 19/03/2014 e o argumento for 2 a data retornada será 17/03/2014
	 * 
	 * @param haDiasAtras
	 * @return data atual com tantos dias atrás
	 */
	public static Calendar getDataCalendarHaDiasAtras(int haDiasAtras) {
		Calendar dataAtras = CalendarMtiUtil.getDataAtualCalendar();

		dataAtras.add(Calendar.DAY_OF_MONTH, -haDiasAtras);

		return dataAtras;
	}

	/**
	 * Formata a data para dd/MM/yyyy
	 * 
	 * @param data
	 * @return data formatada
	 */
	public static String formataData(Calendar data) {
		if (data == null) {
			return "null";
		} else {
			return SDF.format(data.getTime());

		}
	}

	/**
	 * Formata a data para dd/MM/yyyy HH:mm"
	 * 
	 * @param data
	 * @return data formatada
	 */
	public static String formataDataComDataEtHora(Calendar data) {
		if (data == null) {
			return "null";
		} else {
			return DATA_HORA_MINUTO_FORMAT.format(data.getTime());

		}
	}

	/**
	 * Formata a data para dd/MM/yy HH:mm:ss
	 * 
	 * @param data
	 * @return data formatada
	 */
	public static String formataDataParaExpedicao(Calendar data) {
		if (data == null) {
			return "null";
		} else {
			return DIA_MES_ANO_HORA_MINUTO_SEGUNDO_FORMAT.format(data.getTime());

		}
	}

	/**
	 * Formata a data para yyMMddHHmm.
	 * 
	 * @param data
	 * @return data formatada
	 */
	public static String formataDataComDataEtHoraParaValidade(Calendar data) {
		return ANO_MES_DIA_HORA_MINUTO_FORMAT.format(data.getTime());
	}
	
	
	
	/**
	 * Formata a data para yyMMMddHHmm.
	 * 
	 * @param data
	 * @return data formatada
	 */
	public static String formataDataComAnoMesDataEtHoraParaValidade(Calendar data) {
		return ANO_MES_DATA_HORA_MINUTO_FORMAT.format(data.getTime());
	}

	
	
	
	
	
	

	/**
	 * Formata a data para dd/MM/yy HH:mm.
	 * 
	 * @param data
	 * @return data formatada
	 */
	public static String formataDataComDataMesAnoHoraEtMinuto(Calendar data) {
		return DATA_HORA_MINUTO_FORMAT.format(data.getTime());
	}



	
	/**
	 * Calcular a diferença de uma data pela outra.  Não leva em consideração as horas.
	 * @param dataMaior
	 * @param dataMenor
	 * @return número de dias - int
	 */
	public static int calculeAtDiferencaDeDias(Calendar dataMaior,
			Calendar dataMenor) {
		
		
		Calendar calMaior = Calendar.getInstance();
		calMaior.setTime(dataMaior.getTime());
		
		Calendar calMenor = Calendar.getInstance();
		calMenor.setTime(dataMenor.getTime());
		
		
		calMaior.set(calMaior.get(Calendar.YEAR), calMaior.get(Calendar.MONTH), calMaior.get(Calendar.DAY_OF_MONTH), 0, 1);
		calMenor.set(calMenor.get(Calendar.YEAR), calMenor.get(Calendar.MONTH), calMenor.get(Calendar.DAY_OF_MONTH), 0, 0);
		
		
		
		long diferenca = calMaior.getTimeInMillis()
				- calMenor.getTimeInMillis();
		long diasDireto = (diferenca / 86400000L);

		return (int) diasDireto;

	}

	
	/**
	 * Pega o dia da última segunda-feira de acordo com a data passada
	 * @param data
	 * @return a data
	 */
	public static Calendar getUltimaSegundaFeira(Calendar data) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(data.getTime());

		if (cal.get(Calendar.DAY_OF_WEEK) == 2) {
			cal.add(Calendar.DAY_OF_MONTH, -7);

			return cal;
		}

		while (cal.get(Calendar.DAY_OF_WEEK) != 2) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}

		return cal;

	}

	
	/**
	 * Pega o dia da última quinta-feira de acordo com a data passada
	 * @param data
	 * @return a data
	 */
	public static Calendar getUltimaQuintaFeira(Calendar data) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(data.getTime());

		if (cal.get(Calendar.DAY_OF_WEEK) == 5) {
			cal.add(Calendar.DAY_OF_MONTH, -7);

			return cal;
		}

		while (cal.get(Calendar.DAY_OF_WEEK) != 5) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}

		return cal;

	}

}
