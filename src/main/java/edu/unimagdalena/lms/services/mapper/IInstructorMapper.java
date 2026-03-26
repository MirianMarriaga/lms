package edu.unimagdalena.lms.services.mapper;

import edu.unimagdalena.lms.api.dto.InstructorDtos.*;
import edu.unimagdalena.lms.entities.Instructor;
import edu.unimagdalena.lms.entities.InstructorProfile;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface IInstructorMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Instructor toEntity(InstructorCreateRequest req);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "instructor", ignore = true)
    InstructorProfile toEntity(InstructorProfileDto dto);

    @Mapping(target = "profile", source = "instructorProfile")
    InstructorResponse toResponse(Instructor entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patch(@MappingTarget Instructor target, InstructorUpdateRequest changes);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchProfile(@MappingTarget InstructorProfile target, InstructorProfileDto changes);

    // Post-patch para crear el profile si viene en null y el request lo trae
    @AfterMapping
    default void ensureProfile(@MappingTarget Instructor target, InstructorUpdateRequest changes) {
        if (changes != null && changes.profile() != null) {
            if (target.getInstructorProfile() == null) {
                target.setInstructorProfile(new InstructorProfile());
            }
            patchProfile(target.getInstructorProfile(), changes.profile());
        }
    }
}
