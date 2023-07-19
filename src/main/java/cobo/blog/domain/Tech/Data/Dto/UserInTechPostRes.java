package cobo.blog.domain.Tech.Data.Dto;

import cobo.blog.global.Data.Entity.UserEntity;
import lombok.Data;

@Data
public class UserInTechPostRes {

    private String name;
    private String description;

    public UserInTechPostRes(UserEntity userEntity){
        this.name = userEntity.getName();
        this.description = userEntity.getDescription();
    }
}
