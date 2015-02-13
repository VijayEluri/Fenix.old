package net.pkhapps.fenix.core.boundary.rest;

import net.pkhapps.fenix.core.boundary.rest.dto.UserDTOMapper;
import net.pkhapps.fenix.core.boundary.rest.support.Constants;
import net.pkhapps.fenix.core.boundary.rest.support.NoSuchResourceException;
import net.pkhapps.fenix.core.entity.SystemUser;
import net.pkhapps.fenix.core.entity.SystemUserRepository;
import net.pkhapps.fenix.core.security.context.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * REST controller that returns information about the current user.
 */
@RestController
@RequestMapping(Constants.REST_URL_PREFIX + "me")
class CurrentUserController {

    private final UserDTOMapper userDTOMapper;
    private final SystemUserRepository systemUserRepository;

    @Autowired
    CurrentUserController(UserDTOMapper userDTOMapper, SystemUserRepository systemUserRepository) {
        this.userDTOMapper = userDTOMapper;
        this.systemUserRepository = systemUserRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> get() {
        Optional<Authentication> currentUser = CurrentUser.currentUser();
        if (currentUser.isPresent()) {
            SystemUser systemUser = systemUserRepository.findByEmail(currentUser.get().getName());
            if (systemUser != null) {
                return new ResponseEntity<>(userDTOMapper.toDTO(systemUser), HttpStatus.OK);
            }
        }
        throw new NoSuchResourceException();
    }
}
