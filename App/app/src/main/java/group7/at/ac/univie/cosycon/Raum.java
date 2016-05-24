package group7.at.ac.univie.cosycon;

import java.util.List;

/**
 * Created by Nguyen on 24-May-16.
 */
public class Raum {
    private String name;
    private List<GE> list;

    public Raum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GE> getList() {
        return list;
    }

    public void setList(List<GE> list) {
        this.list = list;
    }
    public void addGE(GE ge)
    {
        list.add(ge);
    }
    public void deletege(GE ge)
    {
        for(int i = 0; i < list.size();i++)
        {
            if(list.get(i).equals(ge))
                list.remove(i);
        }
    }
}
