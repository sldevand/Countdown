package fr.sldeveloperand.countdown;

public class MyDate {
    private long days;
    private int months;
    private int years;

    MyDate(int months,long days) {
        this.months = months;
        this.days = days;
        this.years=0;
    }

    public long getDays() {
        return days;
    }


    public int getMonths() {
        return months;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MyDate myDate = (MyDate) o;

        return days == myDate.days && months == myDate.months && years == myDate.years;
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
                '}';
    }
}
