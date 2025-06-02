package tests;

import java.util.Date;
import java.util.Locale;

import org.junit.Test;

import utils.Utility;

public class TestGeneric {
	
	public TestGeneric() {
		// TODO Auto-generated constructor stub
		//Locale.setDefault(new Locale("es"));
	}

	@Test
	public void testDateFormater() {
		String od1 = Utility.dateChangeFormat("14/02/2023", "dd/MM/yyyy", "dd MMMM yyyy");
		System.out.println(od1);
		String od2 = Utility.dateChangeFormat("28/02/2023", "dd/MM/yyyy", "dd MMMM yyyy");
		System.out.println(od2);
	}
	
	@Test
	public void testDateDiference() {
		Date dateI = Utility.dateFromString("14/02/2023", "dd/MM/yyyy");
		Date dateF = Utility.dateFromString("16/02/2023", "dd/MM/yyyy");
		
		long difDays = Utility.dateDiferenceDays(dateI, dateF);
		System.out.println("Diferencia de días: " + difDays);
	}
	
	@Test
	public void testDateAddDays() {
		Date dateI = Utility.dateFromString("14/02/2023", "dd/MM/yyyy");
		Date dateF = Utility.dateAddDays(dateI, 2);
		System.out.println("Fecha modificada: " + dateF.toString());
	}
}
