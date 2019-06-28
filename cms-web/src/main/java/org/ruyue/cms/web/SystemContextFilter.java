package org.ruyue.cms.web;

import org.ruyue.basic.model.SystemContent;

import javax.servlet.*;
import java.io.IOException;


/**
 * @program: cms-parent
 * @description: 分页
 * @author: Ruyue Bian
 * @create: 2019-05-24 00:38
 */
public class SystemContextFilter implements Filter {
    private Integer pageSize;

    @Override
    public void destroy(){
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        Integer offset=0;
        try {
            offset=Integer.parseInt(req.getParameter("pager.offset"));
        }catch (NumberFormatException e){}
        try {
            SystemContent.setOrder(req.getParameter("order"));
            SystemContent.setSort(req.getParameter("sort"));
            SystemContent.setPageOffset(offset);
            SystemContent.setPageSize(pageSize);
            chain.doFilter(req,resp);
        }finally {
            SystemContent.removeOrder();
            SystemContent.removeSort();
            SystemContent.removePageOffset();
            SystemContent.removePageSize();
        }

    }
    @Override
    public void init(FilterConfig cfg) throws ServletException {
        //若没有写pageSize,则默认为15
        try {
            pageSize=Integer.parseInt(cfg.getInitParameter("pageSize"));
        }catch (NumberFormatException e){
            pageSize=15;
        }
    }
}


