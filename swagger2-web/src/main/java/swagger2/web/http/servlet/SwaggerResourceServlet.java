package swagger2.web.http.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * <p>swaggerUI servlet类</p>
 * <p>如何使用:注册SwaggerResourceServlet即可</p>
 * <p>
 * 方式1：使用web.xml
 *  <pre>
 *      &lt;servlet&gt;
 *          &lt;servlet-name&gt;SwaggerResourceServlet&lt;/servlet-name&gt;
 *          &lt;servlet-class&gt;swagger.http.servlet.SwaggerResourceServlet&lt;/servlet-class&gt;
 *      &lt;/servlet&gt;
 *      &lt;servlet-mapping&gt;
 *          &lt;servlet-name&gt;SwaggerResourceServlet&lt;/servlet-name&gt;
 *          &lt;url-pattern&gt;/swagger/*&lt;/url-pattern&gt;
 *      &lt;/servlet-mapping&gt;
 </pre></p>
 * 方式2：代码动态注入
 * 	<pre>
 * 	    public class Swagger2UIConfiguration {
 * 	        {@linkplain @Bean}
 *	        public ServletRegistrationBean registrationBean() {
 *	            ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
 *	            servletRegistrationBean.setServlet(new SwaggerResourceServlet());
 *	            servletRegistrationBean.addUrlMappings("/swagger/*");
 *	            return servletRegistrationBean;
 *	         }
 *	     }
 * 	</pre>
 */
public class SwaggerResourceServlet extends HttpServlet {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

    private String resourcesBasePath;

    protected String getFilePath(String fileName) {
        return resourcesBasePath + fileName;
    }

    protected void returnResourceFile(String fileName, String uri, HttpServletResponse response)
            throws ServletException,
            IOException {

        String filePath = getFilePath(fileName);
        if (fileName.endsWith(".png")) {
            byte[] bytes = Utils.readByteArrayFromResource(filePath);
            if (bytes != null) {
                response.getOutputStream().write(bytes);
            }
            return;
        }

        String text = Utils.readFromResource(filePath);
        if (text == null) {
            response.sendRedirect(uri + "/index.html");
            return;
        }
        if (fileName.endsWith(".css")) {
            response.setContentType("text/css;charset=utf-8");
        } else if (fileName.endsWith(".js")) {
            response.setContentType("text/javascript;charset=utf-8");
        }
        response.getWriter().write(text);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();
        String requestURI = request.getRequestURI();

        response.setCharacterEncoding("utf-8");

        if (contextPath == null) { // root context
            contextPath = "";
        }
        String uri = contextPath + servletPath;
        String path = requestURI.substring(contextPath.length() + servletPath.length());
        if (path == null || path.equals("") || path.equals("/")) {
            response.sendRedirect(uri+"/index.html");
            return;
        }
        returnResourceFile(path, uri, response);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        initParameter(config);
    }

    private void initParameter(ServletConfig config) {
        String resourcesPath = config.getInitParameter(ResoucesConstants.KEY_RESOURCEBASEPATH);
        this.resourcesBasePath = (null==resourcesPath?ResoucesConstants.PATH_RESOURCEBASEPATH:resourcesPath);
    }
}


