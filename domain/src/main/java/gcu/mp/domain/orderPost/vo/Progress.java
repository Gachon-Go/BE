package gcu.mp.domain.orderPost.vo;

public enum Progress {
    ING("진행중"),
    DONE("모집완료");
    final private String name;

    Progress(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
