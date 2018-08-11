package br.com.hoyler.apps.tools;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.scene.control.Hyperlink;

public class HyperlinkCreate {

	private void openLinkes(String link) {

		Desktop desktop = null;

		desktop = Desktop.getDesktop();

		URI uri = null;

		try {

			uri = new URI(link);
			desktop.browse(uri);
 
		} catch (IOException ioe) {

			ioe.printStackTrace();

		} catch (URISyntaxException use) {

			use.printStackTrace();

		}

	}

	public void openLinkeHOYLER(Hyperlink hyperlink) {

		String SITE_HOYLER = "http://www.hoyler.com.br";

		openLinkes(SITE_HOYLER);

		System.out.println(String.format("public class HyperlinkCreate openLinkeHOYLER [%s]\n", SITE_HOYLER));

	}

}
