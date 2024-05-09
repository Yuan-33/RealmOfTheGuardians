package resource;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import main.Main;

public class ResourceHandle {

	public ResourceHandle() {
	}

	private static BufferedImage[] initAlphabet() {
		alphabet = new BufferedImage[40];
		for (int i = 0; i < alphabet.length; i++) {
			alphabet[i] = cropImage(imagesheet, (i % 30) * 8, i >= 30 ? 8 : 0,
					8, 8);
			alphabet[i] = resize(alphabet[i], 32, 32);
		}

		return alphabet;
	}

	public static BufferedImage cropImage(BufferedImage img, int x, int y,
			int w, int h) {
		return img.getSubimage(x, y, w, h);
	}

	private static BufferedImage loadImage(String string) {
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(Thread.currentThread().getContextClassLoader()
					.getResource(string));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bi;
	}

	public static BufferedImage resize(BufferedImage img, int w, int h) {
		return imageToBufferedImage(img.getScaledInstance(w, h, 2));
	}

	public static BufferedImage imageToBufferedImage(Image im) {
		BufferedImage bi = new BufferedImage(im.getWidth(null),
				im.getHeight(null), 2);
		Graphics bg = bi.getGraphics();
		bg.drawImage(im, 0, 0, null);
		bg.dispose();
		return bi;
	}

	public static BufferedImage imagesheet;
	public static BufferedImage img2;
	public static BufferedImage alphabet[];
	public static BufferedImage bullet;
	public static BufferedImage player;
	public static BufferedImage bg[];
	public static BufferedImage Wall[];
	public static BufferedImage WallTopLeft[];
	public static BufferedImage WallDownLeft[];
	public static BufferedImage WallDownRight[];
	public static BufferedImage WallTopRight[];

	public static void inits() {

		imagesheet = loadImage("res/images.png");
		img2 = resize(cropImage(imagesheet, 0, 0, 8, 8), 32, 4);
		bullet = resize(cropImage(imagesheet, 0, 72, 8, 1), 32, 4);
		player = cropImage(imagesheet, 0, 16, 40, 8);

		bg = (new BufferedImage[] {
				resize(cropImage(imagesheet, 0, 24, 8, 8), 64, 64),
				resize(cropImage(imagesheet, 8, 24, 8, 8), 64, 64),
				resize(cropImage(imagesheet, 16, 24, 8, 8), 64, 64),
				resize(cropImage(imagesheet, 24, 24, 8, 8), 64, 64),
				resize(cropImage(imagesheet, 32, 24, 8, 8), 64, 64),
				resize(cropImage(imagesheet, 40, 24, 8, 8), 64, 64),
				resize(cropImage(imagesheet, 48, 24, 8, 8), 64, 64),
				resize(cropImage(imagesheet, 56, 24, 8, 8), 64, 64) });
		Wall = (new BufferedImage[] {
				resize(cropImage(imagesheet, 0, 32, 8, 8), 32, 32),
				resize(cropImage(imagesheet, 8, 32, 8, 8), 32, 32),
				resize(cropImage(imagesheet, 16, 32, 8, 8), 32, 32),
				resize(cropImage(imagesheet, 24, 32, 8, 8), 32, 32),
				resize(cropImage(imagesheet, 32, 32, 8, 8), 32, 32),
				resize(cropImage(imagesheet, 40, 32, 8, 8), 32, 32),
				resize(cropImage(imagesheet, 48, 32, 8, 8), 32, 32),
				resize(cropImage(imagesheet, 56, 32, 8, 8), 32, 32) });
		WallTopLeft = (new BufferedImage[] {
				resize(cropImage(imagesheet, 0, 40, 8, 8), 32, 32),
				resize(cropImage(imagesheet, 8, 40, 8, 8), 32, 32),
				resize(cropImage(imagesheet, 16, 40, 8, 8), 32, 32),
				resize(cropImage(imagesheet, 24, 40, 8, 8), 32, 32),
				resize(cropImage(imagesheet, 32, 40, 8, 8), 32, 32),
				resize(cropImage(imagesheet, 40, 40, 8, 8), 32, 32),
				resize(cropImage(imagesheet, 48, 40, 8, 8), 32, 32),
				resize(cropImage(imagesheet, 56, 40, 8, 8), 32, 32) });
		WallDownLeft = (new BufferedImage[] {
				resize(cropImage(imagesheet, 0, 48, 8, 8), 32, 32),
				resize(cropImage(imagesheet, 8, 48, 8, 8), 32, 32),
				resize(cropImage(imagesheet, 16, 48, 8, 8), 32, 32),
				resize(cropImage(imagesheet, 24, 48, 8, 8), 32, 32),
				resize(cropImage(imagesheet, 32, 48, 8, 8), 32, 32),
				resize(cropImage(imagesheet, 40, 48, 8, 8), 32, 32),
				resize(cropImage(imagesheet, 48, 48, 8, 8), 32, 32),
				resize(cropImage(imagesheet, 56, 48, 8, 8), 32, 32) });
		WallDownRight = (new BufferedImage[] {
				resize(cropImage(imagesheet, 0, 56, 8, 8), 32, 32),
				resize(cropImage(imagesheet, 8, 56, 8, 8), 32, 32),
				resize(cropImage(imagesheet, 16, 56, 8, 8), 32, 32),
				resize(cropImage(imagesheet, 24, 56, 8, 8), 32, 32),
				resize(cropImage(imagesheet, 32, 56, 8, 8), 32, 32),
				resize(cropImage(imagesheet, 40, 56, 8, 8), 32, 32),
				resize(cropImage(imagesheet, 48, 56, 8, 8), 32, 32),
				resize(cropImage(imagesheet, 56, 56, 8, 8), 32, 32) });
		WallTopRight = (new BufferedImage[] {
				resize(cropImage(imagesheet, 0, 64, 8, 8), 32, 32),
				resize(cropImage(imagesheet, 8, 64, 8, 8), 32, 32),
				resize(cropImage(imagesheet, 16, 64, 8, 8), 32, 32),
				resize(cropImage(imagesheet, 24, 64, 8, 8), 32, 32),
				resize(cropImage(imagesheet, 32, 64, 8, 8), 32, 32),
				resize(cropImage(imagesheet, 40, 64, 8, 8), 32, 32),
				resize(cropImage(imagesheet, 48, 64, 8, 8), 32, 32),
				resize(cropImage(imagesheet, 56, 64, 8, 8), 32, 32) });
		alphabet = initAlphabet();
	}
}
