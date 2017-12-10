package newsportal.service;

import newsportal.model.Writer;
import newsportal.repository.WriterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    WriterRepository writerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Writer writer = writerRepository.findByName(username);
        if (writer == null) {
            throw new UsernameNotFoundException("No such user: " + username);
        }

        return new User(
                writer.getName(),
                writer.getPassword(),
                true,
                true,
                true,
                true,
                Arrays.asList(new SimpleGrantedAuthority("WRITER")));
    }
}
