package caps.testing.controller;

import caps.testing.domain.Member;
import caps.testing.dto.member.ManagerSignUpDto;
import caps.testing.dto.member.MemberSignInRequestDto;
import caps.testing.dto.member.MemberSignUpRequestDto;
import caps.testing.dto.token.TokenResponseDto;
import caps.testing.repository.MemberRepository;
import caps.testing.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @PostMapping(value = "/api/signup/worker", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Long signUp_worker(@RequestBody @Valid MemberSignUpRequestDto memberSignUpRequestDto){
        return memberService.join_worker(memberSignUpRequestDto);
    }

    @PostMapping(value = "/api/signup/manager", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Long singUp_manger(@RequestBody ManagerSignUpDto managerSignUpDto) throws Exception {
        return memberService.join_manager(managerSignUpDto);
    }

    @PostMapping(value = "/api/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Member login(@RequestBody @Valid MemberSignInRequestDto requestDto) {
//        String token = memberService.login(requestDto);
        Member member = memberService.login2(requestDto);
//        Map<String, Member> map = new HashMap<>();
//        map.put(token, member);
        return member;
    }

//    @GetMapping(value = "/api/success", consumes = MediaType.APPLICATION_JSON_VALUE)
//    public Member loginSuccess(@RequestBody @Valid MemberSignInRequestDto requestDto){
//        Optional<Member> member = memberRepository.findByEmail(requestDto.getEmail());
//        return member.get();
//    }

    @GetMapping("/members")
    public List<Member> list(Model model){
        List<Member> members = memberService.findMembers();
        return members;
    }

    @GetMapping("/api/member/{member_id}")
    public List<Member> getMemberByTeam(@PathVariable("member_id") Long member_id){
        Long myTeamId = memberService.findMyTeamId(member_id);
        List<Member> members = memberService.findAllMyTeam(myTeamId);
        return members;
    }

    @GetMapping("api/member/my/{member_id}")
    public Member getAllMemberInfo(@PathVariable("member_id") Long member_id){
        Member myInfo = memberService.findMyInfo(member_id);
        return myInfo;
    }

    @GetMapping("api/member/myteam/{member_id}")
    public Long getMyTeamId(@PathVariable("member_id") Long member_id){
        Long myTeamId = memberService.findMyTeamId(member_id);
        return myTeamId;
    }

}
