package com.lesaro.creditsimulator.util;

import com.lesaro.creditsimulator.model.Loan;
import com.lesaro.creditsimulator.model.VehicleCondition;
import com.lesaro.creditsimulator.model.VehicleType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

public class FileInputReader {

    public static Loan readLoanFromFile(String filePath) throws IOException {
        Loan loan = new Loan();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            // Baca jenis kendaraan
            String vehicleTypeStr = br.readLine();
            try {
                loan.setVehicleType(VehicleType.valueOf(vehicleTypeStr.toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Jenis kendaraan tidak valid: " + vehicleTypeStr);
            }

            // Baca kondisi kendaraan
            String vehicleConditionStr = br.readLine();
            try {
                loan.setVehicleCondition(VehicleCondition.valueOf(vehicleConditionStr.toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Kondisi kendaraan tidak valid: " + vehicleConditionStr);
            }

            // Baca tahun kendaraan
            int vehicleYear = Integer.parseInt(br.readLine());
            int currentYear = java.time.LocalDate.now().getYear();
            if (loan.getVehicleCondition() == VehicleCondition.BARU && vehicleYear < currentYear - 1) {
                throw new IllegalArgumentException("Kendaraan baru tidak boleh lebih tua dari tahun " + (currentYear - 1));
            }
            loan.setVehicleYear(vehicleYear);

            // Baca tenor pinjaman
            int loanTenor = Integer.parseInt(br.readLine());
            if (loanTenor < 1 || loanTenor > 6) {
                throw new IllegalArgumentException("Tenor pinjaman tidak valid. Harus antara 1 hingga 6 tahun.");
            }
            loan.setLoanTenor(loanTenor);

            // Baca jumlah pinjaman
            BigDecimal totalLoanAmount = new BigDecimal(br.readLine());
            if (totalLoanAmount.compareTo(BigDecimal.valueOf(1000000000)) > 0) {
                throw new IllegalArgumentException("Jumlah pinjaman tidak boleh lebih dari 1 miliar.");
            }
            loan.setTotalLoanAmount(totalLoanAmount);

            // Baca DP
            BigDecimal downPayment = new BigDecimal(br.readLine());
            if (loan.getVehicleCondition() == VehicleCondition.BARU &&
                    downPayment.compareTo(totalLoanAmount.multiply(BigDecimal.valueOf(0.35))) < 0) {
                throw new IllegalArgumentException("DP untuk kendaraan baru harus minimal 35% dari total pinjaman.");
            } else if (loan.getVehicleCondition() == VehicleCondition.BEKAS &&
                    downPayment.compareTo(totalLoanAmount.multiply(BigDecimal.valueOf(0.25))) < 0) {
                throw new IllegalArgumentException("DP untuk kendaraan bekas harus minimal 25% dari total pinjaman.");
            }
            loan.setDownPayment(downPayment);

        }
        return loan;
    }
}

