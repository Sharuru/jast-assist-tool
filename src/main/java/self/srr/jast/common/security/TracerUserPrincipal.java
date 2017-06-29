package self.srr.jast.common.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import self.srr.jast.entity.TblTracerRole;
import self.srr.jast.entity.TblTracerUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Sharuru on 2017/06/29.
 */
public class TracerUserPrincipal implements UserDetails {


    private TblTracerUser user;

    public TracerUserPrincipal(TblTracerUser user) {
        this.user = user;
    }

    public TblTracerUser getUser() {
        return user;
    }

    public void setUser(TblTracerUser user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>(user.getRoles().size());

        for (TblTracerRole role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getRollName()));
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getEnable();
    }
}

