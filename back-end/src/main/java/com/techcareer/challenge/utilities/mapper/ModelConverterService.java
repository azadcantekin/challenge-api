package com.techcareer.challenge.utilities.mapper;


import java.util.List;

public interface ModelConverterService {
    <R> R convertToType(Object source, Class<R> resultClass);
    <S, T> List<T> mapList(List<S> source, Class<T> targetClass);

}