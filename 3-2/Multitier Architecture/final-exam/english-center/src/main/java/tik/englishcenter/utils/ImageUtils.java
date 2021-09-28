package tik.englishcenter.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

import tik.englishcenter.gui.Resources;

public class ImageUtils {
	public static ImageIcon loadImage(String linkImage, int width, int height) {
		/*
		 * linkImage là tên icon, k kích thước chiều rộng mình muốn,m chiều dài và hàm
		 * này trả về giá trị là 1 icon.
		 */
		try {
			BufferedImage image = ImageIO.read(new File(linkImage));// đọc ảnh dùng BufferedImage
			int x = width;
			int y = height;
			int ix = image.getWidth();
			int iy = image.getHeight();
			int dx = 0, dy = 0;
			if (x / y > ix / iy) {
				dy = y;
				dx = dy * ix / iy;
			} else {
				dx = x;
				dy = dx * iy / ix;
			}
			return new ImageIcon(image.getScaledInstance(dx, dy, image.SCALE_SMOOTH));
		} catch (IOException ex) {
			Logger.getLogger(ImageUtils.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}
	
	public static File savePhysicalImage(File file) {
		if(file == null) {
			return null;
		}
		File fileUpload = null;
		Optional<String> fileExtensionOpt = Optional.ofNullable(file.getName())
				.filter(f -> f.contains("."))
				.map(f -> f.substring(f.lastIndexOf(".")));
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		if(fileExtensionOpt.isPresent()) {
			String fileName = timestamp.getTime() + Math.round(Math.random()*100) + fileExtensionOpt.get();
			fileUpload = new File(Resources.UPLOAD_PATH + fileName);
			file.renameTo(fileUpload);
		}
		return fileUpload;
	}
	
	public static void main(String[] args) {
		JFileChooser chooser = new JFileChooser();
		chooser.showOpenDialog(null);
		File file = ImageUtils.savePhysicalImage(chooser.getSelectedFile());
		System.out.println(file.getPath());
		System.out.println(file.getName());
	}
}
