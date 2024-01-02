//Class representing a single toaster oven product
public class Oven extends Appliance{
  private boolean convection;
  
  public Oven(double initPrice, int initQuantity, int initWattage, String initColor, String initBrand, boolean initConvection){
    super(initPrice, initQuantity, initWattage, initColor, initBrand);
    convection = initConvection;
  }
  
  public String toString(){
    String result = getBrand() + " Toaster ";
    if(convection){
      result += "with convection ";
    }
    
    result += "(" + getColor() + ", " + getWattage() +" watts)";
    
    return result;
  }

  public Object getName() {
    return null;
  }
}