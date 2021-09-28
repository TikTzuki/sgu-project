package tik.englishcenter.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;

import tik.englishcenter.utils.ImageUtils;

public class Resources {
	public static String UPLOAD_PATH = "../DoAn_BanHang/Content/images/uploads/";
	public static String ASSETS_PATH = "./src/main/resources/images/";
	
	public static int MAIN_CONTENT_WIDTH = 1110;
	public static int MAIN_CONTENT_HEIGHT = 700;
	public static final int EDGE_XXXS = 28; 
	public static final int EDGE_XXS = 34; 
	
	public static Dimension MAIN_CONTENT = new Dimension(MAIN_CONTENT_WIDTH, MAIN_CONTENT_HEIGHT);
	public static Dimension CATALOG_SIZE = new Dimension(MAIN_CONTENT_WIDTH-50, MAIN_CONTENT_HEIGHT);
	public static Dimension ICON_BUTTON_SIZE = new Dimension(40, 40);
	
	public static ImageIcon CATALOG = ImageUtils.loadImage(ASSETS_PATH+"catalog.png", 28, 28);
	public static ImageIcon ORDER = ImageUtils.loadImage(ASSETS_PATH+"checklist.png", 28, 28);
	public static ImageIcon BOX = ImageUtils.loadImage(ASSETS_PATH+"box.png", 28, 28);
	public static ImageIcon DEVICE = ImageUtils.loadImage(ASSETS_PATH+"device1.png", 28, 28);
	public static ImageIcon RECEIVING_NOTE = ImageUtils.loadImage(ASSETS_PATH+"receivingNote.png", 28, 28);
	public static ImageIcon CUSTOMER = ImageUtils.loadImage(ASSETS_PATH + "customer.png", 28, 28);
	public static ImageIcon STAFF = ImageUtils.loadImage(ASSETS_PATH + "customer.png", 28, 28);
	public static final ImageIcon CALENDAR_ICON = ImageUtils.loadImage(ASSETS_PATH+"calendar.png", 18, 18);
	public static final ImageIcon DOUBLE_ARROW_UP = ImageUtils.loadImage(ASSETS_PATH+"double-up-arrow.png", 18, 18);
	public static final ImageIcon DOUBLE_ARROW_DOWN = ImageUtils.loadImage(ASSETS_PATH+"double-down-arrow.png", 18, 18);
	public static final ImageIcon ADD_ICON = ImageUtils.loadImage(ASSETS_PATH+"add.png", 14, 14);
	public static final ImageIcon MENU_ICON = ImageUtils.loadImage(ASSETS_PATH+"menu.png", 18, 18);
	public static final ImageIcon TRASH_BIN_ICON = ImageUtils.loadImage(ASSETS_PATH+"trash-bin.png", 18, 18);
	public static final ImageIcon EMPTY_ICON = ImageUtils.loadImage(ASSETS_PATH+"empty.png", 18, 18);
	public static final ImageIcon LOGIN_BACKGROUND = ImageUtils.loadImage(ASSETS_PATH+"login_background.png", 300, 500);

	public static Color C_PRIMARY = new Color(55, 125, 255);
	public static Color C_PRIMARY_DARK = new Color(23, 43, 77);
	public static Color C_SECONDARY = new Color(113, 134, 157);
	public static Color C_SUCCESS = new Color(0, 201, 167);
	public static Color C_INFO = new Color(0, 201, 219);
	public static Color C_WARNING = new Color(245, 202, 153);
	public static Color C_DANGER = new Color(237, 76, 120);
	public static Color C_DARK = new Color(19, 33, 68);
	public static Color C_LIGHT = new Color(249, 250, 252);
	public static Color C_WHITE = new Color(255,255,255);
	public static Color C_BLACK = new Color(0,0,0);
	public static Color C_TEXT = new Color(82, 95, 127);
	
	public static Font F_H2Blod = new Font(Font.SANS_SERIF, Font.BOLD, 40);
	public static Font F_H3Regular = new Font(Font.SANS_SERIF, 0, 20);
	public static Font F_BlodNumber = new Font(Font.SANS_SERIF, Font.BOLD, 20);
	
	public static Dimension TXT_SEARCH = new Dimension(140, EDGE_XXXS);
	public static Dimension TXT_SEARCH_DATE = new Dimension(120, 28);
}
