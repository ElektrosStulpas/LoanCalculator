package Loan;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Monthly {
    private SimpleIntegerProperty month;
    private SimpleDoubleProperty payment;
    private SimpleDoubleProperty credit;
    private SimpleDoubleProperty interest;
    private SimpleDoubleProperty unpaid;

    public Monthly(int month, double payment, double credit, double interest, double unpaid) {
        this.month = new SimpleIntegerProperty(month);
        this.payment = new SimpleDoubleProperty(payment);
        this.credit = new SimpleDoubleProperty(credit);
        this.interest = new SimpleDoubleProperty(interest);
        this.unpaid = new SimpleDoubleProperty(unpaid);
    }

    public int getMonth() {
        return month.get();
    }

    public void setMonth(int month) {
        this.month = new SimpleIntegerProperty(month);
    }

    public double getPayment() {
        return payment.get();
    }

    public void setPayment(double payment) {
        this.payment = new SimpleDoubleProperty(payment);
    }

    public double getCredit() {
        return credit.get();
    }

    public void setCredit(double credit) {
        this.credit = new SimpleDoubleProperty(credit);
    }

    public double getInterest() {
        return interest.get();
    }

    public void setInterest(double interest) {
        this.interest = new SimpleDoubleProperty(interest);
    }

    public double getUnpaid() {
        return unpaid.get();
    }

    public void setUnpaid(double unpaid) {
        this.unpaid = new SimpleDoubleProperty(unpaid);
    }
}
