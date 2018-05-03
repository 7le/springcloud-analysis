package com.cloud.common.dao;

import com.cloud.common.dao.model.ZuulRouteConfig;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ZuulRouteConfigMapper {
    int deleteByPrimaryKey(String id);

    int insert(ZuulRouteConfig record);

    int insertSelective(ZuulRouteConfig record);

    ZuulRouteConfig selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ZuulRouteConfig record);

    int updateByPrimaryKey(ZuulRouteConfig record);

    List<ZuulRouteConfig> selectByEnabled(Integer enabled);
}