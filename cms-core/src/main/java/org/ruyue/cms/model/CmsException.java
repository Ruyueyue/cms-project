package org.ruyue.cms.model;

/**
 * @program: cms-parent
 * @description: 异常
 * @author: Ruyue Bian
 * @create: 2019-05-20 23:29
 */
public class CmsException extends RuntimeException{

    private static final long serialVersionUID=1L;

    public CmsException() {
        super();
    }

    public CmsException(String message) {
        super(message);
    }

    public CmsException(String message, Throwable cause) {
        super(message, cause);
    }

    public CmsException(Throwable cause) {
        super(cause);
    }
}
