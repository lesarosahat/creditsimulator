package com.lesaro.creditsimulator.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Loan {
    private VehicleType vehicleType;
    private VehicleCondition vehicleCondition;
    private int vehicleYear;
    private BigDecimal totalLoanAmount;
    private int loanTenor;
    private BigDecimal downPayment;
    private List<String> calculateYearlyInstallment = new ArrayList<String>();

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public VehicleCondition getVehicleCondition() {
        return vehicleCondition;
    }

    public void setVehicleCondition(VehicleCondition vehicleCondition) {
        this.vehicleCondition = vehicleCondition;
    }

    public int getVehicleYear() {
        return vehicleYear;
    }

    public void setVehicleYear(int vehicleYear) {
        this.vehicleYear = vehicleYear;
    }

    public BigDecimal getTotalLoanAmount() {
        return totalLoanAmount;
    }

    public void setTotalLoanAmount(BigDecimal totalLoanAmount) {
        this.totalLoanAmount = totalLoanAmount;
    }

    public int getLoanTenor() {
        return loanTenor;
    }

    public void setLoanTenor(int loanTenor) {
        this.loanTenor = loanTenor;
    }

    public BigDecimal getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(BigDecimal downPayment) {
        this.downPayment = downPayment;
    }

    public List<String> getCalculateYearlyInstallment() {
        return calculateYearlyInstallment;
    }

    public void setCalculateYearlyInstallment(List<String> calculateYearlyInstallment) {
        this.calculateYearlyInstallment = calculateYearlyInstallment;
    }
}
