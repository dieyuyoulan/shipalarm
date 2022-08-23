package com.example.springbootquant.service.mpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springbootquant.VO.Data1;
import com.example.springbootquant.VO.bearVO;
import com.example.springbootquant.entil.Ship;
import com.example.springbootquant.mapper.ShipMapper;
import com.example.springbootquant.service.ShipService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShipServiceImpl extends ServiceImpl<ShipMapper, Ship> implements ShipService {

    @Override
    public Data1 Bear() {
        List<bearVO> bearVOList = new ArrayList<>();
        QueryWrapper<Ship> schedQueryWrapper = new QueryWrapper<>();
        schedQueryWrapper.eq("is_work", "否");
        int count = count(schedQueryWrapper);
        bearVO bearVO = new bearVO();
        bearVO.setName("未派遣船只");
        bearVO.setValue(count);
        bearVOList.add(bearVO);
        QueryWrapper<Ship> schedQueryWrapper1 = new QueryWrapper<>();
        schedQueryWrapper1.eq("is_advent", "是");
        bearVO bearVO1 = new bearVO();
        bearVO1.setName("已到达");
        bearVO1.setValue(count(schedQueryWrapper1));
        bearVOList.add(bearVO1);
        QueryWrapper<Ship> shipQueryWrapper = new QueryWrapper<>();
        shipQueryWrapper.eq("is_work", "是");
        int count1 = count(shipQueryWrapper) - count(schedQueryWrapper);
        bearVO bearVO2 = new bearVO();
        bearVO2.setValue(count1);
        bearVO2.setName("未到达");
        bearVOList.add(bearVO2);
        Data1 data = new Data1();
        data.setBearVOList(bearVOList);
        return data;
    }
}
