package com.softserve.skillscope.security.payment.model.entity;

import com.softserve.skillscope.security.payment.model.enums.OrderStatus;
import com.softserve.skillscope.sponsor.model.entity.Sponsor;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sponsor_id")
    private Sponsor sponsor;

    private String orderId;

    private String status;

    private String link;

    private LocalDate createDate;

    private LocalDate updateDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "activation")
    private OrderStatus activation;
}
