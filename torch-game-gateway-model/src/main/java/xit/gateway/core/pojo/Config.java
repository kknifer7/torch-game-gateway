package xit.gateway.core.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("config")
public class Config {
    @Id
    private String kee;
    private String val;

    public Config() {
    }

    public Config(String kee, String val) {
        this.kee = kee;
        this.val = val;
    }

    public String getKee() {
        return kee;
    }

    public void setKee(String kee) {
        this.kee = kee;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
