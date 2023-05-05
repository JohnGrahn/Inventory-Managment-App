/**
 * John Grahn
 * Student ID #000961901
 * C482 Software 1
 */
package grahn.c482.Model;

/**
 * Outsourced class based on UML diagram given in course material
 */

public class Outsourced extends Part{
    private String  companyName;
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     *
     * @return the companyName
     */

    public String getCompanyName(){
        return this.companyName;
    }

    /**
     *
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
