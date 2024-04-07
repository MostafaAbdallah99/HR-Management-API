package gov.iti.hr.restcontrollers.utils;

import gov.iti.hr.restcontrollers.beans.PaginationBean;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LinksUtil {

    private LinksUtil() {
    }

    public static <T> Link createSelfLink(UriInfo uriInfo, Class<T> path, String id) {
        return Link.fromUri(uriInfo.getBaseUriBuilder().path(path).path(id).build())
                .rel("self")
                .type("GET")
                .build();
    }

    public static URI createUriAfterPostRequest(UriInfo uriInfo, Class<?> path, String id) {
        return uriInfo.getBaseUriBuilder().path(path).path(id).build();
    }

    public static Link createAbsoluteSelfLink(UriInfo uriInfo) {
        return Link.fromUri(uriInfo.getAbsolutePath())
                .rel("self")
                .type("GET")
                .build();
    }

    public static List<Link> createPaginatedResourceLinks(UriInfo uriInfo, PaginationBean paginationBean, Integer count) {

        Link self = createAbsoluteSelfLink(uriInfo);
        Optional<Link> nextPage = createNextPageLink(uriInfo, paginationBean, count);
        Optional<Link> previousPage = createPreviousPageLink(uriInfo, paginationBean);

        List<Link> links = new ArrayList<>();

        links.add(self);
        nextPage.ifPresent(links::add);
        previousPage.ifPresent(links::add);

        return links;
    }

    private static Optional<Link> createNextPageLink(UriInfo uriInfo, PaginationBean paginationBean, Integer count) {

        if (paginationBean.getOffset() + paginationBean.getLimit() < count) {
            return Optional.of(Link.fromUri(uriInfo.getAbsolutePathBuilder()
                            .queryParam("limit", paginationBean.getLimit())
                            .queryParam("offset", paginationBean.getOffset() + paginationBean.getLimit())
                            .build())
                    .rel("next")
                    .type("GET")
                    .build());
        } else {
            return Optional.empty();
        }
    }

    private static Optional<Link> createPreviousPageLink(UriInfo uriInfo, PaginationBean paginationBean) {
        if (paginationBean.getOffset() > 0) {
            return Optional.of(Link.fromUri(uriInfo.getAbsolutePathBuilder()
                            .queryParam("limit", paginationBean.getLimit())
                            .queryParam("offset", Math.max(0, paginationBean.getOffset() - paginationBean.getLimit()))
                            .build())
                    .rel("previous")
                    .type("GET")
                    .build());
        } else {
            return Optional.empty();
        }
    }
}
