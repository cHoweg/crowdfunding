package com.egc.crowd.service.impl;

import com.egc.crowd.bean.Role;
import com.egc.crowd.bean.RoleExample;
import com.egc.crowd.mapper.RoleMapper;
import com.egc.crowd.service.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @PROJECT_NAME: crowdfunding
 * @DESCRIPTION:
 * @USER: eugenechow
 * @DATE: 2021/1/5 上午11:08
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword) {

        // 1.开启分页功能
        PageHelper.startPage(pageNum,pageSize);

        // 2.执行查询
        List<Role> roleList = roleMapper.selectRoleByKeyword(keyword);

        // 3.封装为PageInfo返回
        return new PageInfo<>(roleList);
    }

    @Override
    public void saveRole(Role role) {
        roleMapper.insert(role);
    }

    @Override
    public void updateRole(Role role) {
        roleMapper.updateByPrimaryKey(role);
    }

    @Override
    public void removeRole(List<Integer> roleIdList) {
        RoleExample roleExample = new RoleExample();

        RoleExample.Criteria criteria = roleExample.createCriteria();

        // delete * from role where id in (5,8,12)
        criteria.andIdIn(roleIdList);

        roleMapper.deleteByExample(roleExample);
    }

}
