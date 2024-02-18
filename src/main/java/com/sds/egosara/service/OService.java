package com.sds.egosara.service;

import com.sds.egosara.dao.ODAO;
import com.sds.egosara.dto.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Log
@Service
public class OService {
    private static final String HOST = "https://kapi.kakao.com";

    private KakaoPayReadyVO kakaoPayReadyVO;

    @Autowired
    private ODAO dao;

    @Autowired
    private HttpSession session;

    private ModelAndView mav = new ModelAndView();

    // 주문하기
    public String orderExcu(String bId, List<Integer> bNum) {
        // 카카오페이 결제에서 필요한 함수
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());


        // 카카오페이 측으로 결제를 요청할때 꼭 필요한 데이터
        // -> 주문번호(난수로 생성), 주문자 아이디(BId와 동일), 선택한 상품의 상품이름, 선택한 상품 수량, 상품의 가격, 총 주문가격

        // 가져가는 데이터 : 로그인 아이디
        // 가져오는 데이터 : 장바구니에서 BSelect 가 y인 데이터의 상품가격, 선택수량 가져오기
        List<BASKET> basketList = dao.getBasketInfo(bId);

        // 주문번호로 이용할 문자열 생성하기 (주문날짜 + 난수)
        // 1. 난수 생성
        String uuid = UUID.randomUUID().toString().substring(1,7);

        // 2. 오늘 날짜를 Date() 함수에서 가져와서 SimpleDateFormat에 형식변환 후 가져오기
        Date date = new Date();
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyyMMdd");

        // 3. 난수와 올해년도 합치기 : 20210101_난수
        String oNum = sdformat.format(date) + "_" + uuid;

        // 총 결제가격 계산하기
        // 1. 총 결제가격인 OPrice를 int로 선언
        int oPrice = 0;

        // 2. 총 상품수량인 OTotalNum(데이터 베이스에는 없으나 결제 요청시 필요해서 만든 변수)
        int oTotalNum = 0;

        // 3. 가져온 basketList로 basket 객체에 정보 담아서 BNum 갯수만큼의 반복문 실행
        for (int i=0; i<basketList.size(); i++){
            // 선택제품 이름 : basketList.get(i).getBName()
            // 선택수량 : basketList.get(i).getBuyNo()

            // 4. 반복문 안에서 총 결제가격 계산하기
            oPrice = oPrice + (basketList.get(i).getBPrice() * basketList.get(i).getBuyNo());

            // 5. 총 상품 수량 계산하기
            oTotalNum = oTotalNum + basketList.get(i).getBuyNo();
        }

        // 서버로 요청할 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + "e172d11e14990db48ae9e1951cf77f2d"); // admin키 입력
        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

        // 서버로 요청할 Body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("cid", "TC0ONETIME");  // 사업자번호가 나오면 카카오페이와 제휴 맺어서 cid를 만들 수 있으나 사업자번호 없기때문에 테스트용 입력!
        params.add("partner_order_id", oNum);     // 주문번호
        params.add("partner_user_id", bId);    // 주문자 아이디(session.loginId = BId = OId)


        params.add("item_name", "[EgoSara]"+oNum);                    // [EgoSara]주문번호 로 값 넘기기
        params.add("quantity", Integer.toString(oTotalNum));          // 구매 수량(종류 상관없이 모든 상품구매 합계)
        params.add("total_amount", Integer.toString(oPrice));         // 총 가격
        params.add("tax_free_amount", "10");                          // 비과세 가격 -> 일단 고정,,,

        params.add("approval_url", "http://localhost:9091/kakaoPaySuccess?oNum=" + oNum + "&bId=" + bId);        // 결제 승인시 이동할 페이지
        params.add("cancel_url", "http://localhost:9091/kakaoPayFail");             // 결제 실패시 이동할 페이지
        params.add("fail_url", "http://localhost:9091/kakaoPaySuccessFail");        // 결제 취소시 이동할 페이지

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        try {
            kakaoPayReadyVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/ready"), body, KakaoPayReadyVO.class);
            log.info("" + kakaoPayReadyVO);
            return kakaoPayReadyVO.getNext_redirect_pc_url();

        } catch (RestClientException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return "/pay";
    }

    // bSelectList : 로그인 아이디로 장바구니 테이블에서 BSelect가 'y'인 정보 담아오기
    public ModelAndView bSelectList(String bId, String oNum) {

        // BId로 장바구니 테이블에서 BSelect가 'y'인 정보 담아오기
        List<BASKET> basList = dao.bSelectList(bId);


        // 1번 -> ORDERING 테이블에 데이터 INSERT
        // 1-1. ordering 객체 선언
        ORDERING ordering = new ORDERING();

        // 1-2. 총 결제 금액인 OPrice1 변수 만들기
        int oPrice1 = 0;

        for (int i=0; i< basList.size(); i++){
            // 총 결제금액 계산하기
            oPrice1 += ( basList.get(i).getBPrice()* basList.get(i).getBuyNo());
        }
        // 1-3. 변화 없이 일정한 값 : ONum(주문번호), OId(구매자 아이디), OPrice(주문 총 가격) <- 을 ordering에 넣기
        ordering.setOId(bId);
        ordering.setONum(oNum);
        ordering.setOPrice(oPrice1);

        // 1번 완료 후, 2번 실행

        // 2번 -> BASKET 테이블에서 테이터 DELETE
        // 2-1. basket 객체 선언
        BASKET basket1 = new BASKET();
        // 2-2. basket에 로그인 아이디 담기
        basket1.setBId(bId);

        for (int i=0; i< basList.size(); i++){
            // 1-4. 일정하지 않은 값 : OBNum(구매한 상품의 번호), OBuyNo(해당제품 구매수량) <- 을 ordering에 넣기
            ordering.setOBNum(basList.get(i).getBNum());
            ordering.setOBuyNo(basList.get(i).getBuyNo());

            // 반복문 안에서 insert실행 -> 상품 갯수만큼의 insert가 진행되어야함!
            // 1번 실행
            int result1 = dao.orderingEx(ordering);

            // 2번 -> BASKET테이블에 주문한 정보 DELETE
            // 2-3. basket에 상품번호 담기
            basket1.setBNum(basList.get(i).getBNum());

            // 2번 실행
            // 주문완료되면, 해당 상품번호와 아이디를 가져가서 장바구니에서 구매한 제품을 삭제 -> 상품번호마다 반복되어야 하므로 반복문 안에서 실행!
            if(result1 > 0){
                int result2 = dao.oBDelete(basket1);
            }

            // 3. 재고 처리
            // BStock - BuyNo = BStock 으로 재설정(update),,
            int result3 = dao.oStockUpdate(basList.get(i));

        }
        return mav;
    }

    // 주문내역 목록 만들기 (마이페이지 -> 주문내역)
    // 주문번호 불러오기
    public ModelAndView getOrderList(String oId) {
        // 중복되는 주문 번호 없이 가져오기 위해서
        // 가장 데이터가 적은 DIB테이블을 이용하기( 가져올때 이름만 빌리는 것 )
        List<DIB> aa = dao.getOrderList(oId);

        // O_List에 addObject로 넘기기!!!
        mav.addObject("aa", aa);
        mav.setViewName("O_List");

        return mav;
    }
    // 불러온 주문 번호를 가지고 데이터 넣기
    public List<OrderingList> orderInfo(String olNum) {
        // 가져가는 데이터 : OlNum

        // 가져오는 데이터
        // 상품번호,  상품종류, 상품이름, 상품판매자, 해당상품 주문 수량, 상품가격 , 상품이미지, 총 결제가격
        // BNum,    GType  , GName , GId     , OBuyNo         , GPrice  , GPhoto  , OPrice
        // OlBNum,  OlType , OlName, Olsaler , OlBuyNo        , OlPrice , OlPhoto , OltotalPrice

        // 주문번호로 정보들 가져오기
        List<OrderingList> orderList = dao.getBNum(olNum);

        mav.addObject("orderList", orderList);

        return orderList;
    }
}
