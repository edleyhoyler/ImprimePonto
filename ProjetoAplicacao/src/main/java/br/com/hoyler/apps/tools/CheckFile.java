package br.com.hoyler.apps.tools;

import java.io.File;

public class CheckFile {

	public Boolean FileExists(String filePath) {

		Boolean booleanReturn = false;

		File file = new File(filePath);

		try {

			booleanReturn = file.exists();

		} catch (Exception ex) {

			System.out.println(String.format("public class CheckFile FileExists [%s] [%s]\n%s", filePath, booleanReturn, ex.getMessage()));
		}

		if (booleanReturn) {

			System.out.printf("public class CheckFile FileExists [%s] [%s]\n", filePath, booleanReturn.toString());
		} else {

			System.out.printf("public class CheckFile FileExists [%s] [%s]\n", filePath, booleanReturn.toString());
		}

		return booleanReturn;
	}

}
