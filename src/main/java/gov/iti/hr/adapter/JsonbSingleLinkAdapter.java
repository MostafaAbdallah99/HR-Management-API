package gov.iti.hr.adapter;

import gov.iti.hr.restcontrollers.beans.AdaptedLink;
import jakarta.json.bind.adapter.JsonbAdapter;
import jakarta.ws.rs.core.Link;

public class JsonbSingleLinkAdapter implements JsonbAdapter<Link, AdaptedLink> {
    @Override
    public AdaptedLink adaptToJson(Link link) {
        return new AdaptedLink(link.getRel(), link.getUri());
    }

    @Override
    public Link adaptFromJson(AdaptedLink adaptedLink) {
        return Link.fromUri(adaptedLink.getUri()).rel(adaptedLink.getRel()).build();
    }
}
