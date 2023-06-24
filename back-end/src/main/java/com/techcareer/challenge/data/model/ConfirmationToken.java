package com.techcareer.challenge.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ConfirmationToken {

    @SequenceGenerator(
            name = "confirmation_token_id_sequence",
            sequenceName = "confirmation_token_id_sequence"
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "confirmation_token_id_sequence"
    )
    private Long id;

    @Column(nullable = false)
    private String confirmationToken;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "user_model_id"
    )
    private UserModel userModel;


    public ConfirmationToken(String confirmationToken,
                             LocalDateTime createdAt,
                             LocalDateTime expiresAt,
                             UserModel userModel) {
        this.confirmationToken = confirmationToken;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.userModel = userModel;
    }
}