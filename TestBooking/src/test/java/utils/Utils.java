package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Utils {

	public static Date dateFromString(String date, String format) {
		SimpleDateFormat oformat = new SimpleDateFormat(format, new Locale("es"));
		try {
			Date odate = oformat.parse(date);
			return odate;
		} catch (ParseException e) {
			e.printStackTrace();
			System.out.println("No se puede formatear la fecha.");
		}
		return null;
	}

	public static String dateToString(Date date, String format) {
		SimpleDateFormat nFormat = new SimpleDateFormat(format, new Locale("es"));
		return nFormat.format(date);
	}

	public static String dateChangeFormat(String date, String currentFormat, String newFormat) {
		Date cd = Utils.dateFromString(date, currentFormat);
		return Utils.dateToString(cd, newFormat);
	}

	public static long dateDiferenceDays(Date dateIni, Date dateEnd) {
		long difference = dateIni.getTime() - dateEnd.getTime();
		return TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
	}

	public static Date dateAddDays(Date dateIni, long numDays) {
		long tudate = dateIni.getTime() + TimeUnit.MILLISECONDS.convert(numDays, TimeUnit.DAYS);
		return new Date(tudate);
	}

	public static List<Double> numbersFromString(String strInput) {
		Pattern regex = Pattern.compile("(\\d+(?:\\.\\d+)?)");
		Matcher matcher = regex.matcher(strInput);
		List<Double> numbers = new ArrayList<Double>();
		while (matcher.find()) {
			// System.out.println(matcher.group(1));
			numbers.add(Double.parseDouble(matcher.group(1)));
		}
		return numbers;
	}

	public static Double numberFromString(String strDouble) {
		return numbersFromString(strDouble).get(0);
	}

	public static Map<String, String> dateToMap(Date fecha) {
		String[] patrones = { "EEEE", "d", "MMMM", "yyyy", "MM", "dd", "HH", "mm", "ss" };

		return Arrays.stream(patrones)
				.collect(Collectors.toMap(
						p -> p,
						p -> new SimpleDateFormat(p, new Locale("es", "ES")).format(fecha)));
	}

}
