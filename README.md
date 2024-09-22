# Credit Simulator

## Deskripsi
Aplikasi konsol untuk menghitung cicilan bulanan kendaraan bermotor. Aplikasi ini mendukung dua jenis kendaraan: Mobil dan Motor, serta dua kondisi kendaraan: Baru dan Bekas. Aplikasi ini menerima input baik secara manual dari pengguna maupun dari file input.

## Fitur
- Input jenis kendaraan (Motor/Mobil).
- Input kondisi kendaraan (Baru/Bekas).
- Input tahun kendaraan.
- Input jumlah pinjaman total.
- Input tenor pinjaman (1-6 tahun).
- Input jumlah uang muka (DP).
- Output cicilan per bulan berdasarkan suku bunga yang meningkat tiap tahun.

## Requirement
Sebelum menjalankan aplikasi, pastikan menginstal:
- Java Development Kit (JDK) 17 atau versi terbaru.
- IntelliJ IDEA (untuk develop dan menjalankan aplikasi).
- Git (untuk meng-clone repository).
- Command Prompt atau Terminal (untuk menjalankan aplikasi dari command line)
- Koneksi internet karena butuh call Mock API
<br>
<br>

## Instalasi dan Setup

### 1. Clone repository project
Clone repository project ke komputer Anda menggunakan perintah berikut:
```bash
https://github.com/lesarosahat/creditsimulator.git
```

<br>

### 2. Buka Project di IntelliJ IDEA
- Buka IntelliJ IDEA.
- Pilih File → Open dan arahkan ke folder project yang telah di-clone.
- Tunggu hingga IntelliJ selesai melakukan indexing dan sync dependencies.

<br>

### 3. Build Project
Untuk membangun project dan menghasilkan folder out/, ikuti langkah berikut:
- Pilih Build → Build Project dari menu utama IntelliJ.
- Pastikan tidak ada error selama proses build.
- Setelah build selesai, folder out/ akan dibuat secara otomatis.

<br>

### 4. Generate JAR File
Untuk menghasilkan JAR executable, ikuti langkah berikut:
- Pilih Maven → Pada Intell
- Klik tombol clean + package.
- Klick tombol running - Output Directory (target/CreditSimulator.jar).

<br>
<br>

## Menjalankan Aplikasi

### 1. Menjalankan dari IntelliJ IDEA
Anda dapat menjalankan aplikasi langsung dari IntelliJ IDEA dengan langkah berikut:
- Pilih Run → Edit Configurations.
- Klik tombol + dan pilih Application.
- Isi konfigurasi sebagai berikut :
  - Name: CreditSimulator 
  - Main class: com.lesaro.creditsimulator.CreditSimulator. 
  - Program arguments: Isi dengan file_inputs.txt jika ingin menggunakan file input, atau biarkan kosong untuk input manual. 
  - Working directory: Pastikan mengarah ke root directory project Anda.
- Klik OK untuk menyimpan konfigurasi.
- Jalankan aplikasi dengan memilih Run → Run 'CreditSimulator' atau klik tombol Run di toolbar.

<br>

### 2. Menjalankan dari Command Line (Windows)
Untuk menjalankan aplikasi dengan input manual, jalankan perintah berikut dari direktori root proyek:


- Menjalankan dengan Input Manual dengan credit_simulator.bat
```bash
.\credit_simulator.bat
```
- Menjalankan dengan Input Manual dengan Command Prompt atau Terminal
```bash
java -jar target\CreditSimulator.jar
```
- Menjalankan dengan Command Prompt pada file jar
```bash
java -jar CreditSimulator.jar
or
java -jar CreditSimulator.jar credit_simulator.txt
```
<br>

### 3. Menjalankan dari Command Line (Windows) dengan File Input
Untuk menjalankan aplikasi dengan file input manual, letakan file dengan nama 'file_inputs.txt' jalankan perintah berikut dari direktori root proyek:
- Menjalankan dengan File Input dengan credit_simulator.bat
```bash
.\credit_simulator.bat file_inputs.txt
```
- Menjalankan dengan File Input dengan Command Prompt atau Terminal
```bash
java -jar target\CreditSimulator.jar file_inputs.txt
```

Untuk setiap line dari file_inputs.txt mewakili 1 variable secara berurutan yaitu:
- Jenis kendaraan (Motor/Mobil).
- Kondisi kendaraan (Baru/Bekas).
- Tahun kendaraan.
- Jumlah pinjaman total.
- Tenor pinjaman (1-6 tahun).
- Jumlah uang muka (DP).

<br>

### 4. Menjalankan Unit Test (IntelliJ IDEA)
Anda dapat menjalankan unit test langsung dari IntelliJ IDEA dengan langkah berikut:
- Pilih Run → Edit Configurations.
- Klik tombol + dan pilih JUnit.
- Isi konfigurasi sebagai berikut :
  - Name: LoanCalculatorServiceTest (Nama bebas, saya sesuaikan dengan nama class testnya saja)
  - class: LoanCalculatorServiceTest.java
  - Klik OK untuk menyimpan konfigurasi.
- Jalankan aplikasi dengan memilih Run → Run 'LoanCalculatorServiceTest' atau klik tombol Run di toolbar.
- Lakukan step yang sama untuk class CreditSimulatorTest.