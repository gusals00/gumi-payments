package flab.gumipayments.presentation.exception.v2;

import flab.gumipayments.presentation.exception.v2.error_code.AuctionErrorCode;
import flab.gumipayments.presentation.exception.v2.error_code.CategoryErrorCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ex/v2")
public class V2Controller {

    @GetMapping("/auction")
    public String ex1() {
        throw new CustomException(AuctionErrorCode.ALREADY_AUCTION_TRANS_COMPLETE);
        // ALREADY_AUCTION_TRANS_COMPLETE(HttpStatus.BAD_REQUEST, "거래 확정이 이미 종료되었습니다."),
    }

    @GetMapping("/category")
    public String ex2() {
        throw new CustomException(CategoryErrorCode.CATEGORY_NOT_FOUND);
        //     CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 카테고리를 찾을 수 없습니다.");
    }
}
