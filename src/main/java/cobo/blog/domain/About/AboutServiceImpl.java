package cobo.blog.domain.About;

import cobo.blog.domain.About.Data.Dto.MemberRes;
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

    public ResponseEntity<List<MemberRes>> getMembers() {
        List<MemberRes> memberRes = new ArrayList<>();
        for(UserEntity userEntity : userRepository.findAll())
            memberRes.add(new MemberRes(userEntity));
        return new ResponseEntity<>(memberRes, HttpStatus.OK);
    }
}
