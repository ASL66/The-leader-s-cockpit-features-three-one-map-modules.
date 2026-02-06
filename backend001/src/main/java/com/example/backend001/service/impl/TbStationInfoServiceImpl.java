package com.example.backend001.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.backend001.entity.TbStationInfo;
import com.example.backend001.mapper.TbStationInfoMapper;
import com.example.backend001.service.TbStationInfoService;
import org.springframework.stereotype.Service;

@Service
public class TbStationInfoServiceImpl extends ServiceImpl<TbStationInfoMapper, TbStationInfo> implements TbStationInfoService {
}
