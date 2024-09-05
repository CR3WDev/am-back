package dev.am.am.models;

import dev.am.am.enums.BillType;
import dev.am.am.enums.StatusEmail;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

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

}
