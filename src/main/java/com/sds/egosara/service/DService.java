package com.sds.egosara.service;

import com.sds.egosara.dao.DDAO;
import com.sds.egosara.dto.DIB;
import com.sds.egosara.dto.GOODS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class DService {
    @Autowired
    private DDAO dao;

    @Autowired
    private HttpSession session;

    private ModelAndView mav = new ModelAndView();

    // 찜 버튼 누르면 checkOR_N로 로그인한 아이디 값으로 데이터가 있는지 부터 확인
    public ModelAndView checkDIB(int dNum, String dId) {

        // 상품번호로 해당 테이블에 아이디 값을 리스트로 받아오기
        List <DIB> DIdList = dao.checkDIB(dNum);

        // 찜 가능, 불가능을 처리할 String 타입의 result 선언
        String result = "";

        // 받아온 리스트가 비어있는지 확인
        if (DIdList.isEmpty()) {
            // 리스트가 비어있다면
            // 상품번호에 해당하는 찜한 아이디 목록이 아예 없음 -> 찜 가능 (ooDIB 실행)
            result = "possible";
            // result 값을 session을 이용해서 해당 페이지로 값 넘기기 -> 이 값으로 버튼을 결정함
            session.setAttribute("result", result);
            mav.setViewName("G_View");
        } else {
            // 리스트가 비어있지 않다면
            // 상품번호에 해당하는 찜한 아이디 목록이 있음 -> 내 아이디가 있는지 확인해야함

            // 반복문을 이용해서 리스트 중 내 아이디가 있는 경우를 찾기
            for(int i=0 ; i<DIdList.size() ; i++) {
                // 조건문을 이용
                // if : 목록에서 내 아이디가 있는경우 -> 찜 불가능 (xxDIB 실행)
                if (dId.equals(DIdList.get(i))) {
                    result = "done";

                    session.setAttribute("result", result);
                    mav.setViewName("G_View");
                }
                // else : 목록에서 내 아이디가 없는 경우 -> 찜 가능 (ooDIB 실행)
                else {
                    System.out.println("찜 가능 : else : 목록에서 내 아이디가 없는 경우");

                    result = "possible";
                    System.out.println("result : " + result);

                    session.setAttribute("result", result);
                    mav.setViewName("G_View");
                }

            }

        }
        return mav;
    }

    // 비어있는 하트 버튼을 누르면 채워진 하트로 변경
    // redirect 를 이용해서 checkDIB를 통해  session.result를 변경할 것이기 때문에
    // 찜 버튼을 누르면 아이디랑 상품 번호를 가져와서 DIB테이블에 insert만 하면 됨
    public void ooDIB(DIB dib) {
        // 로그인한 아이디와 선택한 상품번호를 insert
        int result = dao.ooDIB(dib);
        if (result > 0) {
            mav.setViewName("redirect:/checkDIB?dNum=" + dib.getDNum() + "&dId=" + dib.getDId());
        }
    }

    // 채워진 하트 버튼을 누르면 채워진 하트로 변경
    // redirect 를 이용해서 checkDIB를 통해  session.result를 변경할 것이기 때문에
    // 찜 버튼을 누르면 아이디랑 상품 번호를 가져와서 DIB테이블에 delete만 하면 됨
    public void xxDIB(DIB dib) {
        // 로그인한 아이디와 선택한 상품번호를 delete
        int result = dao.xxDIB(dib);
        if (result > 0) {
            mav.setViewName("redirect:/checkDIB?dNum=" + dib.getDNum() + "&dId=" + dib.getDId());
        }
    }

    // 마이페이지 -> 내 찜 목록 : 내가 찜한 리스트 불러와서 보여주기
    public ModelAndView getDibList(String dId) {
        // 로그인 아이디와 DId가 같은 데이터를 리스트로 받아오기
        // 받아올 데이터 : 상품번호, 상품이름, 상품가격, 상품사진...... -> 그래서 Dib아니고 Goods로 받아오기
        List<GOODS> dibList = dao.getDibList(dId);

        mav.addObject("dibList", dibList);
        mav.setViewName("D_List");

        return mav;
    }

    // 내 찜 리스트에서 찜한 상품 삭제하기
    public ModelAndView dDelete(DIB dib) {
        int result = dao.dDelete(dib);
        if(result>0){
            mav.setViewName("redirect:/getDibList?dId=" + dib.getDId());
        }
        return mav;
    }
}