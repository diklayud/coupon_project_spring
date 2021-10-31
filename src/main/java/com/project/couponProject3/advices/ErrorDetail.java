package com.project.couponProject3.advices;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * Class for creating advice
 */
public class ErrorDetail {

    private String error;
    private String description;

}
