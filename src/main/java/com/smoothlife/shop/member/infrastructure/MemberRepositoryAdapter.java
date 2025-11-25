package com.smoothlife.shop.member.infrastructure;

import com.smoothlife.shop.member.domain.Member;
import com.smoothlife.shop.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryAdapter implements MemberRepository {
    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Page<Member> findAll(Pageable pageable) { return memberJpaRepository.findAll(pageable); }

    @Override
    public Optional<Member> findById(UUID id) { return Optional.empty(); }

    @Override
    public Member save(Member member) { return memberJpaRepository.save(member); }

    @Override
    public void deleteById(UUID id) { memberJpaRepository.deleteById(id); }
}
