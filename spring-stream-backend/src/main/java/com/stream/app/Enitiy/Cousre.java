package com.stream.app.Enitiy;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="Cousre_detials")
public class Cousre {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private  Long Id;
    private  String Title;


//    @OneToMany(mappedBy = "cousre")
//    private List<Vedio> vedio;
}
