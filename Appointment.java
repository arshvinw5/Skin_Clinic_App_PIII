package Skin_Clinic_App;

class Appointment {
   private static double Reg_Fee = 500.00;
   private int id;
   private Patient patient;
   private Dermatologist dermatologist;
   private String date;
   private String time;

   // constructor

   public Appointment(int id, Patient patient, Dermatologist dermatologist, String date, String time) {
      this.id = id;
      this.patient = patient;
      this.dermatologist = dermatologist;
      this.date = date;
      this.time = time;
   }

   // to get the time
   public int getId() {
      return id;
   }

   // to get Patient's NIC
   public String getPatientNIC() {
      return patient.getNIC();
   }

   // to update time and date

   public void setDate(String date) {
      this.date = date;
   }

   public void setTime(String time) {
      this.time = time;
   }

   public Dermatologist getDermatologist() {
      return dermatologist;
   }

   public void displayAppointment() {
      String headerFormat = "| %-20s | %-30s |%n";
      String dataFormat = "| %-20s | %-30s |%n";
      String border = "+----------------------+--------------------------------+";

      System.out.println("\n--- Appoints " + id + "  ---");
      System.out.println(border);
      System.out.printf(headerFormat, "Field", "Patient Details");
      System.out.println(border);
      System.out.printf(dataFormat, "Appointment ID:", id);
      System.out.printf(dataFormat, "Patient Name:", patient.getName());
      System.out.printf(dataFormat, "Dermatologist:", dermatologist.getName());
      System.out.printf(dataFormat, "Date:", date);
      System.out.printf(dataFormat, "Time:", time);
      System.out.printf(dataFormat, "Registration Fee:", "LKR " + Reg_Fee);
      System.out.println(border);
   }

}
