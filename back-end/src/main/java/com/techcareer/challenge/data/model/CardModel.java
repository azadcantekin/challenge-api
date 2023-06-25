package com.techcareer.challenge.data.model;

import com.techcareer.challenge.data.enums.CardType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "t_card")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardModel {

    @Id
    @SequenceGenerator(
            name = "card_id_sequence",
            sequenceName = "card_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "card_id_sequence"
    )
    private Integer id;
    private String cardNumber;
    private String expirationDate;
    @Enumerated(EnumType.STRING)
    private CardType cardType;
    private String cvv;
    @ManyToOne(fetch = FetchType.LAZY)
    private UserModel userModel;
}
