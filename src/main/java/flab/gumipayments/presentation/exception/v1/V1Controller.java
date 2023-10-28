package flab.gumipayments.presentation.exception.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ex/v1")
public class V1Controller {

    @GetMapping("/arg")
    public String illegalArgumentEx(){
        throw new IllegalArgumentException("올바르지 않은 auction입니다.");
    }


    @GetMapping("/dup")
    public String duplicateEx(){
        throw new DuplicateException("중복 auction입니다.");
    }
}
