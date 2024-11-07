package Skin_Clinic_App;

/**
 * Person
 */
abstract class Person {
   protected String NIC;
   protected String name;
   protected String email;
   protected String phone;

   // constructor
   public Person(String NIC, String name, String email, String phone) {
      this.NIC = NIC;
      this.name = name;
      this.email = email;
      this.phone = phone;
   }

   // creating method to get name and NIC #

   public String getName() {
      return name;
   }

   public String getNIC() {
      return NIC;
   }

}