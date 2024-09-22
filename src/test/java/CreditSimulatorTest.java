import com.lesaro.creditsimulator.CreditSimulator;
import com.lesaro.creditsimulator.model.Loan;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

public class CreditSimulatorTest {

    @Test
    public void testValidVehicleTypeInput() {
        // Simulasi input valid menggunakan System.setIn
        String simulatedInput = "MOBIL\nBARU\n2023\n3\n195000000\n105000000\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        // Panggil metode getLoanFromUserInput tanpa argumen
        Loan loan = CreditSimulator.getLoanFromUserInput();

        // Verifikasi hasil
        assertEquals("MOBIL", loan.getVehicleType().toString());
        assertEquals("BARU", loan.getVehicleCondition().toString());
        assertEquals(2023, loan.getVehicleYear());
    }

    @Test
    public void testInvalidVehicleTypeInput() {
        // Simulasi input tidak valid diikuti dengan input valid
        String simulatedInput = "PESAWAT\nMOBIL\nBARU\n2023\n3\n195000000\n105000000\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        // Panggil metode getLoanFromUserInput
        Loan loan = CreditSimulator.getLoanFromUserInput();

        // Verifikasi hasil setelah input kedua yang valid
        assertEquals("MOBIL", loan.getVehicleType().toString());
    }

    @Test
    public void testValidVehicleYearInput() {
        // Simulasi input valid dengan tahun kendaraan
        String simulatedInput = "MOBIL\nBARU\n2023\n5\n500000000\n200000000\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        // Panggil metode getLoanFromUserInput
        Loan loan = CreditSimulator.getLoanFromUserInput();

        // Verifikasi hasil
        assertEquals(2023, loan.getVehicleYear());
    }

    @Test
    public void testValidLoanTenor() {
        // Simulasi input valid dengan tenor pinjaman
        String simulatedInput = "MOBIL\nBARU\n2023\n5\n500000000\n200000000\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        // Panggil metode getLoanFromUserInput
        Loan loan = CreditSimulator.getLoanFromUserInput();

        // Verifikasi hasil
        assertEquals(5, loan.getLoanTenor());
    }

    @Test
    public void testInvalidLoanTenor() {
        // Simulasi input dengan tenor yang tidak valid (di luar rentang 1-6 tahun)
        String simulatedInput = "MOBIL\nBARU\n2023\n7\n5\n500000000\n200000000\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);

        // Panggil metode getLoanFromUserInput
        Loan loan = CreditSimulator.getLoanFromUserInput();

        // Verifikasi bahwa input kedua yang valid diterima
        assertEquals(5, loan.getLoanTenor());
    }
}
