package ru.skillbox.socialnetwork.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
<<<<<<< HEAD
@Table(name = "cities")
=======
@Table(name = "city")
>>>>>>> s1/t6v2
public class Town implements Platform {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    Long id;
    String name;

    @ManyToOne
<<<<<<< HEAD
  //  @JoinColumn(name ="country_id", columnDefinition= "id", insertable = false, updatable = false)
    Country country;
}
=======
    //  @JoinColumn(name ="country_id", columnDefinition= "id", insertable = false, updatable = false)
    Country country;
}

>>>>>>> s1/t6v2
