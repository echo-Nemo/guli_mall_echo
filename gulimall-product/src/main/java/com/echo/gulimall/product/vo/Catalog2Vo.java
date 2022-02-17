package com.echo.gulimall.product.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Catalog2Vo implements Serializable {

    private String id;

    private String name;

    private String catalog1Id;

    private List<Catalog3Vo> catalog3List;
}
