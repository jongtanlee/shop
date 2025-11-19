package com.smoothlife.shop.controller;

import com.smoothlife.shop.common.ResponseEntity;
import com.smoothlife.shop.member.Member;
import com.smoothlife.shop.member.MemberRepository;
import com.smoothlife.shop.member.MemberRequest;
import com.smoothlife.shop.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("${api.v1}/members")
public class MemberController {
    @Autowired
    //private MemberRepository memberRepository;
    private MemberService memberService;

    // private final MemberRepository memberRepository; @Autowired 사용 안할 때

    // public MemberController(MemberRepository memberRepository) {this.memberRepository = memberRepository; } @Autowired 사용 안할 때

    @Operation(
            summary = "회원 목록 조회",
            description = "public.member 테이블에 저장된 모든 회원을 조회한다."
    )
    /*@GetMapping
    public List<Member> findAll() {
        return memberRepository.findAll();
    }*/
    @GetMapping
    // public List<Member> findAll() {

    public ResponseEntity<List<Member>> findAll() {
        return memberService.findAll();
        // return new ResponseEntity<>(HttpStatus.OK.value(), memberRepository.findAll(), memberRepository.count()); 서비스에서 처리되도록 하여 제외
    }

    @Operation(
            summary = "회원 등록",
            description = "요청으로 받은 회원 정보를 public.member 테이블에 저장한다."
    )
    @PostMapping
    /*public Member create(@RequestBody MemberRequest request) {
        Member member = new Member(
                UUID.randomUUID(),
                request.email(),
                request.name(),
                request.password(),
                request.phone(),
                request.saltKey(),
                request.flag()
        );
        return memberRepository.save(member);
    }*/

    public ResponseEntity<Member> create(@RequestBody MemberRequest request) {
        /*Member member = new Member(
                UUID.randomUUID(),
                request.email(),
                request.name(),
                request.password(),
                request.phone(),
                request.saltKey(),
                request.flag()
        );
        Member member1 = memberRepository.save(member);
        //AtomicInteger cnt = new AtomicInteger();
        int cnt = 0;
        if(member1 instanceof List) {
            //cnt.set(((List<?>) member1).size());
            cnt = ((List<?>) member1).size();
        } else {
            //cnt.set(1);
            cnt = 1;
        }
        //return new ResponseEntity<>(HttpStatus.OK.value(), member1, cnt.get());
        return new ResponseEntity<>(HttpStatus.OK.value(), member1, cnt); 서비스에서 처리되도록 하여 제외*/
        return memberService.create(request);
    }

    @Operation(
            summary = "회원 수정",
            description = "요청으로 받은 회원 정보를 public.member 테이블에 수정한다."
    )
    @PutMapping("{id}")
    /*public Member update(@RequestBody MemberRequest request, @PathVariable String id) {
        Member member = new Member(
                id,
                request.email(),
                request.name(),
                request.password(),
                request.phone(),
                request.saltKey(),
                request.flag()
        );
        return memberRepository.save(member);
    } 서비스에서 처리하도록하여 제외*/

    public ResponseEntity<Member> update(MemberRequest request,  @PathVariable String id) {
       return memberService.update(request,id);
    }

    @Operation(
            summary = "회원 정보 삭제",
            description = "요청으로 받은 회원 정보를 public.member 테이블에서 삭제한다."
    )
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        //memberRepository.deleteById(UUID.fromString(id)); 서비스에서 처리하도록 하여 제외
        memberService.delete(id);
    }
}
