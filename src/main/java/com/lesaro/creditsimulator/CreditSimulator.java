package com.lesaro.creditsimulator;

import com.lesaro.creditsimulator.model.Loan;
import com.lesaro.creditsimulator.model.VehicleCondition;
import com.lesaro.creditsimulator.model.VehicleType;
import com.lesaro.creditsimulator.service.LoanCalculatorService;
import com.lesaro.creditsimulator.util.FileInputReader;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CreditSimulator {

    private static Map<String, Loan> sheetMap = new HashMap<>();
    private static List<String> calculateYearlyInstallment = new ArrayList<String>();
    //private static String currentSheet = "default";
    private static Loan currentLoan; // Loan yang aktif

    public static void main(String[] args) {
        LoanCalculatorService calculatorService = new LoanCalculatorService();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        // Jika ada input file
        if (args.length > 0) {
            try {
                currentLoan = FileInputReader.readLoanFromFile(args[0]);
                calculateYearlyInstallment = calculatorService.calculateYearlyInstallment(currentLoan);
            } catch (IOException e) {
                System.out.println("Error reading from file: " + e.getMessage());
                return;
            }
        } else {
            while (running) {
                System.out.println("Pilih opsi: (show / input / load / save / switch / exit)");
                String input = scanner.nextLine().toLowerCase();

                switch (input) {
                    case "show":
                        showAvailableCommands();
                        break;
                    case "input":
                        currentLoan = null;
                        currentLoan = getLoanFromUserInput();  // Input manual dengan validasi
                        // Hitung cicilan setiap tahun dengan suku bunga yang meningkat
                        calculateYearlyInstallment = calculatorService.calculateYearlyInstallment(currentLoan);
                        currentLoan.setCalculateYearlyInstallment(calculateYearlyInstallment);
                        break;
                    case "load":
                        currentLoan = null;
                        loadLoanFromWebService();
                        break;
                    case "save":
                        if(currentLoan == null){
                            System.out.println("Tidak ada data loan yang aktif untuk disimpan.");
                            break;
                        }else {
                            System.out.print("Masukkan nama sheet : ");
                            String currentSheet = scanner.nextLine();
                            saveSheet(currentSheet);
                            break;
                        }
                    case "switch":
                        System.out.print("Masukkan nama sheet untuk switch: ");
                        String sheetName = scanner.nextLine();
                        switchSheet(sheetName);
                        break;
                    case "exit":
                        running = false;
                        System.out.println("Program dihentikan.");
                        break;
                    default:
                        System.out.println("Perintah tidak dikenali.");
                        break;
                }
            }
        }
    }

    // Menampilkan semua perintah yang tersedia
    private static void showAvailableCommands() {
        System.out.println("Perintah yang tersedia:");
        System.out.println("1. show - Menampilkan perintah yang bisa digunakan.");
        System.out.println("2. input - Input dan kalkulasi data loan.");
        System.out.println("3. load - Load data loan menggunaan mock API.");
        System.out.println("4. save - Simpan sheet atau hasil perhitungan.");
        System.out.println("5. switch - Beralih ke sheet lain.");
        System.out.println("6. exit - Keluar dari program.");
    }

    // Menyimpan sheet saat ini ke dalam HashMap dan file filenya gak jadi
    private static void saveSheet(String sheetName) {
        // Pastikan loan yang aktif disimpan
        if (currentLoan != null) {
            sheetMap.put(sheetName, currentLoan); // Simpan ke HashMap
            System.out.println("Sheet '" + sheetName + "' berhasil disimpan.");
            currentLoan = null;
/*
            // Simpan juga ke file
            try (FileWriter writer = new FileWriter(sheetName + ".txt")) {
                writer.write("Total Loan: " + currentLoan.getTotalLoanAmount() + "\n");
                writer.write("Vehicle Type: " + currentLoan.getVehicleType() + "\n");
                writer.write("Vehicle Condition: " + currentLoan.getVehicleCondition() + "\n");
                writer.write("Loan Year: " + currentLoan.getVehicleYear() + "\n");
                writer.write("Loan Tenor: " + currentLoan.getLoanTenor() + " years\n");
                writer.write("Down Payment: " + currentLoan.getDownPayment() + "\n\n");
                for(String data: currentLoan.getCalculateYearlyInstallment()){
                    writer.write(data+"\n\n\n\n");
                }
                System.out.println("Sheet '" + sheetName + "' juga disimpan ke file.");
            } catch (IOException e) {
                System.out.println("Error saat menyimpan ke file: " + e.getMessage());
            }*/
        } else {
            System.out.println("Tidak ada data loan yang aktif untuk disimpan.");
        }
    }

    // Beralih ke sheet lain
    private static void switchSheet(String sheetName) {
        if (sheetMap.containsKey(sheetName)) {
            //currentSheet = sheetName;
            currentLoan = sheetMap.get(sheetName); // Set Loan yang aktif ke sheet yang dipilih
            System.out.println("Switched ke sheet: " + sheetName);
            System.out.println("Vehicle Type: " + currentLoan.getVehicleType());
            System.out.println("Vehicle Condition: " + currentLoan.getVehicleCondition());
            System.out.println("Loan Year: " + currentLoan.getVehicleYear());
            System.out.println("Loan Tenor: " + currentLoan.getLoanTenor() + " years");
            System.out.println("Total Loan: " + currentLoan.getTotalLoanAmount());
            System.out.println("Down Payment: " + currentLoan.getDownPayment());
            for(String data: currentLoan.getCalculateYearlyInstallment()){
                System.out.println(data);
            }
        } else {
            System.out.println("Sheet '" + sheetName + "' tidak ditemukan.");
        }
        currentLoan =null;
    }

    // Fungsi untuk mendapatkan input manual dari pengguna dengan validasi
    //private static Loan getLoanFromUserInput() {
    public static Loan getLoanFromUserInput() {
        Scanner scanner = new Scanner(System.in);
        Loan loan = new Loan();
        int currentYear = LocalDate.now().getYear();

        // Validasi input jenis kendaraan (MOTOR atau MOBIL)
        while (true) {
            try {
                System.out.print("Masukkan jenis kendaraan (MOTOR/MOBIL): ");
                loan.setVehicleType(VehicleType.valueOf(scanner.nextLine().toUpperCase()));
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Input tidak valid. Harap masukkan 'MOTOR' atau 'MOBIL'.");
            }
        }

        // Validasi input kondisi kendaraan (BARU atau BEKAS)
        while (true) {
            try {
                System.out.print("Masukkan kondisi kendaraan (BARU/BEKAS): ");
                loan.setVehicleCondition(VehicleCondition.valueOf(scanner.nextLine().toUpperCase()));
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Input tidak valid. Harap masukkan 'BARU' atau 'BEKAS'.");
            }
        }

        // Validasi input tahun kendaraan
        while (true) {
            try {
                System.out.print("Masukkan tahun kendaraan: ");
                String tempVehicleYear = scanner.nextLine();
                int vehicleYear = Integer.parseInt(tempVehicleYear);
                if (loan.getVehicleCondition() == VehicleCondition.BARU) {
                    if (vehicleYear < currentYear - 1) {
                        System.out.println("Kendaraan baru tidak boleh lebih tua dari tahun " + (currentYear - 1));
                        continue;
                    }
                }
                loan.setVehicleYear(vehicleYear);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Harap masukkan angka.");
            }
        }

        // Validasi tenor pinjaman (1-6 tahun)
        while (true) {
            try {
                System.out.print("Masukkan tenor pinjaman (1-6 tahun): ");
                int tenor = Integer.parseInt(scanner.nextLine());
                if (tenor >= 1 && tenor <= 6) {
                    loan.setLoanTenor(tenor);
                    break;
                } else {
                    System.out.println("Tenor pinjaman tidak valid. Harap masukkan nilai antara 1 dan 6 tahun.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Harap masukkan angka.");
            }
        }

        // Validasi jumlah pinjaman
        while (true) {
            try {
                System.out.print("Masukkan jumlah pinjaman total (maks 1 miliar): ");
                BigDecimal totalLoan = new BigDecimal(scanner.nextLine());
                if (totalLoan.compareTo(BigDecimal.valueOf(1000000000)) <= 0) {
                    loan.setTotalLoanAmount(totalLoan);
                    break;
                } else {
                    System.out.println("Jumlah pinjaman tidak boleh lebih dari 1 miliar. Silakan coba lagi.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Harap masukkan angka.");
            }
        }

        // Validasi DP sesuai kondisi kendaraan
        while (true) {
            try {
                System.out.print("Masukkan jumlah DP: ");
                BigDecimal dp = new BigDecimal(scanner.nextLine());
                loan.setDownPayment(dp);

                if (loan.getVehicleCondition() == VehicleCondition.BARU) {
                    if (dp.compareTo(loan.getTotalLoanAmount().multiply(BigDecimal.valueOf(0.35))) < 0) {
                        System.out.println("DP untuk kendaraan baru harus minimal 35% dari total pinjaman.");
                    } else {
                        break;
                    }
                } else {
                    if (dp.compareTo(loan.getTotalLoanAmount().multiply(BigDecimal.valueOf(0.25))) < 0) {
                        System.out.println("DP untuk kendaraan bekas harus minimal 25% dari total pinjaman.");
                    } else {
                        break;
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Harap masukkan angka.");
            }
        }

        return loan;
    }

    // Fungsi untuk melakukan request data Loan
    private static Boolean loadLoanFromWebService() {
        Boolean success = true;
        try {
            //String urlString = "https://run.mocky.io/v3/621e2742-309f-493e-b9d4-ff168282b69e"; //ini yang dari soal dan udah mati
            String urlString = "";
            //urlString = "https://run.mocky.io/v3/566afe07-58e1-44a3-b10a-8be6a845c1cf"; //ini yang akan kena valiasi
            urlString = "https://run.mocky.io/v3/7a9e012c-767f-4348-ba6b-d58a2790f540";
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            //baca response
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            in.close();
            connection.disconnect();

            // Parsing JSON
            Gson gson = new Gson();

            Loan loan = gson.fromJson(content.toString(), Loan.class);

            // Output data loan yang di-load dari web service
            System.out.println("Vehicle Type: " + loan.getVehicleType());
            System.out.println("Vehicle Condition: " + loan.getVehicleCondition());
            System.out.println("Loan Year: " + loan.getVehicleYear());
            System.out.println("Loan Tenor: " + loan.getLoanTenor());
            System.out.println("Total Loan: " + loan.getTotalLoanAmount());
            System.out.println("Down Payment: " + loan.getDownPayment());

            try {
                VehicleType.valueOf(loan.getVehicleType().toString());
            } catch (IllegalArgumentException e) {
                System.out.println("Input tidak valid. Harap masukkan 'MOTOR' atau 'MOBIL'.");
                return false;
            }

            try {
                VehicleCondition.valueOf(loan.getVehicleCondition().toString());
            } catch (IllegalArgumentException e) {
                System.out.println("Input tidak valid. Harap masukkan 'BARU' atau 'BEKAS'.");
                return false;
            }

            try {
                int currentYear = LocalDate.now().getYear();
                if (loan.getVehicleCondition() == VehicleCondition.BARU) {
                    if (loan.getVehicleYear() < currentYear - 1) {
                        System.out.println("Kendaraan baru tidak boleh lebih tua dari tahun " + (currentYear - 1));
                        return false;
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Harap masukkan angka.");
                return false;
            }


            try {
                if (loan.getLoanTenor() < 1 && loan.getLoanTenor() > 6) {
                    System.out.println("Tenor pinjaman tidak valid. Harap masukkan nilai antara 1 dan 6 tahun.");
                    return false;
                }
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Harap masukkan angka.");
                return false;
            }

            try {
                if (loan.getTotalLoanAmount().compareTo(BigDecimal.valueOf(1000000000)) > 0) {
                    System.out.println("Jumlah pinjaman tidak boleh lebih dari 1 miliar. Silakan coba lagi.");
                    return false;
                }
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Harap masukkan angka.");
                return false;
            }

            try {

                if (loan.getVehicleCondition() == VehicleCondition.BARU) {
                    if (loan.getDownPayment().compareTo(loan.getTotalLoanAmount().multiply(BigDecimal.valueOf(0.35))) < 0) {
                        System.out.println("DP untuk kendaraan baru harus minimal 35% dari total pinjaman.");
                        return false;
                    }
                } else {
                    if (loan.getDownPayment().compareTo(loan.getTotalLoanAmount().multiply(BigDecimal.valueOf(0.25))) < 0) {
                        System.out.println("DP untuk kendaraan bekas harus minimal 25% dari total pinjaman.");
                        return false;
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid. Harap masukkan angka.");
                return false;
            }


            // hitung hasil berdasarkan loan yang di-load
            LoanCalculatorService calculatorService = new LoanCalculatorService();
            currentLoan = loan;
            calculateYearlyInstallment = calculatorService.calculateYearlyInstallment(currentLoan);

            currentLoan.setCalculateYearlyInstallment(calculateYearlyInstallment);

        } catch (Exception e) {
            System.out.println("Error saat ambil data dari web service: " + e.getMessage());
            return false;
        }
        return success;
    }
}
