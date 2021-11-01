package tacos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC, force = true)
@Entity
@Table(name = "ingredient")
public class Ingredient {

    @Id
    @Column(length = 5)
    private String id;
    private String name;
    @Column(columnDefinition = "varchar(10)")
    private String type;

}
