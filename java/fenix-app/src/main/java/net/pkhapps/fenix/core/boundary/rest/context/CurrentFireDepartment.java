package net.pkhapps.fenix.core.boundary.rest.context;

import net.pkhapps.fenix.core.boundary.rest.exceptions.NoSuchFireDepartmentException;
import net.pkhapps.fenix.core.entity.FireDepartment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Utility class that is used to bind a {@link net.pkhapps.fenix.core.entity.FireDepartment} to the current thread.
 *
 * @see net.pkhapps.fenix.core.boundary.rest.support.CurrentFireDepartmentAwareRequestFilter
 */
public final class CurrentFireDepartment {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrentFireDepartment.class);
    private static final InheritableThreadLocal<FireDepartment> CURRENT = new InheritableThreadLocal<>();

    private CurrentFireDepartment() {
    }

    /**
     * Returns the {@link net.pkhapps.fenix.core.entity.FireDepartment} bound to the current thread, if any.
     */
    public static Optional<FireDepartment> currentFireDepartment() {
        return Optional.ofNullable(CURRENT.get());
    }

    /**
     * Returns the {@link net.pkhapps.fenix.core.entity.FireDepartment} bound to the current thread, or throws
     * an exception.
     */
    public static FireDepartment requireFireDepartment() throws NoSuchFireDepartmentException {
        return currentFireDepartment().orElseThrow(() -> new NoSuchFireDepartmentException());
    }

    /**
     * Binds the specified {@link net.pkhapps.fenix.core.entity.FireDepartment} to the current thread. Remember
     * to {@link #reset()} it when no longer needed.
     */
    public static void set(FireDepartment fireDepartment) {
        LOGGER.debug("Setting current fire department to {}", fireDepartment);
        CURRENT.set(fireDepartment);
    }

    /**
     * Clears the current fire department set by {@link #set(net.pkhapps.fenix.core.entity.FireDepartment)}.
     */
    public static void reset() {
        LOGGER.debug("Resetting current fire department");
        CURRENT.remove();
    }
}
