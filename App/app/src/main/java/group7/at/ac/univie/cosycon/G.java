package group7.at.ac.univie.cosycon;

/**
 * Created by Nguyen on 24-May-16.
 */

public class G {
    private String id;
    private String name;
    private ItemTyp ItemTyp;
    private boolean isSensor;
    private GE geraeteinstellung;

    public G(String id, String name, group7.at.ac.univie.cosycon.ItemTyp itemTyp, boolean isSensor, GE geraeteinstellung) {
        this.id = id;
        this.name = name;
        ItemTyp = itemTyp;
        this.isSensor = isSensor;
        this.geraeteinstellung = geraeteinstellung;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public group7.at.ac.univie.cosycon.ItemTyp getItemTyp() {
        return ItemTyp;
    }

    public void setItemTyp(group7.at.ac.univie.cosycon.ItemTyp itemTyp) {
        ItemTyp = itemTyp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSensor() {
        return isSensor;
    }

    public void setSensor(boolean sensor) {
        isSensor = sensor;
    }

    public GE getGeraeteinstellung() {
        return geraeteinstellung;
    }

    public void setGeraeteinstellung(GE geraeteinstellung) {
        this.geraeteinstellung = geraeteinstellung;
    }
}
