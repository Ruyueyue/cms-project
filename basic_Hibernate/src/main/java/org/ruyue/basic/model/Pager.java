package org.ruyue.basic.model;

import java.util.List;

/*分页*/

public class Pager<T> {     //T是泛型等价类，适合所有
    private int size;  /*分页大小*/
    private int offset;  /*分页起始页*/
    private long total;   /*总记录*/
    private List<T> datas; /*分页的数据*/

    /*ALT+INSERT，生成get和set*/
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }
}
