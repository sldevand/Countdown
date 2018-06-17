package fr.sldeveloperand.countdown.helpers;

import android.content.Context;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;

import fr.sldeveloperand.countdown.R;
import fr.sldeveloperand.countdown.models.MyDate;

public class DateHelper {

    private static final int ONE = 1;
    private static final int TWELVE=12;
    private static final int MILLIS_IN_A_DAY=86400000;

    private static MyDate calculateDiffDates(Date date1, Date date2){
        Calendar calStr1 = Calendar.getInstance();
        Calendar calStr2 = Calendar.getInstance();
        Calendar calStr0;

        int nbMonths;
        int nbYears;
        long nbDays;

        if (date1.equals(date2)) {
            return null;
        }

        calStr1.setTime(date1);
        calStr2.setTime(date2);

        nbMonths = 0;
        while (calStr1.before(calStr2)) {
            calStr1.add(GregorianCalendar.MONTH, ONE);
            if (calStr1.before(calStr2) || calStr1.equals(calStr2)) {
                nbMonths++;
            }
        }
        nbYears = (nbMonths / TWELVE);
        nbMonths = (nbMonths - (nbYears * TWELVE));

        calStr0 = Calendar.getInstance();
        calStr0.setTime(date1);
        calStr0.add(GregorianCalendar.YEAR, nbYears);
        calStr0.add(GregorianCalendar.MONTH, nbMonths);
        nbDays = (calStr2.getTimeInMillis() - calStr0.getTimeInMillis()) / MILLIS_IN_A_DAY;

        return new MyDate(nbMonths,nbDays);
    }

    public static String strFromDeadline(Context ctx, Date eventDeadline){

        Date now = new Date();
        Optional<MyDate> diffOpt = Optional.ofNullable(DateHelper.calculateDiffDates(now,eventDeadline));

        StringBuilder builder = new StringBuilder();

        if(diffOpt.isPresent()) {
            MyDate diff = diffOpt.get();
            if ( diff.getMonths() > 0) {
                builder.append(diff.getMonths()).append(" ").append(ctx.getString(R.string.month));
            }

            if (diff.getDays() > 0) {
                builder.append("\n").append(diff.getDays()).append(" ").append(ctx.getString(R.string.day));
                if (diff.getDays() > 1) builder.append("s");
            }
        }

        return builder.toString();
    }

}
