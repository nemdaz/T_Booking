package utils;

public class UtilDelay {
	public static void coolDelay(int miliSec) {

		try {
			Thread.sleep(miliSec);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
