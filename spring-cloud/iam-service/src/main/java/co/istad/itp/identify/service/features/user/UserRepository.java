package co.istad.itp.identify.service.features.user;

import co.istad.itp.identify.service.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User,Long>{

    Optional<User> findByUsername(String username);
}
