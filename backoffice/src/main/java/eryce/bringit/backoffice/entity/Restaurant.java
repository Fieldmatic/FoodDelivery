package eryce.bringit.backoffice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @NotNull
    private String name;

    @NotNull
    private String address;
    @NotNull
    private String phone;

    @NotNull
    private String email;
    @NotNull
    private Double deliveryRate;

    @OneToMany(mappedBy = "restaurant", cascade = {CascadeType.ALL})
    private List<MenuItem> menuItems;
}
