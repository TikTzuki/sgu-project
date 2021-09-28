package tiktzuki.selenium_grid.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ObjectReader {
	Properties props = new Properties();
	
	public Properties getObjectRepository(String filename) {
    InputStream input = null;
    try {
      input = new FileInputStream(filename);
      props.load(input);
    } catch (Exception e) {
    }
    return props;
  }
}
