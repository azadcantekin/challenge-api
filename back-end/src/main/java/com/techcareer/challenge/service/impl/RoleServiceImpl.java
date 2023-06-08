package com.techcareer.challenge.service.impl;

import com.techcareer.challenge.data.dto.RoleDto;
import com.techcareer.challenge.data.model.RoleModel;
import com.techcareer.challenge.exception.BadRequestException;
import com.techcareer.challenge.exception.ResourceNotFoundException;
import com.techcareer.challenge.repository.RoleRepository;
import com.techcareer.challenge.service.RoleService;
import com.techcareer.challenge.utilities.mapper.ModelConverterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelConverterService converterService;

    @Override
    public boolean addRole(RoleDto roleDto) {
        try {
            RoleModel roleModel = converterService.convertToType(roleDto , RoleModel.class);
            roleRepository.save(roleModel);
            return true;
        }
        catch (BadRequestException exception){
            throw new BadRequestException("Bad request");
        }

    }

    @Override
    public RoleDto getRole(Integer roleId) {
        //fetch data from db or throw exception
        Optional<RoleModel> roleModel = Optional.ofNullable(roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found", null)));
        //map and return dto
        return converterService.convertToType(roleModel, RoleDto.class);

    }

    @Override
    public List<RoleDto> getAllRoles() {
        // fetch data from db . İf no data this should be empty list .
        List<RoleModel> roleModelList = roleRepository.findAll();
        return converterService.mapList(roleModelList , RoleDto.class);
    }

    @Override
    public void deleteRole(Integer roleId) {
        try {
            roleRepository.deleteById(roleId);
        }
        catch (ResourceNotFoundException exception){
            throw new ResourceNotFoundException("Role not found",exception.getCause());
        }
    }
}
