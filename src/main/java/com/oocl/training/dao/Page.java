package com.oocl.training.dao;
import java.util.List;

public class Page<T> {
    // 当前页码
    private int page;
    // 每页条数
    private int size;
    // 总条数
    private long total;
    // 总页数
    private int totalPages;
    // 当前页数据
    private List<T> data;

    public Page(int page, int size, long total, List<T> data) {
        this.page = page;
        this.size = size;
        this.total = total;
        this.data = data;
        // 计算总页数
        this.totalPages = (int) Math.ceil((double) total / size);
    }

    // Getter 方法
    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public long getTotal() {
        return total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<T> getData() {
        return data;
    }
}