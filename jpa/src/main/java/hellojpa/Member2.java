package hellojpa;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity

public class Member2 {
    @Id
    private Long id;
    private String name;
    //Getter, Setter …
    public  Long getId()
    {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
