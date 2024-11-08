package Skin_Clinic_App;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TimeSlotGenerator {

   // Method to generate 15-minute intervals from a time range string
   public static List<String> generateTimeSlots(String schedule, DateTimeFormatter timeFormatter) {
      List<String> slots = new ArrayList<>();

      // Split the input schedule into parts "Day StartTime - EndTime"
      String[] parts = schedule.split(" ");
      String day = parts[0];
      LocalTime startTime = LocalTime.parse(parts[1], timeFormatter);
      LocalTime endTime = LocalTime.parse(parts[3], timeFormatter);

      // Loop to create 15-minute intervals from startTime to endTime
      while (startTime.isBefore(endTime)) {
         // Calculate the end of the 15-minute slot
         LocalTime nextSlot = startTime.plusMinutes(15);

         // Format the time slot as "Day StartTime - EndTime" and add to the list
         slots.add(day + " " + startTime.format(timeFormatter) + " - " + nextSlot.format(timeFormatter));

         // Move the start time to the next slot
         startTime = nextSlot;
      }

      return slots;
   }

}
