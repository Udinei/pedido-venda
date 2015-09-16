package com.algawork.pedidovenda.util;

import java.text.ParseException;

import javax.swing.text.MaskFormatter;

public class FormatadorUtil {

	// CEP - resultado: 81580-200
	public final String CEP = "#####-###";
	
	// CPF - resultado 012.345.699-01
	public final String CPF = "###.###.###-##";
	
	// CNPJ - resultado: 01.234.569/9052-34
	public final String CNPJ = "##.###.###/####-##";
	
	
	public static String formatarString(String texto, String mascara) throws ParseException {
	        MaskFormatter mf = new MaskFormatter(mascara);
	        mf.setValueContainsLiteralCharacters(false);
	        return mf.valueToString(texto);
	}

}
