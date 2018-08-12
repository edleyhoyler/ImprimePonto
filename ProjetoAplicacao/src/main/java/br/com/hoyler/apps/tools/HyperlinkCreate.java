package br.com.hoyler.apps.tools;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.scene.control.Hyperlink;

public class HyperlinkCreate {

	private void openLinkes(String link) {

		Desktop desktop = Desktop.getDesktop();

		URI uri;

		try {

			uri = new URI(link);
			desktop.browse(uri);

		} catch (IOException ioe) {
			System.out.println(String.format("public class HyperlinkCreate openLinkes ERRO IOException: %s", ioe.getMessage()));
		} catch (URISyntaxException use) {
			System.out.println(String.format("public class HyperlinkCreate openLinkes ERRO URISyntaxException: %s", use.getMessage()));
		}
	}

	public void openLinkeHOYLER(Hyperlink hyperlink) {

		final String SITE_HOYLER = "http://www.hoyler.com.br";

		openLinkes(SITE_HOYLER);

		System.out.println(String.format("public class HyperlinkCreate openLinkeHOYLER [%s]\n", SITE_HOYLER));

	}

}
