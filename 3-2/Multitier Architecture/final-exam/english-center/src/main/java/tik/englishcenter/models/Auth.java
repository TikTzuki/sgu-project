package tik.englishcenter.models;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "authorities")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Auth implements GrantedAuthority {
    @Id
    @GeneratedValue
    public Integer id;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    public User user;

    public String authority;

    @Override
    public String getAuthority() {
        return this.authority;
    }

    public Auth(User user, String authority) {
        this.user = user;
        this.authority = authority;
    }
}
