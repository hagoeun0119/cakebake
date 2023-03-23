package springjpasideproject.cakebake.security;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import springjpasideproject.cakebake.domain.Member;
import springjpasideproject.cakebake.domain.repository.MemberRepository;
import springjpasideproject.cakebake.security.dto.MemberSecurityDto;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("loadUserByUsername: " + username);

        Optional<Member> result = memberRepository.findByUserId(username);

        if (result.isEmpty()) {
            throw new UsernameNotFoundException("username not found...");
        }

        Member member = result.get();

        MemberSecurityDto memberSecurityDto = new MemberSecurityDto(
                member.getUserId(),
                member.getPassword(),
                member.getRoleSet()
                        .stream().map(memberRole -> new SimpleGrantedAuthority("ROLE_" + memberRole.name()))
                        .collect(Collectors.toList())
        );

        return memberSecurityDto;
    }
}
