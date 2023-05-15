package kr.ac.kopo.ReadyToTravel.root;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
/// TODO: 2023-04-02 전부 수정해야합니다.

public class RootController {

    @RequestMapping("/test")
    public String tMapv2() {

        return "tmapTest2";
    }

    @RequestMapping("/weather")
    public String weather() {

        return "weather";
    }

    @RequestMapping("/list")
    public String list() {
        return "list";
    }

    @RequestMapping("/boardList")
    public String boardList() {
        return "boardList";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }


}