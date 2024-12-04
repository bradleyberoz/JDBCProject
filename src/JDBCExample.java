import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample {
    static final String DB_URL = "jdbc:sqlite:students.db"; // SQLite connection string

    public static void main(String[] args) throws IOException, CsvValidationException, FileNotFoundException {

      Scanner s = new Scanner(System.in);
      
        // Open a connection
        try (Connection conn = DriverManager.getConnection(DB_URL);
            Statement statement = conn.createStatement()) {
            System.out.println("Connection made...");

            statement.executeUpdate("drop table if exists PATIENT_INFO");
            statement.executeUpdate("create table PATIENT_INFO (name VARCHAR(50), age INTEGER, gender VARCHAR(10), blood VARCHAR(10), condition VARCHAR(50), date_of_admission VARCHAR(20), doctor VARCHAR(50), hospital VARCHAR(50), insurance_provider VARCHAR(50), billing_amount DOUBLE)");

            FileReader filereader = new FileReader("lib/healthcare_dataset.csv");
            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;

            // Skip header row
            csvReader.readNext();

            System.out.println("Enter the number of entries you want to insert: ");
            int number_of_entries = s.nextInt();
            s.nextLine();

            int count = 0;
            while ((nextRecord = csvReader.readNext()) != null && count < number_of_entries) {
               String name = nextRecord[0];
                    int age = Integer.parseInt(nextRecord[1]);
                    String gender = nextRecord[2];
                    String blood = nextRecord[3];
                    String condition = nextRecord[4];
                    String date_of_admission = nextRecord[5];
                    String doctor = nextRecord[6];
                    String hospital = nextRecord[7];
                    String insurance_provider = nextRecord[8];
                    double billing_amount = Double.parseDouble(nextRecord[9]);

                    String sql = "INSERT INTO PATIENT_INFO (name, age, gender, blood, condition, date_of_admission, doctor, hospital, insurance_provider, billing_amount) VALUES ('" + name + "', " + age + ", '" + gender + "', '" + blood + "', '" + condition + "', '" + date_of_admission + "', '" + doctor + "', '" + hospital + "', '" + insurance_provider + "', " + billing_amount + ")";
                    statement.executeUpdate(sql);
                    count++;
            }

                    while(true) 
                    {
                     System.out.println("");
                     System.out.println("Please select an option from below (enter number):");
                     System.out.println("1. Search for a patient by name and age");
                     System.out.println("2. Search for patient(s) by doctor");
                     System.out.println("3. Search for patient(s) by hopsital");
                     System.out.println("4. Update an existing patients record");
                     System.out.println("5. Add a new patient record");
                     System.out.println("6. Exit the program");
                     System.out.println("");
                     
                     int choice = s.nextInt();
                     s.nextLine();

                     if (choice == 1) {

                     System.out.println("Enter the first name of the patient: ");
                     String firstName = s.nextLine();
                     System.out.println("Enter the last name of the patient: ");
                     String lastName = s.nextLine();
                     System.out.println("Enter the age of the patient: ");
                     int ageInput = s.nextInt();
                     s.nextLine();
                     String nameInput = firstName + " " + lastName;
                     
                     ResultSet rs = statement.executeQuery("SELECT * FROM PATIENT_INFO WHERE LOWER(name) = LOWER('" + nameInput + "') AND age = " + ageInput);
                     while (rs.next()) {
                           System.out.println("");
                           System.out.println("Inserted Record: ");
                           System.out.println("Name: " + rs.getString("name"));
                           System.out.println("Age: " + rs.getInt("age"));
                           System.out.println("Gender: " + rs.getString("gender"));
                           System.out.println("Blood Type: " + rs.getString("blood"));
                           System.out.println("Condition: " + rs.getString("condition"));
                           System.out.println("Date of Admission: " + rs.getString("date_of_admission"));
                           System.out.println("Doctor: " + rs.getString("doctor"));
                           System.out.println("Hospital: " + rs.getString("hospital"));
                           System.out.println("Insurance Provider: " + rs.getString("insurance_provider"));
                           System.out.println("Billing Amount: " + rs.getDouble("billing_amount"));
                           System.out.println("------");
                        }
                     } else if (choice == 2) {
                        System.out.println("Enter the first and last name of the doctor: ");
                        String doctorInput = s.nextLine();

                        ResultSet rs = statement.executeQuery("SELECT * FROM PATIENT_INFO WHERE LOWER(doctor) = LOWER('" + doctorInput + "')");
                        while (rs.next()) {
                           System.out.println("");
                           System.out.println("Record Found:");
                           System.out.println("Name: " + rs.getString("name"));
                           System.out.println("Age: " + rs.getInt("age"));
                           System.out.println("Gender: " + rs.getString("gender"));
                           System.out.println("Blood Type: " + rs.getString("blood"));
                           System.out.println("Condition: " + rs.getString("condition"));
                           System.out.println("Date of Admission: " + rs.getString("date_of_admission"));
                           System.out.println("Doctor: " + rs.getString("doctor"));
                           System.out.println("Hospital: " + rs.getString("hospital"));
                           System.out.println("Insurance Provider: " + rs.getString("insurance_provider"));
                           System.out.println("Billing Amount: " + rs.getDouble("billing_amount"));
                           System.out.println("------");
                        }
                     } else if (choice == 3) {
                        System.out.println("Enter the name of the hospital: ");
                        String hospitalInput = s.nextLine();

                        ResultSet rs = statement.executeQuery("SELECT * FROM PATIENT_INFO WHERE LOWER(hospital) = LOWER('" + hospitalInput + "')");
                        while (rs.next()) {
                              System.out.println("");
                              System.out.println("Inserted Record: ");
                              System.out.println("Name: " + rs.getString("name"));
                              System.out.println("Age: " + rs.getInt("age"));
                              System.out.println("Gender: " + rs.getString("gender"));
                              System.out.println("Blood Type: " + rs.getString("blood"));
                              System.out.println("Condition: " + rs.getString("condition"));
                              System.out.println("Date of Admission: " + rs.getString("date_of_admission"));
                              System.out.println("Doctor: " + rs.getString("doctor"));
                              System.out.println("Hospital: " + rs.getString("hospital"));
                              System.out.println("Insurance Provider: " + rs.getString("insurance_provider"));
                              System.out.println("Billing Amount: " + rs.getDouble("billing_amount"));
                              System.out.println("------");
                        }
                     } else if (choice == 4) {
                        // Choice 4: Update an existing patient's record
                        System.out.println("Enter the first name of the patient to update: ");
                        String firstName = s.nextLine();
                        System.out.println("Enter the last name of the patient to update: ");
                        String lastName = s.nextLine();
                        String nameInput = firstName + " " + lastName;
                        
                        // Check if the patient exists
                        ResultSet rsCheck = statement.executeQuery("SELECT * FROM PATIENT_INFO WHERE LOWER(name) = LOWER('" + nameInput + "')");
                        if (rsCheck.next()) {
                           System.out.println("Which field would you like to update?");
                           System.out.println("1. Age");
                           System.out.println("2. Gender");
                           System.out.println("3. Blood Type");
                           System.out.println("4. Condition");
                           System.out.println("5. Date of Admission");
                           System.out.println("6. Doctor");
                           System.out.println("7. Hospital");
                           System.out.println("8. Insurance Provider");
                           System.out.println("9. Billing Amount");
                           int updateChoice = s.nextInt();
                           s.nextLine(); // Consume newline

                           String field = "";
                           String newValue = "";

                           if (updateChoice == 1) {
                                 field = "age";
                                 System.out.println("Enter new age: ");
                                 newValue = s.nextLine();
                           } else if (updateChoice == 2) {
                                 field = "gender";
                                 System.out.println("Enter new gender: ");
                                 newValue = s.nextLine();
                           } else if (updateChoice == 3) {
                                 field = "blood";
                                 System.out.println("Enter new blood type: ");
                                 newValue = s.nextLine();
                           } else if (updateChoice == 4) {
                                 field = "condition";
                                 System.out.println("Enter new condition: ");
                                 newValue = s.nextLine();
                           } else if (updateChoice == 5) {
                                 field = "date_of_admission";
                                 System.out.println("Enter new date of admission: ");
                                 newValue = s.nextLine();
                           } else if (updateChoice == 6) {
                                 field = "doctor";
                                 System.out.println("Enter new doctor: ");
                                 newValue = s.nextLine();
                           } else if (updateChoice == 7) {
                                 field = "hospital";
                                 System.out.println("Enter new hospital: ");
                                 newValue = s.nextLine();
                           } else if (updateChoice == 8) {
                              field = "insurance_provider";
                              System.out.println("Enter new insurance provider: ");
                              newValue = s.nextLine();
                          } else if (updateChoice == 9) {
                              field = "billing_amount";
                              System.out.println("Enter new billing amount: ");
                              newValue = s.nextLine();
                          } else {
                              System.out.println("Invalid choice.");
                              continue;
                          }
                  
                          // Construct and execute the update statement
                          String updateSql = "";
                          if (field.equals("age") || field.equals("billing_amount")) {
                              updateSql = "UPDATE PATIENT_INFO SET " + field + " = " + newValue + " WHERE LOWER(name) = LOWER('" + nameInput + "')";
                          } else {
                              updateSql = "UPDATE PATIENT_INFO SET " + field + " = '" + newValue + "' WHERE LOWER(name) = LOWER('" + nameInput + "')";
                          }
                          statement.executeUpdate(updateSql);
                          System.out.println("Record updated successfully.");
                  
                      } else {
                          System.out.println("Patient not found.");
                      }

                     } else if (choice == 5) {
                        // Choice 5: Add a new patient record
                        System.out.println("Enter patient details:");
                        System.out.println("Name: ");
                        String name = s.nextLine();
                        System.out.println("Age: ");
                        int age = s.nextInt();
                        s.nextLine(); // Consume newline
                        System.out.println("Gender: ");
                        String gender = s.nextLine();
                        System.out.println("Blood Type: ");
                        String blood = s.nextLine();
                        System.out.println("Condition: ");
                        String condition = s.nextLine();
                        System.out.println("Date of Admission: ");
                        String date_of_admission = s.nextLine();
                        System.out.println("Doctor: ");
                        String doctor = s.nextLine();
                        System.out.println("Hospital: ");
                        String hospital = s.nextLine();
                        System.out.println("Insurance Provider: ");
                        String insurance_provider = s.nextLine();
                        System.out.println("Billing Amount: ");
                        double billing_amount = s.nextDouble();
                        s.nextLine(); // Consume newline

                        String insertSql = "INSERT INTO PATIENT_INFO (name, age, gender, blood, condition, date_of_admission, doctor, hospital, insurance_provider, billing_amount) VALUES ('" + name + "', " + age + ", '" + gender + "', '" + blood + "', '" + condition + "', '" + date_of_admission + "', '" + doctor + "', '" + hospital + "', '" + insurance_provider + "', " + billing_amount + ")";
                        statement.executeUpdate(insertSql);
                        System.out.println("New patient record added successfully.");
                     } else if (choice == 6) {
                        break;
                     } else {
                        System.out.println("Invalid input. Please try again.");
                  } 
            }

            csvReader.close();
            System.out.println("Data inserted successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
