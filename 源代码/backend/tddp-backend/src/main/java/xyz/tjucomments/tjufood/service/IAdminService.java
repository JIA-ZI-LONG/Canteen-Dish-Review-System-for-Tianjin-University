// 文件路径: src/main/java/xyz/tjucomments/tjufood/service/IAdminService.java

package xyz.tjucomments.tjufood.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xyz.tjucomments.tjufood.dto.AdminCreateDTO;
import xyz.tjucomments.tjufood.dto.AdminInfoVO;
import xyz.tjucomments.tjufood.dto.AdminLoginDTO;
import xyz.tjucomments.tjufood.dto.AdminPasswordChangeDTO;
import xyz.tjucomments.tjufood.dto.AdminProfileUpdateDTO;
import xyz.tjucomments.tjufood.dto.Result;
import xyz.tjucomments.tjufood.entity.Admin;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
public interface IAdminService extends IService<Admin> {

    AdminInfoVO login(AdminLoginDTO loginForm);

    List<String> getRolesByUsername(String username);

    Result logout(String token);

    /**
     * 【新增】创建管理员账号
     * @param createDTO 包含用户名和姓名的数据
     * @return 操作结果
     */
    Result createAdmin(AdminCreateDTO createDTO);

    /**
     * 更新管理员个人信息
     * @param adminId 管理员ID
     * @param updateDTO 包含要更新的个人信息
     * @return 操作结果
     */
    Result updateProfile(Long adminId, AdminProfileUpdateDTO updateDTO);

    /**
     * 修改管理员密码
     * @param adminId 管理员ID
     * @param changeDTO 包含旧密码和新密码
     * @return 操作结果
     */
    Result changePassword(Long adminId, AdminPasswordChangeDTO changeDTO);
    /**
     * 【新增】更新管理员头像
     * @param adminId 管理员ID
     * @param file 头像文件
     * @return 包含新头像URL的Result
     */
    Result updateAvatar(Long adminId, MultipartFile file);

    /**
     * 【新增】刷新管理员头像URL
     * @param adminId 管理员ID
     * @return 包含新头像URL的Result
     */
    Result refreshAvatarUrl(Long adminId);
}