package kr.ac.kopo.ReadyToTravel.member;

import kr.ac.kopo.ReadyToTravel.dto.MemberDTO;
import kr.ac.kopo.ReadyToTravel.util.FileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService service;

    public MemberController(MemberService service) {
        this.service = service;
    }

    @RequestMapping("/signup")
    public String signUp(MemberDTO memberDTO, @RequestParam("file") MultipartFile file) {

        String fileName = FileUpload.fileUpload(file);
        memberDTO.setProfileIMG(fileName);

        service.singUp(memberDTO);

        return "index";
    }

    @RequestMapping("/checkId/{id}")
    @ResponseBody
    public String checkId(@PathVariable String id) {

        return service.checkId(id);
    }

    @RequestMapping("/removerMember")
    @ResponseBody
    public void delete(HttpServletRequest request) {
        MemberDTO dto = (MemberDTO) request.getSession().getAttribute("memberDTO");
        Long num = dto.getNum();

        service.removeMember(num);

    }


    @GetMapping("/initPassword")
    public String initPassword(){
        return "initPassword";
    }

    @PostMapping("/initPassword")
    @ResponseBody
    public String initPassword(@RequestBody String email) throws MessagingException {

        if (service.initPass(email)) {
            return "OK";
        } else {
            return "FAIL";
        }
    }

    @PostMapping("/login")
    public String login(MemberDTO memberDTO,HttpSession session) {
        if (service.login(memberDTO)) {
            session.setAttribute("memberDTO", memberDTO);
            return "index";
        } else {
            return "member/login";
        }
    }
}
