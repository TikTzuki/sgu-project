package tiktzuki.e_store.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat extends SimpleDateFormat {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public DateFormat() {
        super("dd/MM/yyyy");
    }

    @Override
    public Date parse(String source) {
        try {
            return super.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }
}
