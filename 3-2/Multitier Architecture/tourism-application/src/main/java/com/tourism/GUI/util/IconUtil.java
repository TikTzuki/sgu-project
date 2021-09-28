package com.tourism.GUI.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class IconUtil {
	public static ImageIcon loadIcon(String linkImage, int width, int height) {
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
			Logger.getLogger(IconUtil.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}
}
