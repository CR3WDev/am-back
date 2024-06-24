package dev.am.am.dto.security;

import java.util.List;

public record ResponseListDTO<T>(List<T> list,Long totalRecords) {
}
