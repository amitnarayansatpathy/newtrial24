package com.example.newtrial24;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;




@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class ErrorResponse {
    private String Error;

    public ErrorResponse(String Error) {
        this.Error = Error;
    }

    public String getError() {
        return Error;
    }

    public void setError(String Error) {
        this.Error = Error;
    }
}
