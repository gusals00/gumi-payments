PaymentWidget.ANONYMOUS = "ANONYMOUS";

function PaymentWidget(clientKey, customerKey) {
    return new Widget(clientKey, customerKey);
}

function Widget(clientKey, customerKey) {
    this.clientKey = clientKey;
    this.payment = new Payment(customerKey, clientKey);
    this.method = new Method(this.payment);
    this.agreement = new Agreement();
    this.successUrl;
    this.failUrl;

    this.renderPaymentMethods = function (methodDiv, cost, variant) {
        this.method.render(methodDiv, variant.variantKey, cost.value);
        return this.method;
    }

    this.renderAgreement = function (agreementDiv, variant) {
        this.agreement.agreement(variant.variantKey, agreementDiv);
    }

    this.requestPayment = function (paymentRequest) {
        this.successUrl = paymentRequest.successUrl;
        this.failUrl = paymentRequest.failUrl;
        this.payment.request(paymentRequest);
    }

    this.print = function () {
        this.payment.print();
        this.method.print();
    }
}

function Method(payment) {
    this.variantKey;
    this.payment = payment;

    this.render = function (methodDiv, variantKey, amount) {
        this.payment.changeCost(amount)
        console.log("Payment methods are being rendered...");
        this.variantKey = variantKey;
        document.querySelector(methodDiv).style.height = "300px";
        document.querySelector(methodDiv).innerHTML += '<div id="gumi-method-name" style="text-align: center;">결제 수단<div>';
        document.querySelector(methodDiv).innerHTML +=
            '<div id="gumi-method-contents" style="width: 250px; display: flex; flex-direction: row; flex-wrap: wrap; justify-content: space-between; align-content: center; padding: 30px;">' +
            '<div class="method-content" id="card" onclick="showCardSelect()" style="height: 30px; margin: auto; border: 1px solid #4e5968; padding: 10px;">신용/체크카드</div>' +
            '<div class="method-content" id="kakao" onclick="closeCardSelect()" style="height: 30px; margin: auto; border: 1px solid #4e5968; padding: 10px;">카카오 페이</div>' +
            '</div>' +
            '<div id="card-select-box" style="width: 250px; display: flex; flex-direction: row; flex-wrap: wrap; justify-content: space-between; align-content: center; padding: 30px; display: none">' +
            '<select id="card-name" aria-invalid="false" aria-label="카드사 선택" id="input-:r15:">' +
            '<option class="" disabled="" value="">카드사 선택</option>' +
            '<option value="CARD__SHINHAN">신한</option>' +
            '<option value="CARD__HYUNDAI">현대</option>' +
            '</select>' +
            '<select id="installment" aria-invalid="false" aria-label="할부 선택" id="input-:rh:">' +
            '<option class="" disabled="" value="">할부 선택</option>' +
            '<option value="0">일시불</option>' +
            '<option value=“2”>2개월</option>' +
            '<option value=“3”>3개월</option>' +
            '<option value=“4”>4개월</option>' +
            '<option value=“5”>5개월</option>' +
            '<option value=“6”>6개월</option>' +
            '</select>' +
            '</div>';
    };

    this.print = function () {
        console.log("method");
        console.log("variantKey :" + this.variantKey);
    }

    this.updateAmount = function (amount) {
        this.payment.changeCost(amount);
    }
}

function Agreement() {
    this.variantKey;
    this.div;
    this.agreement = function (variantKey, div) {
        this.variantKey = variantKey;
        this.div = div;
        this.showAgreement(this.div);
    }

    this.showAgreement = function (agreementDiv) {
        document.querySelector(agreementDiv).innerHTML +=
            '<div className="agreement" style="padding-left: 25px; margin-bottom: 5px;">' +
            '<label  className="agreement-label">' +
            '<input id="agreement-coupon-box" type="checkbox" aria-checked="true"/>' +
            '<span id ="agreement-content">[필수] 결제 서비스 이용 약관, 개인정보 처리 동의</span>' +
            '<span  onclick="agreementShow()">[보기]</span>' +
            '</label>' +
            '</div>'
    }
}

function Payment(customerKey,clientKey) {

    this.clientKey = clientKey;
    this.customerKey  = customerKey;
    this.amount;
    this.orderId;
    this.orderName;
    this.customerEmail;
    this.customerName;
    this.phoneNumber;

    this.changeCost = function (amount) {
        this.amount = amount;
    }

    this.print = function () {
        console.log("payment");
        console.log("amount :" + this.amount + "clientKey" + this.clientKey);
    }

    this.request = function (paymentRequest){
        this.orderId = paymentRequest.orderId;
        this.orderName = paymentRequest.orderName;
        this.customerEmail = paymentRequest.customerEmail;
        this.customerName = paymentRequest.customerName;
        this.phoneNumber = paymentRequest.phoneNumber;

        // api 요청 날리기
        console.log("결제 요청 api 호출");
    }
}

function agreementShow() {
    var win = window.open("", "PopupWin", "width=500,height=600");
    win.document.write(
        `<article className="p-post text--left css-v0j40w"><h3>전자금융거래 기본약관(이용자용)</h3>` +
        `<h4>제1조 (목적)</h4>` +
        `<p>이 약관은 전자지급결제대행서비스 및 결제대금예치서비스를 제공하는 토스페이먼츠 주식회사(이하 '회사'라 합니다)와 이용자 사이의 전자금융거래에 관한 기본적인 사항을 정함으로써 전자금융거래의 안정성과 신뢰성을 확보함에 그 목적이 있습니다.</p>` +
        `<h4>제2조 (용어의 정의)</h4>` +
        `<p>이 약관에서 정하는 용어의 정의는 다음과 같습니다.</p>` +
        `<p>① 전자금융거래'라 함은 회사가 전자적 장치를 통하여 전자지급결제대행서비스 및 결제대금예치서비스(이하 '전자금융거래 서비스'라고 합니다)를 제공하고, 이용자가 회사의 구성원과 직접 대면하거나 의사소통을 하지 아니하고 전산화된 방식으로 이를 이용하는 거래를 말합니다.</p>` +
        `<p>② '전자지급결제대행 서비스'라 함은 전자적 방법으로 재화의 구입 또는 용역의 이용에 있어서 지급결제정보를 송신하거나 수신하는 것 또는 그 대가의 정산을 대행하거나 매개하는 서비스를 말합니다.</p>` +
        `<p>③ ‘결제대금예치서비스'라 함은 이용자가 재화의 구입 또는 용역의 이용에 있어서 그 대가(이하 '결제대금'이라 한다)의 전부 또는 일부를 재화 또는 용역(이하 '재화 등'이라 합니다)을 공급받기 전에 미리 지급하는 경우, 회사가 이용자의 물품수령 또는 서비스 이용 확인 시점까지 결제대금을 예치하는 서비스를 말합니다.</p>` +
        `<p>④ '이용자'라 함은 이 약관에 동의하고 회사가 제공하는 전자지급결제대행 서비스를 이용하는 자를 말합니다.</p>` +
        `</article>`);
}

// 카드사 선택
function showCardSelect() {
    document.querySelector("#card-select-box").style.display = "block";
}

function closeCardSelect() {
    document.querySelector("#card-select-box").style.display = "none";

}