package kr.ac.kopo.ReadyToTravel.member;

import kr.ac.kopo.ReadyToTravel.board.BoardService;
import kr.ac.kopo.ReadyToTravel.dto.BoardDTO;
import kr.ac.kopo.ReadyToTravel.dto.MemberDTO;
import kr.ac.kopo.ReadyToTravel.plan.PlanService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/member")
public class MemberController {
    final String path = "member/";

    private final MemberService service;
    private final BoardService boardService;
    private final PlanService planService;

    public MemberController(MemberService service, BoardService boardService, PlanService planService) {
        this.service = service;
        this.boardService = boardService;
        this.planService = planService;
    }

    @GetMapping("/checkId/{id}")
    @ResponseBody
    public String checkId(@PathVariable String id) {
        if(service.checkId(id)){
            return "OK";

        } else {

            return "FAIL";
        }
    }

    @RequestMapping("/removerMember")
    @ResponseBody
    public void delete(HttpServletRequest request) {

        MemberDTO dto = (MemberDTO) request.getSession().getAttribute("memberDTO");
        Long num = dto.getNum();

        service.removeMember(num);

    }

    @GetMapping("/initPassword")
    public String initPassword() {

        return path + "initPassword";
    }

    @ResponseBody
    @PostMapping("/initPassword")
    public String initPassword(String memberId, String email) throws MessagingException {

        if (service.initPass(memberId, email)) {
            return "SUCCESS";
        } else {
            return "FAIL";
        }
    }

    @GetMapping("/login")
    public String login() {
        return path + "login";
    }
    @PostMapping("/login")
    public String login(MemberDTO memberDTO, HttpSession session) {

        System.out.println("memberDTO = " + memberDTO.getMemberId());
        System.out.println("memberDTO = " + memberDTO.getPassword());

        MemberDTO login = service.login(memberDTO);

        if (login != null) {
            System.out.println("login ! ===== " + login);
            session.setAttribute("memberDTO", login);

            String targetUrl = (String) session.getAttribute("target_url");
            System.out.println("MemberController: " + targetUrl);
            session.removeAttribute("target_url");
            if (targetUrl == null) {
                return  "redirect:/";
            } else {
                return "redirect:" + targetUrl;
            }

        } else {
            return "redirect:" + path + "login";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("memberDTO");

        return "redirect:/";
    }
    @GetMapping("/signup")
    public String signup() {
        return path + "signup";
    }

    @PostMapping("/signup")
    public String signUp(MemberDTO memberDTO) {
        System.out.println(memberDTO);
        service.singUp(memberDTO);

        return "redirect:/" + path + "login";
    }

    @ResponseBody
    @GetMapping("/checkEmail/{email}")
    public String sendEmailCode(@PathVariable String email){
        if(service.sendEmailCode(email)){

            return "sendMailOK";
        }else{
            return "senMailFail" ;
        }
    }
    @ResponseBody
    @RequestMapping("/validateCode")
    public String validateCode(String email, String mailValidateCode){
        if(service.validateCode(email, mailValidateCode)){
            return "emailValidOK";
        }else {
            return "emailValidFAIL";

        }
    }
    @GetMapping("/myPage")
    public String myPage(/*Model model,*/@SessionAttribute MemberDTO memberDTO){
        /*

        List<BoardDTO> boardList = boardService.findAllByMemberId(memberDTO.getMemberId());
        model.addAttribute("boardList", boardList);

        List<PlanService> planList = planService.findAllByMemberId(memberDTO.getMemberId());
        model.addAttribute("planList", planList);
*/

        return path + "myPage";
    }
}
