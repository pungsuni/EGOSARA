package com.sds.egosara.service;

import com.sds.egosara.dao.NDAO;
import com.sds.egosara.dao.SDAO;
import com.sds.egosara.dto.NOTICE;
import com.sds.egosara.dto.QNA;
import com.sds.egosara.dto.SMEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class NService {

    @Autowired
    private NDAO dao;

    @Autowired
    private SDAO sdao;

    private ModelAndView mav = new ModelAndView();

    // 공지사항 작성 메소드
    public ModelAndView nWrite(NOTICE notice) throws IOException {

        MultipartFile nPhoto = notice.getNPhoto();

        // 2. 원본 파일 이름 가져오기
        String originalFileName = nPhoto.getOriginalFilename();

        // 3. 랜덤한 문자열 만들기
        String uuid = UUID.randomUUID().toString().substring(1, 7);

        // 4. 3번(난수)와 2번(원본파일이름) 합치기!
        String nPhotoName = uuid + "_" + originalFileName;

        // 5. 파일 저장위치
        String savePath = "D:/springBootWorkspace/EGOSARA/src/main/resources/static/img/notice/" + nPhotoName;


        // 6. 파일 선택여부
        if (!nPhoto.isEmpty()) {
            notice.setNPhotoName(nPhotoName);
            nPhoto.transferTo(new File(savePath));
        }

        // Q. 입력, 수정, 삭제 시 필요한 데이터타입과 변수는?
        int result = dao.nWrite(notice);

        // 성공하면 result = 1, 실패하면 result = 0

        if (result > 0) {
            mav.setViewName("redirect:/nView?nNum=" + notice.getNNum());
        } else {
            mav.setViewName("index");
        }


        return mav;
    }


    // 공지사항 목록 메소드
    public ModelAndView nList() {

        List<NOTICE> noticeList = dao.nList();

        mav.setViewName("N_List");
        mav.addObject("noticeList", noticeList);

        System.out.println(mav);

        return mav;
    }

    // 공지사항 상세보기 메소드
    public ModelAndView nView(int nNum) {

        NOTICE notice = dao.nView(nNum);
        //        List<SMEMBER> smemberList = sdao.sList();

        if (notice != null) {
            mav.addObject("notice", notice);
            //        mav.addObject("smemberList", smemberList);
            mav.setViewName("N_View");
        } else {
            mav.setViewName("redirect:/nList");
        }

        return mav;
    }

    // 공지사항 수정페이지로 이동
    public ModelAndView nModiForm(int nNum) {

        NOTICE notice = dao.nView(nNum);

        if (notice != null) {
            mav.addObject("notice", notice);
            mav.setViewName("N_Modify");
        } else {
            mav.setViewName("redirect:/nList");
        }

        return mav;
    }

    // 공지사항 수정하기 메소드
    public ModelAndView nModify(NOTICE notice) throws IOException {

        // 1. 파일 불러오기
        MultipartFile nPhoto = notice.getNPhoto();

        // 2. 원본 파일 이름 가져오기
        String originalFileName = nPhoto.getOriginalFilename();

        // 3. 랜덤한 문자열 만들기
        String uuid = UUID.randomUUID().toString().substring(1, 7);

        // 4. 3번(난수)와 2번(원본파일이름) 합치기!
        String nPhotoName = uuid + "_" + originalFileName;

        // 5. 파일 저장위치
        String savePath = "D:/springBootWorkspace/EGOSARA/src/main/resources/static/img/notice/" + nPhotoName;

        // 6. 파일 선택여부
        if (!nPhoto.isEmpty()) {
            notice.setNPhotoName(nPhotoName);
            nPhoto.transferTo(new File(savePath));
        }

        // Q. 입력, 수정, 삭제 시 필요한 데이터타입과 변수는?
        int result = dao.nModify(notice);

        // 성공하면 result = 1, 실패하면 result = 0

        if (result > 0) {
            mav.setViewName("redirect:/nView?nNum=" + notice.getNNum());
        } else {
            mav.setViewName("redirect:/nModiForm?nNum=" + notice.getNNum());
        }

        return mav;
    }

    // 공지사항 삭제 메소드
    public ModelAndView nDelete(int nNum) {
        int result = dao.nDelete(nNum);

        if (result > 0) {
            mav.setViewName("redirect:/nList");
        } else {
            mav.setViewName("redirect:/nView?nNum=" + nNum);
        }
        return mav;

    }
}

