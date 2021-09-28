package tiktzuki.selenium_grid.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Action {
	public String keyword;
	public String object;
	public String objectType;
	public String value;
}
