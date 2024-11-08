package Skin_Clinic_App;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {
   // to hold dermatologists
   private static ArrayList<Dermatologist> dermatologists = new ArrayList<>();
   // to hold treatments
   private static ArrayList<Treatment> treatments = new ArrayList<>();
   // getting data from initializeDermatologistData() function

   // to get appointment by id
   private static HashMap<Integer, Appointment> appointmentsById = new HashMap<>();

   // to get appointment by NIC
   private static HashMap<String, ArrayList<Appointment>> appointmentsByNIC = new HashMap<>();

   // Map to store existing appointments for each dermatologist, date, and time
   private static final Set<String> bookedSlots = new HashSet<>();

   private static Scanner scanner = new Scanner(System.in);
   private static int appointmentCounter = 1;

   public static void main(String[] args) {
      initializeDermatologistData();

      while (true) {
         System.out.println("\n--- Welcome to Skin Clinic System ---");
         System.out.println("1. Book Appointment");
         System.out.println("2. View Appointments by NIC");
         System.out.println("3. Update Appointment");
         System.out.println("4. Generate Invoice");
         System.out.println("5. Exit");
         System.out.print("Please select your option: ");

         try {
            // to get the user input at initial stage
            int choice = Integer.parseInt(scanner.nextLine().trim());

            switch (choice) {
               case 1 -> bookAppointment();
               case 2 -> viewAppointments();
               case 3 -> updateAppointment();
               case 4 -> generateInvoice();
               case 5 -> {
                  System.out.println("Exiting system...");
                  return;
               }
               default -> System.out.println("Invalid choice. Please try again.");
            }
         } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a valid number.");
         }
      }

   }

   private static void initializeDermatologistData() {
      // dermatologists.add(new Dermatologist("123456789V", "Dr Max",
      // "maxWills@gmail.com", "0775829068",
      // List.of("Monday 10.00am - 01.00pm", "Wednesday 02.00pm - 05.00pm", "Friday:
      // 04:00pm - 08:00pm",
      // "Saturday: 09:00am - 01:00pm")));
      // treatments.add(new Treatment("Acne Treatment", 2750.00));
      // treatments.add(new Treatment("Skin Whitening", 7650.00));
      // treatments.add(new Treatment("Mole Removal", 3850.00));
      // treatments.add(new Treatment("Laser Treatment", 12500.00));

      // timeFormatter: a DateTimeFormatter to format time values according to a
      // specified pattern as hh:mma for "10:00AM"
      DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mma");
      List<String> slots = new ArrayList<>();

      // Generate 15-minute slots for each day and time range
      slots.addAll(TimeSlotGenerator.generateTimeSlots("Monday 10:00AM - 01:00PM", timeFormatter));
      slots.addAll(TimeSlotGenerator.generateTimeSlots("Wednesday 02:00PM - 05:00PM", timeFormatter));
      slots.addAll(TimeSlotGenerator.generateTimeSlots("Friday 04:00PM - 08:00PM", timeFormatter));
      slots.addAll(TimeSlotGenerator.generateTimeSlots("Saturday 09:00AM - 01:00PM", timeFormatter));

      // Add dermatologist with 15-minute time slots
      dermatologists.add(new Dermatologist("123456789V", "Dr Max Wills", "maxWills@gmail.com", "0775829068", slots));
      dermatologists
            .add(new Dermatologist("123456789V", "Dr Wimal Waduge", "wimalWaduge@gmail.com", "0775123456", slots));

      // Add treatments
      treatments.add(new Treatment("Acne Treatment", 2750.00));
      treatments.add(new Treatment("Skin Whitening", 7650.00));
      treatments.add(new Treatment("Mole Removal", 3850.00));
      treatments.add(new Treatment("Laser Treatment", 12500.00));

   }

   // booking appointments function

   private static void bookAppointment() {
      System.out.println("Enter Patient NIC:");
      // method to remove white space from user input
      String NIC = scanner.nextLine().trim();

      if (NIC.isEmpty() || NIC.length() < 10) {
         System.out.println("Invalid NIC, Please enter valid NIC number again.");
         return;
      }

      System.out.println("Enter Patient Name:");
      String name = scanner.nextLine().trim();

      System.out.println("Enter Patient Email:");
      String email = scanner.nextLine().trim();

      System.out.println("Enter Patient Phone Number:");
      String phone = scanner.nextLine().trim();
      Patient patient = new Patient(NIC, name, email, phone);

      System.out.println("Please select the available dermatologists:");

      // loop through the dermatologist list to fetch available ones in the list above
      for (int i = 0; i < dermatologists.size(); i++) {
         System.out.println((i + 1) + ". " + dermatologists.get(i).getName());
      }

      // to get the Dermatologist from existing list
      int dermatologistChoice = -1;
      while (dermatologistChoice < 1 || dermatologistChoice > dermatologists.size()) {
         try {
            System.out.println("Select Dermatologist (1 or 2) :");
            dermatologistChoice = Integer.parseInt(scanner.nextLine().trim());
         } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter valid number ?");
         }
      }

      // creating new verbile to get the selected dermatologist
      Dermatologist selectedDermatologist = dermatologists.get(dermatologistChoice - 1);

      List<String> availableSchedules = selectedDermatologist.getSchedule();

      System.out.println("Available Schedule:");
      for (int i = 0; i < availableSchedules.size(); i++) {
         System.out.println((i + 1) + ". " + availableSchedules.get(i));
      }

      int scheduleChoice = -1;
      while (scheduleChoice < 1 || scheduleChoice > availableSchedules.size()) {
         try {
            System.out.println("Select dermatologist's schedule by number:");
            scheduleChoice = Integer.parseInt(scanner.nextLine().trim());
         } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a valid number.");
         }
      }

      // REtrieve selected schedule details
      String selectedSchedule = availableSchedules.get(scheduleChoice - 1);
      // get the list then divide it to part of strings
      String[] scheduleParts = selectedSchedule.split(" ");

      String date = scheduleParts[0];
      String timeRange = scheduleParts[1] + " . " + scheduleParts[3];

      // confirm booking with selected schedule
      System.out.println("You selected :" + date + " " + timeRange);

      // get the schedule from existing list
      // System.out.println("Available Schedule :" +
      // selectedDermatologist.getSchedule());

      // System.out.println("Enter appointment date (Ex: Monday) :");
      // String date = scanner.nextLine().trim();

      // System.out.println("Enter appointment time (Ex : 10.00am) :");
      // String time = scanner.nextLine().trim();

      // Create a unique key for this appointment slot
      String appointmentKey = selectedDermatologist.getName() + "|" + date + "|" + timeRange;

      // Check if the slot is already booked
      if (bookedSlots.contains(appointmentKey)) {
         System.out.println("This slot has already been booked. Please select a different schedule.");
         return;
      }

      // If not booked, proceed with booking and add to bookedSlots
      bookedSlots.add(appointmentKey);

      System.out
            .println("Registration fee is LKR " + Appointment.getRegFee() + ". Please confirm payment (Y/N):");
      String confirmation = scanner.nextLine().trim().toUpperCase();

      if (!confirmation.equals("Y")) {
         System.out.println("Appointment not booked. Registration fee not confirmed.");
         return;
      }

      availableSchedules.remove(scheduleChoice - 1);

      // creating a new appointment object
      Appointment newAppointment = new Appointment(appointmentCounter++, patient, selectedDermatologist, date,
            timeRange);

      // add newly created appointment in appointmentsById hashmap
      appointmentsById.put(newAppointment.getId(), newAppointment);

      // add newly created appointment in appointmentsByNIC hashmap
      appointmentsByNIC.computeIfAbsent(NIC, k -> new ArrayList<>()).add(newAppointment);

      System.out.println("Appointment booked successfully for " + date + " at " + timeRange + ".");
   }

   // view appointments

   // private static void viewAppointments() {
   // System.out.println("Enter Patient NIC # to view appointments");
   // String NIC = scanner.nextLine().trim();

   // // to fetch the appointment by NIC from HashMap
   // ArrayList<Appointment> patientAppointments = appointmentsByNIC.get(NIC);

   // // input field validation
   // if (patientAppointments == null || patientAppointments.isEmpty()) {
   // System.out.println("No appointments found for entered NIC number" + NIC);
   // return;
   // }

   // // to display appointments
   // for (Appointment appointment : patientAppointments) {
   // appointment.displayAppointment();
   // System.out.println();
   // }

   // }

   private static void viewAppointments() {
      System.out.println("\n--- View Appointments ---");
      System.out.println("1. View Appointments by NIC");
      System.out.println("2. Filter Appointments by Date");
      System.out.print("Choose an option (1 or 2): ");

      int choice = -1;
      while (choice != 1 && choice != 2) {
         try {
            choice = Integer.parseInt(scanner.nextLine().trim());
         } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter 1 or 2.");
         }
      }

      if (choice == 1) {
         viewAppointmentsByNIC();
      } else {
         viewAppointmentsByDate();
      }
   }

   // Method to view appointments by NIC
   private static void viewAppointmentsByNIC() {
      System.out.print("Enter Patient NIC to view appointments: ");
      String NIC = scanner.nextLine().trim();

      // Fetch the appointments by NIC from HashMap
      ArrayList<Appointment> patientAppointments = appointmentsByNIC.get(NIC);

      // Check if any appointments are found
      if (patientAppointments == null || patientAppointments.isEmpty()) {
         System.out.println("No appointments found for NIC number " + NIC);
      } else {
         // Display the appointments
         System.out.println("\nAppointments for NIC " + NIC + ":");
         for (Appointment appointment : patientAppointments) {
            appointment.displayAppointment();
            System.out.println();
         }
      }
   }

   // Method to filter appointments by date
   private static void viewAppointmentsByDate() {
      System.out.print("Enter the date to filter appointments (format: yyyy-MM-dd): ");
      String dateInput = scanner.nextLine().trim();

      DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      LocalDate filterDate;

      try {
         filterDate = LocalDate.parse(dateInput, dateFormatter);
      } catch (DateTimeParseException e) {
         System.out.println("Invalid date format! Please use the format yyyy-MM-dd.");
         return;
      }

      boolean found = false;
      System.out.println("\nAppointments on " + dateInput + ":");
      for (Appointment appointment : appointmentsById.values()) {
         if (appointment.getDate().equals(filterDate.toString())) {
            appointment.displayAppointment();
            System.out.println();
            found = true;
         }
      }

      if (!found) {
         System.out.println("No appointments found for the selected date.");
      }
   }

   // update appointments
   public static void updateAppointment() {
      System.out.println("Enter Patient NIC to update appointment :");
      String NIC = scanner.nextLine().trim();

      // check if the NIC has a corresponding appointment
      ArrayList<Appointment> patientAppointments = appointmentsByNIC.get(NIC);

      if (patientAppointments == null || patientAppointments.isEmpty()) {
         System.out.println("No appointment found by entered NIC number " + NIC);
         return;
      }

      // display available appointment for the nic number
      System.out.println("Available Appointments :");
      for (int i = 0; i < patientAppointments.size(); i++) {
         System.out.println((i + 1) + ". Appointment ID :" + patientAppointments.get(i).getId());
         patientAppointments.get(i).displayAppointment();

      }

      int appointmentIndex = -1;
      // -1 is often used as a sentinel value for invalid or unselected options.
      // our valid schedule choices will be positive numbers (1, 2, 3, )
      // -1 indicates that no valid selection has been made yet.
      boolean validAppointmentSelection = false;

      // loop to ensure valid appointment selection
      while (!validAppointmentSelection) {
         System.out.println("Enter the appointment id to update :");
         try {
            // to get the appointment id tp update
            appointmentIndex = Integer.parseInt(scanner.nextLine().trim()) - 1;
            // Adjusting the Index - 1 is subtracted to convert the user’s 1-based input
            // into a 0-based index used by Java lists.
            if (appointmentIndex >= 0 && appointmentIndex < patientAppointments.size()) {
               validAppointmentSelection = true; // valid index, exit loop
            } else {
               System.out.println("Invalid appointment selection. Please choose a number between 1 and "
                     + patientAppointments.size());
            }
         } catch (NumberFormatException e) {
            System.out.println("Invalid input ! Please enter a valid number");
         }

      }

      // Appointment selected, proceed with updating
      // Appointment appointmentToUpdate = patientAppointments.get(appointmentIndex);

      // System.out.println("Enter date to update the appointment (Ex: Monday) :");
      // String newDate = scanner.nextLine().trim();
      // System.out.println("Enter time to update the appointment (Ex: 10.00am) :");
      // String newTime = scanner.nextLine().trim();

      // set the date and time to selected appointment
      // appointmentToUpdate.setDate(newDate);
      // appointmentToUpdate.setTime(newTime);

      // System.out.println("Appointment updated successfully.");

      // after selecting appointment
      Appointment appointmentToUpdate = patientAppointments.get(appointmentIndex);

      // access selected appointment's Dermatologist through the appointment class to
      // update it
      Dermatologist dermatologist = appointmentToUpdate.getDermatologist();

      String oldDate = appointmentToUpdate.getDate();
      String oldTimeRange = appointmentToUpdate.getTime();
      String oldSchedule = oldDate + " " + oldTimeRange;

      // add the old schedule back to dermatologist schedule list again
      if (!dermatologist.getSchedule().contains(oldSchedule)) {
         dermatologist.getSchedule().add(oldSchedule);
      }

      // make a array to store the dermatologist list
      List<String> availableSchedules = dermatologist.getSchedule();

      // display each schedule option as a numbered list
      System.out.println("Available Schedule for" + dermatologist.getName() + " :");
      for (int i = 0; i < availableSchedules.size(); i++) {
         System.out.println((i + 1) + ". " + availableSchedules.get(i));
      }

      // to get dermatologist available schedule what user want to update
      int scheduleChoiceToUpdate = -1;

      while (scheduleChoiceToUpdate < 1 || scheduleChoiceToUpdate > availableSchedules.size()) {
         try {
            System.out.println("Select the new schedule by number :");
            // get the number from user
            scheduleChoiceToUpdate = Integer.parseInt(scanner.nextLine().trim());

         } catch (NumberFormatException e) {
            System.out.println("Invalid input ! Please enter a valid number");

         }
      }

      // Retrieve the selected schedule
      // then split it to make variables to update the new date and time range
      String selectedSchedule = availableSchedules.get(scheduleChoiceToUpdate - 1);
      String[] scheduleParts = selectedSchedule.split(" ");
      String newDate = scheduleParts[0];
      String newTimeRange = scheduleParts[1] + " . " + scheduleParts[3];

      // condition for check if the selected schedule already has an appointment

      // Generate key for new time slot for new slot
      String newAppointmentKey = dermatologist.getName() + "|" + newDate + "|" + newTimeRange;

      if (bookedSlots.contains(newAppointmentKey)) {
         System.out.println("This is booked slot, Please select a different schedule.");

         return;
      }

      // Remove the old slot from bookedSlots
      String oldAppointmentKey = dermatologist.getName() + "|" + oldDate + "|" + oldTimeRange;
      bookedSlots.remove(oldAppointmentKey);

      // Confirm and update the appointment
      System.out.println("You selected: " + newDate + " " + newTimeRange);
      appointmentToUpdate.setDate(newDate);
      appointmentToUpdate.setTime(newTimeRange);

      // new slot as booked
      bookedSlots.add(newAppointmentKey);

      // Optionally, remove the selected new slot from the available schedule
      availableSchedules.remove(scheduleChoiceToUpdate - 1);

      System.out.println("Appointment updated successfully to " + newDate + " at " + newTimeRange + ".");

   }

   // to generate invoice
   public static void generateInvoice() {
      System.out.println("Enter Patient NIC to generate invoice :");
      String NIC = scanner.nextLine().trim();

      // retrieve appointments for the given NIC the assign it to array list
      ArrayList<Appointment> patientAppointments = appointmentsByNIC.get(NIC);

      if (patientAppointments == null || patientAppointments.isEmpty()) {
         System.out.println("No appointment found for NIC " + NIC);
         return;
      }

      // display available appointments for the nic
      for (int i = 0; i < patientAppointments.size(); i++) {
         System.out.println((i + 1) + ". Appointment ID :" + patientAppointments.get(i).getId());
         patientAppointments.get(i).displayAppointment();
      }

      int appointmentIndex = -1;
      boolean validAppointmentSelection = false;

      if (!validAppointmentSelection) {
         System.out.println("Enter the appointment number to generate invoice: ");
         try {
            // to get the appointment store in array list, 1-1=0 to get index 0
            appointmentIndex = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (appointmentIndex >= 0 && appointmentIndex < patientAppointments.size()) {
               validAppointmentSelection = true; // valid index, exit loop
            } else {
               System.out.println(
                     "Invalid appointment selection, pLease choose a number 1 and " + patientAppointments.size());
            }

         } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a valid number.");
         }
      }

      // retrieve the selected appointment for invoicing
      Appointment appointmentToInvoice = patientAppointments.get(appointmentIndex);

      // treatment selection

      System.out.println("Select Treatment :");
      for (int i = 0; i < treatments.size(); i++) {
         // get the type and price from the array list
         System.out.println((i + 1) + ". " + treatments.get(i).getType() + " - LKR " + treatments.get(i).getPrice());
      }

      int treatmentSelected = -1;
      while (treatmentSelected < 1 || treatmentSelected > treatments.size()) {
         try {
            System.out.println("Select a treatment (1 - " + treatments.size() + ") : ");
            // get the number
            treatmentSelected = Integer.parseInt(scanner.nextLine().trim());

         } catch (NumberFormatException e) {
            System.out.println("Invalid input ! Please enter a valid number.");
         }
      }

      // retrieve the selected treatment and generate the invoice

      Treatment treatment = treatments.get(treatmentSelected - 1);
      Invoice.generateInvoice(appointmentToInvoice, treatment);
   }

}
