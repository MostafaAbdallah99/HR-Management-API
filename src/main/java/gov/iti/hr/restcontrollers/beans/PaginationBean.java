package gov.iti.hr.restcontrollers.beans;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaginationBean {
    @DefaultValue("0")
    @QueryParam("offset")
    private int offset;

    @DefaultValue("10")
    @QueryParam("limit")
    private int limit;
}
