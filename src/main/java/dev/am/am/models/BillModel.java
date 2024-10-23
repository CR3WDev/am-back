package dev.am.am.models;

import dev.am.am.enums.BillType;
import dev.am.am.enums.StatusEmail;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "bill")
public class BillModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private BillType type;
    private String name;
    private Number estimatedValue;
    private Number paidValue;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;

}
