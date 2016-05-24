package group7.at.ac.univie.cosycon;

/**
 * Created by Nguyen on 24-May-16.
 */
public class GE
{
    private static int global_id = 0;
    private int id;
    private String name;
    private String einstellung;

    public GE()
    {
        this.id = global_id++;
    }
    public GE(String name, String einstellung )
    {
        this.id = global_id++;
        this.name = name;
        this.einstellung = einstellung;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEinstellung() {
        return einstellung;
    }

    public void setEinstellung(String einstellung) {
        this.einstellung = einstellung;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
