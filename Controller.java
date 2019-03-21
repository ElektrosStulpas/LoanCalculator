package Loan;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.lang.Math;
import java.text.DecimalFormat;

public class Controller {
    @FXML
    private TableView<Monthly> loanTable;
    @FXML
    private TableColumn<Monthly, Integer> monthCol;
    @FXML
    private TableColumn<Monthly, Double> paymentCol;
    @FXML
    private  TableColumn<Monthly, Double> creditCol;
    @FXML
    private  TableColumn<Monthly, Double> interestCol;
    @FXML
    private TableColumn<Monthly, Double> unpaidCol;

    @FXML
    private TextField amountField;
    @FXML
    private TextField periodYearField;
    @FXML
    private TextField periodMonthField;
    @FXML
    private TextField interestField;
    @FXML
    ChoiceBox<String> choiceBox;

    public double amount, interest, monthlyPayment, credit, x;
    public int years, months;
    public String paymentModel;

    private static DecimalFormat df = new DecimalFormat(".##");

    public void getData() {
        loanTable.getItems().clear();

        monthCol.setCellValueFactory(new PropertyValueFactory<>("month"));
        paymentCol.setCellValueFactory(new PropertyValueFactory<>("payment"));
        creditCol.setCellValueFactory(new PropertyValueFactory<>("credit"));
        interestCol.setCellValueFactory(new PropertyValueFactory<>("interest"));
        unpaidCol.setCellValueFactory(new PropertyValueFactory<>("unpaid"));

        try {
            amount = Double.parseDouble(amountField.getText());
            years = Integer.parseInt(periodYearField.getText());
            months = Integer.parseInt(periodMonthField.getText());
            interest = Double.parseDouble(interestField.getText());
            paymentModel = choiceBox.getSelectionModel().getSelectedItem();
        }catch (NumberFormatException ex){
            return;
        }
        months += years * 12;

        if (paymentModel == null){return;}

        if (paymentModel.equals("Annuity")) {
            interest = interest / 1200;
            monthlyPayment = amount * interest / (1 - 1 / Math.pow(1 + interest, months));

            double credit, interestPayment, amountLocal = amount;

            for (int i = 1; i < months + 1; i++) {
                credit = (monthlyPayment - interest * amountLocal);
                interestPayment = (interest * amountLocal);
                Monthly monthly = new Monthly(i, Double.parseDouble(df.format(monthlyPayment)), Double.parseDouble(df.format(credit)), Double.parseDouble(df.format(interestPayment)), Double.parseDouble(df.format(amountLocal)));
                loanTable.getItems().addAll(monthly);
                amountLocal -= credit;
            }
        }
        if (paymentModel.equals("Linear")){
            interest = interest / 100;
            credit = amount / months;
            x = (amount * interest / 12) / months;

            double monthlyPayment, interestPayment, amountLocal = amount;

            for (int i = 1; i <= months; i++){
                monthlyPayment = credit + x * (months + 1 - i);
                interestPayment = x * (months + 1 - i);
                Monthly monthly = new Monthly(i, Double.parseDouble(df.format(monthlyPayment)), Double.parseDouble(df.format(credit)), Double.parseDouble(df.format(interestPayment)), Double.parseDouble(df.format(amountLocal)));
                loanTable.getItems().addAll(monthly);
                amountLocal -= credit;
            }
        }
    }

    @FXML
    private TextField paymentPeriodFirstField;
    @FXML
    private TextField paymentPeriodSecondField;
    @FXML
    private TextField paymentPeriodSumField;

    public void calculatePeriodTimeSum(){
        double sum =0;
        int firstMonths, secondMonths;
        try{
            firstMonths = Integer.parseInt(paymentPeriodFirstField.getText());
            secondMonths = Integer.parseInt(paymentPeriodSecondField.getText());
        }catch (NumberFormatException ex){
            return;
        }

        if (paymentModel.equals("Annuity")){
            sum = (secondMonths - firstMonths+1) * Double.parseDouble(df.format(monthlyPayment));
        }
        if (paymentModel.equals("Linear")){
            for (int i = firstMonths; i <= secondMonths; i++){
                sum += credit + x * (months + 1 - i);
            }
        }
        paymentPeriodSumField.setText(df.format(sum));
    }

    public void downloadReport(){
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("LoanReport.txt"), "utf-8"))){

            writer.write("Month" + "\t" + "Payment" + "\t" + "Credit" + "\t" + "Interest" + "\t" + "Left unpaid");
            ((BufferedWriter) writer).newLine();

            if (paymentModel.equals("Annuity")){

                double credit, interestPayment, amountLocal = amount;

                for (int i = 1; i < months + 1; i++){
                    credit = (monthlyPayment - interest * amountLocal);
                    interestPayment = (interest * amountLocal);
                    writer.write(i + "\t" + df.format(monthlyPayment) + "\t" + df.format(credit) + "\t" + df.format(interestPayment) + "\t\t" + df.format(amountLocal));
                    ((BufferedWriter) writer).newLine();
                    amountLocal -= credit;
                }
            }

            if (paymentModel.equals("Linear")){

                double monthlyPayment, interestPayment, amountLocal = amount;

                for (int i = 1; i <= months; i++){
                    monthlyPayment = credit + x * (months + 1 - i);
                    interestPayment = x * (months + 1 - i);
                    writer.write(i + "\t" + df.format(monthlyPayment) + "\t" + df.format(credit) + "\t" + df.format(interestPayment) + "\t\t" + df.format(amountLocal));
                    ((BufferedWriter) writer).newLine();
                    amountLocal -= credit;
                }
            }
        }catch (IOException ex) {
            return;
        }
    }
}