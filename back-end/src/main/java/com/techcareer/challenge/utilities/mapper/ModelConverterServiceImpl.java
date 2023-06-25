package com.techcareer.challenge.utilities.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ModelConverterServiceImpl implements ModelConverterService{

    @Autowired
    private final ModelMapper modelMapper;


    public <R> R convertToType(Object source, Class<R> resultClass) {
        return modelMapper.map(source, resultClass);
    }

    public <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }
}