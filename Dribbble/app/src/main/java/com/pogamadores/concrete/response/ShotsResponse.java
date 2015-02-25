package com.pogamadores.concrete.response;

import com.google.gson.annotations.SerializedName;
import com.pogamadores.concrete.domain.Shot;

import java.util.List;

/**
 * Created by imonteiro on 1/27/15.
 */
public class ShotsResponse
{
    @SerializedName("page")
    private int page;
    @SerializedName("per_page")
    private int perPage;
    @SerializedName("pages")
    private int pages;
    @SerializedName("total")
    private int total;
    @SerializedName("shots")
    private List<Shot> shots;


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Shot> getShots() {
        return shots;
    }

    public void setShots(List<Shot> shots) {
        this.shots = shots;
    }
}
