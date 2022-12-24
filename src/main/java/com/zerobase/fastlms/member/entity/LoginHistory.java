package com.zerobase.fastlms.member.entity;


import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(
    name="No",
    sequenceName = "HISTORY_SEQ",
    initialValue = 1,
    allocationSize = 1
)

public class LoginHistory{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "No")
    private long seq;

    private String userId;


    private LocalDateTime lastLogin;
    private String clientIp;
    private String userAgent;

}
