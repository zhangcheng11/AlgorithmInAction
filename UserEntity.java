package com.dfssi.zuul.entity;

import com.dfssi.dataplatform.cloud.common.entity.BaseVO;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Data
public class UserEntity extends BaseVO implements UserDetails, Serializable {
    private String uRoleName;

    @Size(max = 50,message = "字符最长为50")
    private String id;

    @Size(min=1,max = 20,message = "字符最长为20")
    @NotBlank(message = "姓名不能为空")
    private String name;

    @Size(min=1,max=20,message = "字符最长为20")
    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]{4,15}$",message = "用户名需为由字母开头5-16位字符（5-16位）")
    private String uName;

    @Size(max=50,message = "字符最长为50")
    @NotBlank(message = "密码不能为空")
    private String uPsword;

    private Date createTime;

    @Min(value = 0,message = "只能为0或1")
    @Max(value = 1,message = "只能为0或1")
    private String isDelete;

    @Size(max=1000)
    @NotBlank(message = "用户角色不能为空")
    private String uRole;

    private String menuData;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.uPsword;
    }

    @Override
    public String getUsername() {
        return this.uName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}