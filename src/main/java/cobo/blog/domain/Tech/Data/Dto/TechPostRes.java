package cobo.blog.domain.Tech.Data.Dto;

import cobo.blog.global.Data.Entity.TechPostEntity;
import cobo.blog.global.Data.Entity.TechPostSkillTagMappingEntity;
import cobo.blog.global.Data.Entity.UserEntity;
import cobo.blog.global.Util.DateConversion;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TechPostRes {

    private UserInTechPostRes user;

    private Integer id;

    private String title;

    private String createdAt;

    private List<String> skillTag;

    private String content;

    public TechPostRes(TechPostEntity techPostEntity) {
        this.user = new UserInTechPostRes(techPostEntity.getUser());
        this.id = techPostEntity.getId();
        this.title = techPostEntity.getTitle();
        this.createdAt = DateConversion.DateToString(techPostEntity.getCreatedAt());
        this.content = techPostEntity.getContent();
        this.skillTag = new ArrayList<>();
        for(TechPostSkillTagMappingEntity techPostSkillTagMappingEntity : techPostEntity.getTechPostSkillTagMappings())
            this.skillTag.add(techPostSkillTagMappingEntity.getSkillTag().getName());
    }
}
