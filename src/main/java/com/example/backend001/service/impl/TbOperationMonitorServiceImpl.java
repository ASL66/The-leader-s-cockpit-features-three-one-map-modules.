package com.example.backend001.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.backend001.entity.TbOperationMonitor;
import com.example.backend001.mapper.TbOperationMonitorMapper;
import com.example.backend001.service.TbOperationMonitorService;
import org.springframework.stereotype.Service;

@Service
public class TbOperationMonitorServiceImpl extends ServiceImpl<TbOperationMonitorMapper, TbOperationMonitor> implements TbOperationMonitorService {
}
