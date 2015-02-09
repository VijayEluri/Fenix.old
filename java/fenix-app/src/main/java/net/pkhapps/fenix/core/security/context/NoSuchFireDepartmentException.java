package net.pkhapps.fenix.core.security.context;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when an operation is attempted on a fire department that does not exist.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such fire department")
public class NoSuchFireDepartmentException extends RuntimeException {
}