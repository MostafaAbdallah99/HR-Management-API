package gov.iti.hr.restcontrollers.beans;

import lombok.*;

import java.net.URI;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AdaptedLink {
    private String rel;
    private URI uri;
}
