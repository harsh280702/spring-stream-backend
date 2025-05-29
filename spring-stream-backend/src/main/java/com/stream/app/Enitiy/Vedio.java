package com.stream.app.Enitiy;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Video_detials")
public class Vedio {
    @Id
    private String Id;
    private String Title;
    private  String contentType;
    private String filepath;

//    @ManyToOne
//    private Cousre cousre;


}
