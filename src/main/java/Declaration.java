import java.util.Map;
import java.util.HashMap;
import java.util.Random;

/*
 * This is the class which represents a user's declaration of financial support for an alien.
 * All of the relevant information is stored here before being sent to the Workflow/DB.
 * Basic validation also occurs here to ensure proper formatting.
 */
public class Declaration {
    public String date = null;
    public String name = null;
    public String email = null;
    public int durationOfSupport = -1; //days
    public int applicantNumber = -1;
    public String immigrantName;
    public int alienNumber = -1;
    public Boolean isExpired = true;
    public int declarationID = -1;

    static Random rand = new Random();
    
    public static Declaration create(String date, String name, String email, int durationOfSupport,
    int applicantNumber, String immigrantName, int alienNumber, Boolean isExpired, int declarationID) {

        //use the constructor to create a declaration object and return it
        //randomly generate a unique declarationID to go along with it
        Declaration dd = new Declaration(date, name, email, durationOfSupport, applicantNumber, immigrantName, alienNumber, isExpired, rand.nextInt(5000));
        return dd;
    }

    public Declaration(String date, String name, String email, int durationOfSupport,
        int applicantNumber, String immigrantName, int alienNumber, Boolean isExpired, int declarationID) {
            this.date = date; this.name = name; this.email = email;
            this.durationOfSupport = durationOfSupport; this.applicantNumber = applicantNumber;
            this.immigrantName = immigrantName;  this.alienNumber = alienNumber;
            this.isExpired = isExpired; this.declarationID = declarationID;           
        }

    //Does basic checks to ensure the user inputted information is correctly formatted.
    public Boolean validate(){
        return false;
    }

    //returns a Declaration object from the database (held in the Main Screen, accessed through here)
    public static Declaration getFromDB(Integer id){
        for(Declaration d : MainScreen.database){
            if(d.declarationID == id){
                return d;
            }
        }
        return null;
    }

    public static void addToDB(Declaration d){
        MainScreen.database.add(d);
    }

    public static void removeFromDB(Integer id){
        MainScreen.database.remove(getFromDB(id));
    }
}
