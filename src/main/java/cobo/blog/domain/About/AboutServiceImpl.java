package cobo.blog.domain.About;

import cobo.blog.domain.About.Data.Dto.AboutMemberRes;
import cobo.blog.global.Data.Entity.UserEntity;
import cobo.blog.global.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AboutServiceImpl {

    private final UserRepository userRepository;

    public ResponseEntity<List<AboutMemberRes>> getMembers() {
        List<AboutMemberRes> aboutMemberRes = new ArrayList<>();
        for(UserEntity userEntity : userRepository.findAll())
            aboutMemberRes.add(new AboutMemberRes(userEntity));
        return new ResponseEntity<>(aboutMemberRes, HttpStatus.OK);
    }
}
