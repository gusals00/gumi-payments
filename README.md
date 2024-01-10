# gumi-payments
- **카카오페이 , 신용카드 결제 등 온라인 결제서비스를 쉽고 빠르게 연동하기 위한 api 서버입니다.**
- 토스 페이먼츠를 참고하여 만든 SaaS입니다.
- 실제 카드사 인증, 카드사에 결제 승인과 같은 부분은 mocking하여 실제 결제가 이루어지지는 않습니다.

# 프로젝트 구성
- 기간 : 23.10 ~ (진행중)
- 주요 스택 : spring, jpa, mysql
- erd : wiki 문서

# branch 전략
## 트렁크 기반 개발
### git flow 대신 트렁크 기반 개발을 선택한 이유는?
- 트렁크 기반 전략은 짧은 주기로 merge 하기 때문에 merge할 때 충돌이 적을 것이라 판단했고, 여러 feature 브랜치에서 공통적으로 사용하는 기능을 먼저 작업하여 merge하면 다른 feature 브랜치에서 이를 사용하여 빠르게 작업할 수 있다고 생각했기 때문
### git flow와 트렁크 기반 비교

<br>

# 각종 문서
- 유스케이스 다이어그램, 테스크, eventstorming, 협력 다이어그램은 [wiki](https://github.com/gusals00/gumi-payments/wiki) 참고
