package hello.login.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class MemberRepository {
    private static Map<Long,Member> store = new HashMap<>();
    private static long seq = 0L;

    public Member save(Member member){
        member.setId(++seq);
        log.info("save: member={}",member);
        store.put(member.getId(),member);
        return member;
    }

    public Member findById(Long id){
        return store.get(id);
    }

    public Optional<Member> findByLoginId(String loginId){
/*        List<Member> all = findAll();
        for(Member member : all){
            if(member.getLoginId().equals(loginId)){
                return Optional.of(member);
            }
        }
        return Optional.empty();*/
        return findAll().stream() // 리스트를 스트림으로 바꾼다
                .filter(m->m.getLoginId().equals(loginId)) // 해당 조건에 만족하는 것만 다음단계로 넘어간다
                .findFirst(); // 첫번째 만족한 것을 리턴한다
    }

    public List<Member> findAll(){
        return new ArrayList<>(store.values()); // key빼고 values만 반환된다
    }

    public void clearStore(){
        store.clear();
    }
}
