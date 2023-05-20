package gcu.mp.domain.deliveryPost.vo;

public enum ProgressState {
    ING("진행중"),
    DONE("거래 종료"),
    CANCEL("거래 취소");
    final private String name;

    ProgressState(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
