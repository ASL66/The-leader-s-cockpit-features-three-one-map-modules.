package com.example.backend001.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.backend001.entity.TbBasicInfo;
import com.example.backend001.mapper.TbBasicInfoMapper;
import com.example.backend001.service.TbBasicInfoService;
import org.springframework.stereotype.Service;

@Service
public class TbBasicInfoServiceImpl extends ServiceImpl<TbBasicInfoMapper, TbBasicInfo> implements TbBasicInfoService {
}
