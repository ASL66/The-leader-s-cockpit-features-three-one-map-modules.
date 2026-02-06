package com.example.backend001.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.backend001.entity.TbProjectManagement;
import com.example.backend001.mapper.TbProjectManagementMapper;
import com.example.backend001.service.TbProjectManagementService;
import org.springframework.stereotype.Service;

@Service
public class TbProjectManagementServiceImpl extends ServiceImpl<TbProjectManagementMapper, TbProjectManagement> implements TbProjectManagementService {
}
