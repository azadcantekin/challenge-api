package com.techcareer.challenge.service;

import com.techcareer.challenge.data.dto.RoleDto;

import java.util.List;

public interface RoleService {
    boolean addRole(RoleDto roleDto);
    RoleDto getRole(Integer roleId);
    List<RoleDto> getAllRoles();
    void deleteRole(Integer roleId);
}
