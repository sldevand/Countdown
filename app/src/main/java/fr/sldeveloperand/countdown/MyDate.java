package fr.sldeveloperand.countdown;

public class MyDate {
    private long days;
    private int months;
    private int years;
    private long hours;

    public MyDate(int months,long days) {
        this.months = months;
        this.days = days;
    }

    public long getDays() {
        return days;
    }

    public void setDays(long days) {
        this.days = days;
    }

    public int getMonths() {
        return months;
    }

    public void setMonths(int months) {
        this.months = months;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public long getHours() {
        return hours;
    }

    public void setHours(long hours) {
        this.hours = hours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MyDate myDate = (MyDate) o;

        if (days != myDate.days) return false;
        if (months != myDate.months) return false;
        return years == myDate.years;
    }

    @Override
    public int hashCode() {
        int result = (int) (days ^ (days >>> 32));
        result = 31 * result + months;
        result = 31 * result + years;
        return result;
    }

    @Override
    public String toString() {
        return "MyDate{" +
                "days=" + days +
                ", months=" + months +
                ", years=" + years +
                ", hours=" + hours +
                '}';
    }
}
