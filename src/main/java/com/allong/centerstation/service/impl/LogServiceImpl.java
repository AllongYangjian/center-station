package com.allong.centerstation.service.impl;

import com.allong.centerstation.domain.entity.Log;
import com.allong.centerstation.mapper.LogMapper;
import com.allong.centerstation.service.LogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统日志 服务实现类
 * </p>
 *
 * @author 杨建
 * @since 2020-12-09
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

}
