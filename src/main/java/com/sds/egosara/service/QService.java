package com.sds.egosara.service;

import com.sds.egosara.dao.QDAO;
import com.sds.egosara.dao.SDAO;
import com.sds.egosara.dto.QCOMMENT;
import com.sds.egosara.dto.QNA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class QService {

    @Autowired
    private QDAO dao;

    @Autowired
    private SDAO sdao;

    private ModelAndView mav = new ModelAndView();

    private static final int PAGE_LIMIT = 5;
    private static final int BLOCK_LIMIT = 5;

    // QNA 작성 메소드
    public ModelAndView qWrite(QNA qna) throws IOException {
        MultipartFile qPhoto = qna.getQPhoto();

        // 2. 원본 파일 이름 가져오기
        String originalFileName = qPhoto.getOriginalFilename();

        // 3. 랜덤한 문자열 만들기
        String uuid = UUID.randomUUID().toString().substring(1, 7);

        // 4. 3번(난수)와 2번(원본파일이름) 합치기!
        String qPhotoName = uuid + "_" + originalFileName;

        // 5. 파일 저장위치
        String savePath = "D:/springBootWorkspace/EGOSARA/src/main/resources/static/img/qna/" + qPhotoName;


        // 6. 파일 선택여부
        if (!qPhoto.isEmpty()) {
            qna.setQPhotoName(qPhotoName);
            qPhoto.transferTo(new File(savePath));
        }

        // Q. 입력, 수정, 삭제 시 필요한 데이터타입과 변수는?
        int result = dao.qWrite(qna);

        // 성공하면 result = 1, 실패하면 result = 0

        if (result > 0) {
            mav.setViewName("redirect:/qView?qNum=" + qna.getQNum());
        } else {
            mav.setViewName("redirect:/qWrite?qNum=" + qna.getQNum());
        }

        return mav;
    }

    // QNA 목록보기 메소드
    public ModelAndView qList(int page) {

        List<QNA> qnaList = dao.qList();
        List<QNA> qnaList1 = dao.qList1();

        mav.setViewName("Q_List");
        mav.addObject("qnaList", qnaList);
        mav.addObject("qnaList1", qnaList1);


        return mav;
    }


    // QNA 상세보기 메소드
    public ModelAndView qView(int qNum) {
        dao.qCount(qNum);

        QNA qna = dao.qView(qNum);
        //List<SMEMBER> smemberList = sdao.sList();

        if (qna != null) {
            mav.addObject("qna", qna);
            //mav.addObject("smemberList", smemberList);
            mav.setViewName("Q_View");
        } else {
            mav.setViewName("redirect:/qList");
        }

        return mav;

    }

    // QNA 수정하기 메소드
    public ModelAndView qModiForm(int qNum) {
        // 상세보기 때 만들어 놓은 qView(qNum)메소드 사용
        QNA qna = dao.qView(qNum);

        if (qna != null) {
            mav.addObject("qna", qna);
            mav.setViewName("Q_Modify");
        } else {
            mav.setViewName("redirect:/qList");
        }

        return mav;

    }

    // QNA 수정
    public ModelAndView qModify(QNA qna) throws IOException {
        // 1. 파일 불러오기
        MultipartFile qPhoto = qna.getQPhoto();

        // 2. 원본 파일 이름 가져오기
        String originalFileName = qPhoto.getOriginalFilename();

        // 3. 랜덤한 문자열 만들기
        String uuid = UUID.randomUUID().toString().substring(1, 7);

        // 4. 3번(난수)와 2번(원본파일이름) 합치기!
        String qPhotoName = uuid + "_" + originalFileName;

        // 5. 파일 저장위치
        String savePath = "D:/springBootWorkspace/EGOSARA/src/main/resources/static/img/qna/" + qPhotoName;

        // 6. 파일 선택여부
        if (!qPhoto.isEmpty()) {
            qna.setQPhotoName(qPhotoName);
            qPhoto.transferTo(new File(savePath));
        }

        // Q. 입력, 수정, 삭제 시 필요한 데이터타입과 변수는?
        int result = dao.qModify(qna);

        // 성공하면 result = 1, 실패하면 result = 0

        if (result > 0) {
            mav.setViewName("redirect:/qView?qNum=" + qna.getQNum());
        } else {
            mav.setViewName("redirect:/qModiForm?qNum=" + qna.getQNum());
        }

        return mav;
    }

    // QNA 삭제 메소드
    public ModelAndView qDelete(int qNum) {
        int result = dao.qDelete(qNum);

        if (result > 0) {
            mav.setViewName("redirect:/qList");
        } else {
            mav.setViewName("redirect:/qView?qNum=" + qNum);
        }
        return mav;
    }


    // 댓글 목록보기
    List<QCOMMENT> qcommentList = new ArrayList<QCOMMENT>();


    public List<QCOMMENT> qcList(int qcbNum) {
        List<QCOMMENT> qList = dao.qcList(qcbNum);


        return qList;
    }

    // 댓글 작성
    public List<QCOMMENT> qcWrite(QCOMMENT qcomment) {
        List<QCOMMENT> qList = null;
        int result = dao.qcWrite(qcomment);

        if (result > 0) {
            qList = dao.qcList(qcomment.getQcbNum());
        } else {
            qList = null;
        }

        return qList;

    }

    // 댓글 삭제
    public List<QCOMMENT> qcDelete(QCOMMENT qcomment) {
        List<QCOMMENT> qcommentList = null;
        int result = dao.qcDelete(qcomment);

        if (result > 0) {
            qcommentList = dao.qcList(qcomment.getQcbNum());
        } else {
            qcommentList = null;
        }

        return qcommentList;
    }


}





