package com.lucastexeira.authuser.adapters.in.controllers.businessservice.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record BusinessServiceResponse (
    UUID id,
    String name,
    BigDecimal price,
    Long duration
){
}
