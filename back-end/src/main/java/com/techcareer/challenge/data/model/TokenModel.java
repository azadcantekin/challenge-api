package com.techcareer.challenge.data.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "t_token")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenModel {
    @Id
    @SequenceGenerator(
            name = "token_id_sequence",
            sequenceName = "token_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "token_id_sequence"
    )
    private Integer id;
    private String token;
    @ManyToOne()
    private UserModel userModel;
}
