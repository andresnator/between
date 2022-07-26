package tech.between.interview.core.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDescription {
    private String id;
    private String name;
    private BigDecimal price;
    private boolean availability;
}
