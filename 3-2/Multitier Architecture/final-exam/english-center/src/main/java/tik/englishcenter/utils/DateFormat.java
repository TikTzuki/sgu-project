package tik.englishcenter.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateFormat extends SimpleDateFormat {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public DateFormat() {
        super("yyyy/MM/dd");
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
