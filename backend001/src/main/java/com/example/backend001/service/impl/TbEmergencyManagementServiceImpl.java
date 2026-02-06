package com.example.backend001.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.backend001.entity.TbEmergencyManagement;
import com.example.backend001.mapper.TbEmergencyManagementMapper;
import com.example.backend001.service.TbEmergencyManagementService;
import org.springframework.stereotype.Service;

@Service
public class TbEmergencyManagementServiceImpl extends ServiceImpl<TbEmergencyManagementMapper, TbEmergencyManagement> implements TbEmergencyManagementService {
}
