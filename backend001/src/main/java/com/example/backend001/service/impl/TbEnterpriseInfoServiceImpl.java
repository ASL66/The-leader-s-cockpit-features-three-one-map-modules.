package com.example.backend001.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.backend001.entity.TbEnterpriseInfo;
import com.example.backend001.mapper.TbEnterpriseInfoMapper;
import com.example.backend001.service.TbEnterpriseInfoService;
import org.springframework.stereotype.Service;

/**
 * 企业信息服务实现类
 * 继承自MyBatis-Plus的ServiceImpl，提供企业信息的CRUD操作
 */
@Service
public class TbEnterpriseInfoServiceImpl extends ServiceImpl<TbEnterpriseInfoMapper, TbEnterpriseInfo> implements TbEnterpriseInfoService {
}
