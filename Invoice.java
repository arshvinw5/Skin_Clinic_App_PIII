package Skin_Clinic_App;

public class Invoice {
   public static void generateInvoice(Appointment appointment, Treatment treatment) {
      double taxRate = 0.025;
      double taxAmount = treatment.getPrice() * taxRate;
      double total = treatment.getPrice() + taxAmount;

      String headerFormat = "| %-20s | %-30s |%n";
      String border = "+----------------------+--------------------------------+";

      System.out.println("\n--- Invoice ---");
      System.out.println(border);
      System.out.printf(headerFormat, "Field", "Details");
      System.out.println(border);
      System.out.printf(headerFormat, "Patient NIC:", appointment.getPatientNIC());
      System.out.printf(headerFormat, "Treatment Type:", treatment.getType());
      System.out.printf(headerFormat, "Price:", "LKR " + treatment.getPrice());
      System.out.printf(headerFormat, "Tax Rate:", (taxRate * 100) + "%");
      System.out.printf(headerFormat, "Tax Amount:", "LKR " + taxAmount);
      System.out.printf(headerFormat, "Total Amount:", "LKR " + Math.round(total * 100.0) / 100.0);
      System.out.println(border);
   }

}
