package tests;

import java.util.Date;
import org.junit.Test;

import utils.Utils;

public class TestGeneric {

	public TestGeneric() {
		// Locale.setDefault(new Locale("es"));
	}

	@Test
	public void testDateFormater() {
		String od1 = Utils.dateChangeFormat("14/02/2023", "dd/MM/yyyy", "dd MMMM yyyy");
		System.out.println(od1);
		String od2 = Utils.dateChangeFormat("28/02/2023", "dd/MM/yyyy", "dd MMMM yyyy");
		System.out.println(od2);
	}

	@Test
	public void testDateDiference() {
		Date dateI = Utils.dateFromString("14/02/2023", "dd/MM/yyyy");
		Date dateF = Utils.dateFromString("16/02/2023", "dd/MM/yyyy");

		long difDays = Utils.dateDiferenceDays(dateI, dateF);
		System.out.println("Diferencia de días: " + difDays);
	}

	@Test
	public void testDateAddDays() {
		Date dateI = Utils.dateFromString("14/02/2023", "dd/MM/yyyy");
		Date dateF = Utils.dateAddDays(dateI, 2);
		System.out.println("Fecha modificada: " + dateF.toString());
	}
}
