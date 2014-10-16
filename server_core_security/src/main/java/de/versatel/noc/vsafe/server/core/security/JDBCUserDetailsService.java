package de.versatel.noc.vsafe.server.core.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *
 * @author ulrich.stemmer
 */
public class JDBCUserDetailsService implements UserDetailsService{

    public UserDetails loadUserByUsername(String string) throws UsernameNotFoundException {
        
    }
}
