package br.com.hoyler.apps.imagens;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ImagensGetSet {

	private final String caminhoImagens = ("/br/com/hoyler/apps/imagens/");

	
	
	
	private String getStringExitPng() {

		final String file = "exit.png";

		final String patch_img = String.format("%s%s", caminhoImagens, file);

		return (patch_img);

	}

	private String getStringPdfPng() {

		final String file = "pdf.png";

		final String patch_img = String.format("%s%s", caminhoImagens, file);

		return (patch_img);

	}

	private String getStringPrintPng() {

		final String file = "print.png";

		final String patch_img = String.format("%s%s", caminhoImagens, file);

		return (patch_img);

	}

	private String getStringViewPng() {

		final String file = "view.png";

		final String patch_img = String.format("%s%s", caminhoImagens, file);

		return (patch_img);

	}

	private String getStringBackgroudPng() {

		final String file = "background.png";

		final String patch_img = String.format("%s%s", caminhoImagens, file);

		return (patch_img);

	}

	private String getStringEmpresaPng() {

		final String file = "empresa.png";

		final String patch_img = String.format("%s%s", caminhoImagens, file);

		return (patch_img);

	}

	private String getStringFuncaoPng() {

		final String file = "funcao.png";

		final String patch_img = String.format("%s%s", caminhoImagens, file);

		return (patch_img);

	}

	private String getStringPessoaPng() {

		final String file = "pessoa.png";

		final String patch_img = String.format("%s%s", caminhoImagens, file);

		return (patch_img);

	}

	private String getStringLogoPng() {

		final String file = "logo.png";

		final String patch_img = String.format("%s%s", caminhoImagens, file);

		return (patch_img);

	}

	private ImageView getImageViewPng(String nomeBotao) {

		Image image = null;
		ImageView imageView = null;

		try {

			switch (nomeBotao) {
			case ("ButtonPDFID"): {
				image = new Image(this.getStringPdfPng());
				break;
			}
			case ("ButtonImprimirID"): {
				image = new Image(this.getStringPrintPng());
				break;
			}
			case ("ButtonVisualizarID"): {
				image = new Image(this.getStringViewPng());
				break;
			}
			case ("ButtonSairID"): {
				image = new Image(this.getStringExitPng());
				break;
			}
			default: {

				break;
			}
			}

			Boolean imageExists = (image != null);
			imageView = new ImageView(image);

			if (imageExists) {

				imageView = new ImageView(image);

				System.out.printf("public class ImagesGetSet getImageViewPng[%s] [%s]\n", image.toString(),
						imageExists.toString());

			} else {

				System.out.printf("public class ImagesGetSet getImageViewPng[%s] [%s]\n", null, imageExists.toString());

			}
		} catch (Exception e) {

			System.out.printf("public class ImagesGetSet getImageViewPng ERRO: [null]\n");
		}

		return (imageView);
	}

	public void setImageButtons(Button button) {

		String nomeButton = button.getId();

		button.setGraphic(getImageViewPng(nomeButton));

	}

	private Image getImagePng(String nomeImageView) {

		Image image = null;

		try {

			switch (nomeImageView) {

			case ("ImageViewLogo"): {
				image = new Image(this.getStringLogoPng());
				break;
			}
			case ("ImageViewBackgroundID"): {
				image = new Image(this.getStringBackgroudPng());
				break;
			}
			case ("ImageViewPessoaID"): {
				image = new Image(this.getStringPessoaPng());
				break;
			}
			case ("ImageViewFuncaoID"): {
				image = new Image(this.getStringFuncaoPng());
				break;
			}
			case ("ImageViewEmpresaID"): {
				image = new Image(this.getStringEmpresaPng());
				break;
			}
			default: {

				break;
			}
			}

			Boolean imageExists = (image != null);

			if (imageExists) {

				System.out.printf("public class ImagesGetSet ImagesGetSet[%s] [%s]\n", image.toString(),
						imageExists.toString());

			} else {

				System.out.printf("public class ImagesGetSet ImagesGetSet[%s] [%s]\n", null, imageExists.toString());

			}

		} catch (Exception e) {

			System.out.printf("public class ImagesGetSet getImagePng ERRO: [null]\n");
		}

		return (image);
	}

	public void setImageImageView(ImageView imageView) {

		String nomeImageView = imageView.getId();

		imageView.setImage(this.getImagePng(nomeImageView));
	}
	
	public void setIconLogoPNG(Stage stage) {

		Image image = new Image(this.getStringLogoPng());

		Boolean imageExists = (image != null);

		if (imageExists) {

			stage.getIcons().add(image);

			System.out.printf("public class ImagensGetSet setIconLogoPNG[%s] [%s]\n", image.toString(),
					imageExists.toString());

		} else {

			System.out.printf("public class ImagensGetSet setIconLogoPNG[%s] [%s]\n", null, imageExists.toString());

		}
	}
}
