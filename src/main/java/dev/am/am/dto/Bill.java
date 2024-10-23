package dev.am.am.dto;

import org.springframework.stereotype.Component;

import dev.am.am.enums.BillType;
import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class Bill {

    private UUID id;
    private BillType type;
    private String name;
    private Number estimatedValue;
    private Number paidValue;
    private UUID userId;
    private Timestamp createdAt;
    private Timestamp updatedAt;

}
