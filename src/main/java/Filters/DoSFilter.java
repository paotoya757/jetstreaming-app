package Filters;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.sun.media.jfxmedia.logging.Logger;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.shiro.SecurityUtils;
import org.eclipse.persistence.logging.SessionLog;

/**
 *
 * @author paotoyav
 */

public class DoSFilter implements Filter{
    
    private static final boolean debug = true;
    static final int sess_timeout = 15 * 60 ;
    static final int request_num_limit = 100 ;
    static String loginPath = "/";
    static String mainPagePath = "/app-page.jsp";
    private FilterConfig filterConfig = null;
    
    public DoSFilter() {
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        if (debug) {
            log("DoSFilter:doFilter()");
        }
        
        HttpSession session;
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.addHeader("X-FRAME-OPTIONS", "DENY");
        String requestedPath = req.getRequestURI().substring(
                req.getContextPath().length());
        
        session = req.getSession(false);
        if (session == null) {
            session = req.getSession(true);
            session.setAttribute("lastTime", System.currentTimeMillis());
            session.setAttribute("requestCount", 1);
            session.setMaxInactiveInterval( sess_timeout );
            System.out.println("Session created! User first time entry.");
            resp.sendRedirect(loginPath);
        }
        else{
            long delta = System.currentTimeMillis() - (long) session.getAttribute("lastTime");
            int count = (int) session.getAttribute("requestCount");
            count++;
            
            System.out.println( "req-count : " + count );
            if ( count >= request_num_limit ){
                System.out.println("Usuario fue bloqueado");
                sendProcessingError(new Exception("Bloqueo DDoS"), response);
            }else{
                session.setAttribute("lastTime",System.currentTimeMillis());
                session.setAttribute("requestCount", count);
                chain.doFilter(request, response);
            }
        }
        
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("DoSFilter:Initializing filter");
            }
        }
        System.out.println("FILTRO ESTA ARRIBA");
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("DoSFilter()");
        }
        StringBuffer sb = new StringBuffer("DoSFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }
    
    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);
        
        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>CHAU</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }
    
    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }
    
    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }
    
}
