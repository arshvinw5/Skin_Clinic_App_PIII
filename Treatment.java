package Skin_Clinic_App;

class Treatment {
   private String type;
   private double price;

   // constructors

   public Treatment(String type, double price) {
      this.type = type;
      this.price = price;
   }

   // to get type and price
   public String getType() {
      return type;
   }

   public double getPrice() {
      return price;
   }

}
