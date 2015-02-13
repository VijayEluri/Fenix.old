package net.pkhapps.fenix.core.boundary.rest.support;

import net.pkhapps.fenix.core.entity.FireDepartment;
import net.pkhapps.fenix.core.security.context.CurrentFireDepartment;
import net.pkhapps.fenix.core.security.context.FireDepartmentRetriever;
import net.pkhapps.fenix.core.security.context.NoSuchFireDepartmentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * This filter assumes that the fire department ID can be deduced from the REST request URL like this:
 * {@code /Constants.REST_URL_PREFIX/{fireDepartmentId}/...}. If this is the case, the filter will attempt to look
 * up the correct {@link net.pkhapps.fenix.core.entity.FireDepartment} instance using the
 * {@link net.pkhapps.fenix.core.security.context.FireDepartmentRetriever}, and populate
 * {@link net.pkhapps.fenix.core.security.context.CurrentFireDepartment}. If no fire department is found, this filter
 * will return a 404. If the URL does not match the pattern at all, this filter does nothing.
 * <p>
 * Please note that this filter must be added to the Spring Security filter chain after the security context has been populated.
 */
public class CurrentFireDepartmentAwareRequestFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrentFireDepartmentAwareRequestFilter.class);

    private final FireDepartmentRetriever fireDepartmentRetriever;
    private final CurrentFireDepartment currentFireDepartment;

    @Autowired
    public CurrentFireDepartmentAwareRequestFilter(FireDepartmentRetriever fireDepartmentRetriever, CurrentFireDepartment currentFireDepartment) {
        this.fireDepartmentRetriever = fireDepartmentRetriever;
        this.currentFireDepartment = currentFireDepartment;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("Initializing {}", this);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Optional<Long> fireDepartmentId = extractFireDepartmentId(servletRequest);
        if (fireDepartmentId.isPresent()) {
            Optional<FireDepartment> fireDepartment = fireDepartmentId.flatMap(fireDepartmentRetriever::getFireDepartmentIfPermitted);
            if (fireDepartment.isPresent()) {
                currentFireDepartment.set(fireDepartment.get());
                try {
                    filterChain.doFilter(servletRequest, servletResponse);
                } finally {
                    currentFireDepartment.reset();
                }
            } else {
                LOGGER.debug("{} is not a valid or permitted fire department ID, returning 404", fireDepartmentId.get());
                NoSuchFireDepartmentException.sendResponse((HttpServletResponse) servletResponse);
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    private Optional<Long> extractFireDepartmentId(ServletRequest servletRequest) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        final String servletPath = request.getServletPath();
        if (servletPath.startsWith(Constants.REST_URL_PREFIX) && servletPath.length() > Constants.REST_URL_PREFIX.length()) {
            LOGGER.trace("Looking for fire department ID in path {}", servletPath);
            String p = servletPath.substring(Constants.REST_URL_PREFIX.length());
            String id;
            int firstSlashIx = p.indexOf('/');
            if (firstSlashIx == -1) {
                id = p;
            } else {
                id = p.substring(0, firstSlashIx);
            }
            try {
                return Optional.of(Long.valueOf(id));
            } catch (NumberFormatException ex) {
                LOGGER.debug("'{}' was not a valid number", id);
                // Ignore it
            }
        }
        return Optional.empty();
    }

    @Override
    public void destroy() {
        LOGGER.info("Destroying {}", this);
    }
}
