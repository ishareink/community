package life.xnfxzypt.community.dto;

import java.util.ArrayList;
import java.util.List;

public class PaginationDTO {
    private List<QuestionDTO> questions;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private Integer totalPage;

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    private List<Integer> pages = new ArrayList<>();

    public boolean isShowPrevious() {
        return showPrevious;
    }

    public void setShowPrevious(boolean showPrevious) {
        this.showPrevious = showPrevious;
    }

    public boolean isShowFirstPage() {
        return showFirstPage;
    }

    public void setShowFirstPage(boolean showFirstPage) {
        this.showFirstPage = showFirstPage;
    }

    public boolean isShowNext() {
        return showNext;
    }

    public void setShowNext(boolean showNext) {
        this.showNext = showNext;
    }

    public boolean isShowEndPage() {
        return showEndPage;
    }

    public void setShowEndPage(boolean showEndPage) {
        this.showEndPage = showEndPage;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<Integer> getPages() {
        return pages;
    }

    public void setPages(List<Integer> pages) {
        this.pages = pages;
    }

    public List<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDTO> questions) {
        this.questions = questions;
    }

    public void setPagiation(Integer totalPage, Integer page) {
        this.totalPage=totalPage;
        this.page=page;

        pages.add(page);//当前页码
        for(int i=1;i<=3;i++){
            if(page-i>0){
                pages.add(0,page-i);//当前页码左边展示0~3个
            }
            if(page+i<=totalPage){
                pages.add(page+i);//当前页码右边展示0~3个
            }
        }
        //是否展示上一页
        if(page ==1){
            showPrevious = false;
        }else{
            showPrevious = true;
        }
        //是否展示下一页
        if(page == totalPage){
            showNext=false;
        }else{
            showNext=true;
        }
        //是否展示第一页
        if(pages.contains(1)){
            showFirstPage=false;
        }else{
            showFirstPage=true;
        }
        //是否包含最后一页
        if(pages.contains(totalPage)){
            showEndPage=false;
        }else{
            showEndPage=true;
        }
    }
}
