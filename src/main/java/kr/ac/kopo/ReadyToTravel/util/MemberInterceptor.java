package kr.ac.kopo.ReadyToTravel.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.ac.kopo.ReadyToTravel.dto.MemberDTO;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

public class MemberInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession();
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("memberDTO");

        if (memberDTO != null) {
            // 세션에 저장된 memberDTO를 컨트롤러 메서드의 파라미터로 주입하기 위해 설정합니다.
            request.setAttribute("memberDTO", memberDTO);
            return true;
        } else {
            // 로그인 페이지로 리다이렉트합니다.
            String query = request.getQueryString();
            session.setAttribute("target_url", request.getRequestURI() + (query != null ? "?" + query : "") );
            System.out.println("INTERCEPTOR: " + session.getAttribute("target_url"));

            response.sendRedirect("/member/login");

            System.out.println("UserInterceptor: FALSE");
            return false;
        }
    }

    // 현재 요청의 전체 URL을 반환하는 메서드
    private String getFullURL(HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        String queryString = request.getQueryString();

        if (queryString == null) {
            return requestURL.toString();
        } else {
            return requestURL.append('?').append(queryString).toString();
        }
    }
}
