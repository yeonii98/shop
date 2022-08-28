package com.shop.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
//DTO : Data Transfer Object = 데이터 전달용 객체
//데이터를 주고받을 때 엔티티 클래스 자체를 반환하면 안 되고 데이터 전달용 객체를 생성해서 사용한다.
public class ItemDto {
    private Long id;
    private String itemNm;
    private Integer price;
    private String itemDetail;
    private String sellStatCd;
    private LocalDateTime regTime;
    private LocalDateTime updateTime;
}
