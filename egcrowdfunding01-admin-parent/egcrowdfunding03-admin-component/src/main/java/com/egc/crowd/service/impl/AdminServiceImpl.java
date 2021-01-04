package com.egc.crowd.service.impl;

import com.egc.crowd.bean.Admin;
import com.egc.crowd.bean.AdminExample;
import com.egc.crowd.constant.CrowdConstant;
import com.egc.crowd.exception.LoginAcctAlreadyInUseException;
import com.egc.crowd.exception.LoginAcctAlreadyInUseForUpdateException;
import com.egc.crowd.exception.LoginFailedException;
import com.egc.crowd.mapper.AdminMapper;
import com.egc.crowd.service.AdminService;
import com.egc.crowd.util.CrowdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @PROJECT_NAME: crowdfunding
 * @DESCRIPTION:
 * @USER: eugenechow
 * @DATE: 2020/12/24 上午9:54
 */

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public void saveAdmin(Admin admin) {

        // 1.密码加密
        String userPswd = admin.getUserPswd();
        userPswd = CrowdUtil.MD5(userPswd);
        admin.setUserPswd(userPswd);

        // 2.生成创建时间
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String creatTime = dateFormat.format(date);
        admin.setCreateTime(creatTime);

        // 3.执行保存
        try {
            adminMapper.insert(admin);
        } catch (Exception e) {
            e.printStackTrace();

            // 检测当前捕获的异常对象，如果是 DuplicateKeyException 类型说明是账号重复
            if (e instanceof DuplicateKeyException) {
                throw new LoginAcctAlreadyInUseException(CrowdConstant.MESSAGE_LOGIN_ACC_ALREADY_IN_USE);
            }
        }
    }

    @Override
    public List<Admin> getAll() {
        return adminMapper.selectByExample(new AdminExample());
    }

    @Override
    public Admin getAdminByLoginAcct(String loginAcct, String userPswd) {

        // 1.1根据登录账户查询Admin对象
        AdminExample adminExample = new AdminExample();

        // 1.2创建Criteria对象
        AdminExample.Criteria criteria = adminExample.createCriteria();

        // 1.3在Criteria对象中封装查询条件
        criteria.andLoginAcctEqualTo(loginAcct);

        // 1.4调用AdminMapper的方法执行查询
        List<Admin> adminList = adminMapper.selectByExample(adminExample);

        // 2.判断Admin对象是否为空
        if (adminList == null || adminList.size() == 0) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        if (adminList.size() > 1) {
            throw new RuntimeException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
        }

        Admin admin = adminList.get(0);

        // 3.如果admin对象为null抛出异常
        if (admin == null) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        // 4.如果admin对象不为null将数据库密码从admin对象中取出
        String userPswdDB = admin.getUserPswd();

        // 5.将表单提交的明文密码加密
        String userPswdFrom = CrowdUtil.MD5(userPswd);

        // 6.对密码进行比较
        if (!Objects.equals(userPswdFrom, userPswdDB)) {
            // 7.如果比较结果是不一致则抛出异常
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        // 8.如果一致返回admin对象
        return admin;
    }

    @Override
    public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {

        // 1.调用PageHelper的静态方法开启分页功能
        PageHelper.startPage(pageNum,pageSize);

        // 2.执行查询
        List<Admin> adminList = adminMapper.selectAdminByKeyWord(keyword);

        // 3.封装到PageInfo对象中
        return new PageInfo<>(adminList);
    }

    @Override
    public void remove(Integer adminId) {
        adminMapper.deleteByPrimaryKey(adminId);
    }

    @Override
    public Admin getAdminById(Integer adminId) {
        return adminMapper.selectByPrimaryKey(adminId);
    }

    @Override
    public void update(Admin admin) {

        // "Selective"表示有选择的更新，对于null值字段不更新
        try {
            adminMapper.updateByPrimaryKeySelective(admin);
        } catch (Exception e) {
            e.printStackTrace();

            // 检测当前捕获的异常对象，如果是 DuplicateKeyException 类型说明是账号重复
            if (e instanceof DuplicateKeyException) {
                throw new LoginAcctAlreadyInUseForUpdateException(CrowdConstant.MESSAGE_LOGIN_ACC_ALREADY_IN_USE);
            }
        }
    }
}
