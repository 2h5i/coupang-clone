package com.taei.coupangclone.user.entity;

import com.taei.coupangclone.address.entity.Address;
import com.taei.coupangclone.common.entity.BaseEntity;
import com.taei.coupangclone.common.entity.UserRole;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// NoArgsConstructor 는 없으면 @Entity에서 만들어줌. 하지만 접근 제한이 필요하다. 무분별한 생성을 막아 의도하지 않은 엔티티를 만드는 것을 막기 위해 PROTECTED를 붙여 사용
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(nullable = false, length = 10)
    private String userName;

    @Column(nullable = false, length = 200)
    private String password;

    @Column(nullable = false, length = 15)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private UserRole userRole;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Address> addresses = new ArrayList<>();

    @Builder
    public User(String email, String userName, String password, String phone) {
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.phone = phone;
        this.userRole = UserRole.USER;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateUserInfo(String userName, String phone) {
        this.userName = userName;
        this.phone = phone;
    }
}
