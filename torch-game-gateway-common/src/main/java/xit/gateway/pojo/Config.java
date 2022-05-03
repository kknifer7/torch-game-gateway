package xit.gateway.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("config")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Config {
    @Id
    private Long id;
    private String name;
    private String kee;
    private String val;
    private String remark;
}
