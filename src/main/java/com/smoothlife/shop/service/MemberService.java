package com.smoothlife.shop.service;

import com.smoothlife.shop.common.ResponseEntity;
import com.smoothlife.shop.member.Member;
import com.smoothlife.shop.member.MemberRepository;
import com.smoothlife.shop.member.MemberRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public ResponseEntity<List<Member>> findAll() {
        return new ResponseEntity<>(HttpStatus.OK.value(), memberRepository.findAll(), memberRepository.count());
    }

    public ResponseEntity<Member> create(MemberRequest request) {
        Member member = new Member(
                UUID.randomUUID(),
                request.email(),
                request.name(),
                request.password(),
                request.phone(),
                request.saltKey(),
                request.flag()
        );
        Member member1 = memberRepository.save(member);
        int cnt = 0;
        if(member1 instanceof List) {
            cnt = ((List<?>) member1).size();
        } else {
            cnt = 1;
        }
        return new ResponseEntity<>(HttpStatus.OK.value(), member1, cnt);
    }

    public ResponseEntity<Member> update(MemberRequest request, @PathVariable String id) {
        Member member = new Member(
                id,
                request.email(),
                request.name(),
                request.password(),
                request.phone(),
                request.saltKey(),
                request.flag()
        );
        return new ResponseEntity<>(HttpStatus.OK.value(), memberRepository.save(member), memberRepository.count());
    }

    public ResponseEntity<?> delete(@PathVariable String id) {
        memberRepository.deleteById(UUID.fromString(id));
        return null;
    }
}
