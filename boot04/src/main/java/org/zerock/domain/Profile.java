package org.zerock.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;


@Getter
@Setter
@ToString(exclude="member")
@Entity
@Table(name="tbl_profile")
@EqualsAndHashCode(of="fname")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fno;

    private String fname;

    private boolean current;

    @ManyToOne
    private Member member;
}
