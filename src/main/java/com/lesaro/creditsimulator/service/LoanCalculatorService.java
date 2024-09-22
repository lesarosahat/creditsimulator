package com.lesaro.creditsimulator.service;

import com.lesaro.creditsimulator.model.Loan;
import com.lesaro.creditsimulator.model.VehicleType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class LoanCalculatorService {

    private static final BigDecimal BASE_RATE_CAR = BigDecimal.valueOf(0.08);  // 8%
    private static final BigDecimal BASE_RATE_MOTOR = BigDecimal.valueOf(0.09);  // 9%

    // Perhitungan cicilan tahunan
    public List<String> calculateYearlyInstallment(Loan loan) {
        List<String> yearlyInstallments = new ArrayList<>();
        BigDecimal baseRate = getBaseRate(loan);  // Ambil suku bunga dasar
        BigDecimal loanAmount = loan.getTotalLoanAmount().subtract(BigDecimal.ZERO);
        int totalYears = loan.getLoanTenor();
        System.out.println("LOAN AMOUNT "+loanAmount);

        for (int year = 1; year <= totalYears; year++) {
            // Hitung suku bunga untuk tahun ke-n berdasarkan rules
            BigDecimal totalAmount = BigDecimal.ZERO;
            BigDecimal interestAmount = BigDecimal.ZERO;

            BigDecimal currentRate = calculateInterestRate(baseRate, year);

            interestAmount = loanAmount.multiply(currentRate);
            totalAmount =loanAmount.add(interestAmount);

            int totalMonths = 12 * totalYears;
            int months = totalMonths - (12 * (year - 1));
            BigDecimal monthlyInstallment = calculateMonthlyInstallment(totalAmount, currentRate, months);

            System.out.println("Tahun " + year + ": Rp. " + monthlyInstallment + "/bln , Suku Bunga: "
                    + currentRate.multiply(BigDecimal.valueOf(100)) + "%");

            yearlyInstallments.add("Tahun " + year + ": Rp. " + monthlyInstallment + "/bln , Suku Bunga: "
                    + currentRate.multiply(BigDecimal.valueOf(100)) + "%");

            BigDecimal loanAmountYearly = monthlyInstallment.multiply(BigDecimal.valueOf(12));//dikali sebulan

            loanAmount = totalAmount.subtract(loanAmountYearly);

            baseRate = currentRate;
        }
        return yearlyInstallments;
    }

    // Hitung cicilan bulanan
    //private BigDecimal calculateMonthlyInstallment(BigDecimal totalAmount, BigDecimal rate, int months) {
    public BigDecimal calculateMonthlyInstallment(BigDecimal totalAmount, BigDecimal rate, int months) {
        return totalAmount.divide(BigDecimal.valueOf(months), 10, RoundingMode.HALF_UP);
    }

    //private BigDecimal calculateInterestRate(BigDecimal baseRate, int year) {
    public BigDecimal calculateInterestRate(BigDecimal baseRate, int year) {
        // Tahun pertama (year 1) menggunakan suku bunga dasar
        if (year == 1) {
            return baseRate;  // Suku bunga dasar tanpa kenaikan
        }

        // Kenaikan 0.1% setiap tahun
        BigDecimal yearlyIncrease = BigDecimal.ZERO;
        // Kenaikan tambahan 0.5% setiap 2 tahun (hanya di tahun ke-2, ke-4, ke-6, dst.)
        BigDecimal twoYearIncrease = BigDecimal.ZERO;
        if ((year - 1) % 2 == 0) {  // Hanya di tahun ke-2, ke-4, dst.
            yearlyIncrease = BigDecimal.valueOf(0.005);
        }else{
            yearlyIncrease = BigDecimal.valueOf(0.001);
        }

        // Total suku bunga = suku bunga dasar + kenaikan tahunan + kenaikan setiap dua tahun
        return baseRate.add(yearlyIncrease);
    }

    // Mendapatkan suku bunga dasar berdasarkan jenis kendaraan
    private BigDecimal getBaseRate(Loan loan) {
        if (loan.getVehicleType() == VehicleType.MOBIL) {
            return BASE_RATE_CAR;
        } else {
            return BASE_RATE_MOTOR;
        }
    }
}
