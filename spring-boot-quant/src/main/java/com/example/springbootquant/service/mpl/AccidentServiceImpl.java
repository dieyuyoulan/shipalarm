package com.example.springbootquant.service.mpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springbootquant.entil.Accident;
import com.example.springbootquant.mapper.AccidentMapper;
import com.example.springbootquant.service.AccidentService;
import org.springframework.stereotype.Service;

@Service
public class AccidentServiceImpl extends ServiceImpl<AccidentMapper, Accident> implements AccidentService {

}
