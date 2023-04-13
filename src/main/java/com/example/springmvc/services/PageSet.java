package com.example.springmvc.services;

import org.springframework.stereotype.Service;

@Service
public class PageSet {
    private int currentPage;
    private static final int SIZE = 10;
    private String url = "";
    private String previousPage = "#";
    private String nextPage = "#";

    private int pageTotal;


    public int getCurrentPage() {return this.currentPage;}
    public void setCurrentPage(int currentPage) {this.currentPage = currentPage;}

    public int getSIZE() {return SIZE;}

    public int getPageTotal() {
        return pageTotal;
    }

    public PageSet setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public PageSet setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getPreviousPage() {
        return previousPage;
    }

    public PageSet setPreviousPage(String previousPage) {
        this.previousPage = previousPage;
        return this;
    }

    public String getNextPage() {
        return nextPage;
    }

    public PageSet setNextPage(String nextPage) {
        this.nextPage = nextPage;
        return this;
    }
}
