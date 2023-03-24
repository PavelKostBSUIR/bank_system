package com.vojtechruzicka.javafxweaverexample.mapper;

import com.vojtechruzicka.javafxweaverexample.entity.domain.Client;
import com.vojtechruzicka.javafxweaverexample.entity.dto.UpdateClientDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class ClientMapper {
    public abstract Client UpdateClientFromDto(UpdateClientDto dto, @MappingTarget Client entity);

    public abstract UpdateClientDto ClientToUpdateClientDto(Client client);
}
