package com.zerobase.fastlms.util;


public class PageUtil {

    private long totalCount; //전체개수
    private long pageSize = 10; //한페이지에 나오는 개수
    private long pageBlockSize = 10; //페이지 블럭 개수
    private long pageIndex; //현제 페이지 번호
    private long totalBlockCount; //전체 페이지 블럭 개수
    private long startPage; //시작페이지 번호
    private long endPage; //종료페이지 번호
    private String queryString; //페이지 이동시 전달되는 파라미터

    public PageUtil(long totalCount, long pageSize,long pageIndex, String queryString) {
        this.totalCount = totalCount;
        this.pageIndex = pageIndex;
        this.queryString = queryString;
    }

    public PageUtil(long totalCount, long pageIndex, String queryString, long pageSize, long pageBlockSize) {
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.queryString = queryString;
        this.pageBlockSize = pageBlockSize;
        this.pageIndex = pageIndex;
    }

    public String pager() {
        init();

        StringBuilder sb = new StringBuilder();

        long previousPageIndex = startPage > 1 ? startPage - 1 : 1;
        long nextPageIndex = endPage < totalBlockCount ? endPage + 1 : totalBlockCount;

        String addQueryString = "";
        if (queryString != null && queryString.length() > 0) {
            addQueryString = "&" + queryString;
        }


        sb.append(String.format("<a href='?pageIndex=%d%s'>&lt;&lt;</a>", 1, addQueryString));
        sb.append(System.lineSeparator());
        sb.append(String.format("<a href='?pageIndex=%d%s'>&lt;</a>", previousPageIndex, addQueryString));
        sb.append(System.lineSeparator());

        for (long i = startPage; i <= endPage; i++) {
            if (i == pageIndex) {
                sb.append(String.format("<a class='on' href='?pageIndex=%d%s'>%d</a>", i, addQueryString, i));
            } else {
                sb.append(String.format("<a href='?pageIndex=%d%s'>%d</a>", i, addQueryString, i));
            }
            sb.append(System.lineSeparator());
        }

        sb.append(String.format("<a href='?pageIndex=%d%s'>&gt;</a>", nextPageIndex, addQueryString));
        sb.append(System.lineSeparator());
        sb.append(String.format("<a href='?pageIndex=%d%s'>&gt;&gt;</a>", totalBlockCount, addQueryString));
        sb.append(System.lineSeparator());

        return sb.toString();
    }

    private void init() {

        if (pageIndex < 1) {
            pageIndex = 1;
        }

        if (pageSize < 1) {
            pageSize = 1;
        }

        totalBlockCount = totalCount / pageSize + (totalCount % pageSize > 0 ? 1 : 0);

        startPage = ((pageIndex - 1) / pageBlockSize) * pageBlockSize + 1;

        endPage = startPage + pageBlockSize - 1;
        if (endPage > totalBlockCount) {
            endPage = totalBlockCount;
        }
    }
}
