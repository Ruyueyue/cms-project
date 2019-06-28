package org.ruyue.cms.dto;

/**
 * @program: cms-parent
 * @description: 1
 * @author: Ruyue Bian
 * @create: 2019-05-31 15:40
 */
public class TreeDto {
    private int id;
    private String name;
    private int isParent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIsParent() {
        return isParent;
    }

    public void setIsParent(int isParent) {
        this.isParent = isParent;
    }

    public TreeDto(){

    }

    public TreeDto(int id, String name, int isParent) {
        super();
        this.id = id;
        this.name = name;
        this.isParent = isParent;
    }
}
