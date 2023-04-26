package com.mambo.otto.ottoserver.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * AUTH : SW
 * FUNCTION : 로또 판매점
 * DATE : 2023.05.08
 * UPDATE( AUTH ) : -
 * 
 * <pre>
 * 작성일자 기준, 랭크에 관한 컬럼은 없음
 * </pre>
 * 
 * @Id : AutoIncreament 걸지 않음,
 */

@Getter
@Entity
@Table(name = "tbl_shop")
@NoArgsConstructor
public class Shop {

    @Id
    @Column(name = "in_shop_id")
    private Long inShopId;
    @Column(name = "vc_shop_name")
    private String vcShopName;
    @Column(name = "vc_shop_tel")
    private String vcShopTel;
    @Column(name = "vc_shop_address")
    private String vcShopAddress;
    @Column(name = "vc_shop_lotto_type")
    private String vcShopLottoType;
    @Column(name = "db_shop_lat")
    private double dbShopLat;
    @Column(name = "db_shop_log")
    private double dbShopLog;
    @Column(name = "vc_shop_area_code")
    private String vcShopAreaCode;

    @Builder
    public Shop(Long id, String name, String tel, String address, String type, double lat, double log,
            String area_code) {
        this.inShopId = id;
        this.vcShopName = name;
        this.vcShopTel = tel;
        this.vcShopAddress = address;
        this.vcShopLottoType = type;
        this.dbShopLat = lat;
        this.dbShopLog = log;
        this.vcShopAreaCode = area_code;
    }

}
