package Skin_Clinic_App;

import java.util.List;

class Dermatologist extends Person {
   private List<String> schedule;

   // constructor
   public Dermatologist(String NIC, String name, String email, String phone, List<String> schedule) {
      super(NIC, name, email, phone);
      this.schedule = schedule;
   }

   // to get schedule
   public List<String> getSchedule() {
      return schedule;
   }

}
