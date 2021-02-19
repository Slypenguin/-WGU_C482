package Model;
/**
 * This class handles the extended Part, OutsourcedPart
 * */
public class  OutsourcedPart extends Part{
    private String companyName;
    public OutsourcedPart (int id, String name, double price, int stock, int min, int max, String companyName){
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
/**
 * @return the companyName
 * */
    public String getCompanyName() {return companyName;}
/**
 * @param companyName  the companyName to set
 * */
    public void setCompanyName(String companyName) {this.companyName = companyName;}
}
