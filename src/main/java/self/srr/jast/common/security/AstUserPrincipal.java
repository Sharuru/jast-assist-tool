package self.srr.jast.common.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import self.srr.jast.model.entity.TblAstRole;
import self.srr.jast.model.entity.TblAstUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Custom user detail entity
 */
public class AstUserPrincipal implements UserDetails {


    private TblAstUser user;

    AstUserPrincipal(TblAstUser user) {
        this.user = user;
    }

    public TblAstUser getUser() {
        return user;
    }

    public void setUser(TblAstUser user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>(user.getRoles().size());

        for (TblAstRole role : user.getRoles()) {
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

