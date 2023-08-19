package com.safejibsa.springbootdeveloper.old;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TestService {

    private final MemberRepository memberRepository;

    public List<Member> getAllMembers(){
        return memberRepository.findAll();
    }

    public void test(){
        //1) 생성(Create)
        memberRepository.save(new Member(1L, "A"));

        //2) 조회(Read)
        Optional<Member> member = memberRepository.findById(1L); //단건 조회
        List<Member> allMembers = memberRepository.findAll(); //전체 조회

        //3) 삭제(Delete)
        memberRepository.deleteById(1L);
    }
}
