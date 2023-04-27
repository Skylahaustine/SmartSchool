package com.smartcompany.smartschool.common;

import org.springframework.data.domain.*;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.text.MessageFormat;
import java.util.List;
public final class PaginationUtil {

    private static final String API_VERSION = "v1.0";
    private static final String HEADER_X_TOTAL_COUNT = "X-Total-Count";
    private static final String HEADER_LINK_FORMAT = "<{0}>; rel=\"{1}\"";
    public static final Integer DEFAULT_PAGE_SIZE = 1000;

    private PaginationUtil() {
    }

    /**
     * Generate pagination headers for a Spring Data
     * {@link org.springframework.data.domain.Page} object.
     *
     * @param uriBuilder The URI builder.
     * @param page The page.
     * @param <T> The type of object.
     * @return http header.
     */
    public static <T> HttpHeaders generatePaginationHttpHeaders(UriComponentsBuilder uriBuilder, Page<T> page) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-API-Version", API_VERSION);
        headers.add(HEADER_X_TOTAL_COUNT, Long.toString(page.getTotalElements()));
        int pageNumber = page.getNumber();
        int pageSize = page.getSize();
        StringBuilder link = new StringBuilder();
        if (pageNumber < page.getTotalPages() - 1) {
            link.append(prepareLink(uriBuilder, pageNumber + 1, pageSize, "next"))
                    .append(",");
        }
        if (pageNumber > 0) {
            link.append(prepareLink(uriBuilder, pageNumber - 1, pageSize, "prev"))
                    .append(",");
        }
        link.append(prepareLink(uriBuilder, page.getTotalPages() - 1, pageSize, "last"))
                .append(",")
                .append(prepareLink(uriBuilder, 0, pageSize, "first"));
        headers.add(HttpHeaders.LINK, link.toString());
        return headers;
    }

    private static String prepareLink(UriComponentsBuilder uriBuilder, int pageNumber, int pageSize, String relType) {
        return MessageFormat.format(HEADER_LINK_FORMAT, preparePageUri(uriBuilder, pageNumber, pageSize), relType);
    }

    private static String preparePageUri(UriComponentsBuilder uriBuilder, int pageNumber, int pageSize) {
        return uriBuilder.replaceQueryParam("page", Integer.toString(pageNumber))
                .replaceQueryParam("size", Integer.toString(pageSize))
                .toUriString()
                .replace(",", "%2C")
                .replace(";", "%3B");
    }

    public static <T> HttpHeaders generatePaginationHttpHeaders(MultiValueMap<String, String> param, Page<T> page, String path) {

        UriComponentsBuilder uriBuilder = ServletUriComponentsBuilder.fromCurrentContextPath().path(path);
        uriBuilder = uriBuilder.queryParams(param);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-API-Version", API_VERSION);
        headers.add(HEADER_X_TOTAL_COUNT, Long.toString(page.getTotalElements()));
        int pageNumber = page.getNumber();
        int pageSize = page.getSize();
        StringBuilder link = new StringBuilder();
        if (pageNumber < page.getTotalPages() - 1) {
            link.append(prepareLink(uriBuilder, pageNumber + 1, pageSize, "next"))
                    .append(",");
        }
        if (pageNumber > 0) {
            link.append(prepareLink(uriBuilder, pageNumber - 1, pageSize, "prev"))
                    .append(",");
        }
        link.append(prepareLink(uriBuilder, page.getTotalPages() - 1, pageSize, "last"))
                .append(",")
                .append(prepareLink(uriBuilder, 0, pageSize, "first"));
        headers.add(HttpHeaders.LINK, link.toString());
        return headers;
    }

    public static Pageable createPage(Integer page, Integer size) {
        if (page != null) {
            if (page <= 0) {
                throw APIException.badRequest("page must be greater than 0");
            }
            page = page - 1;
        } else {
            page = 0;
        }

        if (size == null) {
            size = DEFAULT_PAGE_SIZE;
        }

        Pageable pageable = PageRequest.of(page, size);
        return pageable;
    }

    public static Pageable createPage(Integer page, Integer size, Sort sort) {
        if (page != null) {
            if (page <= 0) {
                throw APIException.badRequest("page must be greater than 0");
            }
            page = page - 1;
        } else {
            page = 0;
        }

        if (size == null) {
            size = DEFAULT_PAGE_SIZE;
        }

        Pageable pageable = PageRequest.of(page, size, sort);
        return pageable;
    }

    public static Pageable createUnPaged(Integer page, Integer size) {
        if (page != null && size != null) {
            page = page - 1;
            return PageRequest.of(page, size);
        }
        return Pageable.unpaged();
    }

    public static Pageable createUnPaged(Integer page, Integer size, Sort sort) {
        if (page != null && size != null) {
            page = page - 1;
            return PageRequest.of(page, size, sort);
        }
        return Pageable.unpaged();
    }

    public static Pager<?> paginateList(List<?> list, String reportName, String reportPeriod, Pageable pageable) {

        Page<?> pages = listToPage(list, pageable);

        Pager<List<?>> pagers = new Pager<>();
        pagers.setCode("0");
        pagers.setMessage("Success");
        pagers.setContent(pages.getContent());
        PageDetails details = new PageDetails();
        details.setPage(pages.getNumber() + 1);
        details.setPerPage(pages.getSize());
        details.setTotalElements(pages.getTotalElements());
        details.setTotalPage(pages.getTotalPages());
        details.setReportName(reportName);
        details.setReportPeriod(reportPeriod);
        pagers.setPageDetails(details);
        return pagers;
    }

    public static <T> Page<T> listToPage(List<T> list, Pageable pageable) {
        if (!pageable.isUnpaged()) {
            int start = (int) pageable.getOffset();
            int end = (start + pageable.getPageSize()) > list.size() ? list.size() : (start + pageable.getPageSize());
            return new PageImpl<>(list.subList(start, end), pageable, list.size());
        } else {
            return new PageImpl<>(list, pageable, list.size());
        }
    }

    public static Pager<?> toPager(Page<?> list, String reportName) {
        Pager<List<?>> pagers = new Pager<>();
        pagers.setCode("0");
        pagers.setMessage("Success");
        pagers.setContent(list.getContent());
        PageDetails details = new PageDetails();
        details.setPage(list.getNumber() + 1);
        details.setPerPage(list.getSize());
        details.setTotalElements(list.getTotalElements());
        details.setTotalPage(list.getTotalPages());
        details.setReportName(reportName);
        pagers.setPageDetails(details);
        return pagers;
    }

    public static <T> Pager<List<T>> createListPager(Page<T> list, String reportName) {
        Pager<List<T>> pagers = new Pager<>();
        pagers.setCode("0");
        pagers.setMessage("Success");
        pagers.setContent(list.getContent());
        PageDetails details = new PageDetails();
        details.setPage(list.getNumber() + 1);
        details.setPerPage(list.getSize());
        details.setTotalElements(list.getTotalElements());
        details.setTotalPage(list.getTotalPages());
        details.setReportName(reportName);
        pagers.setPageDetails(details);
        return pagers;
    }

}
