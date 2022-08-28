package com.shop.entity;

import com.shop.content.Role;
import com.shop.dto.MemberFormDto;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name="member")
@Data
@ToString
public class Member {

    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(unique = true) //유일한 값이 들어가야 한다.
    private String email;

    private String password;

    private String address;

    @Enumerated(EnumType.STRING) //enum이 string 값으로 들어간다.
    private Role role;

    public static Member createMember(MemberFormDto memberFormDto,
                                      PasswordEncoder passwordEncoder)
    {
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(member.getEmail());
        member.setAddress(member.getAddress());
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        member.setRole(Role.USER);
        return member;
    }
}
