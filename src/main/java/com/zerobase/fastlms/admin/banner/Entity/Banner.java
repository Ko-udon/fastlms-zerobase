package com.zerobase.fastlms.admin.banner.Entity;

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

public class Banner {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "No")
  private long id;

  private String bannerName;

  private String imgPath;
  private LocalDateTime regDt;

  private long seq;  //정렬 순서

  private String openType; // 오픈 방법
  private String urlName;  // 링크 주소?

  private boolean isPublic; // 공개 여부

  //add
  String filename;
  String urlFilename;

}
