package booking.hostel.config;

import booking.hostel.entity.User;
import booking.hostel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("myUserDetailService")
@RequiredArgsConstructor
public class MyUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        var varUser = userRepository.findFirstByPhone(phone);
        if (varUser.isEmpty()) {
            throw new UsernameNotFoundException("User with phone: " + phone + " not found");
        }
        User user = varUser.get();
        GrantedAuthority roleAuthority = new SimpleGrantedAuthority(user.getRole().toString());
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(roleAuthority);
        return buildUserForAuthentication(user, grantedAuthorities);
    }

    private org.springframework.security.core.userdetails.User buildUserForAuthentication(User user, List<GrantedAuthority> grantedAuthorities) {
        return new org.springframework.security.core.userdetails.User(user.getPhone(), user.getPassword(), true, true, true, true, grantedAuthorities);
    }
}
