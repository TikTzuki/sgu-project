package tik.englishcenter.gui.components;

import java.util.ArrayList;

import lombok.Data;
@Data
public class TComboBoxItem<T> {
	T object;
	String displayField;
	public TComboBoxItem(T obj, String displayField) {
		this.object = (T) obj;
		this.displayField = displayField;
	}
	
	@Override
	public String toString() {
		return displayField;
	}
}
