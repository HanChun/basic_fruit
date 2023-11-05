package com.fruit.dao.impl;

import com.fruit.dao.FruitDAO;
import com.fruit.dao.base.BaseDAO;
import com.fruit.pojo.Fruit;
import java.util.List;

public class FruitDAOImpl extends BaseDAO<Fruit> implements FruitDAO {
    private int primaryKEY ;
    @Override// 获取指定页码的 page，每页显示五条
    public List<Fruit> getFruitList(String keyword,Integer pageNo ) {
        System.out.println("select * from t_fruit where fname like ? or remark like ? limit 5 offset ?","%"+keyword+"%","%"+keyword+"%",(pageNo-1)*5);
        return super.executeQuery("select * from t_fruit where fname like ? or remark like ? limit 5 offset ?","%"+keyword+"%","%"+keyword+"%",(pageNo-1)*5);
    }

    @Override
    public Fruit getFruitByFid(Integer fid) {
        return super.load("select * from t_fruit where fid=?", fid);
    }

    @Override
    public boolean addFruit(Fruit fruit) {
        System.out.println(" primaryKEY : " + primaryKEY );

        String sql = "insert into t_fruit values(?,?,?,?,?)";
        primaryKEY = super.executeUpdate(sql,++primaryKEY,fruit.getFname(),fruit.getPrice(),fruit.getFcount(),fruit.getRemark()) ;
        //insert语句返回的是自增列的值，而不是影响行数
        System.out.println(primaryKEY);
        return primaryKEY>0;
    }

    @Override
    public void updateFruit(Fruit fruit) {
        String sql = "update t_fruit set fname = ? , price = ? , fcount = ? , remark = ? where fid = ? " ;
        super.executeUpdate(sql,fruit.getFname(),fruit.getPrice(),fruit.getFcount(),fruit.getRemark(),fruit.getFid());
    }

    @Override
    public Fruit getFruitByFname(String fname) {
        return super.load("select * from t_fruit where fname like ? ",fname);
    }

    @Override
    public void delFruit(Integer fid) {
        String sql = "delete from t_fruit where fid = ? " ;
        super.executeUpdate(sql,fid);
    }
    @Override
    public int getFruitCount(String keyword){
        return ((Long)super.executeComplexQuery("select count(*) from t_fruit")[0]).intValue();
    }


}