package gov.iti.hr.restcontrollers.adapter;

import gov.iti.hr.restcontrollers.beans.AdaptedLink;
import jakarta.ws.rs.core.Link;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

public class JaxbLinkAdapter extends XmlAdapter<AdaptedLink, Link> {

    @Override
    public Link unmarshal(AdaptedLink adaptedLink) {
        return Link.fromUri(adaptedLink.getUri()).rel(adaptedLink.getRel()).build();
    }

    @Override
    public AdaptedLink marshal(Link link) {
        return new AdaptedLink(link.getRel(), link.getUri());
    }
}