package com.fruit.dao;

import com.fruit.pojo.Fruit;

import java.util.List;

public interface FruitDAO {
    //查询库存列表
    List<Fruit> getFruitList(String keyword, Integer pageNo);

    Fruit getFruitByFid(Integer fid);

    //新增库存
    boolean addFruit(Fruit fruit);

    //修改库存
    void updateFruit(Fruit fruit);

    //根据名称查询特定库存
    Fruit getFruitByFname(String fname);

    //删除特定库存记录
    void delFruit(Integer fid);

    int getFruitCount(String keyword);
}
