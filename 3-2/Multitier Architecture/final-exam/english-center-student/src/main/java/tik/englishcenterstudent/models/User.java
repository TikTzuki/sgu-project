package tik.englishcenterstudent.models;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends org.springframework.security.core.userdetails.User {
    @Id
    public String username;

    public String name;

    public String phone;

    public String identity;

    public String password;

    public Boolean enabled;

    @OneToMany(mappedBy = "user")
    public List<Auth> auths;

    public User() {
        super("tik", "password", new ArrayList<>());
    }

    public User(String username, String password, Collection<Auth> authorities) {
        super(username, password, authorities);
        this.username = username;
        this.password = password;
        this.auths = (List<Auth>) authorities;
        this.enabled = true;
    }

    public User(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.username = username;
    }

    public void setAuths(List<Auth> auths) {
        this.auths = auths;
    }

    public List<Auth> getAuths() {
        return this.auths;
    }
}
