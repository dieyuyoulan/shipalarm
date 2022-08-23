package com.example.springbootquant.controllor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootquant.common.Result;
import com.example.springbootquant.entil.Accident;
import com.example.springbootquant.service.AccidentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/accident")
public class AccidentControllor {
    @Resource
    AccidentService accidentService;

    @GetMapping("/display")
    public Result<?> findPage(@RequestParam(defaultValue = "") String search,
                              @RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize){
        if (StringUtils.isNotBlank(search)) {
            QueryWrapper<Accident> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("accidence", search);
            Page<Accident> accidentPage = accidentService.page(new Page<>(pageNum, pageSize), queryWrapper);
            return Result.success(accidentPage);
        }
        Page<Accident> accidentPage = accidentService.page(new Page<>(pageNum, pageSize));
        return Result.success(accidentPage);

    }

    @PostMapping("/acc")
    public Result<?> saveAcc(@RequestBody Accident accident){
        QueryWrapper<Accident> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("accidence", accident.getAccidence());
        int cout = accidentService.count(queryWrapper);
        if(cout == 0){
            accidentService.save(accident);
        }else {
            return Result.error("-1", "该事件已经存在");
        }
        return Result.success();
    }

    @PostMapping("/getAcc")
    public Result<?> getAcc(@RequestBody Accident accident){
        List<Accident> accidentList = accidentService.list();
        return Result.success(accidentList);
    }

    @DeleteMapping("/delete" + "/{id}")
    public Result<?> update(@PathVariable Integer id) {
        accidentService.removeById(id);
        return Result.success();
    }
}
