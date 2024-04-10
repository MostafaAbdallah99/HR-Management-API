package gov.iti.hr.adapter;

import gov.iti.hr.restcontrollers.beans.AdaptedLink;
import jakarta.json.bind.adapter.JsonbAdapter;
import jakarta.ws.rs.core.Link;

import java.util.List;

public class JsonbLinksAdapter implements JsonbAdapter<List<Link>, List<AdaptedLink>> {
    @Override
    public List<AdaptedLink> adaptToJson(List<Link> links) {
        return links.stream()
                .map(link -> new AdaptedLink(link.getRel(), link.getUri()))
                .toList();
    }

    @Override
    public List<Link> adaptFromJson(List<AdaptedLink> adaptedLinks) {
        return adaptedLinks.stream()
                .map(adaptedLink -> Link.fromUri(adaptedLink.getUri())
                        .rel(adaptedLink.getRel())
                        .build())
                .toList();
    }
}
