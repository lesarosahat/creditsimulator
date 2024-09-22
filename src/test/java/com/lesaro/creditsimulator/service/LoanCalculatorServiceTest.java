package com.lesaro.creditsimulator.service;

import com.lesaro.creditsimulator.model.Loan;
import com.lesaro.creditsimulator.model.VehicleCondition;
import com.lesaro.creditsimulator.model.VehicleType;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class LoanCalculatorServiceTest {

    LoanCalculatorService calculator = new LoanCalculatorService();

//    @Test
//    public void testCalculateMonthlyInstallment() {
//        // Nilai contoh input
//        BigDecimal loanAmount = BigDecimal.valueOf(500000000);  // Rp 500 juta
//        BigDecimal interestRate = BigDecimal.valueOf(0.08);  // 8% bunga tahunan
//        int loanTenor = 5;  // 5 tahun
//
//        // Ekspektasi hasil cicilan bulanan (dihitung manual)
//        BigDecimal expectedInstallment = BigDecimal.valueOf(7500000);  // Contoh ekspektasi
//
//        // Panggil metode untuk menghitung cicilan bulanan
//        BigDecimal actualInstallment = calculator.calculateMonthlyInstallment(loanAmount, interestRate, loanTenor);
//
//        // Bandingkan hasilnya dengan ekspektasi
//        assertEquals(expectedInstallment, actualInstallment);
//    }

    @Test
    public void testInvalidVehicleType() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Loan loan = new Loan();
            loan.setVehicleType(VehicleType.valueOf("PESAWAT"));  // Tipe kendaraan yang tidak valid
        });
        assertTrue(exception.getMessage().contains("No enum constant"));
    }

    @Test
    public void testVehicleTypeMobil() {
            Loan loan = new Loan();
            loan.setVehicleType(VehicleType.valueOf("MOBIL"));  // Tipe kendaraan yang tidak valid
        assertTrue(VehicleType.MOBIL.toString().equalsIgnoreCase(loan.getVehicleType().toString()));
    }

    @Test
    public void testVehicleTypeMotor() {
        Loan loan = new Loan();
        loan.setVehicleType(VehicleType.valueOf("MOTOR"));  // Tipe kendaraan MOTOR
        assertTrue(VehicleType.MOTOR.toString().equalsIgnoreCase(loan.getVehicleType().toString()));
    }

    @Test
    public void testInvalidVehicleCondition() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Loan loan = new Loan();
            loan.setVehicleCondition(VehicleCondition.valueOf("SECOND"));  // Kondisi kendaraan yang tidak valid
        });
        assertTrue(exception.getMessage().contains("No enum constant"));
    }

    @Test
    public void testVehicleConditionBaru() {
        Loan loan = new Loan();
        loan.setVehicleCondition(VehicleCondition.valueOf("BARU"));  // Kondisi kendaraan BARU
        assertTrue(VehicleCondition.BARU.toString().equalsIgnoreCase(loan.getVehicleCondition().toString()));
    }

    @Test
    public void testVehicleConditionBekas() {
        Loan loan = new Loan();
        loan.setVehicleCondition(VehicleCondition.valueOf("BEKAS"));  // Kondisi kendaraan BEKAS
        assertTrue(VehicleCondition.BEKAS.toString().equalsIgnoreCase(loan.getVehicleCondition().toString()));
    }

    @Test
    public void testCalculateMonthlyInstallment() {
        // Setup data loan
        BigDecimal BASE_RATE_CAR = BigDecimal.valueOf(0.08); // MOBIL
        BigDecimal totalAmount = BigDecimal.valueOf(195000000); //Rp 195  juta
        BigDecimal currentRate =  BASE_RATE_CAR;
        //JIKA TENOR 60 dan di cari installment untuk tahun pertama
        int month = 60;

        // Hitung cicilan bulanan
        BigDecimal installment = calculator.calculateMonthlyInstallment(totalAmount,currentRate,month);

        // Pastikan cicilan tidak null dan lebih besar dari 0
        assertNotNull(installment);
        assertTrue(installment.compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    public void testCalculateInterestRate() {
        // Suku bunga dasar 8%
        BigDecimal baseRate = BigDecimal.valueOf(0.08);  // 8%

        // Hitung suku bunga untuk tahun 1, 2, dan 3
        BigDecimal rateYear1 = calculator.calculateInterestRate(baseRate, 1);
        baseRate = rateYear1;
        BigDecimal rateYear2 = calculator.calculateInterestRate(baseRate, 2);
        baseRate = rateYear2;
        BigDecimal rateYear3 = calculator.calculateInterestRate(baseRate, 3);

        // Verifikasi kenaikan suku bunga sesuai aturan
        assertEquals(BigDecimal.valueOf(0.08), rateYear1);  // Tahun pertama, bunga tetap 8%
        assertEquals(BigDecimal.valueOf(0.081), rateYear2);  // Tahun kedua, naik 0.1%
        assertEquals(BigDecimal.valueOf(0.086), rateYear3);  // Tahun ketiga, naik 0.6% (0.1% + 0.5%)
    }

//    @Test
//    public void testInterestRateIncrease() {
//        // Suku bunga dasar untuk mobil
//        BigDecimal baseRate = BigDecimal.valueOf(0.08);  // 8% untuk mobil
//        BigDecimal expectedRateYear1 = baseRate;
//        BigDecimal expectedRateYear2 = baseRate.add(BigDecimal.valueOf(0.001));  // Naik 0.1% tahun ke-2
//        BigDecimal expectedRateYear3 = baseRate.add(BigDecimal.valueOf(0.006));  // Naik 0.1% + 0.5% tahun ke-3
//
//        // Hitung suku bunga untuk setiap tahun
//        BigDecimal actualRateYear1 = calculator.calculateInterestRate(baseRate, 1);
//        BigDecimal actualRateYear2 = calculator.calculateInterestRate(baseRate, 2);
//        BigDecimal actualRateYear3 = calculator.calculateInterestRate(baseRate, 3);
//
//        // Bandingkan hasil dengan ekspektasi
//        assertEquals(expectedRateYear1, actualRateYear1);
//        assertEquals(expectedRateYear2, actualRateYear2);
//        assertEquals(expectedRateYear3, actualRateYear3);
//    }
}
